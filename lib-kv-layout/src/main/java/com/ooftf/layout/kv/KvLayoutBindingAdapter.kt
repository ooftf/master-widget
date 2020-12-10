package com.ooftf.layout.kv

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.ooftf.basic.engine.EditBindingHelper

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/12/10
 */
object KvLayoutBindingAdapter {
    @JvmStatic
    @BindingAdapter("kvl_value")
    fun setValue(
            view: KvLayout,
            text: CharSequence?
    ) {
        EditBindingHelper.setValue(view.value, text)
    }

    @JvmStatic
    @InverseBindingAdapter(
            attribute = "kvl_value", event = "kvl_valueAttrChanged"
    )
    fun getValue(view: KvLayout): String = EditBindingHelper.getValue(view.value)

    @JvmStatic
    @BindingAdapter("kvl_valueAttrChanged")
    fun setOnValueChangedListener(
            view: KvLayout,
            bindingListener: InverseBindingListener?
    ) {
        EditBindingHelper.setOnValueChangedListener(view.value, bindingListener)
    }
}