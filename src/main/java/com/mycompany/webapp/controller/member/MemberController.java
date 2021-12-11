package com.mycompany.webapp.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.member.MemberResult;
import com.mycompany.webapp.dto.member.MemberSearchForm;
import com.mycompany.webapp.service.member.MemberService;
import com.mycompany.webapp.vo.Member;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {
	@Resource MemberService memberService;
	
	@GetMapping("")
	public List<Member> memberList() {
		return memberService.selectAllMembers();
	}
	
	@PostMapping("/result")
	public MemberResult getResult(@RequestBody MemberSearchForm searchForm) {
		log.info("백오피스 getResult 실행");
		log.info(searchForm.toString());
		MemberResult memberResult = memberService.selectMemberList(searchForm);
		return memberResult;
	}
}
