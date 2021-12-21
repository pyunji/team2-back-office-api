package com.mycompany.webapp.service.member.job;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.mycompany.webapp.dao.db1member.MemberDao;
import com.mycompany.webapp.dao.db3orders.OrderDao;
import com.mycompany.webapp.vo.GradeAdmin;
import com.mycompany.webapp.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GradeApplicationJob {
	private ThreadPoolTaskScheduler scheduler;
	//default 10분 마다
	private String cron = "0 0/10 * * * *";
	
	//매일 자정
	//private String cron = "0 0 0 * * *";
	
	@Resource private MemberDao memberDao;
	
	@Resource private OrderDao orderDao;
	
	public void startScheduler() {
		scheduler = new ThreadPoolTaskScheduler();
		scheduler.initialize();
		// scheduler setting 
		scheduler.schedule(getRunnable(), getTrigger());
	}
	
	public void changeCronSet(String cron) {
		this.cron = cron;
	}

	public void stopScheduler() {
		scheduler.shutdown();
	}

	private Runnable getRunnable() {
		// do something
		return () -> {
			List<Member> members = memberDao.selectAllMembers();
			for(Member member : members) {
				List<Integer> prices = orderDao.selectPriceByMid(member.getMid());
				int totalPrice = 0;
				for(int price : prices) {
					totalPrice += price;
				}
				
				//멤버 현재 등급
				int userGrade = member.getGradeLevel();
				
				//금액에 따른 등급과 맞는지 확인후 아니라면 update
				if(totalPrice<1000000) {
					//1level
					if(userGrade!=1) member.setGradeLevel(1);
				} else if(totalPrice<2000000) {
					//2level
					if(userGrade!=2) member.setGradeLevel(2);
				} else if(totalPrice<3000000) {
					//3level
					if(userGrade!=3) member.setGradeLevel(3);
				} else if(totalPrice<5000000) {
					//4level
					if(userGrade!=4) member.setGradeLevel(4);
				} else {
					//5level
					if(userGrade!=5) member.setGradeLevel(5);
				}
			
				memberDao.updateMember(member);
			}
			//System.out.println(LocalDateTime.now().toString() + "scheduler");
		};
	}

	private Trigger getTrigger() {
		// cronSetting
		return new CronTrigger(cron);
	}

	@PostConstruct
	public void init() {
		startScheduler();
	}

	@PreDestroy
	public void destroy() {
		stopScheduler();
	}
}
