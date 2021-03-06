package com.ooftf.databinding.extensions

import android.graphics.Color
import android.graphics.Typeface
import android.text.Editable
import android.text.Selection
import android.text.Spannable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import com.ooftf.basic.armor.EmptyTextWatcher
import com.ooftf.basic.utils.genTagId
import com.ooftf.databinding.extensions.empty.DecimalTextWatcher
import java.math.RoundingMode

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
    @BindingAdapter(value = ["exTextStyleTypeface"])
    fun exTextStyleTypeface(text: TextView, resource: Int?) {
        when (resource) {
            Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC -> {
                text.typeface = Typeface.defaultFromStyle(resource)
            }
            else -> text.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
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
    @BindingAdapter(value = ["exSearchAction"])
    fun exSearchAction(textView: TextView, action: Runnable?) {
        textView.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    action?.run()
                    return true
                }
                return false
            }
        })
    }

    @JvmStatic
    @BindingAdapter(value = ["exTextColor"])
    fun setTextColor(textView: TextView, color: Int?) {
        if (color != null) {
            textView.setTextColor(color)
        }
    }

    val TEXT_CHANGED_LISTENER_ID = View.generateViewId()

    /**
     * exMaxInt  修改为
     */
    @JvmStatic
    @BindingAdapter(value = ["exTextMax"], requireAll = false)
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
    @BindingAdapter(value = ["exTextFlag"])
    fun setTextFlags(view: TextView, flag: Int) {
        view.paint.flags = flag
    }

    val ID = R.id.tag_id

    /**
     * @{viewModel.itemsDelivery.size()}
     */
    @JvmStatic
    @BindingAdapter(value = ["exDefault"])
    fun setDefaultValue(view: TextView, text: String?) {
        if (view.text.isEmpty()) {
            view.text = text
        }
        var tag = view.getTag(ID)
        if (tag !is TheTextWatcher) {
            tag = TheTextWatcher(text)
            view.addTextChangedListener(tag)
            view.setTag(ID, tag)
        }
        tag.default = text

    }

    class TheTextWatcher(var default: String?) : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            if (s.toString().isEmpty() && !default.isNullOrEmpty()) {
                s.append(default)
            }
        }
    }

    /**
     *
     */
    @JvmStatic
    @BindingAdapter(value = ["exNumFormat", "exNumFormatModel"], requireAll = false)
    fun setDecimal(
        view: TextView,
        pattern: String?,
        roundingMode: RoundingMode = RoundingMode.HALF_EVEN
    ) {
        var tag = view.getTag(R.id.tag_decimal_watcher)
        if (pattern == null) {
            view.setTag(R.id.tag_decimal_watcher, null)
            (tag as? DecimalTextWatcher)?.let {
                view.removeTextChangedListener(it)
            }
            return
        }
        if (tag !is DecimalTextWatcher) {
            tag = DecimalTextWatcher(pattern, roundingMode, view)
            view.setTag(R.id.tag_decimal_watcher, tag)
            view.addTextChangedListener(tag)
        }
        tag.pattern = pattern
        tag.rm = roundingMode
        view.text = view.text
    }

    @JvmStatic
    @BindingAdapter(value = ["exTextSizeSp"])
    fun exTextSizeSp(view: TextView, size: Int?) {
        size?.let {
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())
        }

    }

    @JvmStatic
    @BindingAdapter(value = ["exTextSizeSp"])
    fun exTextSizeSp(view: TextView, size: Float?) {
        size?.let {
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, it)
        }

    }

    @JvmStatic
    @BindingAdapter(value = ["exSelection"])
    fun exSelection(view: TextView, selection: Int?) {
        (view.text as? Spannable)?.let { spanable ->
            selection?.let { selectionIt ->
                Selection.setSelection(spanable, selectionIt)
            }
        }
    }

    val textSizeWatcherId by lazy {
        genTagId()
    }

    @JvmStatic
    @BindingAdapter(value = ["exTextSize", "exHintTextSize"], requireAll = true)
    fun exTextSize(view: TextView, exTextSize: Int?, hint: Int?) {
        setSize(view, hint, exTextSize)
        (view.getTag(textSizeWatcherId) as? TextWatcher)?.let {
            view.removeTextChangedListener(it)
        }
        val watcher = view.doOnTextChanged { text, start, before, count ->
            setSize(view, hint, exTextSize)
        }
        view.setTag(textSizeWatcherId, watcher)

    }

    private fun setSize(
        view: TextView,
        hint: Int?,
        exTextSize: Int?
    ) {
        if (view.text.isNullOrEmpty()) {
            hint?.toFloat()?.let {
                view.textSize = it
            }

        } else {
            exTextSize?.toFloat()?.let {
                view.textSize = it
            }
        }
    }

}