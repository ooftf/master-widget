package com.ooftf.director.entrance

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import com.ooftf.basic.AppHolder
import com.ooftf.http.monitor.Monitor
import com.ooftf.director.AppUtils
import com.ooftf.director.CommonListViewModel
import com.ooftf.director.ItemAction
import com.ooftf.director.R
import com.ooftf.director.app.Director
import com.ooftf.director.info.DebugInfoActivity
import com.ooftf.director.onoff.SwitchActivity

class DebugEntranceViewModel(application: Application) :
        CommonListViewModel<ItemAction>(application) {

    init {
        title.postValue("调试功能列表")

        items.add(
                ItemAction(
                        "调试信息"
                ) {
                    AppHolder.app.startActivity(Intent(AppHolder.app, DebugInfoActivity::class.java))
                }
        )

        items.add(
                ItemAction(
                        "调试开关入口"
                ) {
                    AppHolder.app.startActivity(Intent(AppHolder.app, SwitchActivity::class.java))
                }
        )


        items.add(
                ItemAction(
                        "网络日志"
                ) {
                    AppHolder.app.startActivity(
                            Monitor.getLogViewIntent(
                                    AppHolder.app
                            )
                    )
                }
        )
        items.add(
                ItemAction(
                        "网络拦截器"
                ) {
                    AppHolder.app.startActivity(
                            Monitor.getSettingIntent(
                                    AppHolder.app
                            )
                    )
                }
        )
        items.add(ItemAction("重置应用（数据和权限）") {
            val am =
                    AppHolder.app.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            am.clearApplicationUserData()
        })
        items.add(ItemAction("跳转设置界面") {
            AppUtils.launchAppDetailsSettings(com.ooftf.basic.engine.ActivityManager.getTopActivity(), AppHolder.app.packageName)
        })
        items.addAll(Director.customDebugItems)
    }

    override fun getItemLayout(): Int {
        return R.layout.director_ooftf_item_debug_entance
    }
}