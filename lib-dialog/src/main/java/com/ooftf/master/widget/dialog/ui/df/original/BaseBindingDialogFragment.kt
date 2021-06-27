package com.ooftf.master.widget.dialog.ui.df.original

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.ooftf.basic.utils.getGenericParamType

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/5/21
 */
abstract class BaseBindingDialogFragment<B : ViewBinding> :
    BaseDialogFragment() {
    lateinit var binding: B

    /**
     * 如果报异常代表泛型设置有问题
     */
    private fun getBClass(): Class<B> {
        return this.getGenericParamType(0) as Class<B>
    }

    override fun inflateView(
        inflater: LayoutInflater,
        savedInstanceState: Bundle?
    ): View {
        val bClass = getBClass()
        val method = bClass.getMethod(
            "inflate",
            LayoutInflater::class.java
        )
        binding = method.invoke(null, inflater) as B
        return binding.root
    }
}