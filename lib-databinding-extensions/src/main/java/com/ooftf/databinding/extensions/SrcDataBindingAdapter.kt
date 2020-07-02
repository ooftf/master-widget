package com.ooftf.databinding.extensions

import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.TouchDelegate
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/12/12
 */
object SrcDataBindingAdapter {







    @JvmStatic
    @BindingAdapter(value = ["exSrc", "exSrcDefault"], requireAll = true)
    fun setImageViewSrc(imageView: ImageView, url: String?, resourceId: Drawable) {
        Glide.with(imageView).load(url).placeholder(resourceId).error(resourceId).placeholder(resourceId).into(imageView)
    }

    @JvmStatic
    @BindingAdapter(value = ["exSrc"])
    fun setImageViewSrc(view: ImageView, uri: Uri?) {
        Glide.with(view).load(uri).into(view)
    }

    @JvmStatic
    @BindingAdapter(value = ["exSrc"], requireAll = false)
    fun setImageViewSrc(imageView: ImageView, url: String?) {
        Glide.with(imageView).load(url).into(imageView)
    }

    @JvmStatic
    @BindingAdapter(value = ["exSrc"])
    fun setImageViewDrawable(imageView: ImageView, resource: Drawable?) {
        imageView.setImageDrawable(resource)
    }

    @JvmStatic
    @BindingAdapter(value = ["exSrc"])
    fun setImageViewSrc(imageView: ImageView, resource: Int?) {
        if (resource == null || resource == 0) {
            imageView.setImageDrawable(null)
        } else {
            imageView.setImageResource(resource)
        }

    }
}