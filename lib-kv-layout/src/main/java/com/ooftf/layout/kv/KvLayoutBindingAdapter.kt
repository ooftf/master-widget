package com.ooftf.layout.kv

import android.content.res.ColorStateList
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
    @BindingAdapter("kvl_key")
    fun setKey(
            view: KvLayout,
            text: CharSequence?
    ) {
        EditBindingHelper.setValue(view.key, text)
    }

    @JvmStatic
    @BindingAdapter("kvl_unit")
    fun setUnit(
            view: KvLayout,
            text: CharSequence?
    ) {
        EditBindingHelper.setValue(view.unit, text)
    }

    @JvmStatic
    @BindingAdapter("kvl_valueEnabled")
    fun setValueEnabled(
            view: KvLayout,
            value: Boolean?
    ) {
        view.setValueEnabled(value == true)
    }


    @JvmStatic
    @BindingAdapter("kvl_hintTextColor")
    fun setHintTextColor(
            view: KvLayout,
            value: Int?
    ) {
        value?.let {
            view.setHintTextColor(it)
        }

    }

    @JvmStatic
    @BindingAdapter("kvl_valueBold")
    fun setValueBold(
            view: KvLayout,
            value: Boolean?
    ) {
        view.setValueBold(value == true)
    }


    @JvmStatic
    @BindingAdapter("kvl_showEndIcon")
    fun setShowEndIcon(
            view: KvLayout,
            value: Boolean?
    ) {
        view.setShowEndIcon(value == true)
    }

    @JvmStatic
    @BindingAdapter("kvl_keyWidth")
    fun setKeyWidth(
            view: KvLayout,
            value: Number?
    ) {
        value?.let {
            view.setKeyWidth(value.toFloat())
        }
    }

    @JvmStatic
    @BindingAdapter("kvl_valueLines")
    fun setValueLines(
            view: KvLayout,
            value: Int?
    ) {
        value?.let {
            view.setValueLines(it)
        }
    }

    @JvmStatic
    @BindingAdapter("kvl_valueLength")
    fun setValueLength(
            view: KvLayout,
            value: Int?
    ) {
        value?.let {
            view.setValueLength(it)
        }
    }

    @JvmStatic
    @BindingAdapter("kvl_textSize")
    fun setTextSize(
            view: KvLayout,
            value: Number?
    ) {
        value?.let {
            view.setTextSize(it.toFloat())
        }
    }

    @JvmStatic
    @BindingAdapter("kvl_keyTextColor")
    fun setKeyTextColor(view: KvLayout,
                        value: ColorStateList?) {
        view.setKeyTextColor(value)
    }

    @JvmStatic
    @BindingAdapter("kvl_valueTextColor")
    fun setValueTextColor(view: KvLayout,
                          value: ColorStateList?) {
        view.setValueTextColor(value)
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