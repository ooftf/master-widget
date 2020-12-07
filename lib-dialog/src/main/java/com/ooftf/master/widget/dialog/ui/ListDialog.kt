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
class ListDialog(activity: Activity) : BottomDialog(activity), ListDialogInterface {
    var dialogDelegate: ListDialogDelegate = ListDialogDelegate(this, activity)
    fun setItemViewAdapter(adapter: ((textView: TextView, position: Int) -> Unit)) {
        dialogDelegate.viewAdapter = adapter
    }
    override fun setShowCancel(showCancel: Boolean): ListDialogInterface {
        return dialogDelegate.setShowCancel(showCancel)
    }

    override fun setList(data: List<String>): ListDialogInterface {
        return dialogDelegate.setList(data)
    }

    override fun setOnItemClickListener(listener: DialogOnItemClickListener): ListDialogInterface {
        return dialogDelegate.setOnItemClickListener(listener)
    }

}