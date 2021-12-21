package com.mycompany.webapp.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.member.MemberResult;
import com.mycompany.webapp.dto.member.MemberSearchForm;
import com.mycompany.webapp.service.member.MemberService;
import com.mycompany.webapp.vo.Grade;
import com.mycompany.webapp.vo.GradeAdmin;
import com.mycompany.webapp.vo.Member;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {
	@Resource MemberService memberService;
	
	@GetMapping("/list")
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
	
	@GetMapping("{mid}")
	public Member selectMember(@PathVariable String mid) {
		log.info("백오피스 selectMember 실행");
		log.info(mid);
		Member member = memberService.selectMember(mid);
		return member;
	}
	
	@PostMapping("/update")
	public String updateMember(@RequestBody Member member) {
		log.info("백오피스  실행");
		log.info(member.toString());
		memberService.updateMember(member);
		return "success";
	}
	
	@GetMapping("/grade")
	public List<Grade> getGrades() {
		return memberService.getGrades();
	}
	
	@RequestMapping("/grade/policy")
	public GradeAdmin getGradeAdmin() {
		log.info("getGradeAdmin 실행");
		return memberService.getGradeAdmin();
	}
	
	@RequestMapping("/grade/policy/update")
	public String updateGradeAdmin(@RequestBody GradeAdmin gradeAdmin) {
		log.info("백오피스 updateGradeAdmin 실행");
		log.info(gradeAdmin.toString());
		memberService.updateGradeAdmin(gradeAdmin);
		return "success";
	}
}
