package com.ooftf.master.widget.suspend;

class MathUtil {
    //求两点之间的距离
    public static double distance(double x1, double y1, double x2, double y2) {
        return java.lang.Math.sqrt(power(x2 - x1, 2) + power(y2 - y1, 2));
    }

    //求多次幂
    static double power(double base, int power) {
        double result = 1.0;
        for (int i = 0; i < power; i++) {
            result = result * base;
        }
        return result;
    }
}
