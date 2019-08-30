package com.ooftf.master.widget.toolbar.official;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ooftf.master.widget.toolbar.R;
import com.ooftf.master.widget.toolbar.util.ContextUtils;

import java.lang.reflect.Method;

/**
 * @author master
 * @date 2017/10/10 0010
 */
public class ToolbarPlus extends Toolbar {
    boolean isTitleCenter = false;

    TextView titleText;


    public ToolbarPlus(Context context) {
        super(context);
    }

    public ToolbarPlus(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarPlus(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        setTitleTextColor(getDefaultTitleTextColor());
    }

    @Override
    public void setTitle(CharSequence title) {
        if (isTitleCenter) {
            checkTitleView();
            titleText.setText(title);
            super.setTitle("");
        } else {
            super.setTitle(title);
        }
    }

    int titleTextColor;

    @Override
    public void setTitleTextColor(int color) {
        titleTextColor = color;
        if (isTitleCenter&&titleText!=null) {
            titleText.setTextColor(color);
        } else {
            super.setTitleTextColor(color);
        }


    }

    private void checkTitleView() {
        if (titleText == null) {
            titleText = new TextView(getContext());
            titleText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            titleText.setTextColor(titleTextColor);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            titleText.setGravity(Gravity.CENTER);
            titleText.setLayoutParams(layoutParams);
            addView(titleText);
        }

    }

    {
        Activity activity = ContextUtils.toActivity(getContext());
        if (activity != null) {
            setNavigationOnClickListener(v -> activity.finish());
        }
        setPopupTheme(R.style.ThemeOverlay_Toolbar_PopupMenu);
        if (getId() == NO_ID) {
            setId(R.id.toolbar);
        }
        showOptionMenuIcon(getMenu());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        /**
         * CollapsingToolbarLayout 模式下 是不支持toolbar剧中的，位置会偏离,也不要设置默认背景色
         */
        if (!(getParent() instanceof CollapsingToolbarLayout)) {
            if (getBackground() == null) {
                setBackgroundColor(getDefaultBackgroundColor());
            }
            isTitleCenter = true;
            // 这个判断是为了防止二次onAttachedToWindow  会导致title设置为空
            if (titleText == null) {
                setTitle(getTitle());
            }

        }
    }

    protected int getDefaultBackgroundColor() {
        return ContextCompat.getColor(getContext(), R.color.white);
    }

    protected int getDefaultTitleTextColor() {
        return ContextCompat.getColor(getContext(), R.color.white);
    }

    public void addMenuItem(MenuItem item) {
        addView(item);
    }

    public static class MenuItem extends RelativeLayout {
        Context context;
        TextView textView;
        ImageView image;

        public MenuItem(Context context) {
            super(context);
            this.context = context;
            LayoutInflater.from(context).inflate(R.layout.item_menu_toolbar, this);
            textView = findViewById(R.id.text);
            image = findViewById(R.id.image);
            initToolbarLayoutParams();
        }

        public MenuItem layoutRight() {
            getToolbarLayoutParams().gravity = Gravity.END;
            return this;
        }

        public MenuItem layoutLeft() {
            getToolbarLayoutParams().gravity = Gravity.START;
            return this;
        }

        public MenuItem setImage(int id) {
            image.setVisibility(View.VISIBLE);
            image.setImageResource(id);
            return this;
        }

        public MenuItem setText(int id) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(id);
            return this;
        }

        public MenuItem setText(String text) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
            return this;
        }

        public MenuItem setOnClickListenerChain(OnClickListener listener) {
            setOnClickListener(listener);
            return this;
        }

        Toolbar.LayoutParams getToolbarLayoutParams() {
            return (Toolbar.LayoutParams) getLayoutParams();
        }

        private void initToolbarLayoutParams() {
            if (getLayoutParams() == null || !(getLayoutParams() instanceof Toolbar.LayoutParams)) {
                Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT, Gravity.END);
                Log.e("layoutParams", layoutParams.toString());
                setLayoutParams(layoutParams);
            }
        }
    }


    public ToolbarPlus addRightMenu(String text) {
        this.getMenu().add(text).setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);
        return this;
    }

    public ToolbarPlus addRightMenu(Drawable icon) {
        this.getMenu().add("").setIcon(icon).setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);
        return this;
    }

    public ToolbarPlus addRightMenu(@DrawableRes int iconRes) {
        this.getMenu().add("").setIcon(iconRes).setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);
        return this;
    }

    @Keep
    public static class BelowBehavior extends CoordinatorLayout.Behavior<View> {
        View dependency;

        public BelowBehavior() {
            super();
        }

        public BelowBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
            if (dependency instanceof ToolbarPlus) {
                this.dependency = dependency;
            }
            return dependency instanceof ToolbarPlus;
        }

        @Override
        public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
            parent.onLayoutChild(child, layoutDirection);
            child.offsetTopAndBottom(dependency.getBottom());
            return true;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
            return true;
        }

        @Override
        public boolean onMeasureChild(CoordinatorLayout parent, View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
            int heightMeasureSpec;
            if (dependency.getBottom() == dependency.getTop()) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(parentHeightMeasureSpec) - dependency.getMeasuredHeight(), MeasureSpec.getMode(parentHeightMeasureSpec));
            } else {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(parentHeightMeasureSpec) - dependency.getBottom(), MeasureSpec.getMode(parentHeightMeasureSpec));
            }
            child.measure(parentWidthMeasureSpec, heightMeasureSpec);
            return true;
        }
    }

    public static void showOptionMenuIcon(Menu menu) {
        if (menu != null && menu.getClass().getSimpleName().equals("MenuBuilder")) {
            try {
                Method m = menu.getClass().getDeclaredMethod(
                        "setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            } catch (NoSuchMethodException e) {
            } catch (Exception e) {
            }
        }
    }

}