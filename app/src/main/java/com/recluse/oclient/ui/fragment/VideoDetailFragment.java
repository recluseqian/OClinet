package com.recluse.oclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.recluse.base.model.event.ClickEvent;
import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.view.activity.BaseAppCompatActivity;
import com.recluse.base.view.fragment.BaseListFragment;
import com.recluse.oclient.R;
import com.recluse.oclient.data.VideoDetailEntity;
import com.recluse.oclient.data.VideoInfo;
import com.recluse.oclient.presenter.VideoDetailPresenter;
import com.recluse.oclient.ui.activity.DetailActivity;
import com.recluse.oclient.ui.listview.widget.OCPlayerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    private String mUrl = URL;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = super.getArguments();
        if (bundle != null) {
            mUrl = bundle.getString(DetailActivity.Const.INTENT_VIDEO_URL);
        }
        if (TextUtils.isEmpty(mUrl)) {
            mUrl = URL;
        }
        if (super.getActivity() instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) super.getActivity()).setUniqueId(mUniqueId);
        }
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
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
        mVideoView.start(mUrl);
    }

    @NonNull
    @Override
    protected IListPresenter<VideoInfo> createPresenter() {
        return new VideoDetailPresenter(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mVideoView.stop();
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    protected boolean isNeedRegisterEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetClickEvent(ClickEvent<VideoDetailEntity.VideoListBean> event) {
        if (event.mUniqueId != mUniqueId || event.mData == null) {
            return;
        }

        if (mVideoView != null) {
            mVideoView.restart(event.mData.mp4SdUrl);
        }
    }

}
