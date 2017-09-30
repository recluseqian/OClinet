package com.recluse.oclient.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.view.NestedScrollingParent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by recluse on 17-9-29.
 */

public class NestedLinearLayout extends LinearLayout implements NestedScrollingParent {

    private static final String TAG = "NestedLayout";

    private NestedHeaderLayout mHeaderView;
    private int mHeaderHeight;

    Scroller mScroller;

    public NestedLinearLayout(Context context) {
        super(context);
        init();
    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mScroller = new Scroller(super.getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = super.getChildAt(0);
        if (view instanceof NestedHeaderLayout) {
            mHeaderView = (NestedHeaderLayout) view;
        } else {
            Log.e(TAG, "NestedLayout do not have header view or not instance of NestedHeaderLayout");
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mHeaderView != null && mHeaderHeight <= 0) {
            mHeaderHeight = mHeaderView.getMeasuredHeight();
            MarginLayoutParams params = (MarginLayoutParams) super.getLayoutParams();
            params.bottomMargin = -mHeaderHeight;
            super.setLayoutParams(params);
        }
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);

        Log.e(TAG, "onNestedPreScroll: " + getScrollY());
        if ((super.getScrollY() < mHeaderHeight && super.getScrollY() >= 0)
                || (dy < 0 && target instanceof RecyclerView && isListAtTop((RecyclerView) target))) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    private boolean isListAtTop(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition() == 0;
        }

        return false;
    }

    @Override
    public void scrollBy(@Px int x, @Px int y) {
        if (y + super.getScrollY() >= 0) {
            super.scrollBy(x, y);
        }
    }

    @Override
    public int getNestedScrollAxes() {
        int orientation = super.getOrientation();
        if (orientation == LinearLayout.HORIZONTAL) {
            return RecyclerView.SCROLL_AXIS_HORIZONTAL;
        } else if (orientation == LinearLayout.VERTICAL) {
            return RecyclerView.SCROLL_AXIS_VERTICAL;
        } else {
            throw new IllegalArgumentException("the wrong orientation");
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        if ((super.getScrollY() < mHeaderHeight && super.getScrollY() >= 0)
                || (velocityY < 0 && target instanceof RecyclerView && isListAtTop((RecyclerView) target))) {
            fling(super.getScrollX(), super.getScrollY(), (int) velocityX, (int) velocityY, 0, 0, 100, mHeaderHeight);
            return true;
        }

        return false;
    }


    private void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            super.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            super.postInvalidate();
        }
    }
}
