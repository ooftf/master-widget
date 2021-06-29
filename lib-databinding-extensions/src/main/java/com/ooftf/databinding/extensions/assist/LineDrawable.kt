package com.ooftf.databinding.extensions.assist

import android.graphics.*
import android.graphics.drawable.Drawable

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/6/28
 */
class LineDrawable : Drawable() {
    var leftPadding = 0;
    var rightPadding = 0;
    var mPaint = Paint()
    var mShow = true

    init {
        mPaint.color = Color.parseColor("#EEEEEE")
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = 1F
    }

    override fun draw(canvas: Canvas) {
        if (!mShow) {
            return
        }
        val y = bounds.bottom.toFloat() - mPaint.strokeWidth
        canvas.drawLine(
            bounds.left + leftPadding.toFloat(), y,
            (bounds.right - rightPadding).toFloat(), y, mPaint
        )
    }

    fun setWidth(width: Float) {
        mPaint.strokeWidth = width
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    fun setShow(show: Boolean) {
        if (mShow == show) {
            return
        }
        mShow = show
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    fun setColor(color: Int) {
        mPaint.color = color
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

}