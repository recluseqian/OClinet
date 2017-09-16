package com.recluse.base.view;

import android.content.Context;

public interface IView<T> {

    void onDataSetChanged();

    void onFailed(int type);

    int getUniqueId();

    Context getCustomContext();

}
