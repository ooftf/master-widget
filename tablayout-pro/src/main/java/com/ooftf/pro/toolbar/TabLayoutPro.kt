package com.ooftf.pro.toolbar

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.ooftf.basic.utils.reflectGetField

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/1/6
 */
open class TabLayoutPro : TabLayout {
    val listener by lazy {
        object : OnTabSelectedListener {
            override fun onTabReselected(tab: Tab?) {

            }

            override fun onTabUnselected(tab: Tab?) {
                getTabText(tab)?.let {
                    it.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                }
            }

            override fun onTabSelected(tab: Tab?) {
                getTabText(tab)?.let {
                    it.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                }
            }

        }
    }

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.let { obtainStyle(it) }


    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        attrs?.let { obtainStyle(it) }
    }


    fun getTabText(tab: Tab?): TextView? {
        return (tab?.view?.reflectGetField("textView") as? TextView)
    }

    fun boldChange(change: Boolean) {
        if (change) {
            addOnTabSelectedListener(listener)
        } else {
            removeOnTabSelectedListener(listener)
        }
    }

    fun setIndicatorHeight(height: Int) {
        var indicator = tabSelectedIndicator as? IndicatorDrawable
        if (indicator == null) {
            indicator = IndicatorDrawable()
            setSelectedTabIndicator(indicator)
        }
        indicator.height = height.toFloat()
    }

    fun setIndicatorWidth(width: Int) {
        var indicator = tabSelectedIndicator as? IndicatorDrawable
        if (indicator == null) {
            indicator = IndicatorDrawable()
            setSelectedTabIndicator(indicator)
        }
        indicator.width = width.toFloat()
    }

    fun setIndicatorRadius(radius: Int) {
        var indicator = tabSelectedIndicator as? IndicatorDrawable
        if (indicator == null) {
            indicator = IndicatorDrawable()
            setSelectedTabIndicator(indicator)
        }
        indicator.cornerRadius = radius.toFloat()
    }

    fun obtainStyle(attrs: AttributeSet) {
        val tabLayoutStyle = context.obtainStyledAttributes(attrs, R.styleable.TabLayout)
        val tabLayoutProStyle = context.obtainStyledAttributes(attrs, R.styleable.TabLayoutPro)
        if (tabLayoutProStyle.hasValue(R.styleable.TabLayoutPro_tabIndicatorWidth)) {
            if (tabLayoutStyle.hasValue(com.google.android.material.R.styleable.TabLayout_tabIndicatorHeight)) {
                setIndicatorHeight(
                    tabLayoutStyle.getDimensionPixelSize(
                        R.styleable.TabLayout_tabIndicatorHeight,
                        -1
                    )
                )
            }
            if (tabLayoutStyle.hasValue(R.styleable.TabLayoutPro_tabIndicatorWidth)) {
                setIndicatorWidth(
                    tabLayoutProStyle.getDimensionPixelSize(
                        R.styleable.TabLayoutPro_tabIndicatorWidth,
                        -1
                    )
                )
            }

            if (tabLayoutProStyle.hasValue(R.styleable.TabLayoutPro_tabIndicatorRadius)) {
                setIndicatorRadius(
                    tabLayoutProStyle.getDimensionPixelSize(
                        R.styleable.TabLayoutPro_tabIndicatorRadius,
                        -1
                    )
                )
            }
        }
        if (tabLayoutProStyle.hasValue(R.styleable.TabLayoutPro_tabTextBoldChange)) {
            boldChange(
                tabLayoutProStyle.getBoolean(
                    R.styleable.TabLayoutPro_tabTextBoldChange,
                    false
                )
            )
        }

        tabLayoutStyle.recycle()
        tabLayoutProStyle.recycle()
    }
}
