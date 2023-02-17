package com.example.utils

import com.example.service.ThreadPoolUtils
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.*

/***
 * 线程池学习
 */
class ThreadPool {
    fun testThreadPool() {
        var runnable = kotlinx.coroutines.Runnable {
            kotlin.run {
                println("创建Runnable接口")
            }
        }

        var executorService = Executors.newFixedThreadPool(10)

        var future = executorService.execute(runnable)


        var fixedPool = Executors.newFixedThreadPool(20)
        var singleThreadPool = Executors.newSingleThreadExecutor()
        var cacheThreadPool = Executors.newCachedThreadPool()
        var scheduledThreadPool = Executors.newScheduledThreadPool(10)


        ThreadPoolExecutor.AbortPolicy()
        ThreadPoolExecutor.CallerRunsPolicy()
        ThreadPoolExecutor.DiscardOldestPolicy()
        ThreadPoolExecutor.DiscardPolicy()


        fixedPool.execute(Runnable {
            kotlin.run {
                println("FixedThreadPool执行的线程")
            }
        })

        var arrayBlockQueue = ArrayBlockingQueue<Runnable>(10)
        var threadPoolExecutor = ThreadPoolExecutor(4, 10, 100, TimeUnit.SECONDS, arrayBlockQueue)
        for (i in 0..20)
            threadPoolExecutor.execute(Runnable {
                kotlin.run {
                    println("ThreadPoolExecutor执行的第${i}个线程")
                }
            })

        var future2: Future<String> = threadPoolExecutor.submit(Callable<String> { "....." })
        var string = future2.get()
    }
}