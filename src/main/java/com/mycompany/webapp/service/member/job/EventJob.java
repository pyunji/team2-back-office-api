package com.mycompany.webapp.service.member.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mycompany.webapp.dao.db1member.EventDao;
import com.mycompany.webapp.vo.Event;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class EventJob {
	
	@Autowired private EventDao eventDao;
	
	/*오늘 날짜 기준 이벤트 상태 변경*/
	@Scheduled(cron="0 0 0 * * *")	//매일 자정에
//	@Scheduled(cron = "*/10 * * * * *") // 10초마다 (테스트용)
	public void changeEventStatus() {
		log.info("changeEventStatus 스케줄러 실행");
		
        //이벤트 전부 가져오기
        List<Event> events = eventDao.selectAllEvents();
        
        //오늘이랑 비교하면서 status변경해야 하는 event status변경
        for(Event event : events) {
        	Date issueDate = event.getEissueDate();
        	Date expireDate = event.getEexpireDate();
        	
        	Date today = new java.util.Date();
        	log.info(today.toString());
        	
        	if(issueDate.compareTo(today)>0) {
        		//진행 예정 이벤트
        		if(event.getEstatus()!=0) event.setEstatus(0);
        	} else if(expireDate.compareTo(today)<0) {
        		//진행 종료 이벤트
        		if(event.getEstatus()!=2) event.setEstatus(2);
        	} else {
        		//진행 중 이벤트
        		if(event.getEstatus()!=1) event.setEstatus(1);
        	}
        	
        	eventDao.updateEventStatus(event);
        }
        
	}
}
