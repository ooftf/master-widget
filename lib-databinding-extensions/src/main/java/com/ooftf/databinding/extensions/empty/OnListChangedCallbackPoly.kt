package com.ooftf.databinding.extensions.empty

import androidx.databinding.ObservableList
import java.lang.ref.WeakReference

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/11/6
 */
open class OnListChangedCallbackPoly<T : ObservableList<*>>(action: (T) -> Unit) : ObservableList.OnListChangedCallback<T>() {
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