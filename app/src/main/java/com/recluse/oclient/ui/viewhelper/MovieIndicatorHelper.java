package com.recluse.oclient.ui.viewhelper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.recluse.base.utils.SystemUtils;
import com.recluse.oclient.R;

/**
 * Created by recluse on 17-9-26.
 */

public class MovieIndicatorHelper<T> implements View.OnClickListener {

    private static final String TAG = "MovieIndicatorHelper";

    private static final int UNKNOWN = -1;
    private static final int PRE_SHOWING = 0;
    private static final int SHOWING = 1;
    private static final int SHOWN = 2;
    private static final int HIDING = 3;
    private static final int HIDED = 4;

    private int mImageWidth;
    private T mData;
    private Activity mActivity;
    ImageView mImageView;

    ObjectAnimator mShowAnimator;
    ObjectAnimator mHideAnimator;

    Runnable mDelayRunnable = new Runnable() {
        @Override
        public void run() {
            show();
        }
    };

    int mState;

    public MovieIndicatorHelper(Activity activity, T data) {
        mData = data;
        mActivity = activity;
        initView();
    }

    private void initView() {
        if (mActivity == null) {
            return;
        }
        View decorView = mActivity.getWindow().getDecorView();
        FrameLayout contentParent = decorView.findViewById(android.R.id.content);
        mImageView = new ImageView(mActivity);
        mImageView.setImageResource(R.drawable.movie_indicator_icon);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        contentParent.addView(mImageView, params);
        mState = SHOWN;
        mImageView.setOnClickListener(this);
    }

    public void hide() {
        if (mImageWidth <= 0) {
            mImageWidth = mImageView.getMeasuredWidth();
        }

        if (mState == PRE_SHOWING) {
            SystemUtils.cancelRunnable(mDelayRunnable);
            return;
        }
        if (mState == SHOWING && mShowAnimator != null) {
            mShowAnimator.cancel();
        }
        if (mState == HIDING || mState == HIDED) {
            Log.e(TAG, "hide: duplicated hide animator");
            return;
        }
        if (mHideAnimator == null) {
            mHideAnimator = ObjectAnimator.ofFloat(mImageView, "translationX", 0, mImageWidth);
            mHideAnimator.setDuration(300);
            mHideAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = HIDED;
                }
            });
        }
        mState = HIDING;
        mHideAnimator.start();
    }

    public void showDelay() {
        if (mState == SHOWING || mState == SHOWN) {
            Log.e(TAG, "showDelay: duplicated show animator");
            return;
        }

        if (mState == HIDING && mHideAnimator != null) {
            mHideAnimator.end();
        }

        mState = PRE_SHOWING;
        SystemUtils.runOnUIThread(mDelayRunnable, 200);
    }

    private void show() {
        if (mImageWidth <= 0) {
            mImageWidth = mImageView.getMeasuredWidth();
        }

        if (mState != PRE_SHOWING) {
            return;
        }

        if (mShowAnimator == null) {
            mShowAnimator = ObjectAnimator.ofFloat(mImageView, "translationX", mImageWidth, 0);
            mShowAnimator.setDuration(300);
            mShowAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = SHOWN;
                }
            });
        }
        mState = SHOWING;
        mShowAnimator.start();
    }

    public void onDestroy() {
        if (mShowAnimator != null) {
            mShowAnimator.removeAllListeners();
            mShowAnimator = null;
        }
        if (mHideAnimator != null) {
            mHideAnimator.removeAllListeners();
            mHideAnimator = null;
        }
        SystemUtils.cancelRunnable(mDelayRunnable);
        mDelayRunnable = null;
        mActivity = null;
        mImageView = null;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
    }

}
