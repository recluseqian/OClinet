package com.recluse.base;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;


/**
 * Created by recluse on 17-9-18.
 */

public class OCApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
