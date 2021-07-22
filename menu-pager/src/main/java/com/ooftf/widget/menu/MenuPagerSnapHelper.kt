package com.ooftf.widget.menu

import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
import androidx.recyclerview.widget.SnapHelper

class MenuPagerSnapHelper : SnapHelper() {
    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {
        val out = IntArray(2)
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToHeader(
                layoutManager, targetView,
                getHorizontalHelper(layoutManager)
            )
        } else {
            out[0] = 0
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToHeader(
                layoutManager, targetView,
                getVerticalHelper(layoutManager)
            )
        } else {
            out[1] = 0
        }
        return out

    }

    private var mVerticalHelper: OrientationHelper? = null
    private var mHorizontalHelper: OrientationHelper? = null
    private fun distanceToHeader(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View, helper: OrientationHelper
    ): Int {
        val childHeader = helper.getDecoratedStart(targetView)
        val containerHeader = helper.startAfterPadding
        return childHeader - containerHeader
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {
        val itemCount = layoutManager.itemCount
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION
        }
        if (layoutManager !is MenuPagerLayoutManager) {
            return RecyclerView.NO_POSITION
        }
        val orientationHelper: OrientationHelper = getOrientationHelper(layoutManager)
            ?: return RecyclerView.NO_POSITION

        // A child that is exactly in the center is eligible for both before and after
        var closestChildBeforeCenter: View? = null
        var distanceBefore = Int.MIN_VALUE
        var closestChildAfterCenter: View? = null
        var distanceAfter = Int.MAX_VALUE
        Log.e("tag", "111111111")
        for (i in 0 until layoutManager.getTotalPage()) {
            val child = layoutManager.getChildAt(i * layoutManager.getPageSize()) ?: continue
            val distance: Int = distanceToHeader(layoutManager, child, orientationHelper)
            if (distance <= 0 && distance > distanceBefore) {
                // Child is before the center and closer then the previous best
                distanceBefore = distance
                closestChildBeforeCenter = child
            }
            if (distance >= 0 && distance < distanceAfter) {
                // Child is after the center and closer then the previous best
                distanceAfter = distance
                closestChildAfterCenter = child
            }
        }
        val forwardDirection: Boolean = isForwardFling(layoutManager, velocityX, velocityY)
        if (forwardDirection && closestChildAfterCenter != null) {
            return layoutManager.getPosition(closestChildAfterCenter)
        } else if (!forwardDirection && closestChildBeforeCenter != null) {
            return layoutManager.getPosition(closestChildBeforeCenter)
        }

        val visibleView =
            (if (forwardDirection) closestChildBeforeCenter else closestChildAfterCenter)
                ?: return RecyclerView.NO_POSITION
        val visiblePosition = layoutManager.getPosition(visibleView)
        val snapToPosition = (visiblePosition
                + if (isReverseLayout(layoutManager) == forwardDirection) -1 else +1)

        return if (snapToPosition < 0 || snapToPosition >= itemCount) {
            RecyclerView.NO_POSITION
        } else snapToPosition
    }

    private fun isForwardFling(
        layoutManager: RecyclerView.LayoutManager, velocityX: Int,
        velocityY: Int
    ): Boolean {
        return if (layoutManager.canScrollHorizontally()) {
            velocityX > 0
        } else {
            velocityY > 0
        }
    }

    private fun isReverseLayout(layoutManager: RecyclerView.LayoutManager): Boolean {
        val itemCount = layoutManager.itemCount
        if (layoutManager is ScrollVectorProvider) {
            val vectorProvider = layoutManager as ScrollVectorProvider
            val vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1)
            if (vectorForEnd != null) {
                return vectorForEnd.x < 0 || vectorForEnd.y < 0
            }
        }
        return false
    }

    private fun getOrientationHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        return if (layoutManager.canScrollVertically()) {
            getVerticalHelper(layoutManager)
        } else if (layoutManager.canScrollHorizontally()) {
            getHorizontalHelper(layoutManager)
        } else {
            null
        }
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        if (layoutManager.canScrollVertically()) {
            return findHeaderView(layoutManager, getVerticalHelper(layoutManager))
        } else if (layoutManager.canScrollHorizontally()) {
            return findHeaderView(layoutManager, getHorizontalHelper(layoutManager))
        }
        return null
    }

    private fun findHeaderView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper
    ): View? {
        val childCount = layoutManager.childCount
        if (childCount == 0) {
            return null
        }
        var closestChild: View? = null
        val header = helper.startAfterPadding
        var absClosest = Int.MAX_VALUE
        if (layoutManager !is MenuPagerLayoutManager) {
            return null
        }
        for (i in 0 until layoutManager.getTotalPage()) {

            val child = layoutManager.getChildAt(i * layoutManager.getPageSize())
            val childHeader = helper.getDecoratedStart(child)
            val absDistance = Math.abs(childHeader - header)
            if (absDistance < absClosest) {
                absClosest = absDistance
                closestChild = child
            }
        }
        return closestChild
    }

    var currentPage: Int = -1
    private val scrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                recyclerView.layoutManager?.let { layoutManager ->
                    findSnapView(layoutManager)?.let { view ->
                        if (layoutManager is MenuPagerLayoutManager) {
                            val page =
                                layoutManager.getPageForPosition(layoutManager.getPosition(view))
                            if (page != currentPage) {
                                onPositionChangeListener?.invoke(currentPage, page)
                                currentPage = page
                            }
                        }
                    }
                }

            }
        }
    }
    var attachTedRecyclerView: RecyclerView? = null
    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        attachTedRecyclerView = recyclerView
        recyclerView?.removeOnScrollListener(scrollListener)
        recyclerView?.addOnScrollListener(scrollListener)
        super.attachToRecyclerView(recyclerView)

    }

    var onPositionChangeListener: ((old: Int, newPosition: Int) -> Unit)? = null

    override fun createSnapScroller(layoutManager: RecyclerView.LayoutManager?): LinearSmoothScroller? {
        return if (layoutManager !is ScrollVectorProvider) {
            null
        } else object : LinearSmoothScroller(attachTedRecyclerView?.context) {
            override fun onTargetFound(
                targetView: View,
                state: RecyclerView.State,
                action: Action
            ) {
                val snapDistances = calculateDistanceToFinalSnap(
                    attachTedRecyclerView?.layoutManager!!,
                    targetView
                )
                val dx = snapDistances!![0]
                val dy = snapDistances[1]
                val time = calculateTimeForDeceleration(
                    Math.max(
                        Math.abs(dx),
                        Math.abs(dy)
                    )
                )
                if (time > 0) {
                    action.update(dx, dy, time, mDecelerateInterpolator)
                }
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return 100f / displayMetrics.densityDpi
            }

            override fun calculateTimeForScrolling(dx: Int): Int {
                return Math.min(
                    100,
                    super.calculateTimeForScrolling(dx)
                )
            }
        }
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (mVerticalHelper == null || mVerticalHelper!!.layoutManager !== layoutManager) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return mVerticalHelper!!
    }

    private fun getHorizontalHelper(
        layoutManager: RecyclerView.LayoutManager
    ): OrientationHelper {
        if (mHorizontalHelper == null || mHorizontalHelper!!.layoutManager !== layoutManager) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return mHorizontalHelper!!
    }
}