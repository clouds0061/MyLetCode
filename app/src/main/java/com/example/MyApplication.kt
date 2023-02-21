package com.example

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.activity.MainActivity2
import java.util.concurrent.*

/**
 * 添加自己的application 一般做一些初始化操作
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("MyApplication", "开始等待!")
        Toast.makeText(this, "开始等待!", Toast.LENGTH_SHORT)
        var count = CountDownLatch(2)
        initBlockCanary()

        var threadPool1 = ThreadPoolExecutor(
            2, 2, 1000 * 10, TimeUnit.SECONDS,
            ArrayBlockingQueue<Runnable>(5)
        )
        var threadPool2 = ThreadPoolExecutor(
            10, 10, 5000 * 10, TimeUnit.SECONDS,
            ArrayBlockingQueue<Runnable>(19)
        )

        threadPool1.execute(Runnable {
            kotlin.run {
                Thread.sleep(2000)
                count.countDown()
            }
        })

        threadPool2.submit(Runnable {
            kotlin.run {
                Thread.sleep(1000)
                count.countDown()
            }
        })

        count.await()
        Log.d("MyApplication", "等待完成!")
        Toast.makeText(this, "等待完成!", Toast.LENGTH_SHORT).show()



        var handlerException = Thread.UncaughtExceptionHandler { t, e -> //上传全局异常信息

            var default: Thread.UncaughtExceptionHandler =
                Thread.getDefaultUncaughtExceptionHandler()
            if (default != null) {
                default.uncaughtException(t, e)
            } else {
                android.os.Process.killProcess(android.os.Process.myPid())
            }
        }
    }

    /**
     * 初始化卡顿监控开源库
     */
    private fun initBlockCanary() {
//        BlockCanary.install(this,MyBlockCanary()).start()
    }

}