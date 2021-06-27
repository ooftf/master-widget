package com.ooftf.master.widget.dialog.ui.df.original

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/5/21
 */
abstract class BaseDataBindingDialogFragment<B : ViewDataBinding> : BaseBindingDialogFragment<B>() {
    override fun inflateView(
        inflater: LayoutInflater,
        savedInstanceState: Bundle?
    ): View {
        val inflateView = super.inflateView(inflater, savedInstanceState)
        binding.lifecycleOwner = this
        return inflateView
    }
}