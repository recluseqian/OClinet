package com.recluse.base.utils;

import android.view.View;

/**
 * Created by recluse on 17-9-14.
 */

public class ViewsUtils {

    public static boolean setViewVisibility(View view, int visibility) {
        if (view != null && view.getVisibility() != visibility) {
            view.setVisibility(visibility);
            return true;
        }
        return false;
    }

}
