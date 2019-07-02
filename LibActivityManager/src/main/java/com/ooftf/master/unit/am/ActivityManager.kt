package com.ooftf.master.unit.am

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import java.lang.ref.WeakReference

/**
 * Created by master on 2016/3/3.
 *
 * 只适用于单进程Activity
 */
public object ActivityManager {
    private val activities = ArrayList<WeakReference<Activity>>()
    private var touchCounter = 0
    private var showCounter = 0
    private var top: WeakReference<Activity>? = null
    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
                if (top?.get() == activity) {
                    top = null
                }
                touchCounter--
            }

            override fun onActivityResumed(activity: Activity) {
                top = WeakReference(activity)
                touchCounter++


            }

            override fun onActivityStarted(activity: Activity?) {
                showCounter++
                notifyShowChange()
            }

            override fun onActivityDestroyed(activity: Activity) {
                val iterator = activities.iterator()
                while (iterator.hasNext()) {
                    val next = iterator.next()
                    if (next.get() == null || next.get() == activity) {
                        iterator.remove()
                    }
                }
                if (activities.size == 0) {
                    emptyActivityObservers.forEach {
                        it.invoke()
                    }
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

            }

            override fun onActivityStopped(activity: Activity?) {
                showCounter--
                notifyShowChange()
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activities.add(WeakReference(activity))
            }

        })
    }

    var isBackground = true
    private fun notifyShowChange() {
        if (isBackground && showCounter > 0) {
            isBackground = false
            foregroundObservers.forEach { it.invoke() }
        } else if (!isBackground && showCounter <= 0) {
            isBackground = true
            backgroundObservers.forEach { it.invoke() }
        }
    }

    private var emptyActivityObservers = LinkedHashSet<() -> Unit>()
    fun getTopActivity(): Activity? = top?.get()

    private var foregroundObservers = LinkedHashSet<() -> Unit>()

    private var backgroundObservers = LinkedHashSet<() -> Unit>()


    fun registerForegroundObserver(observer: () -> Unit) {
        foregroundObservers.add(observer)
    }

    fun clearForegroundObserver() {
        foregroundObservers.clear()
    }

    fun unregisterForegroundObserver(observer: () -> Unit) {
        foregroundObservers.remove(observer)
    }

    fun registerBackgroundObserver(observer: () -> Unit) {
        backgroundObservers.add(observer)
    }

    fun clearBackgroundObserver() {
        backgroundObservers.clear()
    }

    fun unregisterBackgroundObserver(observer: () -> Unit) {
        backgroundObservers.remove(observer)
    }

    fun registerEmptyActvityObserver(observer: () -> Unit) {
        emptyActivityObservers.add(observer)
    }

    fun clearEmptyActvityObserver() {
        emptyActivityObservers.clear()
    }

    fun unregisterEmptyActvityObserver(observer: () -> Unit) {
        emptyActivityObservers.remove(observer)
    }

    fun isAppForeground() = touchCounter > 0
    fun finishAll() {
        activities.forEach { it.get()?.finish() }
    }

    fun finishActivities(cla: Class<*>) {
        activities.filter { it.get()?.javaClass == cla }
                .forEach { it.get()?.finish() }
    }

    fun finishActivities(cla: Class<*>, resultCode: Int, intent: Intent) {
        activities.filter { it.get()?.javaClass == cla }
                .forEach {
                    it.get()?.intent = intent
                    it.get()?.setResult(resultCode)
                    it.get()?.finish()
                }
    }

    fun finishOther(activity: Activity) {
        activities.filter { it.get() != activity }
                .forEach { it.get()?.finish() }
    }
}
