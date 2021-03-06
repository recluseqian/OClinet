package com.recluse.oclient.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.recluse.base.utils.TimerUtils;
import com.recluse.base.utils.ViewsUtils;
import com.recluse.base.view.widget.VideoPlayerView;
import com.recluse.oclient.R;

import butterknife.BindView;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class OCPlayerView extends VideoPlayerView implements SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "OCPlayerView";

    private static int SEEK_BAR_STATE_IDEAL = 0;
    private static int SEEK_BAR_STATE_DRAGGING = 1;

    @BindView(R.id.video_top_cover_layout)
    View mTopCoverLayout;
    @BindView(R.id.video_bottom_cover_layout)
    View mBottomCoverLayout;
    @BindView(R.id.player_seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.player_fore_ground_icon)
    View mPlayerForegroundView;
    @BindView(R.id.player_control_icon)
    ImageView mPlayerControlView;
    @BindView(R.id.player_progress_text)
    TextView mProgressTextView;
    @BindView(R.id.player_duration_text)
    TextView mDurationTextView;
    @BindView(R.id.loading_view)
    LoadingView mLoadingView;

    private int mDuration;
    private int mSeekBarState = SEEK_BAR_STATE_IDEAL;

    public OCPlayerView(Context context) {
        super(context);
    }

    public OCPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getCoverView() {
        return LayoutInflater.from(mContext).inflate(R.layout.player_cover_view_layout, this, false);
    }

    @Override
    public void show() {
        ViewsUtils.setViewVisibility(mTopCoverLayout, View.VISIBLE);
        ViewsUtils.setViewVisibility(mBottomCoverLayout, View.VISIBLE);
    }

    @Override
    public void hide() {
        ViewsUtils.setViewVisibility(mTopCoverLayout, View.GONE);
        ViewsUtils.setViewVisibility(mBottomCoverLayout, View.GONE);
    }

    public void restart(String url) {
        super.stop();
        ViewsUtils.setViewVisibility(mPlayerForegroundView, View.VISIBLE);
        ViewsUtils.setViewVisibility(mLoadingView, View.VISIBLE);
        start(url);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onPrepared(IMediaPlayer player) {
        super.onPrepared(player);
        ViewsUtils.setViewVisibility(mPlayerForegroundView, View.GONE);
        ViewsUtils.setViewVisibility(mLoadingView, View.GONE);
        mPlayerControlView.setImageResource(R.drawable.player_pause_icon);
        mDuration = mPlayerControl.getDuration();
        if (mDuration > 0) {
            mDurationTextView.setText(TimerUtils.getDurationString(mDuration));
            mSeekBar.setMax(mDuration);
        }
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer mp, int percent) {
        super.onBufferingUpdate(mp, percent);
        mSeekBar.setSecondaryProgress((int) (percent / 100.0f * mDuration));
    }

    @Override
    public void onProgressUpdate(int progress) {
        if (mSeekBarState == SEEK_BAR_STATE_IDEAL) {
            mSeekBar.setProgress(progress);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mProgressTextView.setText(TimerUtils.getDurationString(i));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mSeekBarState = SEEK_BAR_STATE_DRAGGING;
        cancelHideRunnable();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mPlayerControl.seekTo(seekBar.getProgress());
        mSeekBarState = SEEK_BAR_STATE_IDEAL;
        startHideRunnable();
    }

    @OnClick(R.id.player_control_icon)
    public void onPlayerControlClick() {
        if (mPlayerControl.isPlaying()) {
            mVideoView.pause();
            mPlayerControlView.setImageResource(R.drawable.player_play_icon);
        } else {
            mVideoView.start();
            mPlayerControlView.setImageResource(R.drawable.player_pause_icon);
        }
    }
}
