package com.ooftf.master.widget.suspend;

import android.app.Application;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/11 0011
 */
public class Suspend {
    static Application application;

    public static void init(Application app) {
        application = app;
        ActivityManager.INSTANCE.init(application);
        SuspendWindow.getInstance().startShow();
    }
}
