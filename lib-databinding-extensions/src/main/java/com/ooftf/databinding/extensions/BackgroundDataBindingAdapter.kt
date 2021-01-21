package com.ooftf.databinding.extensions

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.ooftf.basic.utils.DensityUtil

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/12/12
 */
object BackgroundDataBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["exBackgroundColor", "exBackgroundRadius"], requireAll = true)
    fun setBackgroundDrawable(view: View, color: Int, radius: Number) {
        var drawable = GradientDrawable()
        drawable.setColor(color)
        drawable.cornerRadius = DensityUtil.dip2px(radius.toFloat())
        view.background = drawable
    }

    @JvmStatic
    @BindingAdapter(value = ["exBC", "exBR"], requireAll = true)
    fun setBackgroundDrawableAbb(view: View, color: Int, radius: Float) {
        var drawable = GradientDrawable()
        drawable.setColor(color)
        drawable.cornerRadius = DensityUtil.dip2px(radius)
        view.background = drawable
    }


    @JvmStatic
    @BindingAdapter(value = ["exBackgroundColor", "exBackgroundRadiusTopLeft", "exBackgroundRadiusBottomLeft", "exBackgroundRadiusTopRight", "exBackgroundRadiusBottomRight"], requireAll = false)
    fun setBackgroundDrawable(view: View, color: Int, radiusTopLeft: Float?, radiusBottomLeft: Float?, radiusTopRight: Float?, radiusBottomRight: Float?) {
        var drawable = GradientDrawable()
        drawable.setColor(color)
        //top-left, top-right, bottom-right, bottom-left
        drawable.setCornerRadii(DensityUtil.dip2px(radiusTopLeft ?: 0f),
                DensityUtil.dip2px(radiusTopRight ?: 0f), DensityUtil.dip2px(radiusBottomRight
                ?: 0f), DensityUtil.dip2px(radiusBottomLeft ?: 0f))
        view.background = drawable
    }

    @JvmStatic
    @BindingAdapter(value = ["exBackgroundColor", "exBackgroundRadiusTopLeft", "exBackgroundRadiusBottomLeft", "exBackgroundRadiusTopRight", "exBackgroundRadiusBottomRight"], requireAll = false)
    fun setBackgroundDrawable(view: View, color: String, radiusTopLeft: Float?, radiusBottomLeft: Float?, radiusTopRight: Float?, radiusBottomRight: Float?) {
        var drawable = GradientDrawable()
        drawable.setColor(Color.parseColor(color))
        //top-left, top-right, bottom-right, bottom-left
        drawable.setCornerRadii(DensityUtil.dip2px(radiusTopLeft ?: 0f),
                DensityUtil.dip2px(radiusTopRight ?: 0f),
                DensityUtil.dip2px(radiusBottomRight ?: 0f),
                DensityUtil.dip2px(radiusBottomLeft ?: 0f))
        view.background = drawable
    }

    @JvmStatic
    @BindingAdapter(value = ["exBC", "exBRTL", "exBRBL", "exBRTR", "exBRBR"], requireAll = false)
    fun setBackgroundDrawableAbb(view: View, color: String, radiusTopLeft: Float?, radiusBottomLeft: Float?, radiusTopRight: Float?, radiusBottomRight: Float?) {
        var drawable = GradientDrawable()
        drawable.setColor(Color.parseColor(color))
        //top-left, top-right, bottom-right, bottom-left
        drawable.setCornerRadii(DensityUtil.dip2px(radiusTopLeft ?: 0f),
                DensityUtil.dip2px(radiusTopRight ?: 0f),
                DensityUtil.dip2px(radiusBottomRight ?: 0f),
                DensityUtil.dip2px(radiusBottomLeft ?: 0f))
        view.background = drawable
    }


    @JvmStatic
    @BindingAdapter(value = ["exBackgroundColorId", "exBackgroundRadius"], requireAll = true)
    fun setBackgroundDrawableForId(view: View, colorId: Int, radius: Number) {
        var drawable = GradientDrawable()
        drawable.setColor(ContextCompat.getColor(view.context, colorId))
        drawable.cornerRadius = DensityUtil.dip2px(radius.toFloat())
        view.background = drawable
    }


    @JvmStatic
    @BindingAdapter(value = ["exBackgroundId"], requireAll = false)
    fun setBackgroundId(view: View, id: Int?) {
        id?.let {
            view.setBackgroundResource(it)
        }

    }

    @JvmStatic
    @BindingAdapter(value = ["exBackground"], requireAll = false)
    fun setBackground(view: View, color: Int?) {
        color?.let {
            view.setBackgroundColor(it)
        }

    }

    @JvmStatic
    @BindingAdapter(value = ["exBackground"], requireAll = false)
    fun setBackground(view: View, drawable: Drawable?) {
        view.background = drawable
    }


    @JvmStatic
    @BindingAdapter(value = ["exBackgroundTintColor"], requireAll = false)
    fun setBackgroundTintColor(view: View, color: Int) {
        if (color != 0 && view.background != null) {
            DrawableCompat.setTint(view.background.mutate(), color)
        }
    }


    @JvmStatic
    @BindingAdapter(value = ["exBackgroundColor", "exBackgroundRadius", "exBackgroundStrokeWidth", "exBackgroundStrokeColor"], requireAll = false)
    fun setBackgroundDrawable(view: View, color: String?, radius: Number?, strokeWidth: Number? = 1, strokeColor: String?) {
        var drawable = GradientDrawable()
        color?.let {
            drawable.setColor(Color.parseColor(color))
        }
        radius?.let {
            drawable.cornerRadius = DensityUtil.dip2px(radius.toFloat())
        }
        strokeWidth?.let { strokeWidth_ ->
            strokeColor?.let { strokeColor_ ->
                drawable.setStroke(DensityUtil.dip2pxInt(strokeWidth_.toFloat()), Color.parseColor(strokeColor_))
            }
        }
        view.background = drawable
    }


    @JvmStatic
    @BindingAdapter(value = ["exBC", "exBR", "exBSW", "exBSC"], requireAll = false)
    fun setBackgroundDrawableAbb(view: View, color: String?, radius: Number?, strokeWidth: Number? = 1, strokeColor: String?) {
        var drawable = GradientDrawable()
        color?.let {
            drawable.setColor(Color.parseColor(color))
        }
        radius?.let {
            drawable.cornerRadius = DensityUtil.dip2px(radius.toFloat())
        }
        strokeWidth?.let { strokeWidth_ ->
            strokeColor?.let { strokeColor_ ->
                drawable.setStroke(DensityUtil.dip2pxInt(strokeWidth_.toFloat()), Color.parseColor(strokeColor_))
            }
        }
        view.background = drawable
    }
}

/**
 * 左上，右上，右下，左下
 */
fun GradientDrawable.setCornerRadii(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
    cornerRadii = floatArrayOf(topLeft, topLeft, topRight, topRight,
            bottomRight, bottomRight, bottomLeft, bottomLeft)
}

