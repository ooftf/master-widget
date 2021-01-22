package com.ooftf.master.widget.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.ooftf.arch.frame.mvvm.activity.BaseBindingActivity
import com.ooftf.master.widget.sample.databinding.ActivityMainBinding

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tabLayout.addTab(binding.tabLayout.newTab().apply { text = "it1" })
        binding.tabLayout.addTab(binding.tabLayout.newTab().apply { text = "it2" })
        binding.kvLayout.setOnClickListener {
            binding.tabLayout.tabSelectedIndicator
        }
        Glide.init(applicationContext, GlideBuilder().apply {
            setLogLevel(Log.VERBOSE)
        })
        //
        Glide.with(this).load("https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoCHWAoBMvZt0mpAunoHVYQ2l1vL5LEsGsGKy4rFflbDJol0f1PQKa5ZXNXC2iaAMuNf5bEB3N4aPQ/132").into(binding.imageLayout)
    }
}