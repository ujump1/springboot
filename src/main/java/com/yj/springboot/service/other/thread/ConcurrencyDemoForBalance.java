package com.yj.springboot.service.other.thread;

//synchronized关键词
//synchronized关键字的作用域有二种：
//
//        1）是某个对象实例内，synchronized aMethod(){}可以防止多个线程同时访问这个对象的synchronized方法（如果一个对象有多个synchronized方法，只要一个线程访问了其中的一个synchronized方法，其它线程不能同时访问这个对象中任何一个synchronized方法）。这时，不同的对象实例的 synchronized方法是不相干扰的。也就是说，其它线程照样可以同时访问相同类的另一个对象实例中的synchronized方法；
//
//        2）是某个类的范围，synchronized static aStaticMethod{}防止多个线程同时访问这个类中的synchronized static 方法（因为此时用的是 对象.class 作为锁）。它可以对类的所有对象实例起作用。
//可使用wait/notify 一个类中共享一对

public class ConcurrencyDemoForBalance {

    public static void main(String[] args) {
        Account account = new Account(2000);
        new DrawMoneyThread(account).start();
        new DepositeThread(account).start();
        System.out.println("当前余额"+account.getBalance());
    }
}

class DepositeThread extends Thread{

    private Account account;

    public DepositeThread(Account account){
        this.account = account;
    }

    @Override
    public void run() {
        //每次存200，10次共存2000
        for(int i = 0; i < 10; i++){
            account.deposit(200, i + 1);
        }
    }
}

class DrawMoneyThread extends Thread{

    private Account account;

    public DrawMoneyThread(Account account){
        this.account = account;
    }

    @Override
    public void run() {
        //每次取100，10次共取1000
        for(int i = 0; i < 10; i++){
            account.withdraw(100, i + 1);
        }
    }

}


