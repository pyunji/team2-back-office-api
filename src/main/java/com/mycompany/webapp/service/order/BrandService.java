package com.mycompany.webapp.service.order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db2product.BrandDao;
import com.mycompany.webapp.dao.db3orders.OrderSearchDao;
import com.mycompany.webapp.dto.display.ShareByBrand;
import com.mycompany.webapp.dto.display.ShareByBrandResult;
import com.mycompany.webapp.dto.display.StatDto;
import com.mycompany.webapp.dto.display.StatResult;
import com.mycompany.webapp.dto.order.OrderResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BrandService {

	@Resource OrderSearchDao orderSearchDao;
	@Resource BrandDao brandDao;
	public ShareByBrandResult  getShareByBrand() {
		//주문완료인 모든 주문을 다 가져옴
		OrderResult orderResult = new OrderResult();
		orderResult.setOrderList(orderSearchDao.selectOrderListAll());
		log.info("orderList 전체 값" +orderResult.toString());
		//반복으로 주문의 pstockid로 
		//key는 Brand이름 value는 총 가격과 총 수량이다.
		ShareByBrandResult shareByBrandResult = new ShareByBrandResult();
		ArrayList<ShareByBrand> array = new ArrayList<>();
		Map<String, ShareByBrand> map = new LinkedHashMap<>();
		for(int i = 0; i < orderResult.getOrderList().size();i++) {
			String brandName = brandDao.selectOrderBrand(orderResult.getOrderList().get(i));
			int addPrice = orderResult.getOrderList().get(i).getAfterDcPrice();
			int addCount = orderResult.getOrderList().get(i).getOcount();
			
			ShareByBrand mapShare = new ShareByBrand();
			if(!map.containsKey(brandName)) {
				mapShare.setBname(brandName);
				mapShare.setTotalcount(addCount);
				mapShare.setTotalprice(addPrice);
				map.put(brandName, mapShare);
			}
			else {
				addPrice = addPrice + map.get(brandName).getTotalprice();
				addCount = addCount + map.get(brandName).getTotalcount();
				mapShare.setTotalcount(addCount);
				mapShare.setTotalprice(addPrice);
				map.put(brandName, mapShare);
			}
			
		}
		for( String key : map.keySet()) {
			ShareByBrand shareByBrand = new ShareByBrand();
			shareByBrand.setBname(key);
			shareByBrand.setTotalcount(map.get(key).getTotalcount());
			shareByBrand.setTotalprice(map.get(key).getTotalprice());
			array.add(shareByBrand);
		}
		shareByBrandResult.setShareByBrandList(array);
		
		
		
		return shareByBrandResult;
	}
	public StatResult getStatByDay() {
		
		LocalDate now = LocalDate.now();
		String day = now.toString();
		day = day.replace("-", "/");
		
		StatResult statResult = new StatResult();
		statResult.setStatDtoList(orderSearchDao.selectStatByDay(day));
		
		return statResult;
	}
		
		@Scheduled(cron = "59 23 * * * *") // 매 23시 59분마다 통계 테이블 업데이트
		public void setStatByDay() {
		
		log.info("스케줄러 테스트");
		LocalDate now = LocalDate.now();
		String day = now.toString();
		day = day.replace("-", "/");
		
		StatResult statResult = new StatResult();
		statResult.setStatDtoList(orderSearchDao.selectStatByDay(day));
		
		StatDto statDto = orderSearchDao.selectScheduled(day);
		orderSearchDao.insertScheduled(statDto);

	}
	public StatResult getStatByMonth() {
		StatResult statResult = new StatResult();
		
		
		List<StatDto> list = new ArrayList();
		String [] arr = {"01", "02","03","04","05","06","07","08","09","10","11","12"};
		for(int i = 0; i < 12; i++) {
			LocalDate now = LocalDate.now();
			String day = now.toString();
			day = day.replace("-", "/");
			day = day.substring(0,5);
			day = day + arr[i];
			list.add(orderSearchDao.selectStatByMonth(day));
		}
		statResult.setStatDtoList(list);
		return statResult;
	}
	
	public StatResult getStatByYear() {
		StatResult statResult = new StatResult();
		LocalDate now = LocalDate.now();
		String day = now.toString();
		day = day.replace("-", "/");
		day = day.substring(0,4);
		orderSearchDao.selectStatByYear(day);
		statResult.setStatDtoList(orderSearchDao.selectStatByYear(day));
		
		return statResult;
	}

}
