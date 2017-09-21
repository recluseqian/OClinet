package com.recluse.oclient.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.view.fragment.BaseListFragment;
import com.recluse.base.view.widget.ijk_media.IjkVideoView;
import com.recluse.oclient.R;
import com.recluse.oclient.data.VideoInfo;
import com.recluse.oclient.presenter.VideoDetailPresenter;
import com.recluse.oclient.ui.listview.widget.OCPlayerView;

import butterknife.BindView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by recluse on 17-9-19.
 */

public class VideoDetailFragment extends BaseListFragment<VideoInfo> {

    private static final String TAG = "VideoDetailFragment";

    public static final String URL = "http://mov.bn.netease.com/open-movie/nos/mp4/2014/04/22/S9PH2JA01_sd.mp4";

    public static VideoDetailFragment newInstance(Bundle bundle) {
        VideoDetailFragment fragment = new VideoDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.video_view)
    OCPlayerView mVideoView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        IjkMediaPlayer.loadLibrariesOnce(null);
//        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_video_detail;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewGroup.LayoutParams params = mVideoView.getLayoutParams();
        float width = DisplayUtils.getWindowWidth(super.getContext());
        params.width = (int) width;
        params.height = (int) (width * 9 / 16 + 0.5f);
        mVideoView.setLayoutParams(params);
        mVideoView.start(URL);
    }

    @NonNull
    @Override
    protected IListPresenter<VideoInfo> createPresenter() {
        return new VideoDetailPresenter(this);
    }

}
