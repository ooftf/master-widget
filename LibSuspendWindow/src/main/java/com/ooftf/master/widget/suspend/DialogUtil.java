package com.ooftf.master.widget.suspend;

import android.view.View;
import android.widget.FrameLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/10 0010
 */
public class DialogUtil {
    private static ArrayList<View> getWindowViews() {
        try {
            View rootView = null;
            Class wmgClass = Class.forName("android.view.WindowManagerGlobal");
            Object wmgInstnace = wmgClass.getMethod("getInstance").invoke(null, (Object[]) null);
            Field mViewsField = wmgClass.getDeclaredField("mViews");
            mViewsField.setAccessible(true);
            ArrayList<View> o = (ArrayList<View>) mViewsField.get(wmgInstnace);
            return o;

//            private final ArrayList<View> mViews = new ArrayList<View>();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isHasDialog() {
        ArrayList<View> windowViews = getWindowViews();
        for (View view : windowViews) {
            if (view instanceof FrameLayout && view.getClass().toString().contains("DecorView")) {
                FrameLayout frameLayout = (FrameLayout) view;
                if (frameLayout.getChildCount() == 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
