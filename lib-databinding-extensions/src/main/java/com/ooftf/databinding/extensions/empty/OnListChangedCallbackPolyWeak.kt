package com.ooftf.databinding.extensions.empty

import android.view.View
import androidx.databinding.ObservableList
import com.ooftf.basic.utils.genTagId
import java.lang.ref.WeakReference

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/11/6
 */
open class OnListChangedCallbackPolyWeak<T : ObservableList<*>>(bindView: View? = null, action: (T) -> Unit) : ObservableList.OnListChangedCallback<T>() {
    val tagId = genTagId()

    init {
        /**
         * 为了防止因为action只有软引用，而导致回收，而可以选择将action和bindView相互绑定
         */
        bindView?.setTag(tagId, action)
    }

    var weak = WeakReference(action)
    override fun onChanged(sender: T) {
        weak.get()?.invoke(sender)
    }

    override fun onItemRangeChanged(sender: T, positionStart: Int, itemCount: Int) {
        weak.get()?.invoke(sender)
    }

    override fun onItemRangeInserted(sender: T, positionStart: Int, itemCount: Int) {
        weak.get()?.invoke(sender)
    }

    override fun onItemRangeMoved(sender: T, fromPosition: Int, toPosition: Int, itemCount: Int) {
        weak.get()?.invoke(sender)
    }

    override fun onItemRangeRemoved(sender: T, positionStart: Int, itemCount: Int) {
        weak.get()?.invoke(sender)
    }

    fun setAction(action: (T) -> Unit) {
        weak = WeakReference(action)
    }
}