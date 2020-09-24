package com.ooftf.databinding.extensions.empty

import android.text.Editable
import android.widget.TextView
import com.ooftf.basic.armor.EmptyTextWatcher
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/9/23
 */
class DecimalTextWatcher(var pattern: String, var rm: RoundingMode = RoundingMode.HALF_EVEN, textview: TextView) : EmptyTextWatcher(textview) {
    override fun afterTextChanged(s: Editable) {
        val toString = s.toString()
        val resultString = newString(toString)
        setText(resultString)
    }

    private fun newString(toString: String): String {
        return DecimalFormat(pattern).apply {
            roundingMode = rm
        }.format(toString.toDoubleOrNull() ?: "0")


    }
}