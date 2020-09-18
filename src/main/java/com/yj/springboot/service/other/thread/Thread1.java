package com.yj.springboot.service.other.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Thread1 extends Thread{
//    //可修改名字
//    public Thread1(String name){
//        super(name);
//    }
//    public Thread1()
//    {
//
//    }
//    public void run(){
//        for(int i=0;i<5;i++){
//            //使用线程的getName()方法可以直接获取当前线程的名称
//            System.out.println(this.getName() + "   " + i);
//        }
//    }
//    public static void main(String args[]){
//        System.out.println(Thread.currentThread().getName());
//        Thread1 ttt=new Thread1("ttt");
//        Thread1 ttt1=new Thread1();
//        ttt.run();
//        ttt1.setName("ttt1");
//        ttt1.run();
//
//    }
    public Thread1(String name){
        super(name);
    }
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println(this.getName()+"  "+i);
        }
    }
    public static void main(String args[]){
        System.out.println(Thread.currentThread().getName());
        ExecutorService cachedThreadPools= Executors.newCachedThreadPool();
        Thread1 ttt=new Thread1("tttt");
        ttt.start();
        Thread1 ttt1=new Thread1("tttt1");
        cachedThreadPools.execute(ttt1);
        cachedThreadPools.execute(ttt);
        cachedThreadPools.submit(ttt1);
        cachedThreadPools.shutdown();
    }
}
