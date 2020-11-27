package com.ooftf.databinding.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.ooftf.basic.utils.DensityUtil

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/12/12
 */
object DataBindingAdapter {


    @JvmStatic
    @BindingAdapter(value = ["exTextColor"])
    fun setTextColor(view: TextView, color: String?) {
        color?.let {
            view.setTextColor(Color.parseColor(it))
        }
    }

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
            val expandPx = DensityUtil.dip2px(expandDp.toFloat()).toInt()
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

    /**
     * @{viewModel.itemsDelivery.size()}
     */
    @JvmStatic
    @BindingAdapter(value = ["exChecked"])
    fun setChecked(view: RadioButton, checked: Boolean?) {
        if (checked != null) {
            view.isChecked = checked
        }
    }

    /**
     * 复制文本
     */
    @JvmStatic
    @BindingAdapter(value = ["exCopy"], requireAll = false)
    fun setCopyContent(view: View, content: String?) {
        view.setOnClickListener {
            //获取到服务
            try {
                val systemService =
                        it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                //创建ClipData
                val newPlainText = ClipData.newPlainText("Label", content)
                //设置到剪贴板中
                systemService.setPrimaryClip(newPlainText)
                Toast.makeText(it.context.applicationContext, "复制成功", Toast.LENGTH_SHORT).show()
            } catch (exception: Exception) {
                exception.printStackTrace()
                Toast.makeText(it.context.applicationContext, "复制失败", Toast.LENGTH_SHORT).show()
            }
        }

    }

    @JvmStatic
    @BindingAdapter(value = ["android:visibility"], requireAll = false)
    fun androidVisibility(view: View, visibility: Boolean?) {
        view.visibility = if (visibility == true) {
            View.VISIBLE
        } else {
            View.GONE
        }
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter(value = ["exVisible"], requireAll = false)
    fun exVisible(view: View, visibility: Boolean?) {
        view.visibility = if (visibility == true) {
            View.VISIBLE
        } else {
            View.GONE
        }
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter(value = ["android:background"], requireAll = false)
    fun androidBackground(view: View, color: String?) {
        view.setBackgroundColor(Color.parseColor(color))
    }

}