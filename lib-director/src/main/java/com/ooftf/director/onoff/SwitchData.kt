package com.ooftf.director.onoff

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField


class SwitchData {
    var key = ObservableField<String>()
    var isChecked = ObservableBoolean()
    var listener: View.OnClickListener? = null
}