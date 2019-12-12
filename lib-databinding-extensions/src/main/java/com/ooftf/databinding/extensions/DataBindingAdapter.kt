package com.ooftf.databinding.extensions

import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/12/12
 */
object DataBindingAdapter {
    @BindingAdapter(value = ["exOnClick"], requireAll = false)
    fun setOnClick(view: View, listener: View.OnClickListener?) {
        listener?.let {
            view.setOnClickListener(AntiOnClickListener(it))
        }
    }

    @BindingAdapter(value = ["exUrl"], requireAll = false)
    fun setUrl(imageView: ImageView, url: String?) {
        if (url != null) {
            Glide.with(imageView).load(url).into(imageView)
        }
    }

    @BindingAdapter(value = ["exSrc"])
    fun setImageViewResource(imageView: ImageView, resource: Drawable?) {
        imageView.setImageDrawable(resource)
    }

    @BindingAdapter(value = ["exBackgroundTintColor"], requireAll = false)
    fun setBackgroundTintColor(view: View, color: Int) {
        if (color != 0 && view.background != null) {
            DrawableCompat.setTint(view.background.mutate(), color)
        }
    }

    @BindingAdapter(value = ["exMaxInt"], requireAll = false)
    fun setMaxInt(textView: TextView, max: Int) {
        textView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val s1 = s.toString()
                val anInt = getInt(s1)
                if (anInt > max) {
                    textView.text = max.toString()
                }
            }
        })
    }

    fun getInt(value: String): Int {
        return try {
            Integer.valueOf(value)
        } catch (e: Exception) {
            Log.e("stringUtil", "$value--> Integer is error")
            0
        }
    }
}