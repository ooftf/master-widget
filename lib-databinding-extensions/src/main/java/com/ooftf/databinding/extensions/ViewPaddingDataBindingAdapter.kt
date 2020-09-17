package com.ooftf.databinding.extensions

import android.view.View
import androidx.databinding.BindingAdapter

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/9/16
 */
object ViewPaddingDataBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["exPadding"], requireAll = false)
    fun setPadding(view: View, padding: Int) {
        view.setPadding(padding, padding, padding, padding)
    }

    @JvmStatic
    @BindingAdapter(value = ["exPaddingLeft"], requireAll = false)
    fun setPaddingLeft(view: View, padding: Int) {
        view.setPadding(padding, view.paddingTop, view.paddingRight, view.paddingBottom)
    }

    @JvmStatic
    @BindingAdapter(value = ["exPaddingTop"], requireAll = false)
    fun setPaddingTop(view: View, padding: Int) {
        view.setPadding(view.paddingLeft, padding, view.paddingRight, view.paddingBottom)
    }

    @JvmStatic
    @BindingAdapter(value = ["exPaddingRight"], requireAll = false)
    fun setPaddingRight(view: View, padding: Int) {
        view.setPadding(view.paddingLeft, view.paddingTop, padding, view.paddingBottom)
    }

    @JvmStatic
    @BindingAdapter(value = ["exPaddingBottom"], requireAll = false)
    fun setPaddingBottom(view: View, padding: Int) {
        view.setPadding(view.paddingLeft, view.paddingTop, view.paddingRight, padding)
    }

}