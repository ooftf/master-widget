package com.ooftf.databinding.extensions

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/12/12
 */
object DrawableDataBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["exDrawableStart"], requireAll = true)
    fun setDrawableStart(view: TextView, drawable: Drawable?) {
        view.setCompoundDrawablesWithIntrinsicBounds(drawable, view.compoundDrawables[1], view.compoundDrawables[2], view.compoundDrawables[3])
    }

    @JvmStatic
    @BindingAdapter(value = ["exDrawableTop"], requireAll = true)
    fun setDrawableTop(view: TextView, drawable: Drawable?) {
        view.setCompoundDrawablesWithIntrinsicBounds(view.compoundDrawables[0], drawable, view.compoundDrawables[2], view.compoundDrawables[3])
    }

    @JvmStatic
    @BindingAdapter(value = ["exDrawableEnd"], requireAll = true)
    fun setDrawableEnd(view: TextView, drawable: Drawable?) {
        view.setCompoundDrawablesWithIntrinsicBounds(view.compoundDrawables[0], view.compoundDrawables[1], drawable, view.compoundDrawables[3])
    }

    @JvmStatic
    @BindingAdapter(value = ["exDrawableBottom"], requireAll = true)
    fun setDrawableBottom(view: TextView, drawable: Drawable?) {
        view.setCompoundDrawablesWithIntrinsicBounds(view.compoundDrawables[0], view.compoundDrawables[1], view.compoundDrawables[2], drawable)
    }


    @JvmStatic
    @BindingAdapter(value = ["exDrawableStartId"], requireAll = true)
    fun setDrawableStart(view: TextView, drawableId: Int?) {
        val drawable = if(drawableId !=null){
            view.context.resources.getDrawable(drawableId)
        }else{
            null
        }
        view.setCompoundDrawablesWithIntrinsicBounds(drawable, view.compoundDrawables[1], view.compoundDrawables[2], view.compoundDrawables[3])
    }

    @JvmStatic
    @BindingAdapter(value = ["exDrawableTopId"], requireAll = true)
    fun setDrawableTop(view: TextView,  drawableId: Int?) {
        val drawable = if(drawableId !=null){
            view.context.resources.getDrawable(drawableId)
        }else{
            null
        }
        view.setCompoundDrawablesWithIntrinsicBounds(view.compoundDrawables[0], drawable, view.compoundDrawables[2], view.compoundDrawables[3])
    }

    @JvmStatic
    @BindingAdapter(value = ["exDrawableEndId"], requireAll = true)
    fun setDrawableEnd(view: TextView, drawableId: Int?) {
        val drawable = if(drawableId !=null){
            view.context.resources.getDrawable(drawableId)
        }else{
            null
        }
        view.setCompoundDrawablesWithIntrinsicBounds(view.compoundDrawables[0], view.compoundDrawables[1], drawable, view.compoundDrawables[3])
    }

    @JvmStatic
    @BindingAdapter(value = ["exDrawableBottomId"], requireAll = true)
    fun setDrawableBottom(view: TextView,  drawableId: Int?) {
        val drawable = if(drawableId !=null){
            view.context.resources.getDrawable(drawableId)
        }else{
            null
        }
        view.setCompoundDrawablesWithIntrinsicBounds(view.compoundDrawables[0], view.compoundDrawables[1], view.compoundDrawables[2], drawable)
    }
}