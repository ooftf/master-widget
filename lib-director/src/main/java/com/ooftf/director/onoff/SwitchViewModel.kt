package com.ooftf.director.onoff

import android.app.Application
import android.view.View
import android.widget.Switch
import com.ooftf.director.CommonListViewModel
import com.ooftf.director.app.MockDataSwitch
import com.ooftf.director.app.ShowNetLogSwitch
import com.ooftf.director.app.ShowEntranceSwitch
import com.lzf.easyfloat.EasyFloat
import com.ooftf.director.R
import com.ooftf.director.app.PanelDialog

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/5/21
 */
class SwitchViewModel(application: Application) : CommonListViewModel<SwitchData>(application) {

    init {
        title.postValue("调试开关")
        items.add(SwitchData().apply {
            key.set("是否显示开发工具浮窗")
            isChecked.set(isDevShow())
            listener = View.OnClickListener {
                (it as? Switch)?.let { switch ->
                    this.isChecked.set(switch.isChecked)
                    setDevShow(switch.isChecked)
                    ShowEntranceSwitch.set(switch.isChecked)
                }
            }
        })
        items.add(SwitchData().apply {
            key.set("是否开启网络请求日志")
            isChecked.set(ShowNetLogSwitch.get())
            listener = View.OnClickListener {
                (it as? Switch)?.let { switch ->
                    this.isChecked.set(switch.isChecked)
                    ShowNetLogSwitch.set(switch.isChecked)
                }
            }
        })
        items.add(SwitchData().apply {
            key.set("是否开启修改网络响应数据的功能")
            isChecked.set(MockDataSwitch.get())
            listener = View.OnClickListener {
                (it as? Switch)?.let { switch ->
                    this.isChecked.set(switch.isChecked)
                    MockDataSwitch.set(switch.isChecked)
                }
            }
        })
    }


    fun isDevShow(): Boolean {
        return EasyFloat.appFloatIsShow(PanelDialog.tag)
    }

    fun setDevShow(isChecked: Boolean) {
        if (isChecked) {
            EasyFloat.showAppFloat(PanelDialog.tag)
        } else {
            EasyFloat.hideAppFloat(PanelDialog.tag)
        }

    }

    override fun getItemLayout(): Int {
        return R.layout.director_ooftf_item_switch
    }

}
