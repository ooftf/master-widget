package com.ooftf.databinding.extensions

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/11/6
 */
object FocusedDataBindingAdapter {
    @JvmStatic
    @BindingAdapter("exFocused")
    fun focused(view: View, focused: Boolean?) {
        if (focused == true) {
            view.requestFocusFromTouch()
        } else {
            view.clearFocus()
        }
    }

    @JvmStatic
    @InverseBindingAdapter(
            attribute = "exFocused", event = "exFocusedAttrChanged"
    )
    fun getValue(view: View): Boolean = view.isFocused

    @JvmStatic
    @BindingAdapter("exFocusedAttrChanged")
    fun setOnFocusedChangedListener(
            view: View,
            bindingListener: InverseBindingListener?
    ) {
        var oldWatcher = view.getTag(R.id.tag_onFocusChangeListener) as OFCL?
        if (bindingListener != null) {
            if (oldWatcher != null) {
                oldWatcher.bindingListener = bindingListener
            } else {
                oldWatcher = OFCL(bindingListener)
                view.onFocusChangeListener = oldWatcher
                view.setTag(R.id.tag_onFocusChangeListener, oldWatcher)
            }
        } else {
            view.onFocusChangeListener = null
        }
    }

    class OFCL(var bindingListener: InverseBindingListener?) : View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            bindingListener?.onChange()
        }
    }
}