package com.ooftf.master.widget.toolbar.custom;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.ooftf.master.widget.toolbar.R;
import com.ooftf.master.widget.toolbar.util.ContextUtils;
import com.ooftf.master.widget.toolbar.util.DensityUtil;

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/5/7 0007
 */
public class MasterToolbar extends ConstraintLayout {
    LinearLayout leftContainer;
    LinearLayout rightContainer;
    TextView title;
    ToolbarItem leftDefaultButton;
    ToolbarItem rightDefaultButton;

    public MasterToolbar(Context context) {
        super(context);
    }

    public MasterToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttrs(attrs);
    }

    public MasterToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttrs(attrs);

    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MasterToolbar);
        setTitle(typedArray.getText(R.styleable.MasterToolbar_title));

        if (typedArray.hasValue(R.styleable.MasterToolbar_left_text)) {
            setLeftText(typedArray.getText(R.styleable.MasterToolbar_left_text));
        }
        if (typedArray.hasValue(R.styleable.MasterToolbar_right_text)) {
            setRightText(typedArray.getText(R.styleable.MasterToolbar_right_text));
        }
        if (typedArray.hasValue(R.styleable.MasterToolbar_left_icon)) {
            setLeftIcon(typedArray.getDrawable(R.styleable.MasterToolbar_left_icon));
        }
        if (typedArray.hasValue(R.styleable.MasterToolbar_right_icon)) {
            setRightIcon(typedArray.getDrawable(R.styleable.MasterToolbar_right_icon));
        }
        if (typedArray.hasValue(R.styleable.MasterToolbar_rightTextColor)) {
            setRightTextColor(typedArray.getColor(R.styleable.MasterToolbar_rightTextColor, ContextCompat.getColor(getContext(), R.color.font_black)));
        }
        if (typedArray.hasValue(R.styleable.MasterToolbar_leftTextColor)) {
            setLeftTextColor(typedArray.getColor(R.styleable.MasterToolbar_leftTextColor, ContextCompat.getColor(getContext(), R.color.font_black)));
        }
        typedArray.recycle();
    }

    {
        inflate(getContext(), R.layout.toolbar_master, this);
        title = findViewById(R.id.title);
        leftContainer = findViewById(R.id.leftContainer);
        rightContainer = findViewById(R.id.rightContainer);
    }

    public MasterToolbar setTitle(CharSequence textView) {
        title.setText(textView);
        return this;
    }

    public MasterToolbar setTitleSize(int sp) {
        title.setTextSize(sp);
        return this;
    }

    public MasterToolbar setTitleColor(int color) {
        title.setTextColor(color);
        return this;
    }

    /**
     * 左边按钮设置
     *
     * @param text
     * @return
     */
    public MasterToolbar setLeftText(CharSequence text) {
        checkLeftButton();
        leftDefaultButton.setText(text);
        return this;
    }

    public MasterToolbar setLeftIcon(Drawable drawable) {
        checkLeftButton();
        leftDefaultButton.setLeftIcon(drawable);
        return this;
    }

    public MasterToolbar setLeftIcon(@DrawableRes int resId) {
        checkLeftButton();
        leftDefaultButton.setLeftIcon(resId);
        return this;
    }

    public MasterToolbar setLeftTextColor(int color) {
        checkLeftButton();
        leftDefaultButton.setTextColor(color);
        return this;
    }

    public MasterToolbar setLeftClickListener(View.OnClickListener listener) {
        checkLeftButton();
        leftDefaultButton.setOnClickListener(listener);
        return this;
    }

    public ToolbarItem getLeftDefaultButton() {
        return leftDefaultButton;
    }

    protected void checkLeftButton() {
        if (leftDefaultButton == null) {
            leftDefaultButton = newDefaultToolbarItem();
            leftDefaultButton.setOnClickListener(v -> {
                Activity activity = ContextUtils.toActivity(getContext());
                if (activity != null) {
                    activity.finish();
                }
            });
            leftDefaultButton.setPadding(DensityUtil.dp2px(16), DensityUtil.dp2px(8), DensityUtil.dp2px(8), DensityUtil.dp2px(8));
            leftContainer.addView(leftDefaultButton, 0);
        }
    }

    public TextView getTitleView() {
        return title;
    }

    /**
     * 右边按钮设置
     *
     * @return
     */
    public ToolbarItem getRightDefaultButton() {
        return rightDefaultButton;
    }

    public MasterToolbar setRightText(CharSequence text) {
        checkRightButton();
        rightDefaultButton.setText(text);
        return this;
    }

    public MasterToolbar setRightIcon(Drawable drawable) {
        checkRightButton();
        rightDefaultButton.setRightIcon(drawable);
        return this;
    }

    public MasterToolbar setRightIcon(@DrawableRes int resId) {
        checkRightButton();
        rightDefaultButton.setRightIcon(resId);
        return this;
    }

    public MasterToolbar setRightTextColor(int color) {
        checkRightButton();
        rightDefaultButton.setTextColor(color);
        return this;
    }

    public MasterToolbar setRightClickListener(View.OnClickListener listener) {
        checkRightButton();
        rightDefaultButton.setOnClickListener(listener);
        return this;
    }

    protected void checkRightButton() {
        if (rightDefaultButton == null) {
            rightDefaultButton = newDefaultToolbarItem();
            rightDefaultButton.setPadding(DensityUtil.dp2px(8), DensityUtil.dp2px(8), DensityUtil.dp2px(16), DensityUtil.dp2px(8));
            rightContainer.addView(rightDefaultButton, rightContainer.getChildCount());
        }
    }


    public ToolbarItem newDefaultToolbarItem() {
        return new ToolbarItem(getContext());
    }


    public MasterToolbar addItemLeft(ToolbarItem item) {
        leftContainer.addView(item);
        return this;
    }

    public MasterToolbar addItemRight(ToolbarItem item) {
        rightContainer.addView(item);
        return this;
    }

    /**
     * @param toolbar
     * @param text
     */
    @BindingAdapter(value = "rightText", requireAll = false)
    public static void setToolbarRightText(MasterToolbar toolbar, String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        toolbar.setRightText(text);
    }

    /**
     * @param toolbar
     * @param color
     */
    @BindingAdapter(value = "rightTextColor", requireAll = false)
    public static void setToolbarRightTextColor(MasterToolbar toolbar, int color) {
        if (color <= 0) {
            return;
        }
        toolbar.setRightTextColor(color);
    }

    /**
     * @param toolbar
     * @param listener
     */
    @BindingAdapter(value = "rightClickListener", requireAll = false)
    public static void setToolbarRightClickListener(MasterToolbar toolbar, View.OnClickListener listener) {
        if (listener == null) {
            return;
        }
        toolbar.setRightClickListener(listener);
    }

    /**
     * @param toolbar
     * @param text
     */
    @BindingAdapter(value = "leftText", requireAll = false)
    public static void setToolbarLeftText(MasterToolbar toolbar, String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        toolbar.setLeftText(text);
    }

    /**
     * @param toolbar
     * @param color
     */
    @BindingAdapter(value = "leftTextColor", requireAll = false)
    public static void setToolbarLeftTextColor(MasterToolbar toolbar, int color) {
        if (color <= 0) {
            return;
        }
        toolbar.setLeftTextColor(color);
    }

    /**
     * @param toolbar
     * @param listener
     */
    @BindingAdapter(value = "leftClickListener", requireAll = false)
    public static void setToolbarLeftClickListener(MasterToolbar toolbar, View.OnClickListener listener) {
        if (listener == null) {
            return;
        }
        toolbar.setLeftClickListener(listener);
    }
}
