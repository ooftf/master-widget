package com.ooftf.director.app

import com.ooftf.basic.engine.serializable.SerializableBoolean

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/7/1
 */
object MockDataSwitch : SerializableBoolean() {
    override fun getKey(): String {
        return DebugConst.KV_KEV_MOCK_NET
    }
}