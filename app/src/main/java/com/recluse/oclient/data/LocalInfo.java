package com.recluse.oclient.data;

import java.io.Serializable;

public class LocalInfo implements Serializable {

    public static final int LOCAL_DATA_TYPE_NORMAL = 0;
    public static final int LOCAL_DATA_TYPE_HEADER = 1;
    public static final int LOCAL_DATA_TYPE_LOADING = 2;
    public static final int LOCAL_DATA_TYPE_NO_MORE = 3;

    public int mDataType;

    public static final int DIVIDER_NORMAL = 0;
    public static final int DIVIDER_HIDE_PRE = 1 << 1;
    public static final int DIVIDER_HIDE_SELF = 1 << 2;
    public int mDividerType;

}
