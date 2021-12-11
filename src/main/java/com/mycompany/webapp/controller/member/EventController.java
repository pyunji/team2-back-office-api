package com.mycompany.webapp.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.event.EventResult;
import com.mycompany.webapp.dto.event.EventSearchForm;
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
	
	@PostMapping("")
	public String addEvent(@RequestBody Event event) {
//		log.info("백오피스 addEvent 실행");
//		log.info(event.toString());
		eventService.addEvent(event);
		return "success";
	}
	
	@PostMapping("/result")
	public EventResult getResult(@RequestBody EventSearchForm searchForm) {
//		log.info("백오피스 getResult 실행");
//		log.info(searchForm.toString());
		EventResult eventResult = eventService.selectEventList(searchForm);
		return eventResult;
	}
}
