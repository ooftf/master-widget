package com.ooftf.arch.frame.mvvm.utils

import androidx.databinding.ObservableArrayList

class ObservableArrayListPro<T> : ObservableArrayList<T>() {
    fun notifyChange(item: T) {
        notifyChange(indexOf(item))
    }

    fun notifyChange(index: Int) {
        set(index, get(index))
    }

    fun notifyChange() {
        val arrayList = ArrayList(this)
        clear()
        addAll(arrayList)
    }
}