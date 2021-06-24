package com.ooftf.layout.utils

import android.text.TextPaint
import android.text.style.URLSpan
import android.view.View

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/6/24
 */
class URLSpanProxy(real: URLSpan,val preClick:Runnable) : URLSpan(real.url) {

    override fun updateDrawState(ds: TextPaint) {
        ds.color = ds.linkColor
        ds.isUnderlineText = false
    }

    override fun onClick(widget: View?) {
        preClick.run()
        super.onClick(widget)
    }
}