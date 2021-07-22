package com.ooftf.widget.menu

import android.graphics.PointF
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

/**
 *  RecyclerView 的宽高需要是固定值，不支持 wrap_content
 *  由于目前 menu 一般个数不多，所以目前采用的是全 Item 布局
 */
class MenuPagerLayoutManager(
    val horizontalCount: Int = 5,
    val verticalCount: Int = 2
) :
    RecyclerView.LayoutManager(),
    RecyclerView.SmoothScroller.ScrollVectorProvider {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        // 先把所有的View先从RecyclerView中detach掉，然后标记为"Scrap"状态，表示这些View处于可被重用状态(非显示中)。
        // 实际就是把View放到了Recycler中的一个集合中。
        detachAndScrapAttachedViews(recycler);
        calculateChildrenSite(recycler);
    }

    private fun calculateChildrenSite(recycler: Recycler) {
        totalWith = 0
        val heightFinally = getVerticalSpace() / verticalCount
        val widthFinally = getHorizontallySpace() / horizontalCount

        for (i in 0 until itemCount) {
            var page = i / (horizontalCount * verticalCount)
            var x = i % horizontalCount
            var y = i / horizontalCount
            y %= verticalCount
            val left = paddingLeft + x * widthFinally + page * width
            val top = paddingTop + y * heightFinally
            val right = left + widthFinally
            val bottom = top + heightFinally

            val view: View = recycler.getViewForPosition(i)
            addView(view) // 因为刚刚进行了detach操作，所以现在可以重新添加
            measureChildWithMargins(
                view,
                widthFinally * (horizontalCount - 1),
                heightFinally * (verticalCount - 1)
            )
            /*     Log.e("x", "$width :: $childWidth   :: $x")
                 Log.e("y", "$height :: $childHeight   :: $y")*/
            // 调用这句我们指定了该View的显示区域，并将View显示上去，此时所有区域都用于显示View，
            //包括ItemDecorator设置的距离。
            layoutDecorated(
                view,
                left,
                top,
                right,
                bottom
            )
        }
        totalWith = getTotalPage() * width
    }

    fun getTotalPage(): Int {
        return (itemCount - 1) / (horizontalCount * verticalCount) + 1
    }

    fun getPageSize(): Int {
        return horizontalCount * verticalCount
    }


    fun getPageForPosition(position: Int): Int {
        return position / getPageSize()
    }

    override fun canScrollVertically(): Boolean {
        return false
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    private fun getHorizontallySpace(): Int {
        //计算RecyclerView的可用高度，除去上下Padding值
        return width - paddingStart - paddingEnd
    }

    private fun getVerticalSpace(): Int {
        //计算RecyclerView的可用高度，除去上下Padding值
        return height - paddingTop - paddingBottom
    }

    var horizontallyScrollOffset = 0
    var totalWith = 0
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: Recycler,
        state: RecyclerView.State
    ): Int {
        //列表向下滚动dy为正，列表向上滚动dy为负，这点与Android坐标系保持一致。
        //实际要滑动的距离
        var travel = dx
        //如果滑动到最顶部
        if (horizontallyScrollOffset + dx < 0) {
            travel = -horizontallyScrollOffset
        } else if (horizontallyScrollOffset + dx > totalWith - getHorizontallySpace()) { //如果滑动到最底部
            travel = totalWith - getHorizontallySpace() - horizontallyScrollOffset
        }

        //将竖直方向的偏移量+travel
        horizontallyScrollOffset += travel

        // 调用该方法通知view在y方向上移动指定距离
        offsetChildrenHorizontal(-travel)
        return travel
    }

    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
        if (childCount == 0) {
            return null
        }
        val firstChildPos = getPosition(getChildAt(0)!!)
        val direction = if (targetPosition < firstChildPos) -1 else 1
        return PointF(direction.toFloat(), 0f)
    }


}