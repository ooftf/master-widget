package com.ooftf.master.widget.eye

import android.os.Handler
import android.os.Looper
import android.os.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2020/9/1
 */
object LogKnife {
    var queue = Vector<String>()
    const val cacheSize = 200
    val MESSAGE_PUBLISH_LOG = 4896
    var isRunning = true
    val internalHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            if (msg?.what == MESSAGE_PUBLISH_LOG) {
                while (queue.size >= cacheSize) {
                    //queue.poll()
                    queue.removeAt(queue.size-1)
                }
                //queue.offer(msg.obj.toString())
                queue.add(0,msg.obj.toString())
                dataChangeListener.forEach { it() }
            }
        }
    }

    init {
        GlobalScope.launch (Dispatchers.IO){
            obtainLog()
        }
    }

    var dataChangeListener = ArrayList<() -> Unit>();
    fun registerDataChange(listener: () -> Unit) {
        dataChangeListener.add(listener)
    }

    fun unregisterDataChange(listener: () -> Unit) {
        dataChangeListener.remove(listener)
    }

    fun obtainLog() {
        Runtime.getRuntime().exec("logcat -c")
        val process = Runtime.getRuntime().exec("logcat -v time")
        val iStream = process.inputStream
        val reader = InputStreamReader(iStream);
        val br = BufferedReader(reader);

        var log: String
        while (br.readLine().also { log = it } != null && isRunning) {
            var message = Message.obtain();
            message.what = MESSAGE_PUBLISH_LOG;
            message.obj = log;
            internalHandler.sendMessage(message);
        }
        br.close();
        reader.close();
        iStream.close();
    }


}