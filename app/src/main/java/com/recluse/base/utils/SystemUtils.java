package com.recluse.base.utils;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class SystemUtils {
    private static final String TAG = "SystemUtils";

    @NonNull
    private static Context mContext;
    @NonNull
    private static Handler mHandler;

    public static int IMAGE_ROUND_RADIUS;

    public static void onCreate(Context context) {
        mContext = context.getApplicationContext();
        mHandler = new Handler(mContext.getMainLooper());

        IMAGE_ROUND_RADIUS = (int) DisplayUtils.dp2px(mContext, 5);
    }

    private static AtomicInteger sAtomicInteger = new AtomicInteger(1);

    public static int getSystemUniqueId() {
        return sAtomicInteger.addAndGet(1);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay < 0) {
            delay = 0;
        }
        if (runnable != null) {
            mHandler.postDelayed(runnable, delay);
        }
    }

    public static void cancelRunnable(Runnable runnable) {
        if (runnable != null) {
            mHandler.removeCallbacks(runnable);
        }
    }

    public static Context getApplicationContext() {
        return mContext;
    }

    public static boolean isListEmtpy(List<?> list) {
        return list == null || list.isEmpty();
    }
}
