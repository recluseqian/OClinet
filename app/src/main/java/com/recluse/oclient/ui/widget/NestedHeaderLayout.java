package com.recluse.oclient.ui.widget;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class NestedHeaderLayout extends RelativeLayout implements NestedScrollingChild {

    private static final String TAG = "NestedHeaderLayout";

    public NestedHeaderLayout(Context context) {
        super(context);
        init();
    }

    public NestedHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    public void scrollByWithMargin(int dx, int dy) {
        MarginLayoutParams params = (MarginLayoutParams) super.getLayoutParams();
        params.leftMargin -= dx;
        params.topMargin -= dy;
        setLayoutParams(params);
    }
}
