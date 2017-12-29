/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:Thread
 * Module Name:TODO:Module
 */

/**
 * what:    方法join()的作用是等待线程对象销毁。
 *
 * @author 郭飞 created on 2017/12/28
 */
public class Join implements  Runnable{
    private String name;
    public Join(String name){
         this.name = name;
    }

    public String getName() {
        return name;
    }


    public void run() {
        System.out.println(getName());
    }
    public static void main(String[] args) throws InterruptedException {
        // 启动子进程
        Thread thread = new Thread( new Join("T1"));
        Thread thread2 = new Thread( new Join("T2"));
        Thread thread3 = new Thread( new Join("T3"));

        thread.start();
        thread3.start();
        thread3.join();
        thread2.start();
        thread2.join();


    }
}
