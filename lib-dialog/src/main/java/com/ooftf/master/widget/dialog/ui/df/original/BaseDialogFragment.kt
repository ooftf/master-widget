package com.ooftf.master.widget.dialog.ui.df.original

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/6/23
 */
abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflateView(inflater, savedInstanceState)
        initView(view)
        return view
    }

    abstract fun inflateView(
        inflater: LayoutInflater,
        savedInstanceState: Bundle?
    ): View

    abstract fun initView(view: View)

}