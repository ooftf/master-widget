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
class ListBlurDialog(activity: Activity) : BlurDialog(activity), ListDialogInterface {
    var delegate: ListDialogDelegate = ListDialogDelegate(this, activity)
    fun setItemViewAdapter(adapter: ((textView: TextView, position: Int) -> Unit)) {
        delegate.viewAdapter = adapter
    }

    override fun setShowCancel(showCancel: Boolean): ListBlurDialog {
        return delegate.setShowCancel(showCancel) as ListBlurDialog
    }

    override fun setList(data: List<String>): ListBlurDialog {
        return delegate.setList(data) as ListBlurDialog
    }

    override fun setOnItemClickListener(listener: DialogOnItemClickListener): ListBlurDialog {
        return delegate.setOnItemClickListener(listener) as ListBlurDialog
    }

}