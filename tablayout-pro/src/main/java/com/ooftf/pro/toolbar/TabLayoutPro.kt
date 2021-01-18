package com.ooftf.pro.toolbar

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.ooftf.basic.utils.DensityUtil
import com.ooftf.basic.utils.reflectGetField

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/1/6
 */
open class TabLayoutPro : TabLayout {

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        obtainStyle(attrs)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        obtainStyle(attrs)

    }


    fun getTabText(tab: Tab?): TextView? {
        return (tab?.view?.reflectGetField("textView") as? TextView)
    }

    private fun boldChange() {
        addOnTabSelectedListener(object : OnTabSelectedListener {
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

        })
    }

    fun getShapeIndicator(): GradientDrawable? {
        return ((tabSelectedIndicator as? LayerDrawable)?.getDrawable(0) as? GradientDrawable)
    }

    fun obtainStyle(attrs: AttributeSet) {
        val tabLayoutStyle = context.obtainStyledAttributes(attrs, R.styleable.TabLayout)
        val tabLayoutProStyle = context.obtainStyledAttributes(attrs, R.styleable.TabLayoutPro)
        if (tabLayoutProStyle.hasValue(R.styleable.TabLayoutPro_tabIndicatorWidth)) {
            setSelectedTabIndicator(R.drawable.ooftf_tablayout_pro_indicator)
            if (tabLayoutStyle.hasValue(com.google.android.material.R.styleable.TabLayout_tabIndicatorHeight)) {
                getShapeIndicator()?.setSize(tabLayoutProStyle.getDimensionPixelSize(R.styleable.TabLayoutPro_tabIndicatorWidth, -1), tabLayoutStyle.getDimensionPixelSize(R.styleable.TabLayout_tabIndicatorHeight, -1))
            } else {
                getShapeIndicator()?.setSize(tabLayoutProStyle.getDimensionPixelSize(R.styleable.TabLayoutPro_tabIndicatorWidth, -1), DensityUtil.dip2pxInt(4f))
            }

            if (tabLayoutProStyle.hasValue(R.styleable.TabLayoutPro_tabIndicatorRadius)) {
                getShapeIndicator()?.cornerRadius = tabLayoutProStyle.getDimensionPixelSize(R.styleable.TabLayoutPro_tabIndicatorRadius, -1).toFloat()
            }
        }
        if (tabLayoutProStyle.hasValue(R.styleable.TabLayoutPro_tabTextBoldChange)) {
            if (tabLayoutProStyle.getBoolean(R.styleable.TabLayoutPro_tabTextBoldChange, false)) {
                boldChange()
            }
        }

        tabLayoutStyle.recycle()
        tabLayoutProStyle.recycle()
    }
}
