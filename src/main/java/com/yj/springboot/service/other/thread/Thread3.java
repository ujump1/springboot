package com.yj.springboot.service.other.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Thread3 {
    public static void main(String args[]){
        FutureTask<Integer> task=new FutureTask<>(()->{System.out.println(Thread.currentThread().getName() + "   " + "开始执行任务！");
        return 1;});
        new Thread(task,"线程1 ").start();

        Thread4 callT=new Thread4();
        FutureTask<Integer> task1=new FutureTask<>(callT);
        new Thread(task1,"线程2").start();

        Thread4 callT1=new Thread4();
        FutureTask<Integer> task11=new FutureTask<>(callT);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(task11);
        executor.shutdown();
        try
        {
            System.out.println(task.get());
        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }
}
