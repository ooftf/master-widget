package com.ooftf.director.app

import android.app.Activity
import android.view.View
import com.alibaba.android.arouter.facade.service.SerializationService
import com.alibaba.android.arouter.launcher.ARouter
import com.didichuxing.doraemonkit.DoraemonKit
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.permission.PermissionUtils
import com.ooftf.basic.AppHolder
import com.ooftf.basic.engine.ActivityManager
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
            override fun getApplication() = AppHolder.app

            override fun getTopActivity(): Activity? {
                return ActivityManager.getTopActivity()
            }

            override fun isMockNetData(): Boolean {
                return MockDataSwitch.get()
            }

            override fun isShowNetLog(): Boolean {
                return ShowNetLogSwitch.get()
            }

            override fun object2Json(instance: Any): String? {
                return ARouter.getInstance().navigation(SerializationService::class.java)
                        .object2Json(instance)
            }

            override fun <T> parseObject(input: String, clazz: Type): T? {
                return ARouter.getInstance().navigation(SerializationService::class.java)
                        .parseObject<T>(input, clazz)
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