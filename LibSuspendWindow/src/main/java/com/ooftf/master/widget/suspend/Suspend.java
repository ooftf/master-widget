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
    static SuspendConfig config;

    public static void init(Application app, SuspendConfig config, ThreadPoolExecutor executor) {
        application = app;
        Suspend.config = config;
        Suspend.executor = executor;
        if (Suspend.config.isBindActivity) {
            ActivityManager.INSTANCE.init(application);
        }
    }

    public static void show() {
        SuspendWindow.getInstance().startShow();
    }

    public static void init(Application app, ThreadPoolExecutor executor) {
        init(app, new SuspendConfig(), executor);
    }
}
