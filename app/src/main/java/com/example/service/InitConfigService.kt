package com.example.service

import android.app.IntentService
import android.content.Context
import androidx.core.app.JobIntentService
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * 因为 IntentService 和 JobIntentService 都将要被弃用 所一百度了一下替代品
 * WorkManager
 * 一、引言  WorkManager 是google提供的异步执行任务的管理框架，是 Android Jetpack 的一部分，
 * 会根据手机的API版本和应用程序的状态来选择适当的方式执行任务。  在后台执行任务的需求是非常常见的，
 * Android也提供了多种解决方案，如JobScheduler、Loader、Service等，如果这些API没有被恰当使用，
 * 则可能会消耗大量电量。Android在解决应用程序耗电问题上做了各种尝试，从Doze到App Standby，
 * 通过各种方式限制和管理应用程序，以保证应用程序不会在后台消耗过多的设备电量。
 * WorkManager为应用程序中那些不需要及时完成的任务提供了一个统一的解决方案，以便在设备电量和用户体验之间达到一个比较好的平衡
 */
//第二部继承Worker类 重写dowork()方法
class InitConfigService(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    //二、WorkManager特点
    //
    //    针对的是不需要及时完成的任务
    //    例如，发送应用程序日志、同步应用程序数据、备份用户数据等，这些任务一般都不需要立即完成，如果我们自己来管理这些任务，逻辑可能会非常复杂，若API使用不恰当，可能会消耗大量电量。
    //    保证任务一定会执行
    //    WorkManager能保证任务一定会被执行，即使应用程序当前不在运行中，甚至在设备重启过后任务仍然会在适当的时刻被执行。WorkManager有自己的数据库，关于任务的所有信息和数据都保存在该数据库中。因此只要任务交给了WorkManager，哪怕应用程序彻底退出或者设备被重新启动，WorkManager依然能够保证完成任务。
    //    兼容范围广
    //    WorkManager最低能兼容API Level 14，并且不需要设备安装Google Play Services。因此，不用过于担心兼容性问题，因为API Level 14已经能够兼容几乎100%的设备了
    //    看介绍的有点吊啊，兼容好做
    //    而WorkManager不是即时的，它不能保证任务能立即得到执行。这个很重要，所以只能用来做一些后台任务，比如上传日志，自动更新(非即时的后台自动更新)等

    //重写方法，里面添加需要运行的任务
    override fun doWork(): Result {
        //7-1 在任务中可以获得传入的数据并使用，相应的，也可以将数据从任务中传递回WorkManager
        var name = inputData.getString("name")
        var result = Data.Builder().putString("name", "收到了名字是$name").build()
        return Result.success(result)//任务成功
    }
}