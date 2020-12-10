package com.ooftf.layout.kv

import android.content.Context
import android.util.AttributeSet


/**
 *
 * 以Padding来布局，兼容多行显示
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/2/6
 */
class KvEditLayout : KvLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    )
    constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)
    override fun getLayoutId(): Int {
        return R.layout.kvl_ooftf_layout_kv_edit
    }

}