package com.mycompany.webapp.dto.event;

import java.util.List;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.vo.Event;

import lombok.Data;

@Data
public class EventResult {
	List<Event> eventList;
	Pager pager;
}
