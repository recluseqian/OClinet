package com.recluse.base.view;

public interface IListView<T> extends IView<T> {

    void onUpdateList(int positionStart, int itemCount);

    void onNoMoreContent();

    boolean supportRefresh();

    boolean supportLoadMore();

}
