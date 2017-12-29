/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:Thread
 * Module Name:TODO:Module
 */

/**
 * what:    (这里用一句话描述这个类的作用). <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 郭飞 created on 2017/12/29
 */
public class DeadLock {

    public static void main(String[] args){
          final Object lock1= new Object();
          final Object lock2= new Object();
          new Thread(new Runnable() {
              public void run() {
                  String name = Thread.currentThread().getName();
                  synchronized (lock1){
                      System.out.println(name + " got lock1,  want Lock2");
                      try {
                          Thread.sleep(100);
                      } catch (InterruptedException e) {

                          e.printStackTrace();
                      }
                      synchronized (lock2) {
                          System.out.println(name + " got lock2");
                          System.out.println(name + ": say Hello!");
                      }
                  }
              }
          },"线程A").start();

        new Thread(new Runnable() {
            public void run() {
                String name = Thread.currentThread().getName();
                synchronized (lock2){
                    System.out.println(name + " got lock2,  want Lock1");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                    synchronized (lock1) {
                        System.out.println(name + " got lock1");
                        System.out.println(name + ": say Hello!");
                    }
                }
            }
        },"线程B").start();
    }
}
