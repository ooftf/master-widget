package com.ooftf.master.widget.dialog.utils;

import android.app.Activity;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

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
            Class wmgClass = Class.forName("android.view.WindowManagerGlobal");
            Object wmgInstnace = wmgClass.getMethod("getInstance").invoke(null, (Object[]) null);
            Field mViewsField = wmgClass.getDeclaredField("mViews");
            mViewsField.setAccessible(true);
            ArrayList<View> o = (ArrayList<View>) mViewsField.get(wmgInstnace);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isHasDialog(@NonNull Activity activity) {
        IBinder token = ((WindowManager.LayoutParams) activity.getWindow().getDecorView().getLayoutParams()).token;
        ArrayList<View> windowViews = getWindowViews();
        if (windowViews == null) {
            return false;
        }
        int count = 0;
        for (View view : windowViews) {
            if (view.getLayoutParams() instanceof WindowManager.LayoutParams) {
                if (((WindowManager.LayoutParams) view.getLayoutParams()).token == token) {
                    count++;
                    if (count > 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
