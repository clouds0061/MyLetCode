package com.android.Lock;

import static java.lang.Thread.sleep;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SynchronizedInJava {

    int i = 0;
    public static void main(String[] args) {
        SynchronizedInJava inJava = new SynchronizedInJava();
        inJava.buy();
        inJava.buy();
        inJava.buy();


        SynchronizedInJava inJava2 = new SynchronizedInJava();
        inJava2.buy();

        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);
    }

    volatile int a = 1;

    /**
     * 同步方法 这里的锁是this，也就是对象所
     */
    public synchronized void buy(){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        i++;
        System.out.println(i);
    }

    /***
     * 静态同步方法，使用的锁是SynchronizedInJava.class
     */
    public static synchronized void buyStatic(){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void block(){
        synchronized(this){
            //这是个代码块
        }
    }

    Object object = new Object();
    public void block2(){
        synchronized(object){
            //这是个代码块
        }
    }

    public synchronized void fun(){

    }


    /**
     * 静态方法
     */
    public synchronized static void staticLock(){
        //....
    }

    //代码块加类锁，即使用class文件当锁
    public void block3(){
        synchronized (SynchronizedInJava.class){
            //...
        }
    }
}


