package com.recluse.base.view.listview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by recluse on 17-9-15.
 */

public class BaseDividerDecoration extends RecyclerView.ItemDecoration {

    private static Paint sPaint;

    private int mOrientation;
    private int mWidth;

    public static BaseDividerDecoration newDefaultInstance() {
        return new Builder().build();
    }

    private BaseDividerDecoration(){}

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (mOrientation == RecyclerView.HORIZONTAL) {
            drawVerticalDivider(c, parent);
        } else if (mOrientation == RecyclerView.VERTICAL) {
            drawHorizontalDivider(c, parent);
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mWidth;
            RectF rectF = new RectF(left, top, right, bottom);
            c.drawRect(rectF, sPaint);
        }
    }

    private void drawVerticalDivider(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + mWidth;
            RectF rectF = new RectF(left, top, right, bottom);
            c.drawRect(rectF, sPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (mOrientation == RecyclerView.HORIZONTAL) {
            outRect.set(0, 0, mWidth, 0);
        } else if (mOrientation == RecyclerView.VERTICAL) {
            outRect.set(0, 0, 0, mWidth);
        }
    }

    public static class Builder {

        private int mColor = Color.parseColor("#dddddddd");
        private int mWidth = 1;
        private int mOrientation = RecyclerView.VERTICAL;

        public Builder setOrientation(int orientation) {
            if (orientation != RecyclerView.VERTICAL && orientation != RecyclerView.HORIZONTAL) {
                throw new IllegalArgumentException(
                        "the orientation must be either RecyclerView.VERTICAL or RecyclerView.HORIZONTAL");
            }
            mOrientation = orientation;
            return this;
        }

        public Builder setColor(int color) {
            mColor = color;
            return this;
        }

        public Builder setWidth(int width) {
            if (width < 0) {
                throw new IllegalArgumentException("the width of divider must be positive");
            }
            mWidth = width;
            return this;
        }

        public BaseDividerDecoration build() {
            BaseDividerDecoration decoration = new BaseDividerDecoration();
            if (sPaint == null) {
                sPaint = new Paint();
            }
            sPaint.setColor(mColor);
            decoration.mOrientation = mOrientation;
            decoration.mWidth = mWidth;
            return decoration;
        }
    }
}
