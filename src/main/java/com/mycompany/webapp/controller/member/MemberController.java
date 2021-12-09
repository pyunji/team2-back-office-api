package com.mycompany.webapp.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
