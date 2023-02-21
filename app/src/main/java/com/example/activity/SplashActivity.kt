package com.example.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.renderscript.RenderScript.Priority
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import androidx.work.impl.Processor
import androidx.work.impl.utils.ProcessUtils
import com.example.myletcode.databinding.ActivitySplashBinding
import com.example.service.InitConfigService
import com.example.service.ThreadPoolUtils
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference
import java.time.Duration
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation

/**
 * 启动页，避免白屏闪屏
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    //不知道为啥，这个一个参数的才能正常欸
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initService()
//        initThreadPool()
        binding = ActivitySplashBinding.inflate(layoutInflater)
//
        setContentView(binding.root)
        Log.d("SplashActivity" , "开始等待!")
        Thread.sleep(2000)
        Log.d("SplashActivity" , "等待完成!")
        jump2Main()
//        Application
    }


    /**
     * 线程池
     */
    private fun initThreadPool() {
        var thread = ThreadPoolUtils.instance
        var thread2 = ThreadPoolUtils.getService2()

        thread.getService().execute(Runnable {
            kotlin.run {
                Thread.currentThread().priority = 1//设置优先度
                Thread.currentThread().name = "hello world"
            }
        })
        thread2.execute(Runnable {
            kotlin.run {
                Thread.currentThread().priority = 5
                Thread.currentThread().name = "hello kotlin"
            }
        })


    }

    /**
     * //3.配置任务
     * 初始化 后台任务
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initService() {
        //3-1.初始化任务触发条件
        //约束条件如下
        //NetworkType	约束运行工作所需的网络类型。例如 Wi-Fi (UNMETERED)
        //BatteryNotLow	如果设置为 true，那么当设备处于“电量不足模式”时，工作不会运行
        //RequiresCharging	如果设置为 true，那么工作只能在设备充电时运行
        //DeviceIdle	如果设置为 true，则要求用户的设备必须处于空闲状态，才能运行工作。在运行批量操作时，此约束会非常有用；若是不用此约束，批量操作可能会降低用户设备上正在积极运行的其他应用的性能
        //StorageNotLow	如果设置为 true，那么当用户设备上的存储空间不足时，工作不会运行
        var constraints = Constraints(
            //NetworkType.NOT_REQUIRED 对网络没有要求
            //NetworkType.CONNECTED 网络链接额时候执行
            //NetworkType.UNMETERED 任何工作网络连接 比如 WIFI 联动 移动下执行
            //NetworkType.NOT_ROAMING 非漫游状态下执行
            //NetworkType.METERED 需要按流量计费的网络下比如 3G,4G下执行
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build()
        )

        //3-2.为任务创建workRequest对象
        //WorkRequest对象有两种类型
        //    OneTimeWorkRequest:只会执行一次的任务请求
        //    PeriodicWorkRequest:将以周期形式反复执行的任务请求

        //3-3.设置标签
        //在WorkRequest中可以为单个任务设置标签，
        // 也可以为一组具有逻辑联系的多个任务设置相同的标签，
        // 标签可以在任务状态操作中作为任务的标识，
        // 可以通过标签来监听任务状态，也可以取消所有具有相同标签的任务

        //3-4 设置重试和退避策略
        //当任务的返回值为Result.retry()时，系统需要根据一定的策略来决定每次重试的间隔时间，策略中包含退避延迟时间和退避策略。其中，退避延迟时间指定了首次尝试后重试工作前的最短等待时间，其值不得小于10秒；退避策略定义了退避延迟时间随时间以怎样的方式增长，取值分为两种，线性倍数增长：LINEAR 和指数型增长：EXPONENTIAL。
        //系统默认的策略是EXPONENTIAL，延迟时间为 10 秒，我们也可以在WorkRequest中自定义策略，下面举例设置了一个线性增长的，等待时间初值为12秒的策略

        var workRequest = OneTimeWorkRequest.Builder(InitConfigService::class.java)
            .setConstraints(constraints) //设置任务触发条件
            .setInitialDelay(10, TimeUnit.SECONDS) //设置延迟 10s
            .addTag("InitConfigService") //设置标签
            .setBackoffCriteria(BackoffPolicy.LINEAR, Duration.ofSeconds(10))//设置重试和退避策略
            .build()

        //4 将任务加入队列
        //避免内存泄露 使用Application的context 或者还可以加一层若引用
        var weakReference = WeakReference<Context>(this.applicationContext)
        var workManager = WorkManager.getInstance(weakReference.get()!!)
        workManager.enqueue(workRequest)

        //5 任务状态监听
        //在任务运行的时候，我们可以随时通过任务id或任务标签来查询其状态，利用对应的LiveData方法可以注册监听器来观察WorkInfo的变化
        workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer<WorkInfo> {
            Log.d("workInfo", "workInfo = ${it.toString()}")
        })

        //6.取消任务
        //当不再需要运行先前加入队列的任务时，可以根据任务id或任务标签来取消任务，WorkManager 会在后台检查工作的状态。如果工作已经完成，系统不会执行任何操作。否则，工作的状态会更改为CANCELLED，之后就不会运行这个工作。任何依赖于此工作的WorkRequest的状态也将变为CANCELLED
        //所以如果对任务有取消的需求，那么需要一个地方持久化任务id
        Timer().schedule(object : TimerTask() {
            override fun run() {
                workManager.cancelWorkById(workRequest.id)//更具id 取消任务
                workManager.cancelAllWorkByTag("InitConfigService")//根据标签 取消任务
            }
        }, 2000)

        //7.参数传递
        //WorkManager可以与具体任务之间互相传递参数，在定义WorkRequest对象时可以将参数传入任务，所传参数是一个Data类型的实例化对象
        //在有些开启任务的时候传入任务需要的参数
        var inputData = Data.Builder()
            .putString("name", "张三")
            .build()
        var requestWithData = OneTimeWorkRequest.Builder(InitConfigService::class.java)
            .setInputData(inputData)
            .build()
        workManager.enqueue(requestWithData)
        //接受返回的参数
        workManager.getWorkInfoByIdLiveData(requestWithData.id).observe(this, Observer() {
            var result = it.outputData.getString("name")
        })

        //8. 配置周期性任务
        //上述示例均为一次性任务，在实际开发中可能需要定期运行某些任务。例如，定期备份数据、定期下载应用中的新鲜内容或者定期上传日志到服务器，此时则需要用到WorkRequest的另一种类型：PeriodicWorkRequest
        var periodicRequest =
            PeriodicWorkRequest.Builder(InitConfigService::class.java, 2, TimeUnit.HOURS)
                .addTag("周期性任务标签")
                .setInputData(inputData)
                .build()

        //9.任务链与任务组合
        //当需要以特定顺序运行多个任务时，可以创建任务链并将其加入队列，任务链用于指定多个依存任务并定义这些任务的运行顺序。
        //新建两个类AWorker和BWorker继承Worker类，将其按照任务链形式添加进任务列表
        var aRequest = OneTimeWorkRequest.Builder(InitConfigService::class.java)
            .addTag("A周期性任务标签")
            .setInputData(inputData)
            .build()
        var bRequest = OneTimeWorkRequest.Builder(InitConfigService::class.java)
            .addTag("B周期性任务标签")
            .setInputData(inputData)
            .build()
        //9-1.队列只能装一次任务，循环任务不行
        WorkManager.getInstance(weakReference.get()!!)
            .beginWith(aRequest)
            .then(bRequest)
            .enqueue()
        //9-2.有时一些任务需要在若干个任务执行完毕后方可执行，此时就需要用到任务组合
        var aaRequest = OneTimeWorkRequest.Builder(InitConfigService::class.java)
            .addTag("A周期性任务标签")
            .setInputData(inputData)
            .build()
        var bbRequest = OneTimeWorkRequest.Builder(InitConfigService::class.java)
            .addTag("B周期性任务标签")
            .setInputData(inputData)
            .build()
        var cRequest = OneTimeWorkRequest.Builder(InitConfigService::class.java)
            .addTag("A周期性任务标签")
            .setInputData(inputData)
            .build()
        var dRequest = OneTimeWorkRequest.Builder(InitConfigService::class.java)
            .addTag("B周期性任务标签")
            .setInputData(inputData)
            .build()
        var eRequest = OneTimeWorkRequest.Builder(InitConfigService::class.java)
            .addTag("A周期性任务标签")
            .setInputData(inputData)
            .build()
        //将同一条任务线的任务定义一个任务组合
        var workContinuationAB =
            WorkManager.getInstance(weakReference.get()!!).beginWith(aaRequest).then(bbRequest)
        var workContinuationCD =
            WorkManager.getInstance(weakReference.get()!!).beginWith(cRequest).then(dRequest)
        //把任务放入一个集合
        var listContinuation = listOf<WorkContinuation>(workContinuationAB, workContinuationCD)
        //执行任务  任务是按照下面的顺序走的
        // aa    c
        // |     |
        // bb    d
        //  \   /
        //    e
        WorkContinuation.combine(listContinuation).then(eRequest).enqueue()
    }

    //开始使用的是这个 一直出问题，找了好久，要吐了
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }


    /**
     * 跳转到主页面
     */
    private fun jump2Main() {
        startActivity(Intent(this, MainActivity2::class.java))
        finish()
    }

}