/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:Thread
 * Module Name:TODO:Module
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * what:    (这里用一句话描述这个类的作用). <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 郭飞 created on 2017/12/28
 */
public class Lock2 {
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    public static void main(String[] args)  {
        final Lock2 test = new Lock2();

       /* for(int i=0;i<3;i++){
            new Thread(){
                public void run() {
                    test.get(Thread.currentThread());
                };
            }.start();
        }

        for(int i=0;i<3;i++) {
            new Thread() {
                public void run() {
                    test.put(Thread.currentThread());
                }

                ;
            }.start();
        }*/

      //要注意的是，如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待释放读锁。
        // 如果有一个线程已经占用了写锁，则此时其他线程如果申请写锁或者读锁，则申请的线程会一直等待释放写锁。
        /*final Lock test = new Lock();
        test.getNotUnlock(Thread.currentThread());
        test.put(Thread.currentThread());*/

        //锁降级测试
        test.processData(false);
    }
    //创建锁，不释放锁
    public void getNotUnlock(Thread thread) {
        rwl.readLock().lock();
        System.out.println(thread.getName()+"正在进行读操作");

    }
    //创建读锁，并释放锁
    public void get(Thread thread) {
        rwl.readLock().lock();
        System.out.println(thread.getName()+"正在进行读操作");
        try {
            Thread.sleep((int)(Math.random()*1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwl.readLock().unlock();
        }
        System.out.println(thread.getName()+"读操作完毕");
    }
    //创建写锁，并释放锁
    public void put(Thread thread) {
        rwl.writeLock().lock();
        System.out.println(thread.getName()+"正在进行写操作");
        try {
            Thread.sleep((int)(Math.random()*1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwl.writeLock().unlock();
        }
        System.out.println(thread.getName()+"写操作完毕");
    }
    //锁降级
    //锁降级是指把持住（当前拥有的）写锁，再获取到读锁，随后释放（先前拥有的）写锁的过程。
    public void processData(boolean update){
        int[] a = null;
        rwl.readLock().lock();
        if (!update) {
            // 必须先释放读锁
            rwl.readLock().unlock();
            // 锁降级从写锁获取到开始
            rwl.writeLock().lock();
            try {
                if (!update) {
                    System.out.print("准备数据");
                     a=new int[]{1,2,3};
                    update = true;
                }
                rwl.readLock().lock();
            } finally {
                rwl.writeLock().unlock();
            }
            // 锁降级完成，写锁降级为读锁
        }
        try {
          for(int o :a){
              System.out.print(o);
          }
        } finally {
            rwl.readLock().unlock();
        }
    }
}
