package com.mycompany.webapp.service.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.controller.member.EventController;
import com.mycompany.webapp.dao.db1member.EventDao;
import com.mycompany.webapp.dao.db2product.SearchDao;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.event.EventResult;
import com.mycompany.webapp.dto.event.EventSearchForm;
import com.mycompany.webapp.vo.Event;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventService {
	@Resource EventDao eventDao;
	
	public List<Event> selectAllEvents() {
		return eventDao.selectAllEvents();
	}
	
	public void addEvent(Event event) {
		eventDao.addEvent(event);
	}
	
	public EventResult selectEventList(EventSearchForm searchForm) {
		if(searchForm.getEtitle() != null && (!searchForm.getEtitle().equals(""))) {
			StringBuilder ename = new StringBuilder();
			ename.append("%");
			for(String str:searchForm.getEtitle().split("")) {
				ename.append(str);
				ename.append("%");
			}
			searchForm.setEtitle(ename.toString());
		}
		
		int totalEvent = eventDao.getTotalEventNum(searchForm);
//		log.info(String.valueOf(totalEvent));

		int rowsPerPage = searchForm.getPager().getRowsPerPage();
		int pagesPerGroup = searchForm.getPager().getPagesPerGroup();
		int pageNo = searchForm.getPager().getPageNo();
		Pager pager = new Pager(rowsPerPage, pagesPerGroup, totalEvent, pageNo);
		
		searchForm.setPager(pager);
		EventResult eventResult = new EventResult();
		eventResult.setPager(pager);
		
		eventResult.setEventList(eventDao.selectEventList(searchForm));
		
		return eventResult;
	}
}
