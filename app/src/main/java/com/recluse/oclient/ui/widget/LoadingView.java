package com.recluse.oclient.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.recluse.oclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingView extends FrameLayout {

    private static final String TAG = "LoadingView";

    @BindView(R.id.loading_image)
    ImageView mLoadingImgView;

    ObjectAnimator mObjectAnimator;

    public LoadingView(@NonNull Context context) {
        super(context);
        init();
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(super.getContext()).inflate(R.layout.loading_view_layout, this);
        ButterKnife.bind(this);

        mObjectAnimator = ObjectAnimator.ofFloat(mLoadingImgView, "rotation", 0, 359);
        mObjectAnimator.setDuration(600);
        mObjectAnimator.setRepeatCount(-1);
        mObjectAnimator.setRepeatMode(ObjectAnimator.RESTART);

        mObjectAnimator.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mObjectAnimator != null && !mObjectAnimator.isRunning()) {
            mObjectAnimator.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mObjectAnimator != null && mObjectAnimator.isRunning()) {
            mObjectAnimator.end();
        }
    }
}
