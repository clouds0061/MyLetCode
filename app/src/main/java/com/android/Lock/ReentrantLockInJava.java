package com.android.Lock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 */
public class ReentrantLockInJava {

    public void easyUseLock() {
        Lock lock = new ReentrantLock(true);//入参 是否公平
        try {
            lock.lock();
            //doSomething
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void easyUseTryLock() {
        Lock lock = new ReentrantLock(true);//入参 是否公平
        if (lock.tryLock()){//如果已经被lock，则立即返回false不会等待，达到忽略操作的效果
            try {
                //doSomething
            }finally {
                lock.unlock();
            }
        }
    }

    public void easyUseTryLockWithIncome(int seconds){
        Lock lock = new ReentrantLock(true);//入参 是否公平
        try {
            if (lock.tryLock(10L, TimeUnit.SECONDS)){//如果已经被lock，尝试等待5s，看是否可以获得锁，如果10s后仍然无法获得锁则返回false继续执行
                try {
                    //doSomething
                }finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {

    }
}
