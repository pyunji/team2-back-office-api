package com.mycompany.webapp.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.service.member.EventService;
import com.mycompany.webapp.vo.Event;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/event")
public class EventController {
	@Resource EventService eventService;
	
	@GetMapping("")
	public List<Event> eventList() {
		return eventService.selectAllEvents();
	}
}
