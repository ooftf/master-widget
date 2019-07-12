package com.ooftf.master.widget.suspend;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;

import com.ooftf.master.unit.am.ActivityManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页开发者工具球
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/3/6 0006
 */
public class SuspendWindow {
    WindowManager windowManager;
    WindowManager.LayoutParams layoutParams;
    ValueAnimator valueAnimator;
    View view;
    public static SuspendWindow INSTANCE;


    public static SuspendWindow getInstance() {
        if (INSTANCE == null) {
            synchronized (SuspendWindow.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SuspendWindow();
                }
            }
        }
        return INSTANCE;
    }

    private OnClickListener onClickListener;

    public SuspendWindow setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    boolean isCreated = false;

    private SuspendWindow() {
        Suspend.executor.execute(() -> {
            valueAnimator = new ValueAnimator();
            valueAnimator.setDuration(300);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            windowManager = (WindowManager) Suspend.application.getSystemService(Context.WINDOW_SERVICE);
            initLayoutParams();
            LayoutInflater layoutInflater = LayoutInflater.from(Suspend.application);
            view = layoutInflater.inflate(R.layout.window_suspend, null);
            isCreated = true;
            new Handler(Looper.getMainLooper()).post(() -> {
                for (Runnable runnable : onCreateRunnable) {
                    runnable.run();
                }
                onCreateRunnable.clear();
                onCreate();
            });
        });
    }

    void onCreate() {
        // 设置点击事件
        view.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(ActivityManager.INSTANCE.getTopActivity());
            }
        });
        Log.e("SuspendWindow", "4");
        view.setOnTouchListener(new View.OnTouchListener() {
            float cX = 0;
            float cy = 0;
            boolean move = false;
            final int DISTANCE = 10;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    move = false;
                    cX = event.getRawX();
                    cy = event.getRawY();
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (!move && MathUtil.distance(cX, cy, event.getRawX(), event.getRawY()) > DISTANCE) {
                        move = true;
                    }
                    if (move) {
                        layoutParams.x = (int) event.getRawX() - v.getWidth() / 2;
                        layoutParams.y = (int) event.getRawY() - v.getHeight() / 2;
                        windowManager.updateViewLayout(v, layoutParams);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (!move) {
                        v.performClick();
                    } else {
                        if (Suspend.config.isKeepSide) {
                            reviseLocation(view);
                        }
                    }
                }
                return true;
            }
        });
        if (Suspend.config.isBindActivity()) {
            ActivityManager.INSTANCE.registerBackgroundObserver(() -> {
                view.setVisibility(View.GONE);
                return null;
            });
            ActivityManager.INSTANCE.registerForegroundObserver(() -> {

                view.setVisibility(View.VISIBLE);
                return null;
            });
            if (ActivityManager.INSTANCE.isAppForeground()) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }

        }
    }

    public void startShow() {
        postOnCreate(() -> {
            try {
                if (view.getParent() == null) {
                    windowManager.addView(view, layoutParams);
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        });
    }

    List<Runnable> onCreateRunnable = new ArrayList<>();

    public void postOnCreate(Runnable runnable) {
        if (isCreated) {
            runnable.run();
        } else {
            onCreateRunnable.add(runnable);
        }
    }

    public void dismiss() {
        try {
            if (view != null && view.getParent() != null) {
                windowManager.removeView(view);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void initLayoutParams() {
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        //FLAG_NOT_FOCUSABLE只有控件部分有焦点，FLAG_FORCE_NOT_FULLSCREEN，FLAG_FULLSCREEN整个屏幕的焦点
        layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.x = ScreenUtils.getScreenWidth(Suspend.application);
        layoutParams.y = (int) (ScreenUtils.getScreenHeight(Suspend.application) / 6f * 4);
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.format = PixelFormat.RGBA_8888;
    }

    void reviseLocation(View view) {
        int right = ScreenUtils.getScreenWidth(Suspend.application) - layoutParams.x - view.getWidth();
        int bottom = ScreenUtils.getScreenHeight(Suspend.application) - layoutParams.y - view.getHeight();
        if (layoutParams.x < layoutParams.y && layoutParams.x < right && layoutParams.x < bottom) {
            moveTo(true, 0);
        } else if (layoutParams.y < layoutParams.x && layoutParams.y < right && layoutParams.y < bottom) {
            moveTo(false, 0);
        } else if (right < layoutParams.x && right < layoutParams.y && right < bottom) {
            moveTo(true, ScreenUtils.getScreenWidth(Suspend.application) - view.getWidth());
        } else {
            moveTo(false, ScreenUtils.getScreenHeight(Suspend.application) - view.getHeight());
        }
    }


    void moveTo(boolean isX, int to) {
        valueAnimator.removeAllUpdateListeners();
        if (isX) {
            valueAnimator.setIntValues(layoutParams.x, to);
            valueAnimator.addUpdateListener(animation -> {
                layoutParams.x = (int) animation.getAnimatedValue();
                windowManager.updateViewLayout(view, layoutParams);
            });
        } else {
            valueAnimator.setIntValues(layoutParams.y, to);
            valueAnimator.addUpdateListener(animation -> {
                layoutParams.y = (int) animation.getAnimatedValue();
                windowManager.updateViewLayout(view, layoutParams);
            });
        }
        valueAnimator.start();
    }


}
