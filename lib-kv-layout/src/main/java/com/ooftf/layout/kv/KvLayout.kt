package com.ooftf.layout.kv

import android.content.Context
import android.graphics.Color
import android.text.Spanned
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import kotlinx.android.synthetic.main.layout_kv.view.*


/**
 *
 * 以Padding来布局，兼容多行显示
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/2/6
 */
class KvLayout : ConstraintLayout {
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
            context.obtainStyledAttributes(attr, R.styleable.KvLayout).run {
                getString(R.styleable.KvLayout_kl_key)
                        ?.let { it -> setKey(it) }
                getString(R.styleable.KvLayout_kl_value)
                        ?.let { it -> setValue(it) }
                getString(R.styleable.KvLayout_kl_hint)
                        ?.let { it -> setHint(it) }

                setKeyWidth(
                        getDimension(
                                R.styleable.KvLayout_kl_keyWidth,
                                DensityUtil.dip2px(context, 100f)
                        )
                )
                setTextSize(
                        getDimension(
                                R.styleable.KvLayout_kl_TextSize,
                                DensityUtil.sp2px(16f).toFloat()
                        )
                )
                setPaddingHorizontal(
                        getDimension(
                                R.styleable.KvLayout_kl_paddingHorizontal,
                                DensityUtil.dip2px(context, 16f)
                        ).toInt()
                )
                setPaddingVertical(
                        getDimension(
                                R.styleable.KvLayout_kl_paddingVertical,
                                DensityUtil.dip2px(context, 16f)
                        ).toInt()
                )


                setKeyTextColor(
                        getColor(
                                R.styleable.KvLayout_kl_keyTextColor,
                                Color.parseColor("#ff777777")
                        )
                )
                setValueTextColor(
                        getColor(
                                R.styleable.KvLayout_kl_valueTextColor,
                                Color.parseColor("#ff333333")
                        )
                )
                setShowEndIcon(getBoolean(R.styleable.KvLayout_kl_showEndIcon, false))
                setShowDivider(getBoolean(R.styleable.KvLayout_kl_showDivider, false))
                recycle()
            }

        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_kv, this, true)
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

    object Binding {
        @JvmStatic
        @BindingAdapter("value")
        fun setValue(
                view: KvLayout,
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
    }


}