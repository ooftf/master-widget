package com.ooftf.widget.statelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.BindingAdapter;

import java.util.ArrayList;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/5/8 0008
 */
public class StateLayoutSwitcher extends FrameLayout implements IStateLayout {

    static int defaultErrorLayoutId = NO_ID;
    static int defaultLoadLayoutId = NO_ID;
    static int defaultEmptyLayoutId = NO_ID;

    int errorLayoutId = defaultErrorLayoutId;
    int loadLayoutId = defaultLoadLayoutId;
    int emptyLayoutId = defaultEmptyLayoutId;
    int firstLayoutId = NO_ID;
    int secondLayoutId = NO_ID;
    int thirdLayoutId = NO_ID;

    View firstLayout;
    View secondLayout;
    View thirdLayout;
    View errorLayout;
    View loadLayout;
    View emptyLayout;
    View successLayout;
    boolean blockReloading = true;
    int refreshId = R.id.sls_error_refresh;
    int emptyActionId = R.id.sls_empty_action;
    int firstActionId = R.id.sls_first_action;
    int secondActionId = R.id.sls_second_action;
    int thirdActionId = R.id.sls_third_action;


    public StateLayoutSwitcher(@NonNull Context context) {
        super(context);
    }

    public StateLayoutSwitcher(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        obtainAttrs(attrs);
    }

    Runnable refresh = new Runnable() {
        @Override
        public void run() {
            if (errorLayout != null && errorLayout.getVisibility() == VISIBLE) {
                if (onRetryListener != null) {
                    onRetryListener.onRetry();
                }
            }
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        unRegister(refresh);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        register(refresh);
    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StateLayoutSwitcher);
        errorLayoutId = typedArray.getResourceId(R.styleable.StateLayoutSwitcher_error_layout, defaultErrorLayoutId);
        loadLayoutId = typedArray.getResourceId(R.styleable.StateLayoutSwitcher_loading_layout, defaultLoadLayoutId);
        emptyLayoutId = typedArray.getResourceId(R.styleable.StateLayoutSwitcher_empty_layout, defaultEmptyLayoutId);
        firstLayoutId = typedArray.getResourceId(R.styleable.StateLayoutSwitcher_first_layout, firstLayoutId);
        secondLayoutId = typedArray.getResourceId(R.styleable.StateLayoutSwitcher_second_layout, secondLayoutId);
        thirdLayoutId = typedArray.getResourceId(R.styleable.StateLayoutSwitcher_third_layout, thirdLayoutId);


        refreshId = typedArray.getResourceId(R.styleable.StateLayoutSwitcher_error_refresh, R.id.sls_error_refresh);
        emptyActionId = typedArray.getResourceId(R.styleable.StateLayoutSwitcher_empty_action, R.id.sls_empty_action);

        firstActionId = typedArray.getResourceId(R.styleable.StateLayoutSwitcher_first_action, R.id.sls_first_action);
        secondActionId = typedArray.getResourceId(R.styleable.StateLayoutSwitcher_second_action, R.id.sls_second_action);
        thirdActionId = typedArray.getResourceId(R.styleable.StateLayoutSwitcher_third_action, R.id.sls_third_action);

        blockReloading = typedArray.getBoolean(R.styleable.StateLayoutSwitcher_block_reloading, true);
        typedArray.recycle();
    }

