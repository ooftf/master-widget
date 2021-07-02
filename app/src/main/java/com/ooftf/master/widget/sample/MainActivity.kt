package com.ooftf.master.widget.sample
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.and
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.ooftf.arch.frame.mvvm.activity.BaseBindingActivity
import com.ooftf.basic.utils.getVisibleRectOfScreen
import com.ooftf.master.widget.sample.databinding.ActivityMainBinding

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tabLayout.addTab(binding.tabLayout.newTab().apply { text = "it2" })
        binding.tabLayout.addTab(binding.tabLayout.newTab().apply { text = "it2" })
        binding.kvLayout.setOnClickListener {
            binding.tabLayout.tabSelectedIndicator
        }
        binding.imageLayout.setOnClickListener {
            FullDialog(this@MainActivity).show()
        }
        Glide.init(applicationContext, GlideBuilder().apply {
            setLogLevel(Log.VERBOSE)
        })
        //
        Glide.with(this).load("https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoCHWAoBMvZt0mpAunoHVYQ2l1vL5LEsGsGKy4rFflbDJol0f1PQKa5ZXNXC2iaAMuNf5bEB3N4aPQ/132").into(binding.imageLayout)

        window.decorView.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            Log.e("fitNavigationBar", "${left} ${top} ${right} ${bottom} ${oldLeft} ${oldTop} ${oldRight} ${oldBottom}")
            window.decorView.findViewById<View>(android.R.id.navigationBarBackground)?.getVisibleRectOfScreen()?.let {
                Log.e("navigationR",it.toShortString())
                Log.e("decorViewR",window.decorView.getVisibleRectOfScreen().toShortString())
                Log.e("andR",it.and(window.decorView.getVisibleRectOfScreen()).toShortString())

            }


        }
        binding.root.postDelayed({ printParent(binding.root) },2000)

    }
    private fun printParent(root: View) {
        val parent = root.parent
        if (parent == null) {
            return
        } else if (parent is ViewGroup) {
            val locations = IntArray(2)
            parent.getLocationOnScreen(locations)
            val r = Rect()
            val p = Point()
            parent.getGlobalVisibleRect(r,p)
            Log.e("printParent",parent.javaClass.simpleName+"" + r.toShortString()+ p.toString())
            printParent(parent)
        } else {
            Log.e("printParent",parent.javaClass.simpleName)
        }


    }
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        level == TRIM_MEMORY_UI_HIDDEN
    }

    override fun onLowMemory() {
        super.onLowMemory()

    }
}