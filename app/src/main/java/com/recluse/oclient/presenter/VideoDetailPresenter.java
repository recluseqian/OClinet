package com.recluse.oclient.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.base.view.listview.IListView;
import com.recluse.oclient.data.VideoDetailEntity;
import com.recluse.oclient.data.VideoInfo;
import com.recluse.oclient.data.VideoSubscribeEntity;
import com.recluse.oclient.event.VideoDetailEvent;
import com.recluse.oclient.event.VideoSubscribeEvent;
import com.recluse.oclient.network.RxOClient;
import com.recluse.oclient.ui.activity.DetailActivity;
import com.recluse.oclient.ui.listview.VideoDetailVHFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailPresenter extends IListPresenter.SimpleListPresenter<VideoInfo> {

    private static final String TAG = "VideoDetailPresenter";

    private String mPlid;
    private String mMid;

    public VideoDetailPresenter(@NonNull IListView callback) {
        super(callback);
    }

    @Override
    public void initData(Bundle bundle) {
        super.initData(bundle);
        if (bundle != null) {
            mPlid = bundle.getString(DetailActivity.Const.INTENT_VIDEO_PLID);
            mMid = bundle.getString(DetailActivity.Const.INTENT_VIDEO_MID);
        }
    }

    @Override
    public void requestData() {
        super.requestData();
        RxOClient.getVideoDetail(mUniqueId, mPlid);
        RxOClient.getVideoSubscribe(mUniqueId, mMid);
    }

    @Override
    public List<BaseViewHolderFactory<VideoInfo>> createFactoryList(Context context) {
        List<BaseViewHolderFactory<VideoInfo>> list = new ArrayList<>();
        list.add(new VideoDetailVHFactory(context));
        return list;
    }

    private VideoDetailEntity mDetailEntity;
    private VideoSubscribeEntity mSubscribeEntity;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetVideoDetailEvent(VideoDetailEvent event) {
        if (mCallback == null || event.uniqueId != mUniqueId) {
            return;
        }

        if (event.data != null) {
            mDetailEntity = event.data;
        } else {
            mCallback.onFailed(0);
        }

        if (canUpdateList()) {
            updateList();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetVideoSubscribeEvent(VideoSubscribeEvent event) {
        if (mCallback == null || mUniqueId != event.uniqueId) {
            return;
        }
        if (event.data != null) {
            mSubscribeEntity = event.data;
        } else {

        }

        if (canUpdateList()) {
            updateList();
        }
    }

    private boolean canUpdateList() {
        return mDetailEntity != null && mSubscribeEntity != null;
    }

    private void updateList() {
        VideoInfo placeHolder = new VideoInfo(VideoDetailVHFactory.VIEW_TYPE_VIDEO_PLACE_HOLDER);
        mDataList.add(placeHolder);
        if (mSubscribeEntity != null) {
            VideoInfo subscribeInfo = new VideoInfo(VideoDetailVHFactory.VIEW_TYPE_SUBSCRIBE_INTRO);
            subscribeInfo.mSubscribeInfo = mSubscribeEntity.data;
            mDataList.add(subscribeInfo);
        }

        if (mDetailEntity != null) {
            VideoInfo videoDesc = new VideoInfo(VideoDetailVHFactory.VIEW_TYPE_VIDEO_DESC);
            videoDesc.mVideoDescInfo = mDetailEntity.data;
            mDataList.add(videoDesc);

            if (mDetailEntity.data != null && mDetailEntity.data.videoList != null
                    && mDetailEntity.data.videoList.size() > 1) {
                VideoInfo videoRecomList = new VideoInfo(VideoDetailVHFactory.VIEW_TYPE_VIDEO_SERIES);
                videoRecomList.mVideoList = mDetailEntity.data.videoList;
                videoRecomList.title = "全部课程";
                mDataList.add(videoRecomList);
            }
        }

        if (mSubscribeEntity != null && mSubscribeEntity.data != null) {
            VideoInfo subscribeRecom = new VideoInfo(VideoDetailVHFactory.VIEW_TYPE_SUBSCRIBE_RECOM);
            subscribeRecom.mSubscribeRecomList = mSubscribeEntity.data.subscribeList;
            subscribeRecom.title = "订阅内容推荐";
            mDataList.add(subscribeRecom);
        }

        if (mDetailEntity != null && mDetailEntity.data != null) {
            VideoInfo videoRecom = new VideoInfo(VideoDetailVHFactory.VIEW_TYPE_VIDEO_RECOM);
            videoRecom.mVideoRecomList = mDetailEntity.data.recommendList;
            videoRecom.title = "更多内容";
            mDataList.add(videoRecom);
        }

        mCallback.onDataSetChanged();
    }
}
