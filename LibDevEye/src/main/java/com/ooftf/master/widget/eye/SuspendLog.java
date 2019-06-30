package com.ooftf.master.widget.eye;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/10 0010
 */
class SuspendLog {
    static List<LogCallback> logCallbacks = new ArrayList<>();

    public static void log(String string) {
        for (LogCallback c : logCallbacks) {
            c.onLog(string);
        }
    }

    public static void register(LogCallback logCallback) {
        logCallbacks.add(logCallback);
    }

    public static void unRegister(LogCallback logCallback) {
        logCallbacks.remove(logCallback);
    }

    interface LogCallback {
        void onLog(String message);
    }
}
