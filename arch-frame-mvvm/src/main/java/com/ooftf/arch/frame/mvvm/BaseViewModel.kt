package com.chaitai.libbase.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import com.ooftf.mapping.lib.ui.BaseLiveData
import io.reactivex.disposables.CompositeDisposable

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/7/23 0023
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var baseLiveData = BaseLiveData()
    var disposables = CompositeDisposable()
    fun getLoadMoreState() = baseLiveData.smartLoadMore
    fun getRefreshState() = baseLiveData.smartRefresh
    fun getStateLayout() = baseLiveData.stateLayout
    var doOnCleared: MutableList<() -> Unit> = ArrayList()
    override fun onCleared() {
        disposables.dispose()
        doOnCleared.forEach {
            it.invoke()
        }
        super.onCleared()
    }
    fun doOnCleared(event:() -> Unit){
        doOnCleared.add(event)
    }
}