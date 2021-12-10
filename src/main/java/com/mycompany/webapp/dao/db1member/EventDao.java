package com.mycompany.webapp.dao.db1member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.vo.Event;

@Mapper
public interface EventDao {
	public List<Event> selectAllEvents();
	public void addEvent(Event event);
}
