package com.ooftf.master.widget.dialog.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/6 0006
 */
public class BarUtils {
    /**
     * Return whether the status bar is visible.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarVisible(@NonNull final Activity activity) {
        int flags = activity.getWindow().getAttributes().flags;
        return (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0;
    }

    /**
     * Return the status bar's height.
     *
     * @return the status bar's height
     */
    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

}
