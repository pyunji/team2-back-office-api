package com.mycompany.webapp.service.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.MemberDao;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.member.MemberResult;
import com.mycompany.webapp.dto.member.MemberSearchForm;
import com.mycompany.webapp.vo.Grade;
import com.mycompany.webapp.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
	@Resource MemberDao memberDao;
	
	public List<Member> selectAllMembers() {
		return memberDao.selectAllMembers();
	}
	
	public MemberResult selectMemberList(MemberSearchForm searchForm) {
		int totalMember = memberDao.getTotalMemberNum(searchForm);
		//log.info(String.valueOf(totalMember));

		int rowsPerPage = searchForm.getPager().getRowsPerPage();
		int pagesPerGroup = searchForm.getPager().getPagesPerGroup();
		int pageNo = searchForm.getPager().getPageNo();
		Pager pager = new Pager(rowsPerPage, pagesPerGroup, totalMember, pageNo);
		//log.info(String.valueOf(rowsPerPage)+" " + String.valueOf(pagesPerGroup)+" " + String.valueOf(pageNo));
		log.info(searchForm.toString());
		searchForm.setPager(pager);
		MemberResult memberResult = new MemberResult();
		memberResult.setPager(pager);
		memberResult.setMemberList(memberDao.selectMemberList(searchForm));
		
		return memberResult;
	}
	
	public Member selectMember(String mid) {
		return memberDao.selectMember(mid);
	}
	
	public void updateMember(Member member) {
		memberDao.updateMember(member);
	}
	
	public List<Grade> getGrades() {
		return memberDao.getGrades();
	}
}
