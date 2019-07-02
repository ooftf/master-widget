package com.ooftf.master.widget.suspend;

import android.app.Application;

import com.ooftf.master.unit.am.ActivityManager;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/11 0011
 */
public class Suspend {
    static Application application;
    static ThreadPoolExecutor executor;

    public static void init(Application app, ThreadPoolExecutor executor) {
        application = app;
        Suspend.executor = executor;
        ActivityManager.INSTANCE.init(application);
        SuspendWindow.getInstance().startShow();
    }
}
