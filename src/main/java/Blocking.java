/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:Thread
 * Module Name:TODO:Module
 */


/**
 * what:   这里使用两种方式实现阻塞，一种是synchronized方式，另一种是使用ReentrantLock
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 郭飞 created on 2017/12/29
 */
public class Blocking {
  /*  final Lock lock = new ReentrantLock();
    final Condition notFull  = lock.newCondition();
    final Condition notEmpty = lock.newCondition();
    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                System.out.println("put notFull 等待");
                notFull.await();
            }
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            System.out.println("put notEmpty 唤醒");
            notEmpty.signal();
        } finally {
            System.out.println("put lock 解锁");
            lock.unlock();
        }
    }


    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                System.out.println("take notEmpty 等待");
                notEmpty.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            System.out.println("take notFull 唤醒");
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
            System.out.println("take lock 解锁");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Blocking bb = new Blocking();
        System.out.println(Thread.currentThread()+","+bb);

        new Thread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread()+","+bb);
                    bb.put("xx");
                    bb.put("yy");
                    bb.put("zz");
                    bb.put("zz");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        bb.take();
    }*/

    private Object[] items = new Object[4];
    private Object notEmpty = new Object();
    private Object notFull = new Object();
    int count,putidx,takeidx;

    public  void put(Object obj) throws InterruptedException{
        synchronized(notFull){
            while(count == items.length){
                notFull.wait();
            }
        }
        items[putidx] = obj;
        System.out.println("放入"+putidx+"个元素"+obj);
        if(++putidx == items.length){
            putidx = 0;
        }
        count ++;
        synchronized (notEmpty) {
            notEmpty.notify();
        }
    }
    public Object take() throws InterruptedException{
        synchronized(notEmpty){
            while(count == 0){ // 啥也没有呢 取啥
                notEmpty.wait();
            }
        }
        Object x = items[takeidx];
        System.out.println("取第"+takeidx+"个元素"+x);
        if(++takeidx == items.length){
            takeidx = 0;
        }
        count --;
        synchronized (notFull) {
            notFull.notify();
        }
        return x;
    }
    public static void main(String[] args) throws InterruptedException {
        final Blocking bb = new Blocking();
        System.out.println(Thread.currentThread()+","+bb);

        new Thread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread()+","+bb);
                    bb.put("1");
                    bb.put("2");
                    bb.put("3");
                    bb.put("4");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        bb.take();
        bb.take();
    }

}
