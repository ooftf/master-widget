package com.ooftf.databinding.extensions

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ooftf.basic.utils.getActivity

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/12/12
 */
object SrcDataBindingAdapter {


    @JvmStatic
    @BindingAdapter(value = ["exSrc", "exSrcDefault"], requireAll = true)
    fun setImageViewSrc(imageView: ImageView, url: String?, resourceId: Drawable) {
        val activity = imageView.context.getActivity()
        if (activity == null || !activity.isDestroyed) {
            Glide.with(imageView).load(url).placeholder(resourceId).error(resourceId).placeholder(resourceId).into(imageView)
        }

    }

    @JvmStatic
    @BindingAdapter(value = ["exSrc"])
    fun setImageViewSrc(view: ImageView, uri: Uri?) {
        val activity = view.context.getActivity()
        if (activity == null || !activity.isDestroyed) {
            Glide.with(view).load(uri).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["exSrc"], requireAll = false)
    fun setImageViewSrc(imageView: ImageView, url: String?) {
        val activity = imageView.context.getActivity()
        if (activity == null || !activity.isDestroyed) {
            Glide.with(imageView).load(url).into(imageView)
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