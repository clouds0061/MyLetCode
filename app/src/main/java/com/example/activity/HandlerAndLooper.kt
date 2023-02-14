package com.example.activity

import android.os.Handler
import android.os.Looper
import android.os.Message

class HandlerAndLooper {

    fun testHandler() {
        var handler = Handler()
        Looper.prepare()
        Looper.myLooper()?.quit()

        handler.post {
            run {

            }
        }



    }

}

fun main(args : Array<String>){

    var message = Message()
    message.what = 1
    var message2 = Message.obtain()

    println(message === message2)


    var messageA = Message()
    messageA.recycle()

    var messageB = Message.obtain()
    println(messageA === messageB)
}