package com.ooftf.master.widget.dialog.ui

import android.app.Activity
import android.widget.TextView
import com.ooftf.master.widget.dialog.delegate.ListDialogDelegate
import com.ooftf.master.widget.dialog.inteface.DialogOnItemClickListener
import com.ooftf.master.widget.dialog.inteface.ListDialogInterface

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2018/10/17 0017
 */
open class ListBlurDialog(activity: Activity) : BlurDialog(activity), ListDialogInterface {
    var delegate: ListDialogDelegate = ListDialogDelegate(this, activity)
    override fun setItemViewAdapter(adapter: ((textView: TextView, position: Int) -> Unit)): ListBlurDialog {
        delegate.setItemViewAdapter(adapter)
        return this
    }

    override fun setShowCancel(showCancel: Boolean): ListBlurDialog {
        delegate.setShowCancel(showCancel)
        return this
    }

    override fun setList(data: List<String>): ListBlurDialog {
        delegate.setList(data)
        return this
    }

    override fun setOnItemClickListener(listener: DialogOnItemClickListener): ListBlurDialog {
        delegate.setOnItemClickListener(listener)
        return this
    }

}