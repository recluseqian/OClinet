package com.recluse.oclient.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.base.view.listview.IListView;
import com.recluse.oclient.data.VideoInfo;

import java.util.List;

/**
 * Created by recluse on 17-9-19.
 */

public class VideoDetailPresenter extends IListPresenter.SimpleListPresenter<VideoInfo> {

    public VideoDetailPresenter(@NonNull IListView callback) {
        super(callback);
    }

    @Override
    public List<BaseViewHolderFactory<VideoInfo>> createFactoryList(Context context) {
        return null;
    }
}
