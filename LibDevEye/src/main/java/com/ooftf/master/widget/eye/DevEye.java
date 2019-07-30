package com.ooftf.master.widget.eye;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import com.ooftf.master.widget.dialog.ui.ListDialog;
import com.ooftf.master.widget.suspend.Suspend;
import com.ooftf.master.widget.suspend.SuspendWindow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/11 0011
 */
public class DevEye {
    static final int cacheSize = 15;
    static LinkedList<String> queue = new LinkedList<>();
    static Application application;

    public static void init(Application app, ThreadPoolExecutor executor) {
        application = app;
        Suspend.init(app, executor);
        SuspendLog.register(message -> {
            while (queue.size() >= cacheSize) {
                queue.poll();
            }
            queue.offer(message);
        });
        SuspendWindow.getInstance().setOnClickListener(topActivity -> {
            if (topActivity == null || DialogUtil.isHasDialog(topActivity)) {
                return;
            }
            new ListDialog(topActivity)
                    .setList(new ArrayList<String>() {
                        {
                            add("显示当前Activity名称");
                            add("显示当前LOG");
                            add("清除应用缓存");
                            add("跳转设置界面");
                        }
                    })
                    .setShowCancel(false)
                    .setOnItemClickListener((dialog, position, item) -> {
                        dialog.dismiss();
                        switch (position) {
                            case 0:
                                Toast.makeText(application, topActivity.getClass().getName(), Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                new LogDialog(topActivity).setData(queue.subList(0, queue.size())).show();
                                break;
                            case 2:

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    ActivityManager am = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
                                    am.clearApplicationUserData();
                                } else {
                                    Toast.makeText(application, "暂时只支持API19+", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 3:
                                AppUtils.launchAppDetailsSettings(application, application.getPackageName());
                                break;
                            default:
                        }
                    })
                    .show_();
        }).startShow();
    }

    public static void log(String message) {
        SuspendLog.log(message);
    }
}
