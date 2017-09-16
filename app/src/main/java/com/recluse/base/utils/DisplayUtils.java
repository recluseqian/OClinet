package com.recluse.base.utils;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.WindowManager;

/**
 * Created by recluse on 17-9-14.
 */

public class DisplayUtils {

    public static final float GOLDEN_RATIO = 0.618f;

    private static Point sWindowSize;

    public static int getWindowWidth(@NonNull Context context) {
        if (sWindowSize == null) {
            sWindowSize = new Point();
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            manager.getDefaultDisplay().getSize(sWindowSize);
        }

        return sWindowSize.x;
    }

    public static int getWindowHeight(@NonNull Context context) {
        if (sWindowSize == null) {
            sWindowSize = new Point();
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            manager.getDefaultDisplay().getSize(sWindowSize);
        }

        return sWindowSize.y;
    }

    public static float dp2px(@NonNull Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
