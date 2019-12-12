package com.ooftf.databinding.extensions

import android.view.View

/**
 * Created by 99474 on 2017/11/17 0017.
 */
open class AntiOnClickListener(var src: View.OnClickListener) : View.OnClickListener {

    var last = 0L

    override fun onClick(view: View) {
        var current = System.currentTimeMillis()
        if (current - last > getAntiMillis()) {
            src.onClick(view)
            last = current
        }
    }

    open fun getAntiMillis(): Long = 1000
}