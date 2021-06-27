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
class URLSpanPlus(url: String, var preClick: ClickInterceptor? = null) : URLSpan(url) {

    override fun updateDrawState(ds: TextPaint) {
        ds.color = ds.linkColor
        ds.isUnderlineText = false
    }

    override fun onClick(widget: View?) {

        preClick?.preOnClick(url.split(":".toRegex(), 2)[1]) {
            super.onClick(widget)
        }
    }

    interface ClickInterceptor {
        fun preOnClick(content: String, realClick: Runnable)
    }
}