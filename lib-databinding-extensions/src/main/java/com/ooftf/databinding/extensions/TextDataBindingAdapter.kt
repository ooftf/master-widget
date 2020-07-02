package com.ooftf.databinding.extensions

import android.graphics.Color
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/12/12
 */
object TextDataBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["exTextStyle"])
    fun exTextStyle(text: TextView, resource: Int?) {
        if (resource == null || resource == 0) {
            text.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        } else if (resource > 0) {
            text.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        } else {
            text.typeface = Typeface.defaultFromStyle(Typeface.ITALIC)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["exTextColorId"])
    fun setTextColorResource(textView: TextView, resource: Int?) {
        if (resource != null && resource != 0 && resource != View.NO_ID) {
            textView.setTextColor(ContextCompat.getColor(textView.context, resource))
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["exTextColor"])
    fun setTextColor(textView: TextView, colorString: String?) {
        if (colorString != null) {
            textView.setTextColor(Color.parseColor(colorString))
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["exTextColor"])
    fun setTextColor(textView: TextView, color: Int?) {
        if (color != null) {
            textView.setTextColor(color)
        }
    }

    val TEXT_CHANGED_LISTENER_ID = View.generateViewId()

    @JvmStatic
    @BindingAdapter(value = ["exMaxInt"], requireAll = false)
    fun setMaxInt(textView: TextView, max: Int) {

        (textView.getTag(TEXT_CHANGED_LISTENER_ID) as? TextWatcher)?.let {
            textView.removeTextChangedListener(it)
        }
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val s1 = s.toString()
                val anInt = getInt(s1)
                if (anInt > max) {
                    textView.text = max.toString()
                }
            }
        }.run {
            textView.setTag(TEXT_CHANGED_LISTENER_ID, this)
            textView.addTextChangedListener(this)
        }

    }

    fun getInt(value: String): Int {
        return try {
            Integer.valueOf(value)
        } catch (e: Exception) {
            Log.e("stringUtil", "$value--> Integer is error")
            0
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["exFlag"])
    fun setTextFlags(view: TextView, flag: Int) {
        view.paint.flags = flag
    }
}