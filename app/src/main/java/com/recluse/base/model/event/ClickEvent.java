package com.recluse.base.model.event;

import android.view.View;

public class ClickEvent<T> {

    public int mUniqueId;
    public T mData;
    public View mView;
    public int mPosition;

    public ClickEvent(int uniqueId, T data, View view, int position) {
        mUniqueId = uniqueId;
        mData = data;
        mView = view;
        mPosition = position;
    }

}
