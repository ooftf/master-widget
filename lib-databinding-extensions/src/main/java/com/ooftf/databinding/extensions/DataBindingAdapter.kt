package com.ooftf.databinding.extensions

import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.TouchDelegate
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/12/12
 */
object DataBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["exOnClick"], requireAll = false)
    fun setOnClick(view: View, listener: View.OnClickListener?) {
        listener?.let {
            view.setOnClickListener(AntiOnClickListener(it))
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["exRoutePath"], requireAll = false)
    fun exRoutePath(view: View, path: String?) {
        path?.let {
            view.setOnClickListener(AntiOnClickListener(View.OnClickListener {
                try {
                    com.alibaba.android.arouter.launcher.ARouter.getInstance().build(path).navigation()
                } catch (e: ClassNotFoundException) {
                    throw Exception("exStartPath   只支持ARouter 路由系统")
                }
            }))
        }
    }



    @JvmStatic
    @BindingAdapter(value = ["exClickAreaExpand"])
    fun setClickAreaExpand(view: View, expandDp: Int) {
        (view.parent as? View)?.let { parentView ->
            val expandPx = DensityUtil.dip2px(view.context, expandDp.toFloat()).toInt()
            parentView.post {
                val rect = Rect();
                view.getHitRect(rect);
                rect.top -= expandPx;
                rect.bottom += expandPx;
                rect.left -= expandPx;
                rect.right += expandPx;
                parentView.touchDelegate = TouchDelegate(rect, view);
            }
        }

    }


}