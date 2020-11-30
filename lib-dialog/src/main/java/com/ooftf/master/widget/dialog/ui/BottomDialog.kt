package com.ooftf.master.widget.dialog.ui

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.ooftf.master.widget.dialog.R
import com.ooftf.master.widget.dialog.ui.BaseDialog

/**
 * 底部显示的Dialog
 * 特点：宽度MATCH_PARENT,位于底部
 */
abstract class BottomDialog(activity: Activity) : BaseDialog(activity, R.style.master_DialogTheme_Transparent) {

    init {
        this.setGravity(Gravity.BOTTOM)
        this.setInOutAnimations(R.style.master_WindowAnimTranslateBottom)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setWidthPercent(1f)
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        setWidthPercent(1f)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        setWidthPercent(1f)
    }
}