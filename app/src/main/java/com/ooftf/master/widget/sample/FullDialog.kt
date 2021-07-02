package com.ooftf.master.widget.sample

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.ooftf.basic.utils.getVisibleRectOfScreen
import com.ooftf.master.widget.dialog.ui.BaseDialog
import com.ooftf.master.widget.dialog.ui.fitNavigationBar
import com.ooftf.master.widget.dialog.ui.lightStatusBar
import com.ooftf.master.widget.dialog.ui.translucentNavigationBar

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/7/1
 */
class FullDialog(context: Context) : BaseDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_my)
        setHeightMatchParent()
        setWidthMatchParent()
        findViewById<View>(R.id.rootView).let {
           it.postDelayed({ printParent(it) },2000)
        }
        setImmersion()
        lightStatusBar(true)
        translucentNavigationBar()
        fitNavigationBar()
    }
    private fun printParent(root: View) {
        val parent = root.parent
        if (parent == null) {
            return
        } else if (parent is ViewGroup) {
            Log.e("printParent",parent.javaClass.simpleName+"" + parent.getVisibleRectOfScreen().toShortString())
            printParent(parent)
        } else {
            Log.e("printParent",parent.javaClass.simpleName)
        }


    }
}