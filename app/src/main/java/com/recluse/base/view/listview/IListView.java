package com.recluse.base.view.listview;

import com.recluse.base.view.IView;

/**
 * Created by recluse on 17-9-15.
 */

public interface IListView<T> extends IView<T> {

    void onNoMoreContent();

}
