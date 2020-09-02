package com.ooftf.master.widget.eye

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.lzf.easyfloat.enums.SidePattern
import com.lzf.easyfloat.interfaces.OnInvokeView
import com.lzf.easyfloat.interfaces.OnPermissionResult
import com.lzf.easyfloat.permission.PermissionUtils.checkPermission
import com.lzf.easyfloat.permission.PermissionUtils.requestPermission
import com.ooftf.master.widget.dialog.inteface.ListDialogInterface
import com.ooftf.master.widget.dialog.ui.ListDialog
import com.ooftf.master.widget.dialog.utils.DialogUtil

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2019/6/11 0011
 */
object DevEye {
    @JvmField
    var application: Application? = null
    var sProvider: DevEyeProvider? = null
    fun init(app: Application, provider: DevEyeProvider) {
        application = app
        sProvider = provider
    }

    private val items by lazy {
        ArrayList<ItemAction>().apply {
            if (sProvider == null) {
                throw Exception("请先调用 DevEye.init()初始化")
            }
            add(ItemAction("显示当前Activity名称") { Toast.makeText(application, sProvider!!.topActivity.javaClass.name, Toast.LENGTH_SHORT).show() })
            add(ItemAction("显示当前LOG") {
                LogDialog(sProvider!!.topActivity).apply {
                    setData(LogKnife.queue)
                    val listener = {
                        notifyDataSetChanged()
                    }
                    LogKnife.registerDataChange(listener)
                    setOnDismissListener {
                        LogKnife.unregisterDataChange(listener)
                    }
                }.show()
            })
            add(ItemAction("重置应用（数据和权限）") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    val am = application!!.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                    am.clearApplicationUserData()
                } else {
                    Toast.makeText(application, "暂时只支持API19+", Toast.LENGTH_SHORT).show()
                }
            })
            add(ItemAction("清除应用缓存") { CacheUtil.clearCache(application) })
            add(ItemAction("清除应用数据") { CacheUtil.clearAll(application) })
            add(ItemAction("跳转设置界面") { AppUtils.launchAppDetailsSettings(application, application!!.packageName) })
        }
    }

    private fun show(topActivity: Activity) {
        if (DialogUtil.isHasDialog(topActivity)) {
            return
        }
        ListDialog(topActivity)
                .setList(items.map { it.name })
                .setShowCancel(false)
                .setOnItemClickListener { dialog: ListDialogInterface, position: Int, item: String? ->
                    dialog.dismiss()
                    items[position].action()
                }
                .show_()
    }

    fun showFloat(activity: Activity?) {
        if (checkPermission(activity!!)) {
            realShowFloat(application)
        } else {
            requestPermission(activity, object : OnPermissionResult {
                override fun permissionResult(isOpen: Boolean) {
                    if (isOpen) {
                        realShowFloat(application)
                    } else {
                        Toast.makeText(application, "打开悬浮窗权限失败", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    var tag = "FLOAT_WINDOW"
    private fun realShowFloat(context: Context?) {
        val appFloatView = EasyFloat.getAppFloatView(tag)
        if (appFloatView == null) {
            EasyFloat.with(context!!)
                    .setLayout(R.layout.dev_eye_float_layout, OnInvokeView { view: View -> view.setOnClickListener { v: View? -> show(sProvider!!.topActivity) } })
                    .setSidePattern(SidePattern.DEFAULT)
                    .setShowPattern(ShowPattern.FOREGROUND)
                    .setTag(tag)
                    .setGravity(Gravity.END or Gravity.BOTTOM, -100, -260)
                    .setMatchParent(false, false)
                    .show()
        } else {
            EasyFloat.showAppFloat(tag)
        }
    }
}