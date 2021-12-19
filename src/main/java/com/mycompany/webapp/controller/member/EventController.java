package com.mycompany.webapp.controller.member;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.dto.event.EventResult;
import com.mycompany.webapp.dto.event.EventSearchForm;
import com.mycompany.webapp.dto.event.NewEvent;
import com.mycompany.webapp.service.member.EventAddService;
import com.mycompany.webapp.service.member.EventService;
import com.mycompany.webapp.vo.Event;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/event")
public class EventController {
	@Resource EventService eventService;
	@Resource EventAddService evnetAddService;
	
	@GetMapping("")
	public List<Event> eventList() {
		return eventService.selectAllEvents();
	}
	
//	@PostMapping("")
//	public String addEvent(@RequestBody Event event) {
////		log.info("백오피스 addEvent 실행");
////		log.info(event.toString());
//		eventService.addEvent(event);
//		return "success";
//	}
	
	@PostMapping("")
	public String addEvent(@RequestPart(required=false) Integer eno,
						   @RequestPart(required=false) String etitle,
						   @RequestPart(required=false) String econtent,
						   @RequestPart(required=false) String eissueDate,
						   @RequestPart(required=false) String eexpireDate,
						   @RequestPart(required=false) Integer elimitCount,
						   @RequestPart(required=false) Integer ecount,
						   @RequestPart(required=false) MultipartFile eimg,
						   @RequestPart(required=false) Integer estatus
						   ) throws IllegalStateException, ParseException, IOException {
		log.info("백오피스 api addEvent실행");
		NewEvent newEvent = new NewEvent();
		newEvent.setEno(eno);
		newEvent.setEtitle(etitle);
		newEvent.setEcontent(econtent);
		newEvent.setEissueDate(eissueDate);
		newEvent.setEexpireDate(eexpireDate);
		newEvent.setElimitCount(elimitCount);
		newEvent.setEcount(ecount);
		newEvent.setEimg(eimg);
		newEvent.setEstatus(estatus);
		
		//eventService.addEvent(newEvent);
		evnetAddService.addEvent(newEvent);
		
		return "success";
	}
	
	@PostMapping("/result")
	public EventResult getResult(@RequestBody EventSearchForm searchForm) {
		log.info("백오피스 getResult 실행");
//		log.info(searchForm.toString());
		EventResult eventResult = eventService.selectEventList(searchForm);
		log.info(eventResult.toString());
		return eventResult;
	}
}
