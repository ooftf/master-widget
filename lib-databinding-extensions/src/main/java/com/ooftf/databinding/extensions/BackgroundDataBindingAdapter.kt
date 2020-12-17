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
    fun setBackgroundDrawable(view: View, color: Int, radius: Float) {
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
        drawable.cornerRadii = floatArrayOf(radiusTopLeft ?: 0f, radiusTopRight
                ?: 0f, radiusBottomRight ?: 0f, radiusBottomLeft ?: 0f)
        view.background = drawable
    }

    @JvmStatic
    @BindingAdapter(value = ["exBackgroundColor", "exBackgroundRadiusTopLeft", "exBackgroundRadiusBottomLeft", "exBackgroundRadiusTopRight", "exBackgroundRadiusBottomRight"], requireAll = false)
    fun setBackgroundDrawable(view: View, color: String, radiusTopLeft: Float?, radiusBottomLeft: Float?, radiusTopRight: Float?, radiusBottomRight: Float?) {
        var drawable = GradientDrawable()
        drawable.setColor(Color.parseColor(color))
        //top-left, top-right, bottom-right, bottom-left
        drawable.cornerRadii = floatArrayOf(radiusTopLeft ?: 0f, radiusTopRight
                ?: 0f, radiusBottomRight ?: 0f, radiusBottomLeft ?: 0f)
        view.background = drawable
    }

    @JvmStatic
    @BindingAdapter(value = ["exBackgroundColor", "exBackgroundRadius"], requireAll = true)
    fun setBackgroundDrawable(view: View, color: String, radius: Float) {
        var drawable = GradientDrawable()
        drawable.setColor(Color.parseColor(color))
        drawable.cornerRadius = DensityUtil.dip2px(radius)
        view.background = drawable
    }

    @JvmStatic
    @BindingAdapter(value = ["exBackgroundColorId", "exBackgroundRadius"], requireAll = true)
    fun setBackgroundDrawableForId(view: View, colorId: Int, radius: Float) {
        var drawable = GradientDrawable()
        drawable.setColor(ContextCompat.getColor(view.context, colorId))
        drawable.cornerRadius = DensityUtil.dip2px(radius)
        view.background = drawable
    }


    @JvmStatic
    @BindingAdapter(value = ["exBackgroundId"], requireAll = false)
    fun setBackgroundColorId(view: View, id: Int?) {
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
}