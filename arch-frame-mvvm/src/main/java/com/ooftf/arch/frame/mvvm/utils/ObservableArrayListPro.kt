package com.ooftf.arch.frame.mvvm.utils

import androidx.databinding.ObservableArrayList

import okhttp3.internal.notify

class ObservableArrayListPro<T> : ObservableArrayList<T>() {
    fun notifyChange(item: T) {
        val index = indexOf(item)
        if (remove(item)) {
            add(index, item)
        }
    }

    fun notifyChange(index: Int) {
        val item = get(index)
        if (remove(item)) {
            add(index, item)
        }
    }

    fun notifyChange() {
        val arrayList = ArrayList(this)
        clear()
        addAll(arrayList)
    }
}