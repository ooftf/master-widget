package com.ooftf.pro.toolbar

import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import com.ooftf.basic.utils.DensityUtil

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/6/29
 */
class IndicatorDrawable : Drawable() {
    var width = DensityUtil.dip2px(32f)
        set(value) {
            field = value
            invalidateSelf()
        }
    var height = DensityUtil.dip2px(4f)
        set(value) {
            field = value
            invalidateSelf()
        }
    var cornerRadius = DensityUtil.dip2px(4f)
        set(value) {
            field = value
            invalidateSelf()
        }
    val mPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    var mBlendModeColorFilter: BlendModeColorFilter? = null
    override fun draw(canvas: Canvas) {
        val r = RectF()
        r.left = bounds.exactCenterX() - (width / 2)
        r.right = r.left + width
        r.bottom = bounds.bottom.toFloat()
        r.top = r.bottom - height
        canvas.drawRoundRect(r, cornerRadius, cornerRadius, mPaint)
    }

    override fun getIntrinsicHeight(): Int {
        return height.toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return width.toInt()
    }

    override fun setBounds(bounds: Rect?) {
        super.setBounds(bounds)
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    fun setColor(color: Int) {
        mPaint.color = color
    }

    override fun setTint(tintColor: Int) {
        mPaint.colorFilter  = PorterDuffColorFilter(tintColor,PorterDuff.Mode.SRC_IN)
        super.setTint(tintColor)
    }


    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

}