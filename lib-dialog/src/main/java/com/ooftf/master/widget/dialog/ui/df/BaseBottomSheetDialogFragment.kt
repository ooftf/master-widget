package com.ooftf.master.widget.dialog.ui.df

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ooftf.basic.utils.DensityUtil
import com.ooftf.master.widget.dialog.R

/**
 *
 * view 是在 onActivityCreated 中 和 dialog 绑定的
 * dialog 创建 是在 onCreateView 之前 生成LayoutInflater 的时候
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2021/5/21
 */
abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflateView(inflater, savedInstanceState)
        initView(view)
        return view
    }

    abstract fun inflateView(
        inflater: LayoutInflater,
        savedInstanceState: Bundle?
    ): View

    abstract fun initView(view: View)

    override fun getDialog(): BottomSheetDialog? {
        return super.getDialog() as BottomSheetDialog?
    }

    /**
     * 需要在 view 和 dialog 绑定之后调用，否则没有效果
     * 推荐在 onActivityCreated 中配置
     * 可设置 ViewGroup.LayoutParams.MATCH_PARENT
     *
     * 设置展开状态高度
     */
    fun setHeight(height: Int) {
        dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.layoutParams?.height =
            height
    }

    fun setHeight(@FloatRange(from = 0.0, to = 1.0) percent: Float) {
        setHeight((DensityUtil.getScreenHeightPx() * percent).toInt())
    }

    fun setPeekHeight(@FloatRange(from = 0.0, to = 1.0) percent: Float) {
        setPeekHeight((DensityUtil.getScreenHeightPx() * percent).toInt())
    }

    /**
     * 设置中间状态高度
     */
    fun setPeekHeight(height: Int) {
        dialog?.behavior?.peekHeight = height
    }

    /**
     * 设置背景透明
     * Calling this after the fragment's Dialog is created will have no effect.
     *
     * 需要在 onCreateView 之前调用 比如 onCreate
     */
    fun setTransparentStyle() {
        setStyle(STYLE_NORMAL, R.style.master_ooftf_transparentBottomSheetDialogTheme);
    }
}