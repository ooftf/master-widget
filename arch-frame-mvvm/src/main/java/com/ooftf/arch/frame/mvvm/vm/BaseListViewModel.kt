package com.ooftf.arch.frame.mvvm.vm
import android.app.Application
import android.view.View
import com.ooftf.arch.frame.mvvm.BR
import com.ooftf.arch.frame.mvvm.utils.ObservableArrayListPro
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/12/30
 */
abstract class BaseListViewModel<T>(application: Application) : BaseViewModel(application) {
    val items = ObservableArrayListPro<T>()
    val itemBinding by lazy {
        ItemBinding.of<T>(BR.item, getItemLayout()).bindExtra(BR.viewModel, this)
    }

    fun handleResponseList(data: List<T>) {
        items.addAll(data)
        if (items.isEmpty()) {
            baseLiveData.switchToEmpty()
        }
    }
    open fun onItemClick(v: View, item: T) {}

    abstract fun getItemLayout(): Int


}