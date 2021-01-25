package com.ooftf.databinding.extensions

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
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
        try {
            Glide.with(imageView).load(url).placeholder(resourceId).error(resourceId).placeholder(resourceId).into(imageView)
        } catch (e: Exception) {
            // 主要捕获activity已销毁和fragment未attach的异常
            e.printStackTrace()
        }


    }

    @JvmStatic
    @BindingAdapter(value = ["exSrc", "exSrcDefault", "exSrcSize"], requireAll = false)
    fun setImageViewSrc(imageView: ImageView, url: String?, resourceId: Drawable?, size: Int?) {
        try {
            Glide.with(imageView).load(url).apply {
                resourceId?.let {
                    this.error(resourceId).placeholder(resourceId)
                }
                size?.let {
                    this.override(it, it)
                }
            }.into(imageView)


        } catch (e: Exception) {
            // 主要捕获activity已销毁和fragment未attach的异常
            e.printStackTrace()
        }

    }


    @JvmStatic
    @BindingAdapter(value = ["exSrc"])
    fun setImageViewSrc(view: ImageView, uri: Uri?) {
        try {
            Glide.with(view).load(uri).into(view)
        } catch (e: Exception) {
            // 主要捕获activity已销毁和fragment未attach的异常
            e.printStackTrace()
        }


    }

    @JvmStatic
    @BindingAdapter(value = ["exSrc"], requireAll = false)
    fun setImageViewSrc(imageView: ImageView, url: String?) {
        try {
            Glide.with(imageView).load(url).into(imageView)
        } catch (e: Exception) {
            // 主要捕获activity已销毁和fragment未attach的异常
            e.printStackTrace()
        }
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