package com.yj.springboot.service.schedule;


import com.yj.springboot.service.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ShowTime {

	//@Scheduled(cron = "*/5 * * * * ?")
	public void showTime(){

		System.out.println("定时任务1"+Thread.currentThread().getName()+new Date());
	    log();
	}
	//@Scheduled(cron = "*/5 * * * * ?")
	public void showTime1(){
		System.out.println("定时任务2"+Thread.currentThread().getName()+new Date());
	}

	public void log(){
		String message="This is test{}";
		LogUtil.bizLog(message,1);
	}
}
