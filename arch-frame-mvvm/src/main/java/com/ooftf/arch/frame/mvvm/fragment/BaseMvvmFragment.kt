package com.ooftf.arch.frame.mvvm.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.ooftf.arch.frame.mvvm.BR
import com.ooftf.arch.frame.mvvm.vm.BaseViewModel
import com.ooftf.mapping.lib.ui.BaseLiveDataObserve
import java.lang.reflect.ParameterizedType

/**
 * 需要对继承ViewDataBinding进行keep
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/10/9
 */
open class BaseMvvmFragment<B : ViewDataBinding, V : BaseViewModel> : BaseLazyFragment() {
    lateinit var binding: B
    var viewModel: V? = null
    lateinit var baseLiveDataObserve: BaseLiveDataObserve

    @CallSuper
    override fun onLoad(rootView: View) {
        viewModel = createViewModel().apply {
            setLifecycleOwner(this@BaseMvvmFragment)
            activity?.let {
                setActivity(it)
            }
            setFragment(this@BaseMvvmFragment)
            binding.setVariable(getVariableId(), viewModel)
            binding.lifecycleOwner = this@BaseMvvmFragment
            baseLiveDataObserve = baseLiveData.attach(this@BaseMvvmFragment)
        }

    }

    open fun createViewModel() =
            ViewModelProvider(this, getViewModelFactory()).get(getVClass())

    override fun getLayoutId(): Int {
        return 0
    }

    open fun getViewModelFactory(): ViewModelProvider.Factory {
        return defaultViewModelProviderFactory
    }

    /**
     * 如果报异常代表泛型设置有问题
     */
    private fun getVClass(): Class<V> {
        val superClass = this.javaClass.genericSuperclass
        val pt = superClass as ParameterizedType
        return pt.actualTypeArguments[1] as Class<V>
    }

    open fun getVariableId() = BR.viewModel

    /**
     * 如果报异常代表泛型设置有问题
     */
    private fun getBClass(): Class<B> {
        val superClass = this.javaClass.genericSuperclass
        val pt = superClass as ParameterizedType
        return pt.actualTypeArguments[0] as Class<B>
    }

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        val bClass = getBClass()
        val method = bClass.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
        )
        binding = method.invoke(null, inflater, container, false) as B
        return binding.root
    }
}