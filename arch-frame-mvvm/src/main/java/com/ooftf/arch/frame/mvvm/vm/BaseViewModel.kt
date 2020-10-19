package com.ooftf.arch.frame.mvvm.vm;

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import com.ooftf.mapping.lib.ui.BaseLiveData
import com.ooftf.mapping.lib.ui.ISmartLayoutData
import com.ooftf.mapping.lib.ui.IStateLayoutData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.lang.ref.WeakReference

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/7/23 0023
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application), IStateLayoutData, ISmartLayoutData {
    var baseLiveData = BaseLiveData()
    var disposables = CompositeDisposable()
    private var lifecycleOwnerWeakReference: WeakReference<LifecycleOwner>? = null
    private var activityWeakReference: WeakReference<Activity>? = null
    private var fragmentOwnerWeakReference: WeakReference<Fragment>? = null
    override fun getLoadMoreState() = baseLiveData.smartLoadMore
    override fun getRefreshState() = baseLiveData.smartRefresh
    override fun nextPage() {

    }

    override fun getStateLayout() = baseLiveData.stateLayout
    var doOnCleared: MutableList<() -> Unit> = ArrayList()
    override fun onCleared() {
        disposables.dispose()
        doOnCleared.forEach {
            it.invoke()
        }
        super.onCleared()
    }

    fun doOnCleared(event: () -> Unit) {
        doOnCleared.add(event)
    }

    override fun emptyAction() {

    }

    open fun setLifecycleOwner(lifecycle: LifecycleOwner) {
        lifecycleOwnerWeakReference = WeakReference(lifecycle)
    }


    override fun getLifecycleOwner(): LifecycleOwner? {
        return lifecycleOwnerWeakReference?.get()
    }
    open fun setActivity(activity: Activity) {
        activityWeakReference = WeakReference(activity)
    }


    fun getActivity(): Activity? {
        return activityWeakReference?.get()
    }
    open fun setFragment(fragment: Fragment) {
        fragmentOwnerWeakReference = WeakReference(fragment)
    }


    fun getFragment(): Fragment? {
        return fragmentOwnerWeakReference?.get()
    }

    override fun refresh() {

    }
}