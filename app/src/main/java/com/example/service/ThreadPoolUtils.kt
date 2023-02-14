package com.example.service

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

/**
 * 线程相关
 */
class ThreadPoolUtils {

    private var instanceService = Executors.newFixedThreadPool(5, ThreadFactory { it ->
        Thread(it, "ThreadUtils")
    })

    /**
     * 对外开放 获取线程
     */
    companion object {
        fun getService2(): ExecutorService {
            return ThreadPoolUtils().instanceService
        }

    }

    object instance {
        fun getService(): ExecutorService {
            return ThreadPoolUtils().instanceService
        }
    }


}