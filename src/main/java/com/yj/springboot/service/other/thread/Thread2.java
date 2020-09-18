package com.yj.springboot.service.other.thread;

public class Thread2 implements Runnable{
//    //自定义变量
//    private int i;
//    //重写run()方法，run()方法的方法体是线程执行体
//    public void run(){
//        //实现Runnable接口时，只能使用如下方法获取线程名
//        System.out.println(Thread.currentThread().getName() + "   " + i);
//        i++;
//    }
//
//    public static void main(String[] args){
//        System.out.println(Thread.currentThread().getName());
//        Thread2 tt = new Thread2();
//        //创建第一个线程并开始执行
//        //输出   新线程1   0
//        new Thread(tt,"新线程1").start();
//        //创建第二个线程并开始执行
//        //输出   新线程2   1
//        new Thread(tt,"新线程2").start();
//        //使用Lambda表达式创建Runnable对象
//        new Thread(()->{
//            System.out.print("AmosH");
//            System.out.println("'s blog");
//        }).start();
//    }
    private int i;

    public Thread2(int i) {
        this.i = i;
    }

    public void run()
    {
        System.out.println(Thread.currentThread().getName()+"  "+i);
        i++;
    }
    public static void main(String args[]){
        System.out.println(Thread.currentThread().getName());
        Thread2 tt=new Thread2(1);
        new Thread(tt,"tt1").start();
        new Thread(tt,"tt2").start();
        new Thread(()->{
            System.out.println("yujiang");
        }).start();

    }
}

