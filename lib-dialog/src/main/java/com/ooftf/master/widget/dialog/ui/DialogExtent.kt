package com.ooftf.master.widget.dialog.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import com.ooftf.basic.immersion.ImmersionUtil
import com.ooftf.basic.utils.getActivity
import com.ooftf.master.widget.dialog.utils.BarUtils

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/7/2
 */
/**
 *  布局文件使用  android:fitsSystemWindows="true" 完成沉浸式
 */
fun Dialog.applyImmersion() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = Color.TRANSPARENT
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    // 允许在刘海区域布局
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        window.attributes.apply {
            layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = this
        }
    }
}
fun Dialog.setBackground(drawable: Drawable?) {
    window?.decorView?.background = drawable
}

fun Dialog.setBackground(color: Int) {
    window?.decorView?.setBackgroundColor(color)
}
fun Dialog.fitNavigationBar() {
    ImmersionUtil.fitNavigationBar(window)
}

fun Dialog.translucentNavigationBar() {
    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
}

fun Dialog.hideNavigationBar() {
    window.decorView.setSystemUiVisibility(window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
}

fun Dialog.lightStatusBar(light: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setStatusBarLightMode(window, light)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = Color.parseColor("#32000000")
    }
}


private fun Dialog.setStatusBarLightMode(
    window: Window,
    isLightMode: Boolean
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decorView = window.decorView
        var vis = decorView.systemUiVisibility
        vis = if (isLightMode) {
            vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        decorView.systemUiVisibility = vis
    }
}

fun Dialog.getContentView(): View? {
    val contentViewParent = getContentViewParent()
    return if (contentViewParent.childCount == 0) {
        null
    } else {
        contentViewParent.getChildAt(0)
    }

}

fun Dialog.getContentViewParent(): ViewGroup {
    return findViewById(Window.ID_ANDROID_CONTENT)
}

fun Dialog.setBackgroundResource(@DrawableRes resid: Int) {
    window?.decorView?.setBackgroundResource(resid)
}

/**
 * 必须在设置完 View 之后调用
 *
 * @param percent
 */
fun Dialog.setWidthPercent(percent: Float) {
    setWidth((getScreenWindowWidth() * percent).toInt())
}

/**
 * 除去状态栏的区域高度
 *
 * @return
 */
fun Dialog.getScreenWindowHeight(): Int {
    return if (BarUtils.isStatusBarVisible(context.getActivity()!!)) {
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
fun Dialog.getScreenWindowWidth(): Int {
    return context.resources.displayMetrics.widthPixels
}

/**
 * 必须在设置完 View 之后调用
 *
 * @param percent
 */
fun Dialog.setHeightPercent(percent: Float) {
    setHeight((getScreenWindowHeight() * percent).toInt())
}

/**
 * 必须在设置完 View 之后调用
 *
 * @param width
 */
fun Dialog.setWidth(width: Int) {
    val attributes = window?.attributes
    attributes?.width = width
    window?.attributes = attributes
}

fun Dialog.setWidthMatchParent() {
    setWidth(WindowManager.LayoutParams.MATCH_PARENT)
}

fun Dialog.setHeightMatchParent() {
    setHeight(WindowManager.LayoutParams.MATCH_PARENT)
}

/**
 * 必须在设置完 View 之后调用
 *
 * @param height
 */
fun Dialog.setHeight(height: Int) {
    val attributes = window?.attributes
    attributes?.height = height
    window?.attributes = attributes
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
fun Dialog.setInOutAnimations(@StyleRes resId: Int) {
    window?.setWindowAnimations(resId)
}

/**
 * 设置dialog显示的位置
 *
 * @param gravity The desired gravity constant.
 * @see Gravity
 */
fun Dialog.setGravity(gravity: Int) {
    window?.setGravity(gravity)
}