    public StateLayoutSwitcher(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        successLayout = getChildAt(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StateLayoutSwitcher(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void switchToEmpty() {

        blocking = false;

        if (emptyLayoutId != NO_ID && emptyLayout == null) {
            emptyLayout = LayoutInflater.from(getContext()).inflate(emptyLayoutId, this, false);
            View action = emptyLayout.findViewById(emptyActionId);
            if (action != null) {
                action.setOnClickListener(v -> {
                    if (emptyAction != null) {
                        emptyAction.run();
                    }
                });
            }
            this.addView(emptyLayout);
        }
        showView(emptyLayout);
    }

    @Override
    public void switchToLoading() {
        if (blocking) {
            return;
        }
        if (loadLayoutId != NO_ID && loadLayout == null) {
            loadLayout = LayoutInflater.from(getContext()).inflate(loadLayoutId, this, false);
            this.addView(loadLayout);
        }
        if (loadLayout != null && loadLayout.getParent() == null) {
            this.addView(loadLayout);
        }
        showView(loadLayout);
    }

    @Override
    public void switchToError() {
        if (blocking) {
            return;
        }
        if (errorLayoutId != NO_ID && errorLayout == null) {
            errorLayout = LayoutInflater.from(getContext()).inflate(errorLayoutId, this, false);
            View refresh = errorLayout.findViewById(refreshId);
            if (refresh != null) {
                refresh.setOnClickListener(v -> {
                    if (onRetryListener != null) {
                        onRetryListener.onRetry();
                    }
                });
            }

            this.addView(errorLayout);
        }
        showView(errorLayout);
    }

    OnRetryListener onRetryListener;

    public void setOnRetryListener(OnRetryListener listener) {
        this.onRetryListener = listener;
    }

    public StateLayoutSwitcher setBlocking(boolean blocking) {
        this.blocking = blocking;
        return this;
    }

    EmptyAction emptyAction;

    public void setEmptyAction(EmptyAction listener) {
        this.emptyAction = listener;
    }

    Runnable firstAction;

    public void setFirstAction(Runnable listener) {
        this.firstAction = listener;
    }

    Runnable secondAction;

    public void setSecondAction(Runnable listener) {
        this.secondAction = listener;
    }

    Runnable thirdAction;

    public void setThirdAction(Runnable listener) {
        this.thirdAction = listener;
    }

    @Override
    public void switchToSuccess() {
        if (blockReloading) {
            blocking = true;
        }
        showView(successLayout);
    }

    @Override
    public void switchToUndefinedFirst() {
        if (blocking) {
            return;
        }
        if (firstLayoutId != NO_ID && firstLayout == null) {
            firstLayout = LayoutInflater.from(getContext()).inflate(firstLayoutId, this, false);
            View action = firstLayout.findViewById(firstActionId);
            if (action != null) {
                action.setOnClickListener(v -> {
                    if (firstAction != null) {
                        firstAction.run();
                    }
                });
            }
            this.addView(firstLayout);
        }
        showView(firstLayout);
    }

    @Override
    public void switchToUndefinedSecond() {
        if (blocking) {
            return;
        }
        if (secondLayoutId != NO_ID && secondLayout == null) {
            secondLayout = LayoutInflater.from(getContext()).inflate(secondLayoutId, this, false);
            View action = secondLayout.findViewById(secondActionId);
            if (action != null) {
                action.setOnClickListener(v -> {
                    if (secondAction != null) {
                        secondAction.run();
                    }
                });
            }
            this.addView(secondLayout);
        }
        showView(secondLayout);
    }

    @Override
    public void switchToUndefinedThird() {
        if (blocking) {
            return;
        }
        if (thirdLayoutId != NO_ID && thirdLayout == null) {
            thirdLayout = LayoutInflater.from(getContext()).inflate(thirdLayoutId, this, false);
            View action = thirdLayout.findViewById(thirdActionId);
            if (action != null) {
                action.setOnClickListener(v -> {
                    if (thirdAction != null) {
                        thirdAction.run();
                    }
                });
            }
            this.addView(thirdLayout);
        }
        showView(thirdLayout);
    }

    boolean blocking = false;


    private void showView(View except) {
        if (except == null) {
            return;
        }
        if (errorLayout != null && except != errorLayout) {
            errorLayout.setVisibility(View.GONE);
        }
        if (loadLayout != null && except != loadLayout) {
            loadLayout.setVisibility(View.GONE);
        }
        if (emptyLayout != null && except != emptyLayout) {
            emptyLayout.setVisibility(View.GONE);
        }

        if (successLayout != null && except != successLayout) {
            successLayout.setVisibility(View.GONE);
        }
        if (firstLayout != null && except != firstLayout) {
            firstLayout.setVisibility(View.GONE);
        }
        if (secondLayout != null && except != secondLayout) {
            secondLayout.setVisibility(View.GONE);
        }
        if (thirdLayout != null && except != thirdLayout) {
            thirdLayout.setVisibility(View.GONE);
        }
        except.setVisibility(View.VISIBLE);
    }

    @BindingAdapter(value = {"onRetryListener"}, requireAll = false)
    public static void setOnRetryListener(StateLayoutSwitcher view, OnRetryListener listener) {
        view.setOnRetryListener(listener);
    }

    @BindingAdapter(value = {"state"}, requireAll = false)
    public static void setValue(StateLayoutSwitcher view, Integer state) {
        if (state == null) {
            return;
        }
        switch (state) {
            case STATE_LOAD:
                view.switchToLoading();
                break;
            case STATE_EMPTY:
                view.switchToEmpty();
                break;
            case STATE_ERROR:
                view.switchToError();
                break;
            case STATE_UNDEFINED_FIRST:
                view.switchToUndefinedFirst();
                break;
            case STATE_UNDEFINED_SECOND:
                view.switchToUndefinedSecond();
                break;
            case STATE_UNDEFINED_THIRD:
                view.switchToUndefinedThird();
                break;
            default:
                view.switchToSuccess();
        }
    }


    public interface OnRetryListener {
        void onRetry();
    }

    public interface EmptyAction {
        void run();
    }


    public static void setDefaultErrorLayoutId(int errorLayoutId) {
        defaultErrorLayoutId = errorLayoutId;
    }

    public static void setDefaultLoadLayoutId(int loadLayoutId) {
        defaultLoadLayoutId = loadLayoutId;
    }

    public static void setDefaultEmptyLayoutId(int emptyLayoutId) {
        defaultEmptyLayoutId = emptyLayoutId;
    }

    public static void notifyReConnection() {
        for (Runnable runnable : reConnectionListener) {
            runnable.run();
        }
    }

    static ArrayList<Runnable> reConnectionListener = new ArrayList<>();

    static void register(Runnable runnable) {
        reConnectionListener.add(runnable);
    }


    static void unRegister(Runnable runnable) {
        reConnectionListener.remove(runnable);
    }
}
