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
open class ListDialog(activity: Activity) : BottomDialog(activity), ListDialogInterface {
    var dialogDelegate: ListDialogDelegate = ListDialogDelegate(this, activity)
    override fun setItemViewAdapter(adapter: ((textView: TextView, position: Int) -> Unit)): ListDialog {
        dialogDelegate.setItemViewAdapter(adapter)
        return this
    }

    override fun setShowCancel(showCancel: Boolean): ListDialog {
        dialogDelegate.setShowCancel(showCancel)
        return this
    }

    override fun setList(data: List<String>): ListDialog {
        dialogDelegate.setList(data)
        return this
    }

    override fun setOnItemClickListener(listener: DialogOnItemClickListener): ListDialog {
        dialogDelegate.setOnItemClickListener(listener)
        return this
    }

}