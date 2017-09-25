package com.recluse.base.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;

import com.recluse.base.utils.SystemUtils;
import com.recluse.base.utils.ViewsUtils;
import com.recluse.base.view.widget.ijk_media.IMediaController;
import com.recluse.base.view.widget.ijk_media.IjkVideoView;
import com.recluse.oclient.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public abstract class VideoPlayerView extends FrameLayout implements IMediaController, VideoPlayerListener {

    private static final String TAG = "VideoPlayerView";

    Unbinder mUnbinder;
    protected Context mContext;

    @BindView(R.id.ijk_video_view)
    protected IjkVideoView mVideoView;
    protected View mCoverView;

    protected boolean isShowing;
    protected MediaController.MediaPlayerControl mPlayerControl;

    private Timer mTimer;

    public VideoPlayerView(Context context) {
        super(context);
        init(context, null);
    }

    public VideoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        mContext = context;
        inflate(context, R.layout.video_player_view_layout, this);
        FrameLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mCoverView = getCoverView();
        super.addView(mCoverView, params);
        mUnbinder = ButterKnife.bind(this);

        initVideoView();
    }

    private void initVideoView() {
        mVideoView.setMediaController(this);
        mVideoView.setPlayerListener(this);
    }

    public void setVideoUrl(String url) {
        mVideoView.setVideoPath(url);
    }

    public void start() {
        mVideoView.start();
    }

    public void start(String url) {
        mVideoView.setVideoPath(url);
        mVideoView.start();
    }

    public void stop() {
        mVideoView.stopPlayback();
        mVideoView.release(true);
        cancelHideRunnable();
        stopProgressTask();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mVideoView != null) {
            mVideoView.release(true);
        }
        stopProgressTask();
        cancelHideRunnable();
    }

    protected abstract View getCoverView();

    @Override
    public void hide() {
        isShowing = false;
        ViewsUtils.setViewVisibility(mCoverView, View.GONE);
        cancelHideRunnable();
    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void setAnchorView(View view) {

    }

    @Override
    public void setMediaPlayer(MediaController.MediaPlayerControl player) {
        mPlayerControl = player;
    }

    @Override
    public void show(int timeout) {
        isShowing = true;
        Log.d(TAG, "show: timeout: " + timeout);
    }

    @Override
    public void show() {
        isShowing = true;
        ViewsUtils.setViewVisibility(mCoverView, View.VISIBLE);
        startHideRunnable();
    }

    @Override
    public void showOnce(View view) {
        Log.d(TAG, "showOnce: ");
    }

    @Override
    public void onPrepared(IMediaPlayer player) {
        startProgressTask();
        startHideRunnable();
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer mp, int percent) {

    }

    @Override
    public void onComplete(IMediaPlayer player) {
        stopProgressTask();
        cancelHideRunnable();
    }

    @Override
    public void onError(IMediaPlayer player, int frameErr, int implErr) {
        stopProgressTask();
    }

    public abstract void onProgressUpdate(int progress);

    private void startProgressTask() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mProgressTask == null) {
            mProgressTask = new ProgressTask();
        }

        mTimer.schedule(mProgressTask, 200, 1000);
    }

    private void stopProgressTask() {
        if (mProgressTask != null) {
            mProgressTask.cancel();
        }
        if (mTimer != null) {
            mTimer.cancel();
        }
        mProgressTask = null;
        mTimer = null;
    }

    protected void startHideRunnable() {
        if (mHideRunnable == null) {
            mHideRunnable = new HideRunnable();
        }
        SystemUtils.runOnUIThread(mHideRunnable, 5000);
    }

    protected void cancelHideRunnable() {
        if (mHideRunnable != null) {
            SystemUtils.cancelRunnable(mHideRunnable);
        }
    }

    private TimerTask mProgressTask;

    private class ProgressTask extends TimerTask {
        @Override
        public void run() {
            if (mVideoView != null) {
                onProgressUpdate(mPlayerControl.getCurrentPosition());
            }
        }
    }

    private Runnable mHideRunnable;

    private class HideRunnable implements Runnable {
        @Override
        public void run() {
            hide();
        }
    }

}
