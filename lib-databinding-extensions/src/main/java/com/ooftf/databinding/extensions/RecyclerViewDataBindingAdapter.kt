package com.ooftf.databinding.extensions

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/9/16
 */
object RecyclerViewDataBindingAdapter {
    /**
     * @{viewModel.itemsDelivery.size()}
     */
    @JvmStatic
    @BindingAdapter(value = ["exSpanCount"])
    fun setSpanCount(view: RecyclerView, text: Int?) {
        if (text != null && text > 0) {
            (view.layoutManager as? GridLayoutManager)?.spanCount = text
        }
    }
}