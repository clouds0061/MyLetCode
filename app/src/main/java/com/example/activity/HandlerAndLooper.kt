package com.example.activity

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.util.Arrays
import java.util.Objects

class HandlerAndLooper {

    fun testHandler() {
//        var handler = Handler()
//
//
//        Looper.prepare()
//        Looper.myLooper()?.quit()
//
//        handler.post {
//            run {
//
//            }
//        }
//
//
//        var thread1 = Thread({},"")
//        var thread = Thread {
//            kotlin.run {
//            }
//        }
//
//        thread.start()
//        thread.run()
//
////        System.arraycopy()
//
//        Thread.sleep(10000)
//
//        Thread.yield()
//
//        var objects = Object()
//        objects.wait()
//
//
//        Looper.prepare()
//        //这种非静态得匿名内部类可能造成内存泄露
        var handler2 =  Handler()
        var meg = Message.obtain()
        var whens = meg.`when`
        whens = -111111L
        meg.data.putString("name","zz")

        handler2.handleMessage(meg)
        handler2.sendMessage(meg)

        handler2.post(object : Runnable{
            override fun run() {

            }
        })
//        Looper.loop()
//
//        System.currentTimeMillis()
    }

}

fun main(args : Array<String>){

//    var message = Message()
//    message.what = 1
//    var message2 = Message.obtain()
//
//    println(message === message2)
//
//
//    var messageA = Message()
//    messageA.recycle()
//
//    var messageB = Message.obtain()
//    println(messageA === messageB)

    var msg : Message? = null
    var msg2 = Message.obtain()
    var msg3 = Message()

    msg2.what = 2
    msg3.what = 3

    msg = msg2

    if (msg!=null) msg.arg2

}






















