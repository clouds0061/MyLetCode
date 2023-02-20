package com.android.Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockInJava {
    private String data = "";
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    public void get(){
        readWriteLock.readLock().lock();
        try {
            Thread.sleep(10000);
            System.out.println("读取数据");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void set(String input){
        readWriteLock.writeLock().lock();
        try {
            this.data = input;
            Thread.sleep(10000);
            System.out.println("写入数据");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }


    private Lock myLock = new ReentrantLock();
    public void a(){
        try {
            myLock.lock();
            b();
        }finally {
            myLock.unlock();
        }
    }

    public void b(){
        try {
            myLock.lock();
        }finally {
            myLock.unlock();
        }
    }

    public static void main(String[] args) {
        new ReentrantReadWriteLockInJava().a();
        new ReentrantReadWriteLockInJava().b();
    }
}
