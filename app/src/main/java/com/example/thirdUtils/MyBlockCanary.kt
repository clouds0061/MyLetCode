//package com.example.thirdUtils
//
//import com.github.moduth.blockcanary.BlockCanaryContext
//
///**
// * 继承实现监听类
// */
//class MyBlockCanary : BlockCanaryContext() {
//
//
//
//    //事件处理时间的阀值，能够经过修改这个阀值来修改超时阀值
//    override fun provideBlockThreshold(): Int {
//        //超时1s才会处理记录
//        return 1000
//    }
//}