package com.recluse.oclient.data;

import android.support.annotation.NonNull;

import java.io.Serializable;

public interface ModuleInfo extends Serializable {

    @NonNull
    LocalInfo getLocalInfo();

}
