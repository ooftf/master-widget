package com.ooftf.databinding.extensions

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableList
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ooftf.databinding.extensions.empty.OnListChangedCallbackPoly

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/11/6
 */
object TabLayoutDataBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["exTabLayoutTabNames", "exTabLayoutViewPagerTag"], requireAll = true)
    fun exTabLayout(tabLayout: TabLayout, tabs: ObservableList<String>?, tag: String?) {
        if (tabs == null) {
            return
        }
        var callback = tabLayout.getTag(R.id.tag_onListChangedCallback) as? OnListChangedCallbackPoly<ObservableList<String>>
        if (callback == null) {
            callback = OnListChangedCallbackPoly {
                update(tabLayout, tag, tabs)
            }
            tabLayout.setTag(R.id.tag_onListChangedCallback, callback)
        } else {
            tabs.removeOnListChangedCallback(callback)
        }

        update(tabLayout, tag, tabs)
    }

    private fun update(tabLayout: TabLayout, tag: String?, tabs: ObservableList<String>) {
        var tabLayoutMediator = tabLayout.getTag(R.id.tag_tabLayoutMediator) as? TabLayoutMediator
        tabLayoutMediator?.detach()
        tabLayoutMediator = TabLayoutMediator(tabLayout, tabLayout.rootView.findViewWithTag(tag)) { tab, position ->
            tab.text = tabs.getOrElse(position) { "" }
        }
        tabLayoutMediator.attach()
        tabLayout.setTag(R.id.tag_tabLayoutMediator, tabLayoutMediator)
    }

    @JvmStatic
    @BindingAdapter(value = ["exTabLayoutTabNames", "exTabLayoutModel", "exTabLayoutSelectedListener"], requireAll = false)
    fun exTabLayout(tabLayout: TabLayout, tabs: ObservableList<String>?, mode: Int? = TabLayout.MODE_AUTO, listener: ((Int) -> Unit)?){
        if (tabs == null) {
            return
        }
        mode?.let {
            tabLayout.tabMode
        }
        var onTabSelectedListener = tabLayout.getTag(R.id.tag_onTabSelectedListener) as? OTSL
        if (onTabSelectedListener == null) {
            onTabSelectedListener = OTSL(listener)
            tabLayout.setTag(R.id.tag_onTabSelectedListener, onTabSelectedListener)
            tabLayout.addOnTabSelectedListener(onTabSelectedListener)
        }
        onTabSelectedListener.listener = listener
        var callback = tabLayout.getTag(R.id.tag_onListChangedCallback) as? OnListChangedCallbackPoly<ObservableList<String>>
        if (callback == null) {
            callback = OnListChangedCallbackPoly<ObservableList<String>> {
                update(tabLayout, it)
            }
            tabLayout.setTag(R.id.tag_onListChangedCallback, callback)
        } else {
            tabs.removeOnListChangedCallback(callback)
        }
        tabs.addOnListChangedCallback(callback)
        update(tabLayout, tabs)
    }

    private fun update(tabLayout: TabLayout, tabs: ObservableList<String>) {
        tabLayout.removeAllTabs()
        tabs.forEach {
            tabLayout.addTab(tabLayout.newTab().apply { text = it })
        }
    }

    class OTSL(var listener: ((Int) -> Unit)?) : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            listener?.invoke(tab.position)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
        }

    }
}