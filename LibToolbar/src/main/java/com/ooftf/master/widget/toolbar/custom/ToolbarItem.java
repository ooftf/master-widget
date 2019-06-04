package com.ooftf.master.widget.toolbar.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ooftf.master.widget.toolbar.R;


/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/5/7 0007
 */
public class ToolbarItem extends ConstraintLayout {
    ImageView leftIcon;
    TextView text;
    ImageView rightIcon;

    public ToolbarItem(Context context) {
        super(context);
    }

    public ToolbarItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        inflate(getContext(), R.layout.layout_toolbar_item, this);
        leftIcon = findViewById(R.id.left_icon);
        rightIcon = findViewById(R.id.right_icon);
        text = findViewById(R.id.text);
    }


    public ToolbarItem setLeftIcon(@DrawableRes int resId) {
        this.leftIcon.setVisibility(VISIBLE);
        this.leftIcon.setImageResource(resId);
        return this;
    }

    public ToolbarItem setLeftIcon(Drawable resId) {
        this.leftIcon.setVisibility(VISIBLE);
        this.leftIcon.setImageDrawable(resId);
        return this;
    }

    public ToolbarItem setRightIcon(@DrawableRes int resId) {
        this.rightIcon.setVisibility(VISIBLE);
        this.rightIcon.setImageResource(resId);
        return this;
    }

    public ToolbarItem setRightIcon(Drawable resId) {
        this.rightIcon.setVisibility(VISIBLE);
        this.rightIcon.setImageDrawable(resId);
        return this;
    }

    public ToolbarItem setTextColor(int color) {
        this.text.setTextColor(color);
        return this;
    }

    public ToolbarItem setTextSize(float sp) {
        this.text.setTextSize(sp);
        return this;
    }

    public ToolbarItem setText(CharSequence text) {
        this.text.setText(text);
        return this;
    }

    public ToolbarItem setMarginSpace(int marginSpace) {
        LayoutParams leftLayoutParams = (LayoutParams) this.leftIcon.getLayoutParams();
        leftLayoutParams.rightMargin = marginSpace;
        LayoutParams rightLayoutParams = (LayoutParams) this.rightIcon.getLayoutParams();
        rightLayoutParams.leftMargin = marginSpace;
        return this;
    }
}
