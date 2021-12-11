package com.mycompany.webapp.dao.db1member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.event.EventSearchForm;
import com.mycompany.webapp.vo.Event;

@Mapper
public interface EventDao {
	public List<Event> selectAllEvents();
	public void addEvent(Event event);
	public int getTotalEventNum(EventSearchForm searchForm);
	public List<Event> selectEventList(EventSearchForm searchForm);
}
