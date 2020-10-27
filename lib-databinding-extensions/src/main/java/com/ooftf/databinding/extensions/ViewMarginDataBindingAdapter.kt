package com.ooftf.databinding.extensions

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/9/16
 */
object ViewMarginDataBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["exMargin"], requireAll = false)
    fun setPadding(view: View, margin: Int) {
        (view.layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            it.setMargins(margin, margin, margin, margin)
            view.requestLayout()
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["exMarginLeft"], requireAll = false)
    fun setMarginLeft(view: View, margin: Int) {
        (view.layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            it.setMargins(margin, it.topMargin, it.rightMargin, it.bottomMargin)
            view.requestLayout()
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["exMarginTop"], requireAll = false)
    fun setMarginTop(view: View, margin: Int) {
        (view.layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            it.setMargins(it.leftMargin, margin, it.rightMargin, it.bottomMargin)
            view.requestLayout()
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["exMarginRight"], requireAll = false)
    fun setMarginRight(view: View, margin: Int) {
        (view.layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            it.setMargins(it.leftMargin, it.topMargin, margin, it.bottomMargin)
            view.requestLayout()
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["exMarginBottom"], requireAll = false)
    fun setMarginBottom(view: View, margin: Int) {
        (view.layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            it.setMargins(it.leftMargin, it.topMargin, it.rightMargin, margin)
            view.requestLayout()
        }
    }

}