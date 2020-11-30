package com.ooftf.director.info

import android.app.Application
import android.content.Context
import android.location.LocationManager
import com.blankj.utilcode.util.*
import com.ooftf.director.CommonListViewModel
import com.ooftf.director.R

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/5/21
 */
class DebugInfoViewModel(application: Application) : CommonListViewModel<Pair<String, String>>(application) {
    init {
        title.postValue("调试信息")
/*        items.add(Pair("打包时间", iGlobalParamProviders.buildTime()))
        items.add(Pair("App启动时间", iGlobalParamProviders.getAppStartTime()))*/
        items.add(Pair("AppVersionName", AppUtils.getAppVersionName()))
        items.add(Pair("UniqueDeviceId", DeviceUtils.getUniqueDeviceId()))
        items.add(Pair("设备型号", DeviceUtils.getModel()))
        items.add(Pair("设备厂商", DeviceUtils.getManufacturer()))
        items.add(Pair("设备系统版本号", DeviceUtils.getSDKVersionName()))
        items.add(Pair("设备是否 rooted", DeviceUtils.isDeviceRooted().toString()))
        items.add(Pair("Wifi是否连接", NetworkUtils.isWifiConnected().toString()))
        items.add(Pair("GPS", isGpsEnabled().toString()))
        items.add(Pair("ROM信息", RomUtils.getRomInfo().toString()))

    }

    fun isGpsEnabled(): Boolean {
        val lm =
                Utils.getApp().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


    override fun getItemLayout(): Int {
        return R.layout.director_ooftf_item_debug_info
    }
}