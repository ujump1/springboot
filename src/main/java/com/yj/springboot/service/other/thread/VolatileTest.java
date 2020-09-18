package com.yj.springboot.service.other.thread;

public class VolatileTest {

    private static int MY_INT = 0;
    private volatile static int a = 0;

    public static void main(String[] args) {
        Account a = new Account(0);
        new ChangeListener(a).start();
        new ChangeMaker(a).start();
    }
}


class ChangeListener extends Thread {

	 private  Account a;

	 public ChangeListener(Account a){this.a=a;}

	 @Override
	 public void run() {
		 double local=a.getBalance();
		 while (a.getBalance() <5){
			 //System.out.println("1"+(a.getBalance()<5)+a.getBalance());
			// System.out.println("local" + a.getBalance()+  local );
			// System.out.println("2"+(a.getBalance()<5)+a.getBalance());
			 if( a.getBalance()!=local){
				 System.out.println("改变 a " + a.getBalance());
				 local=a.getBalance();
			 }
			 try {
				 Thread.sleep(500);
			 } catch (InterruptedException e) { e.printStackTrace(); }
		 }
	 }
 }

     class ChangeMaker extends Thread{

        private  Account a;

        public ChangeMaker(Account a){this.a=a;}
        @Override
        public void run() {
            int local_value = 0;
            while (a.getBalance() <5){
                System.out.println("增加 a 至" + (local_value+1));
                a.setBalance( ++local_value);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }

