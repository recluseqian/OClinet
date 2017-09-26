package com.recluse.oclient.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.base.view.listview.IListView;
import com.recluse.oclient.data.SubscribeModuleInfo;
import com.recluse.oclient.data.VideoDetailInfo;
import com.recluse.oclient.data.VideoDetailItemInfo;
import com.recluse.oclient.data.VideoDetailSubItemInfo;
import com.recluse.oclient.event.VideoDetailEvent;
import com.recluse.oclient.event.VideoSubscribeEvent;
import com.recluse.oclient.network.RxOClient;
import com.recluse.oclient.ui.activity.DetailActivity;
import com.recluse.oclient.ui.viewholderfactory.VideoDetailItemVHFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailPresenter extends IListPresenter.SimpleListPresenter<VideoDetailItemInfo<?>> {

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
        mRequestCount++;
        RxOClient.getVideoSubscribe(mUniqueId, mMid);
        mRequestCount++;
    }

    @Override
    public List<BaseViewHolderFactory<VideoDetailItemInfo<?>>> createFactoryList(Context context) {
        List<BaseViewHolderFactory<VideoDetailItemInfo<?>>> list = new ArrayList<>();
        list.add(new VideoDetailItemVHFactory(context));
        return list;
    }

    private VideoDetailInfo mVideoDetailInfo;
    private SubscribeModuleInfo mSubscribeModuleInfo;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetVideoDetailEvent(VideoDetailEvent event) {
        if (mCallback == null || event.uniqueId != mUniqueId) {
            return;
        }

        if (event.data != null) {
            mVideoDetailInfo = event.data.data;
        } else {
            mCallback.onFailed(0);
        }

        mRequestCount--;
        if (mRequestCount <= 0) {
            updateList();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetVideoSubscribeEvent(VideoSubscribeEvent event) {
        if (mCallback == null || mUniqueId != event.uniqueId) {
            return;
        }
        if (event.data != null) {
            mSubscribeModuleInfo = event.data.data;
        } else {

        }

        mRequestCount--;
        if (mRequestCount <= 0) {
            updateList();
        }
    }

    private void updateList() {
        VideoDetailItemInfo placeHolder = new VideoDetailItemInfo();
        mDataList.add(placeHolder);

        if (mSubscribeModuleInfo != null) {
            mDataList.add(new VideoDetailItemInfo<>(
                    VideoDetailItemInfo.Type.SUBSCRIBE_INFO,
                    mSubscribeModuleInfo));
        }

        if (mVideoDetailInfo != null) {
            mDataList.add(
                    new VideoDetailItemInfo<>(
                            VideoDetailItemInfo.Type.VIDEO_DETAIL_INFO,
                            mVideoDetailInfo));

            if (mVideoDetailInfo.videoList != null
                    && mVideoDetailInfo.videoList.size() > 1) {
                mDataList.add(createNestedListItemInfo(
                        VideoDetailItemInfo.Type.VIDEO_LIST,
                        mVideoDetailInfo.videoList,
                        "全部课程"));
            }
        }

        if (mSubscribeModuleInfo != null
                && mSubscribeModuleInfo.subscribeList != null
                && !mSubscribeModuleInfo.subscribeList.isEmpty()) {
            mDataList.add(createNestedListItemInfo(
                    VideoDetailItemInfo.Type.SUBSCRIBE_RECOM_LIST,
                    mSubscribeModuleInfo.subscribeList,
                    "订阅内容推荐"));
        }

        if (mVideoDetailInfo != null) {
            mDataList.add(createNestedListItemInfo(
                    VideoDetailItemInfo.Type.VIDEO_RECOM_LIST,
                    mVideoDetailInfo.recommendList,
                    "更多内容"));
        }
        mCallback.onDataSetChanged();
    }

    private <S> VideoDetailItemInfo<List<S>> createNestedListItemInfo(int type, List<S> data, String title) {
        if (data == null || data.size() <= 1) {
            return null;
        }
        List<VideoDetailSubItemInfo<S>> list = new ArrayList<>();
        for (S item : data) {
            list.add(new VideoDetailSubItemInfo<S>(type, item));
        }
        VideoDetailItemInfo itemInfo = new VideoDetailItemInfo(type, list);
        itemInfo.title = title;
        return itemInfo;
    }
}
