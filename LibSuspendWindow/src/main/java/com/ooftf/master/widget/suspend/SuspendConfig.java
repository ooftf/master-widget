package com.ooftf.master.widget.suspend;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/7/3 0003
 */
public class SuspendConfig {
    boolean isBindActivity = true;
    boolean isKeepSide = true;

    public boolean isBindActivity() {
        return isBindActivity;
    }

    public SuspendConfig setBindActivity(boolean bindActivity) {
        isBindActivity = bindActivity;
        return this;
    }

    public boolean isKeepSide() {
        return isKeepSide;
    }

    public SuspendConfig setKeepSide(boolean keepSide) {
        isKeepSide = keepSide;
        return this;
    }


}
