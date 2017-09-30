package com.recluse.oclient.data;

import android.support.annotation.NonNull;

public class BaseModuleInfo implements ModuleInfo{

    private LocalInfo mLocalInfo;

    @NonNull
    @Override
    public LocalInfo getLocalInfo() {
        if (mLocalInfo == null) {
            mLocalInfo = new LocalInfo();
        }
        return mLocalInfo;
    }
}
