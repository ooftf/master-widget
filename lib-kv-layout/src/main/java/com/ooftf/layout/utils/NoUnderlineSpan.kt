package com.ooftf.layout.utils

import android.text.TextPaint

import android.text.style.UnderlineSpan




/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/6/24
 */
class NoUnderlineSpan : UnderlineSpan() {
    override fun updateDrawState(ds: TextPaint) {
        ds.color = ds.linkColor
        ds.isUnderlineText = false
    }
}