package com.ooftf.layout.kv

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import kotlinx.android.synthetic.main.layout_kv.view.*


/**
 *
 * 以Padding来布局，兼容多行显示
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/2/6
 */
class KvEditLayout : ConstraintLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        obtainAttrs(attrs)
    }


    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        obtainAttrs(attrs)
    }


    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        obtainAttrs(attrs)
    }

    private fun obtainAttrs(attrs: AttributeSet?) {
        attrs?.let { attr ->
            context.obtainStyledAttributes(attr, R.styleable.KvEditLayout).run {
                getString(R.styleable.KvEditLayout_kel_key)
                    ?.let { it -> setKey(it) }
                getString(R.styleable.KvEditLayout_kel_value)
                    ?.let { it -> setValue(it) }
                getString(R.styleable.KvEditLayout_kel_hint)
                    ?.let { it -> setHint(it) }

                setKeyWidth(
                    getDimension(
                        R.styleable.KvEditLayout_kel_keyWidth,
                        DensityUtil.dip2px(context, 100f)
                    )
                )
                setTextSize(
                    getDimension(
                        R.styleable.KvEditLayout_kel_TextSize,

                            DensityUtil.sp2px(16f).toFloat()
                    )
                )
                setPaddingHorizontal(
                    getDimension(
                        R.styleable.KvEditLayout_kel_paddingHorizontal,
                        DensityUtil.dip2px(context, 16f)
                    ).toInt()
                )
                setPaddingVertical(
                    getDimension(
                        R.styleable.KvEditLayout_kel_paddingVertical,
                        DensityUtil.dip2px(context, 16f)
                    ).toInt()
                )


                setKeyTextColor(
                    getColor(
                        R.styleable.KvEditLayout_kel_keyTextColor,
                            Color.parseColor("#ff777777")
                    )
                )
                setValueTextColor(
                    getColor(
                        R.styleable.KvEditLayout_kel_valueTextColor,
                            Color.parseColor("#ff333333")
                    )
                )
                setIndentation(
                    getDimension(
                        R.styleable.KvEditLayout_kel_indentation,
                        0f
                    ).toInt()
                )
                setEdit(getBoolean(R.styleable.KvEditLayout_kel_edit, true))
                setShowEndIcon(getBoolean(R.styleable.KvEditLayout_kel_showEndIcon, false))
                setShowDivider(getBoolean(R.styleable.KvEditLayout_kel_showDivider, false))
                if (getBoolean(R.styleable.KvEditLayout_kel_valueNumberDecimal, false)) {
                    setValueNumberDecimal()
                }
                recycle()
            }

        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_kv_edit_, this, true)
    }

    fun setIndentation(indentation: Int) {
        (key.layoutParams as LayoutParams).marginStart = indentation
    }

    fun setKey(text: CharSequence) {
        key.text = text
    }

    fun setKeyWidth(px: Float) {
        key.layoutParams.width = px.toInt()
    }

    fun setValue(text: CharSequence?) {
        value.setText(text)
    }

    fun setHint(text: CharSequence) {
        value.hint = text
    }

    fun setValueNumberDecimal() {
        value.inputType = EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL
    }

    fun setTextSize(px: Float) {
        key.setTextSize(TypedValue.COMPLEX_UNIT_PX, px)
        value.setTextSize(TypedValue.COMPLEX_UNIT_PX, px)
    }

    fun setKeyTextColor(color: Int) {
        key.setTextColor(color)
    }

    fun setValueTextColor(color: Int) {
        value.setTextColor(color)
    }

    fun setPaddingVertical(px: Int) {
        key.setPadding(0, px, 0, px)
        value.setPadding(0, px, 0, px)
    }

    fun setPaddingHorizontal(px: Int) {
        setPadding(px, 0, px, 0)
        (value.layoutParams as LayoutParams).marginEnd = px
    }


    fun setShowEndIcon(show: Boolean) {
        endIcon.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun setShowDivider(show: Boolean) {
        divider.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun setEdit(edit: Boolean) {
        if (value.isEnabled != edit) {
            value.isEnabled = edit
        }
    }

    object Binding {
        @JvmStatic
        @BindingAdapter("value")
        fun setValue(
                view: KvEditLayout,
                text: CharSequence?
        ) {
            val oldText: CharSequence = view.value.getText()
            if (text === oldText || text == null && oldText.length == 0) {
                return
            }
            if (text is Spanned) {
                if (text == oldText) {
                    return  // No change in the spans, so don't set anything.
                }
            } else if (!haveContentsChanged(text, oldText)) {
                return  // No content changes, so don't set anything.
            }
            view.value.setText(text)
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

        @JvmStatic
        @InverseBindingAdapter(
            attribute = "value"
            , event = "valueAttrChanged"
        )
        fun getValue(view: KvEditLayout): String = view.value.text.toString()

        @JvmStatic
        @BindingAdapter("valueAttrChanged")
        fun setOnValueChangedListener(
                view: KvEditLayout,
                bindingListener: InverseBindingListener?
        ) {
            val oldWatcher = view.getTag(R.id.value) as TextWatcher?
            if (bindingListener != null) {
                val watcher = object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        bindingListener.onChange()
                    }
                }
                view.value.addTextChangedListener(watcher)
                view.setTag(R.id.value, watcher)
            } else {
                oldWatcher?.let {
                    view.value.removeTextChangedListener(it)
                }
            }

        }
    }


}