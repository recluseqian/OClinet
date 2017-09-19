package com.recluse.base.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.recluse.base.view.IView;
import com.recluse.base.model.event.NullEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public interface IPresenter<T> {

    void onCreate();

    void onDestroy();

    void initData(Bundle bundle);

    void requestData();

    T getDataSet();

    abstract class SimplePresenter<T> implements IPresenter<T> {

        protected IView<T> mCallback;
        protected int mUniqueId;

        private T mDataSet;

        public SimplePresenter(@NonNull IView<T> iView) {
            mCallback = iView;
            mUniqueId = iView.getUniqueId();
        }

        @Override
        public void onCreate() {
            EventBus.getDefault().register(this);
        }

        @Override
        public void onDestroy() {
            EventBus.getDefault().unregister(this);
        }

        @Override
        public void initData(@NonNull Bundle bundle) {
            //do nothing
        }

        @Override
        public void requestData() {
            //do nothing
        }

        @Override
        public T getDataSet() {
            return mDataSet;
        }

        @Subscribe(threadMode = ThreadMode.POSTING)
        public void onReceiveNullEvent(NullEvent event) {
            //do nothing
        }
    }

}
