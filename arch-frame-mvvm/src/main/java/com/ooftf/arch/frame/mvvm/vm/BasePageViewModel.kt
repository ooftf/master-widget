package com.ooftf.arch.frame.mvvm.vm;

import android.app.Application

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/12/30
 */
abstract class BasePageViewModel<T>(application: Application) : BaseListViewModel<T>(application) {


    fun refresh() {
        requestData(getStartPage())
    }

    fun nextPage() {
        requestData(items.size / getPageCount() + getStartPage())
    }

    open fun getStartPage() = 0

    open fun getPageCount(): Int = 10
    open fun requestData(page: Int) {

    }

    fun handleResponseList(page: Int, total: Int, data: List<T>) {
        if (page == getStartPage()) {
            items.clear()
        }
        handleResponseList(data)

        if (items.size >= total) {
            baseLiveData.finishLoadMoreWithNoMoreData()
        }
    }

    fun handleResponseList(page: Int, data: List<T>) {
        if (page == getStartPage()) {
            items.clear()
        }
        handleResponseList(data)
        if (data.size < getPageCount()) {
            baseLiveData.finishLoadMoreWithNoMoreData()
        }
    }
}