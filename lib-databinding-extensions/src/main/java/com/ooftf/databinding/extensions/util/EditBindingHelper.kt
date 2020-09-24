package com.ooftf.databinding.extensions.util

import android.text.Spanned
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.InverseBindingListener
import com.ooftf.basic.armor.EmptyTextWatcher
import com.ooftf.databinding.extensions.R

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/9/11
 */
object EditBindingHelper {
    fun setValue(
            view: TextView,
            text: CharSequence?
    ) {
        val oldText: CharSequence = view.text
        if (text === oldText || text == null && oldText.isEmpty()) {
            return
        }
        if (text is Spanned) {
            if (text == oldText) {
                return  // No change in the spans, so don't set anything.
            }
        } else if (!haveContentsChanged(text, oldText)) {
            return  // No content changes, so don't set anything.
        }
        view.setText(text)
    }

    private fun haveContentsChanged(
            str1: CharSequence?,
            str2: CharSequence?
    ): Boolean {
        if (str1 == null != (str2 == null)) {
            return true
        } else if (str1 == null) {
            return false
        }
        val length = str1.length
        if (length != str2!!.length) {
            return true
        }
        for (i in 0 until length) {
            if (str1[i] != str2[i]) {
                return true
            }
        }
        return false
    }

    fun getValue(view: TextView): String = view.text.toString()

    fun setOnValueChangedListener(
            view: EditText,
            bindingListener: InverseBindingListener?
    ) {
        var oldWatcher = view.getTag(R.id.tag_id) as TheTextWatcher?
        if (bindingListener != null) {
            if (oldWatcher != null) {
                oldWatcher.bindingListener = bindingListener
            } else {
                oldWatcher = TheTextWatcher(bindingListener)
                view.addTextChangedListener(oldWatcher)
                view.setTag(R.id.tag_id, oldWatcher)
            }
        } else {
            oldWatcher?.let {
                view.removeTextChangedListener(it)
            }
        }
    }

}

class TheTextWatcher(var bindingListener: InverseBindingListener) : EmptyTextWatcher() {
    override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
    ) {
        bindingListener.onChange()
    }
}