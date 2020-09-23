package com.ooftf.databinding.extensions.empty

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/1/6
 */
open class EmptyTextWatcher(var textView: TextView? = null) : TextWatcher {
    override fun afterTextChanged(s: Editable) {
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    fun setText(text: String) {
        textView?.run {
            removeTextChangedListener(this@EmptyTextWatcher)
            setText(text)
            addTextChangedListener(this@EmptyTextWatcher)
        }

    }
}