package com.yj.springboot.service.other.thread;

//账户类
public class Account {
    //存钱
    public synchronized void deposit(double amount, int i){
        try {
            Thread.sleep((long)Math.random()*10000);  //模拟存钱的延迟
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.balance = this.balance + amount;
        System.out.println("第" + i + "次，存入钱:" + amount);
        System.out.println("第" + i + "次，存钱后账户余额:" + this.balance);
    }

    //取钱
    public synchronized void withdraw(double amount, int i){
        if(this.balance >= amount){
            try {
                Thread.sleep((long)Math.random()*10000);  //模拟取钱的延迟
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.balance = this.balance - amount;
            System.out.println("第" + i + "次，取出钱:" + amount);
            System.out.println("第" + i + "次，取钱后账户余额:" + this.balance);
        }else{
            System.out.println("第" + i + "次，余额不足");
        }
    }

    public Account(){

    }

    public Account(double balance){
        this.balance = balance;
    }

    public  double getBalance(){
        return this.balance;
    }
    public   void setBalance(double balance){
        this.balance=balance;
    }

    private  double balance;
}
