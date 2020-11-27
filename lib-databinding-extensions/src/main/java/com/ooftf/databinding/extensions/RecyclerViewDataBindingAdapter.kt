package com.ooftf.databinding.extensions

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ooftf.basic.utils.scrollPositionToCenter
import com.ooftf.basic.utils.scrollPositionToFirst

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

    @JvmStatic
    @BindingAdapter(value = ["exLayoutManager"])
    fun setLayoutManager(view: RecyclerView, layoutManager: RecyclerView.LayoutManager?) {
        view.layoutManager = layoutManager
    }


    @JvmStatic
    @BindingAdapter(value = ["exFirstPosition"])
    fun exTopPosition(view: RecyclerView, position: Int?) {
        position?.let {
            view.scrollPositionToFirst(position)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["exCenterPosition"])
    fun exCenterPosition(view: RecyclerView, position: Int?) {
        position?.let {
            view.scrollPositionToCenter(position)
        }
    }

}