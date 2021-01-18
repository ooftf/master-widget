package com.ooftf.master.widget.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    }
}