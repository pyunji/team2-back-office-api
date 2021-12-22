package com.mycompany.webapp.controller.member;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.display.ContextDto;
import com.mycompany.webapp.service.member.DisplayService;
import com.mycompany.webapp.service.member.MemberService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/member")
public class DisplayController {
	@Resource MemberService memberService;
	@Resource DisplayService displayService;

	
	@PostMapping("/display/result")
	public ContextDto getResult(@RequestBody ContextDto contextDto) {
		log.info("백오피스 getContext 실행");
		log.info(contextDto.getContext0());
		ContextDto contextResult =  displayService.transContextResult(contextDto);
		return contextResult;
	}
	
	
}
