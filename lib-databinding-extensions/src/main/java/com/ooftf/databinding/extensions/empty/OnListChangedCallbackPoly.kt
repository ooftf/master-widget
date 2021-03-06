package com.ooftf.databinding.extensions.empty

import androidx.databinding.ObservableList

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/11/6
 */
open class OnListChangedCallbackPoly<T : ObservableList<*>>(var action: ((T) -> Unit)? = null,
                                                            var onChanged: ((T) -> Unit)? = null,
                                                            var onItemRangeChanged: ((sender: T, positionStart: Int, itemCount: Int) -> Unit)? = null,
                                                            var onItemRangeInserted: ((sender: T, positionStart: Int, itemCount: Int) -> Unit)? = null,
                                                            var onItemRangeMoved: ((sender: T, fromPosition: Int, toPosition: Int, itemCount: Int) -> Unit)? = null,
                                                            var onItemRangeRemoved: ((sender: T, positionStart: Int, itemCount: Int) -> Unit)? = null) : ObservableList.OnListChangedCallback<T>() {

    override fun onChanged(sender: T) {
        action?.invoke(sender)
        onChanged?.invoke(sender)
    }

    override fun onItemRangeChanged(sender: T, positionStart: Int, itemCount: Int) {
        action?.invoke(sender)
        onItemRangeChanged?.invoke(sender, positionStart, itemCount)
    }

    override fun onItemRangeInserted(sender: T, positionStart: Int, itemCount: Int) {
        action?.invoke(sender)
        onItemRangeInserted?.invoke(sender, positionStart, itemCount)
    }

    override fun onItemRangeMoved(sender: T, fromPosition: Int, toPosition: Int, itemCount: Int) {
        action?.invoke(sender)
        onItemRangeMoved?.invoke(sender, fromPosition, toPosition, itemCount)
    }

    override fun onItemRangeRemoved(sender: T, positionStart: Int, itemCount: Int) {
        action?.invoke(sender)
        onItemRangeRemoved?.invoke(sender, positionStart, itemCount)
    }

}