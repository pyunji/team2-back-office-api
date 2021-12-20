package com.mycompany.webapp.service.product.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mycompany.webapp.dao.db2product.BinDao;
import com.mycompany.webapp.service.product.BinService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@EnableScheduling
public class PermanentlyDeleteJob {
	
	@Autowired
	private BinDao binDao;
	
	@Autowired
	private BinService binService;
	
	/* 오늘 날짜 기준 30일 지난 휴지통 상품 삭제 */
	@Scheduled(cron = "0 0 0 1 * *") // 매달 1일마다
//	@Scheduled(cron = "*/10 * * * * *") // 10초마다 (테스트용)
	public void permDel() {
		log.info("==========스케줄러 실행=========");
        java.util.Date u = new java.util.Date();
        java.sql.Date delDay = new java.sql.Date(u.getTime()); 
        log.info("Today is... the day... " + delDay);
        // 오늘 날짜 기준 휴지통에 30일 넘게 있던 상품 아이디 가져옴
        List<String> targetPstockids = binDao.selectDelTargetPstockids(delDay);
        log.info("targetPstockids = " + targetPstockids);
        // 슥삭 삭제
        binService.permDel(targetPstockids);
	}
	
}
