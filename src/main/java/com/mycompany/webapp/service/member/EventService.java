package com.mycompany.webapp.service.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.EventDao;
import com.mycompany.webapp.vo.Event;

@Service
public class EventService {
	@Resource EventDao eventDao;
	
	public List<Event> selectAllEvents() {
		return eventDao.selectAllEvents();
	}
}
