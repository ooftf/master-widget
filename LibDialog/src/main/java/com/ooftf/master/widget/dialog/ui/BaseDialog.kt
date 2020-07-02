package com.ooftf.master.widget.dialog.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import com.ooftf.master.widget.dialog.R
import com.ooftf.master.widget.dialog.utils.BarUtils
import com.ooftf.master.widget.dialog.utils.ContextUtils

/**
 *
 * 如果使用默认theme,即使将dialog设置为最宽也无法填充屏幕
 *
 * @author lihang9
 *
 */
open class BaseDialog : Dialog {
    constructor(context: Context) : super(context, R.style.master_DialogTheme_Transparent)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    protected constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener) {}

    val activity: Activity?
        get() = ContextUtils.toActivity(context)

    protected fun setBackground(drawable: Drawable?) {
        window?.decorView?.background = drawable
    }

    protected fun setBackground(color: Int) {
        window?.decorView?.setBackgroundColor(color)
    }

    /**
     *  布局文件使用  android:fitsSystemWindows="true" 完成沉浸式
     */
    protected fun setImmersion() {
        //需要设置这个才能设置状态栏和导航栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window?.statusBarColor = Color.TRANSPARENT
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window?.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
    }

    protected fun setBackgroundResource(@DrawableRes resid: Int) {
        window?.decorView?.setBackgroundResource(resid)
    }

    /**
     * 必须在设置完 View 之后调用
     *
     * @param percent
     */
    fun setWidthPercent(percent: Float) {
        setWidth((getScreenWindowWidth() * percent).toInt())
    }

    /**
     * 除去状态栏的区域高度
     *
     * @return
     */
    fun getScreenWindowHeight(): Int {
        return if (BarUtils.isStatusBarVisible(activity!!)) {
            context.resources.displayMetrics.heightPixels - BarUtils.getStatusBarHeight()
        } else {
            context.resources.displayMetrics.heightPixels
        }
    }

    /**
     * 除去状态栏的区域宽度
     *
     * @return
     */
    fun getScreenWindowWidth(): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 必须在设置完 View 之后调用
     *
     * @param percent
     */
    fun setHeightPercent(percent: Float) {
        setHeight((getScreenWindowHeight() * percent).toInt())
    }

    /**
     * 必须在设置完 View 之后调用
     *
     * @param width
     */
    open fun setWidth(width: Int) {
        val attributes = window.attributes
        attributes.width = width
        window.attributes = attributes
    }

    fun setWidthMatchParent() {
        val attributes = window.attributes
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = attributes
    }

    fun setHeightMatchParent() {
        val attributes = window.attributes
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = attributes
    }

    /**
     * 必须在设置完 View 之后调用
     *
     * @param height
     */
    open fun setHeight(height: Int) {
        val attributes = window.attributes
        attributes.height = height
        window.attributes = attributes
    }

    /**
     * 设置进场动画和退场动画
     * <style name="DialogTranslateBottom">
    <item name="@android:windowEnterAnimation">@anim/dialog_enter_bottom</item>
    <item name="@android:windowExitAnimation">@anim/dialog_outer_bottom</item>
    </style>
     *
     * @param resId
     */
    fun setInOutAnimations(@StyleRes resId: Int) {
        window.setWindowAnimations(resId)
    }

    /**
     * 设置dialog显示的位置
     *
     * @param gravity The desired gravity constant.
     * @see Gravity
     */
    open fun setGravity(gravity: Int) {
        window.setGravity(gravity)
    }

    override fun show() {
        /**
         * 捕获BadTokenException崩溃信息，这个只能作为最坏的处理手段，最好找到导致BadTokenException的原因处理掉
         */
        try {
            super.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismiss() {
        /**
         * 捕获BadTokenException崩溃信息，这个只能作为最坏的处理手段，最好找到导致BadTokenException的原因处理掉
         */
        try {
            super.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}