package com.recluse.base.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by recluse on 17-9-13.
 */

public class SystemUtils {
    private static final String TAG = "SystemUtils";

    private static AtomicInteger sAtomicInteger = new AtomicInteger(1);

    public static int getSystemUniqueId() {
        return sAtomicInteger.addAndGet(1);
    }
}
