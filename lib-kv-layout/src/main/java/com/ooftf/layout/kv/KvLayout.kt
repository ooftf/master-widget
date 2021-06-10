package com.ooftf.layout.kv

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter.LengthFilter
import android.text.Selection
import android.text.Spannable
import android.text.TextUtils
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ooftf.basic.armor.EmptyTextWatcher
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

    val PADDING = context.getDimensionPixelSize(R.dimen.kvl_ooftf_padding)
    val DIVIDER_EDGE_LEFT = context.getDimensionCompat(R.dimen.kvl_ooftf_dividerEdgeLeft)
    val DIVIDER_EDGE_RIGHT = context.getDimensionCompat(R.dimen.kvl_ooftf_dividerEdgeRight)
    val DIVIDER_COLOR = context.getColorCompat(R.color.kvl_ooftf_dividerColor)
    val DIVIDER_HEIGHT = context.getDimensionCompat(R.dimen.kvl_ooftf_dividerHeight)
    val dividerPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = DIVIDER_COLOR
            strokeWidth = DIVIDER_HEIGHT
        }
    }

    lateinit var key: TextView
    lateinit var value: TextView
    lateinit var endIcon: ImageView
    lateinit var startIcon: ImageView
    lateinit var unit: TextView

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

    init {
        LayoutInflater.from(context).inflate(getLayoutId(), this, true)
        initView()
        setWillNotDraw(false)
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
                            0f
                        )
                    )
                }
                if (hasValue(R.styleable.KvLayout_kvl_textSize)) {
                    setTextSize(
                        getDimension(
                            R.styleable.KvLayout_kvl_textSize,
                            0f
                        )
                    )
                }
                if (hasValue(R.styleable.KvLayout_kvl_valueHintTextSize)) {
                    setValueHintTextSize(
                        getDimension(
                            R.styleable.KvLayout_kvl_valueHintTextSize,
                            0f
                        )
                    )
                }

                if (hasValue(R.styleable.KvLayout_kvl_keyTextColor)) {
                    setKeyTextColor(
                        getColorStateList(
                            R.styleable.KvLayout_kvl_keyTextColor
                        )
                    )
                }

                if (hasValue(R.styleable.KvLayout_kvl_valueTextColor)) {
                    setValueTextColor(
                        getColorStateList(
                            R.styleable.KvLayout_kvl_valueTextColor
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
                if (hasValue(R.styleable.KvLayout_kvl_valueNumber)) {
                    if (getBoolean(R.styleable.KvLayout_kvl_valueNumber, false)) {
                        setValueNumber()
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
                    setDividerHeight(
                        getDimension(
                            R.styleable.KvLayout_kvl_dividerHeight,
                            DIVIDER_HEIGHT
                        )
                    )
                }
                if (hasValue(R.styleable.KvLayout_kvl_dividerColor)) {
                    setDividerColor(getColor(R.styleable.KvLayout_kvl_dividerColor, DIVIDER_COLOR))
                }
                if (hasValue(R.styleable.KvLayout_kvl_dividerEdge)) {
                    setDividerEdge(getDimension(R.styleable.KvLayout_kvl_dividerEdgeLeft, 0f))
                }
                if (hasValue(R.styleable.KvLayout_kvl_dividerEdgeLeft)) {
                    dividerEdgeLeft =
                        getDimension(R.styleable.KvLayout_kvl_dividerEdgeLeft, DIVIDER_EDGE_LEFT)
                }
                if (hasValue(R.styleable.KvLayout_kvl_dividerEdgeRight)) {
                    dividerEdgeRight =
                        getDimension(R.styleable.KvLayout_kvl_dividerEdgeRight, DIVIDER_EDGE_RIGHT)
                }

                if (hasValue(R.styleable.KvLayout_kvl_valueLines)) {
                    setValueLines(getInt(R.styleable.KvLayout_kvl_valueLines, 0))
                }

                if (hasValue(R.styleable.KvLayout_kvl_valueLength)) {
                    setValueLength(getInt(R.styleable.KvLayout_kvl_valueLength, 0))
                }
                if (hasValue(R.styleable.KvLayout_kvl_startIcon)) {
                    startIcon.setImageResource(
                        getResourceId(
                            R.styleable.KvLayout_kvl_startIcon,
                            NO_ID
                        )
                    )
                    startIcon.visibility = View.VISIBLE
                }
                if (hasValue(R.styleable.KvLayout_kvl_endIcon)) {
                    endIcon.setImageResource(getResourceId(R.styleable.KvLayout_kvl_endIcon, NO_ID))
                    setShowEndIcon(true)
                }

                if (hasValue(R.styleable.KvLayout_kvl_endIconGap)) {
                    (unit.layoutParams as? LayoutParams)?.run {
                        marginEnd = getDimensionPixelSize(R.styleable.KvLayout_kvl_endIconGap, 0)
                    }
                }
                if (hasValue(R.styleable.KvLayout_kvl_startIconGap)) {
                    (key.layoutParams as? LayoutParams)?.run {
                        marginStart =
                            getDimensionPixelSize(R.styleable.KvLayout_kvl_startIconGap, 0)
                    }
                }
                if (hasValue(R.styleable.KvLayout_kvl_unitTextColor)) {
                    unit.setTextColor(getColorStateList(R.styleable.KvLayout_kvl_unitTextColor))
                }

                if (hasValue(R.styleable.KvLayout_kvl_unit)) {
                    unit.text = getString(R.styleable.KvLayout_kvl_unit)
                }
                if (hasValue(R.styleable.KvLayout_kvl_valueGravity)) {
                    val gravity = getInt(R.styleable.KvLayout_kvl_valueGravity, 0)
                    setGravity(value, gravity)
                }

                if (hasValue(R.styleable.KvLayout_kvl_keyGravity)) {
                    val gravity = getInt(R.styleable.KvLayout_kvl_keyGravity, 0)
                    setGravity(key, gravity)
                }

                if (hasValue(R.styleable.KvLayout_kvl_unitGravity)) {
                    val gravity = getInt(R.styleable.KvLayout_kvl_unitGravity, 0)
                    setGravity(unit, gravity)
                }

                if (hasValue(R.styleable.KvLayout_kvl_keyDrawablePadding)) {
                    val keyDrawablePadding =
                        getLayoutDimension(R.styleable.KvLayout_kvl_keyDrawablePadding, 0)
                    setKeyDrawablePadding(keyDrawablePadding)
                }
                if (hasValue(R.styleable.KvLayout_kvl_keyDrawableLeft)) {
                    val drawable = getDrawable(R.styleable.KvLayout_kvl_keyDrawableLeft)
                    setKeyDrawableLeft(drawable)
                }
                if (hasValue(R.styleable.KvLayout_kvl_keyDrawableRight)) {
                    val drawable = getDrawable(R.styleable.KvLayout_kvl_keyDrawableRight)
                    setKeyDrawableRight(drawable)
                }
                if (hasValue(R.styleable.KvLayout_kvl_keyDrawableTop)) {
                    val drawable = getDrawable(R.styleable.KvLayout_kvl_keyDrawableTop)
                    setKeyDrawableTop(drawable)
                }
                if (hasValue(R.styleable.KvLayout_kvl_keyDrawableBottom)) {
                    val drawable = getDrawable(R.styleable.KvLayout_kvl_keyDrawableBottom)
                    setKeyDrawableBottom(drawable)
                }

                if (hasValue(R.styleable.KvLayout_kvl_valueOnlyNumberLetter)) {
                    val valueOnlyNumberLetter =
                        getBoolean(R.styleable.KvLayout_kvl_valueOnlyNumberLetter, false)
                    setValueOnlyNumberLetter(valueOnlyNumberLetter)
                }


                if (hasValue(R.styleable.KvLayout_kvl_valueDecimalCount)) {
                    setValueNumberDecimal()
                    val count = getInt(R.styleable.KvLayout_kvl_valueDecimalCount, 0)
                    value.addTextChangedListener(object : EmptyTextWatcher(value) {
                        var selectionEnd = 0
                        override fun beforeTextChanged(
                            s: CharSequence,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                            selectionEnd = value.selectionEnd
                        }

                        override fun afterTextChanged(s: Editable) {
                            val ss = value.text
                            val dotIndex = ss.indexOf(".")
                            if (dotIndex >= 0 && dotIndex < ss.length - 1 - count) {
                                val result = ss.subSequence(0, dotIndex + count + 1)
                                setText(result.toString())
                                (value.text as? Spannable)?.let { spanable ->
                                    Selection.setSelection(spanable, selectionEnd)
                                }
                            }
                        }
                    })
                }
                recycle()
            }

        }
    }

    private fun setValueOnlyNumberLetter(valueOnlyNumberLetter: Boolean) {
        val filter =
            DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
        if (valueOnlyNumberLetter) {
            if (value.filters.contains(filter)) {
                return
            }
            value.filters = value.filters.plus(filter)
        } else {
            if (!value.filters.contains(filter)) {
                return
            }

            value.filters = value.filters.toList().filter { it != filter }.toTypedArray()
        }

    }

    fun setKeyDrawablePadding(padding: Int) {
        key.compoundDrawablePadding = padding
    }

    fun setKeyDrawableLeft(drawable: Drawable) {
        key.setDrawableLeft(drawable)
    }

    fun setKeyDrawableRight(drawable: Drawable) {
        key.setDrawableRight(drawable)
    }

    fun setKeyDrawableTop(drawable: Drawable) {
        key.setDrawableTop(drawable)
    }

    fun setKeyDrawableBottom(drawable: Drawable) {
        key.setDrawableBottom(drawable)
    }

    private fun setGravity(unit: TextView, gravity: Int) {
        val lp = unit.layoutParams as LayoutParams
        unit.gravity = gravity
        val absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection)

        when (absoluteGravity and Gravity.VERTICAL_GRAVITY_MASK) {
            Gravity.CENTER_VERTICAL -> {
                lp.topToTop = LayoutParams.PARENT_ID
                lp.bottomToBottom = LayoutParams.PARENT_ID
            }
            Gravity.TOP -> {
                lp.topToTop = LayoutParams.PARENT_ID
                lp.bottomToBottom = LayoutParams.UNSET
            }
            Gravity.BOTTOM -> {
                lp.topToTop = LayoutParams.UNSET
                lp.bottomToBottom = LayoutParams.PARENT_ID
            }
        }
    }

    private fun setDefaultPadding(attr: AttributeSet) {
        if (attr.getAttributeValue(ANDROID_NAMESPACE, "padding") !== null) {
            return
        }
        if (attr.getAttributeValue(ANDROID_NAMESPACE, "paddingHorizontal") == null) {
            if (attr.getAttributeValue(ANDROID_NAMESPACE, "paddingRight") == null
                && attr.getAttributeValue(ANDROID_NAMESPACE, "paddingEnd") == null
            ) {
                setPaddingRight(PADDING)
            }
            if (attr.getAttributeValue(ANDROID_NAMESPACE, "paddingLeft") == null
                && attr.getAttributeValue(ANDROID_NAMESPACE, "paddingStart") == null
            ) {
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


    fun setDividerColor(color: Int) {
        dividerPaint.color = color
    }

    fun setDividerHeight(dimensionPixelSize: Float) {
        dividerPaint.strokeWidth = dimensionPixelSize
    }

    fun setValueEnabled(enabled: Boolean) {
        value.isEnabled = enabled
    }

    fun setHintTextColor(color: Int) {
        value.setHintTextColor(color)
    }

    fun setValueBold(boolean: Boolean) {
        if (boolean) {
            value.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        } else {
            value.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        }
    }


    private fun initView() {
        key = findViewById(R.id.key)
        value = findViewById(R.id.value)
        endIcon = findViewById(R.id.endIcon)
        startIcon = findViewById(R.id.startIcon)
        unit = findViewById(R.id.unit)
    }

    open fun getLayoutId(): Int {
        return R.layout.kvl_ooftf_layout_kv
    }

    fun setValueNumberDecimal() {
        value.inputType = EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL
    }

    fun setValueNumber() {
        value.inputType = EditorInfo.TYPE_CLASS_NUMBER
    }

    fun setKey(text: CharSequence) {
        key.text = text
    }

    fun setKeyWidth(px: Float) {
        key.layoutParams.width = px.toInt()
    }

    fun setValue(text: CharSequence?) {
        value.text = text
    }

    fun setHint(text: CharSequence) {
        value.hint = text
    }

    fun setValueLines(line: Int) {
        if (line == 1) {
            value.setSingleLine()
        } else {
            value.setLines(line)
        }

        value.ellipsize = TextUtils.TruncateAt.END
    }

    fun setValueLength(length: Int) {
        value.filters =
            value.filters.toList().filter { it !is LengthFilter }.toMutableList().apply {
                add(LengthFilter(length))
            }.toTypedArray()
    }

    fun setTextSize(px: Float) {
        key.setTextSize(TypedValue.COMPLEX_UNIT_PX, px)
        value.setTextSize(TypedValue.COMPLEX_UNIT_PX, px)
        unit.setTextSize(TypedValue.COMPLEX_UNIT_PX, px)
    }

    val textWatcher by lazy {
        val result = TextWatcher()
        value.addTextChangedListener(result)
        result
    }

    fun setValueHintTextSize(px: Float) {
        textWatcher.hintSize = px
        afterTextChanged(textWatcher.hintSize)
    }

    fun setKeyTextColor(color: ColorStateList?) {
        key.setTextColor(color)
    }

    fun setValueTextColor(color: ColorStateList?) {
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

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        //解决EditText作为RecyclerView的最后一行时，输入换行，在某些机型（华为）崩溃的问题
        try {
            return super.dispatchKeyEvent(event)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true

    }

    companion object {

        const val ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android"
    }

    inner class TextWatcher(
        var hintSize: Float = key.textSize
    ) : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            afterTextChanged(hintSize)
        }
    }

    private fun afterTextChanged(hintSize: Float) {
        if (value.text.isNullOrEmpty()) {
            value.setTextSize(TypedValue.COMPLEX_UNIT_PX, hintSize)
        } else {
            value.setTextSize(TypedValue.COMPLEX_UNIT_PX, key.textSize)
        }
    }

}

