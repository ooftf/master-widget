package com.ooftf.layout.kv

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ooftf.basic.AppHolder
import com.ooftf.basic.utils.*

/**
 *
 * 以Padding来布局，兼容多行显示
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/2/6
 */
open class KvLayout : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        obtainAttrs(attrs)
    }


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        obtainAttrs(attrs)
    }


    constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        obtainAttrs(attrs)
    }

    val dividerPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = DIVIDER_COLOR
            strokeWidth = DIVIDER_HEIGHT
        }
    }

    private fun obtainAttrs(attrs: AttributeSet?) {
        attrs?.let { attr ->
            Log.e("padding", "" + attr.getAttributeValue(ANDROID_NAMESPACE, "padding"))
            setDefaultPadding(attr)
            context.obtainStyledAttributes(attr, R.styleable.KvLayout).run {
                if (hasValue(R.styleable.KvLayout_kvl_key)) {
                    getString(R.styleable.KvLayout_kvl_key)
                            ?.let { it -> setKey(it) }
                }
                if (hasValue(R.styleable.KvLayout_kvl_value)) {
                    getString(R.styleable.KvLayout_kvl_value)
                            ?.let { it -> setValue(it) }
                }
                if (hasValue(R.styleable.KvLayout_kvl_hint)) {
                    getString(R.styleable.KvLayout_kvl_hint)
                            ?.let { it -> setHint(it) }
                }
                if (hasValue(R.styleable.KvLayout_kvl_keyWidth)) {
                    setKeyWidth(
                            getDimension(
                                    R.styleable.KvLayout_kvl_keyWidth,
                                    KEY_WIDTH
                            ))
                }
                if (hasValue(R.styleable.KvLayout_kvl_TextSize)) {
                    setTextSize(
                            getDimension(
                                    R.styleable.KvLayout_kvl_TextSize,
                                    TEXT_SIZE
                            )
                    )
                }
                if (hasValue(R.styleable.KvLayout_kvl_keyTextColor)) {
                    setKeyTextColor(
                            getColor(
                                    R.styleable.KvLayout_kvl_keyTextColor,
                                    KEY_TEXT_COLOR
                            )
                    )
                }

                if (hasValue(R.styleable.KvLayout_kvl_valueTextColor)) {
                    setValueTextColor(
                            getColor(
                                    R.styleable.KvLayout_kvl_valueTextColor,
                                    VALUE_TEXT_COLOR
                            )
                    )
                }

                if (hasValue(R.styleable.KvLayout_kvl_showEndIcon)) {
                    setShowEndIcon(getBoolean(R.styleable.KvLayout_kvl_showEndIcon, false))
                }
                isShowDivider = getBoolean(R.styleable.KvLayout_kvl_showDivider, true)
                if (hasValue(R.styleable.KvLayout_kvl_valueNumberDecimal)) {
                    if (getBoolean(R.styleable.KvLayout_kvl_valueNumberDecimal, false)) {
                        setValueNumberDecimal()
                    }
                }
                if (hasValue(R.styleable.KvLayout_kvl_valueBold)) {
                    setValueBold(getBoolean(R.styleable.KvLayout_kvl_valueBold, false))
                }

                if (hasValue(R.styleable.KvLayout_kvl_hintTextColor)) {
                    setHintTextColor(getColor(R.styleable.KvLayout_kvl_hintTextColor, 0))
                }

                if (hasValue(R.styleable.KvLayout_kvl_valueEnabled)) {
                    setValueEnabled(getBoolean(R.styleable.KvLayout_kvl_valueEnabled, true))
                }
                if (hasValue(R.styleable.KvLayout_kvl_dividerHeight)) {
                    setDividerHeight(getDimension(R.styleable.KvLayout_kvl_dividerHeight, DIVIDER_HEIGHT))
                }
                if (hasValue(R.styleable.KvLayout_kvl_dividerColor)) {
                    setDividerColor(getColor(R.styleable.KvLayout_kvl_dividerColor, DIVIDER_COLOR))
                }
                if (hasValue(R.styleable.KvLayout_kvl_dividerEdge)) {
                    setDividerEdge(getDimension(R.styleable.KvLayout_kvl_dividerEdgeLeft, 0f))
                }
                if (hasValue(R.styleable.KvLayout_kvl_dividerEdgeLeft)) {
                    dividerEdgeLeft = getDimension(R.styleable.KvLayout_kvl_dividerEdgeLeft, DIVIDER_EDGE_LEFT)
                }
                if (hasValue(R.styleable.KvLayout_kvl_dividerEdgeRight)) {
                    dividerEdgeRight = getDimension(R.styleable.KvLayout_kvl_dividerEdgeRight, DIVIDER_EDGE_RIGHT)
                }
                recycle()
            }

        }
    }

    private fun setDefaultPadding(attr: AttributeSet) {
        if (attr.getAttributeValue(ANDROID_NAMESPACE, "padding") !== null) {
            return
        }
        if (attr.getAttributeValue(ANDROID_NAMESPACE, "paddingHorizontal") == null) {
            if (attr.getAttributeValue(ANDROID_NAMESPACE, "paddingRight") == null
                    && attr.getAttributeValue(ANDROID_NAMESPACE, "paddingEnd") == null) {
                setPaddingRight(PADDING)
            }
            if (attr.getAttributeValue(ANDROID_NAMESPACE, "paddingLeft") == null
                    && attr.getAttributeValue(ANDROID_NAMESPACE, "paddingStart") == null) {
                setPaddingLeft(PADDING)
            }
        }
        if (attr.getAttributeValue(ANDROID_NAMESPACE, "paddingVertical") == null) {
            if (attr.getAttributeValue(ANDROID_NAMESPACE, "paddingBottom") == null) {
                setPaddingBottom(PADDING)
            }

            if (attr.getAttributeValue(ANDROID_NAMESPACE, "paddingTop") == null) {
                setPaddingTop(PADDING)
            }
        }

    }


    var dividerEdgeLeft = DIVIDER_EDGE_LEFT
    var dividerEdgeRight = DIVIDER_EDGE_RIGHT
    private fun setDividerEdge(dimensionPixelSize: Float) {
        dividerEdgeLeft = dimensionPixelSize
        dividerEdgeRight = dimensionPixelSize
    }


    private fun setDividerColor(color: Int) {
        dividerPaint.color = color
    }

    private fun setDividerHeight(dimensionPixelSize: Float) {
        dividerPaint.strokeWidth = dimensionPixelSize
    }

    private fun setValueEnabled(enabled: Boolean) {
        value.isEnabled = enabled
    }

    private fun setHintTextColor(color: Int) {
        value.setTextColor(color)
    }

    private fun setValueBold(boolean: Boolean) {
        if (boolean) {
            value.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        } else {
            value.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        }
    }

    init {
        LayoutInflater.from(context).inflate(getLayoutId(), this, true)
        initView()
        setWillNotDraw(false)
    }

    lateinit var key: TextView
    lateinit var value: TextView
    lateinit var endIcon: ImageView
    private fun initView() {
        key = findViewById(R.id.key)
        value = findViewById(R.id.value)
        endIcon = findViewById(R.id.endIcon)
    }

    open fun getLayoutId(): Int {
        return R.layout.kvl_ooftf_layout_kv
    }

    fun setValueNumberDecimal() {
        value.inputType = EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL
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


    fun setShowEndIcon(show: Boolean) {
        endIcon.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    var isShowDivider = true


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isShowDivider) {
            val y = height.toFloat() - dividerPaint.strokeWidth
            canvas?.drawLine(DIVIDER_EDGE_LEFT, y, width - DIVIDER_EDGE_RIGHT, y, dividerPaint)
        }


    }

    companion object {
        val KEY_WIDTH = AppHolder.app.getDimensionCompat(R.dimen.kvl_ooftf_keyWidth)
        val TEXT_SIZE = AppHolder.app.getDimensionCompat(R.dimen.kvl_ooftf_textSize)
        val PADDING = AppHolder.app.resources.getDimensionPixelSize(R.dimen.kvl_ooftf_padding)
        val DIVIDER_EDGE_LEFT = AppHolder.app.getDimensionCompat(R.dimen.kvl_ooftf_dividerEdgeLeft)
        val DIVIDER_EDGE_RIGHT = AppHolder.app.getDimensionCompat(R.dimen.kvl_ooftf_dividerEdgeRight)
        val DIVIDER_COLOR = AppHolder.app.getColorCompat(R.color.kvl_ooftf_dividerColor)
        val DIVIDER_HEIGHT = AppHolder.app.getDimensionCompat(R.dimen.kvl_ooftf_dividerHeight)
        val KEY_TEXT_COLOR = AppHolder.app.getColorCompat(R.color.kvl_ooftf_key_textColor)
        val VALUE_TEXT_COLOR = AppHolder.app.getColorCompat(R.color.kvl_ooftf_value_textColor)
        const val ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android"
    }

}