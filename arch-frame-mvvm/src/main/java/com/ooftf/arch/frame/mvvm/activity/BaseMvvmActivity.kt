package com.ooftf.arch.frame.mvvm.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.ooftf.arch.frame.mvvm.BR
import com.ooftf.arch.frame.mvvm.vm.BaseViewModel
import com.ooftf.mapping.lib.ui.BaseLiveDataObserve
import java.lang.reflect.ParameterizedType

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/8/2 0002
 */
abstract class BaseMvvmActivity<B : ViewDataBinding, V : BaseViewModel> : BaseActivity() {
    lateinit var binding: B
    lateinit var viewModel: V
    lateinit var baseLiveDataObserve: BaseLiveDataObserve

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindLayout()
        val viewModelFactory = getViewModelFactory()
        viewModel = if (viewModelFactory == null) {
            ViewModelProvider(this).get(getVClass())
        } else {
            ViewModelProvider(this, viewModelFactory).get(getVClass())
        }
        viewModel.setLifecycleOwner(this)
        viewModel.setActivity(this)
        binding.setVariable(getVariableId(), viewModel)
        binding.lifecycleOwner = this
        baseLiveDataObserve = viewModel.baseLiveData.attach(this, this)
    }

    open fun getViewModelFactory(): ViewModelProvider.Factory? {
        return null
    }


    /**
     * 如果报异常代表泛型设置有问题
     */
    private fun getVClass(): Class<V> {
        val superClass = this.javaClass.genericSuperclass
        val pt = superClass as ParameterizedType
        return pt.actualTypeArguments[1] as Class<V>
    }

    /**
     * 如果报异常代表泛型设置有问题
     */
    private fun getBClass(): Class<B> {
        val superClass = this.javaClass.genericSuperclass
        val pt = superClass as ParameterizedType
        return pt.actualTypeArguments[0] as Class<B>
    }

    open fun getVariableId() = BR.viewModel

    /**
     * 设置layout，生成binding
     */
    private fun bindLayout() {
        val bClass = getBClass()
        val method = bClass.getMethod("inflate", LayoutInflater::class.java)
        binding = method.invoke(null, layoutInflater) as B
        setContentView(binding.root)
    }
}