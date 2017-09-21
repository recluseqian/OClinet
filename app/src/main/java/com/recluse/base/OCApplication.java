package com.recluse.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.recluse.base.utils.SystemUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class OCApplication extends Application {

    private static final String TAG = "OCApplication";

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mRefWatcher = LeakCanary.install(this);

        SystemUtils.onCreate(super.getApplicationContext());
    }

    @NonNull
    public static RefWatcher getRefWatcher(Context context) {
        OCApplication application = (OCApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }
}
