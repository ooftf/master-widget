package com.ooftf.director.app

import android.view.View
import com.didichuxing.doraemonkit.DoraemonKit
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.permission.PermissionUtils
import com.ooftf.basic.AppHolder
import com.ooftf.http.monitor.LogViewInterceptor
import com.ooftf.http.monitor.Monitor
import com.ooftf.http.monitor.MonitorProvider
import com.ooftf.http.monitor.ReviseInterceptor
import com.ooftf.director.ItemAction
import okhttp3.Interceptor
import java.lang.reflect.Type

object Director {
    fun init(doraemonKitProductId: String, debug: Boolean) {
        EasyFloat.init(AppHolder.app, debug)
        Monitor.init(object : MonitorProvider {
            override fun isMockNetData(): Boolean {
                return MockDataSwitch.get()
            }

            override fun isShowNetLog(): Boolean {
                return ShowNetLogSwitch.get()
            }
        })
        DoraemonKit.setAwaysShowMainIcon(false)
        DoraemonKit.install(AppHolder.app, doraemonKitProductId)
        ShowNetLogSwitch.get().takeIf { it }?.takeIf { PermissionUtils.checkPermission(AppHolder.app) }?.let {
            PanelDialog.showFloat(
                    AppHolder.app
            )
        }
    }

    fun getLogViewInterceptor(): Interceptor {
        return LogViewInterceptor()
    }

    fun getReviseInterceptor(): Interceptor {
        return ReviseInterceptor()
    }

    fun setDebugEntranceView(v: View) {
        v.setOnClickListener(object : View.OnClickListener {
            var time = 0L
            var count = 0
            override fun onClick(v: View) {
                val currentTimeMillis = System.currentTimeMillis()
                if (currentTimeMillis - time < 500) {
                    count++
                } else {
                    count = 1

                }
                if (count > 10) {
                    count = 0
                    PanelDialog.perShowFloat(v)
                }
                time = currentTimeMillis
            }

        })
    }

    val customPanelItems by lazy {
        ArrayList<ItemAction>()
    }
    val customDebugItems by lazy {
        ArrayList<ItemAction>()
    }

    /**
     * 添加调试面板
     */
    fun addPanelItem(item: ItemAction) {
        customPanelItems.add(item)
    }

    /**
     * 添加调试界面
     */
    fun addDebugItem(item: ItemAction) {
        customDebugItems.add(item)
    }
}