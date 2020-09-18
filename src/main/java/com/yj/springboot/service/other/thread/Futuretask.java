package com.yj.springboot.service.other.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Futuretask {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long sum = 0;
        ExecutorService exec = Executors.newCachedThreadPool();
        List<Callable<Long>> callList = new ArrayList<Callable<Long>>();
        callList.add(new Callable<Long>(){
            public Long call(){
                long sum = 0;
                for(long i=0;i<50;i++){
                    sum+=i;
                }
                return sum;
            }
        });
        callList.add(new Callable<Long>(){
            public Long call(){
                long sum = 0;
                for(long i=50;i<=100;i++){
                    sum+=i;
                }
                return sum;
            }
        });
        // 一 future
        // 1. 启动多个
        List<Future <Long>> futureList=exec.invokeAll(callList);
        for(Future<Long> future:futureList){
            sum+=future.get();
        }
        System.out.println(sum);
        // 2. 启动一个
        Future<Long> future1 = exec.submit(callList.get(0));
        Future<Long> future2 = exec.submit(new Callable<Long>(){
            public Long call(){
                long sum = 0;
                for(long i=50;i<=100;i++){
                    sum+=i;
                }
                return sum;
            }
        });
        // 二 futureTask
        // 1 启动一个
        FutureTask<Long> futureTask1 = new FutureTask<>(new Callable<Long>(){
            public Long call(){
                long sum = 0;
                for(long i=50;i<=100;i++){
                    sum+=i;
                }
                return sum;
            }
        });
        exec.submit(futureTask1);   //启动1
        Thread thread = new Thread(futureTask1);
        thread.start(); // 启动二
        exec.shutdown();
    }
}
