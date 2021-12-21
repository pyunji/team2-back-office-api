package com.mycompany.webapp.service.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.MemberDao;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.member.MemberResult;
import com.mycompany.webapp.dto.member.MemberSearchForm;
import com.mycompany.webapp.service.member.job.GradeApplicationJob;
import com.mycompany.webapp.vo.Grade;
import com.mycompany.webapp.vo.GradeAdmin;
import com.mycompany.webapp.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
	@Autowired GradeApplicationJob ps;
	
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
	
	public GradeAdmin getGradeAdmin() {
		return memberDao.getGradeAdmin();
	}
	
	public void updateGradeAdmin(GradeAdmin gradeAdmin) throws InterruptedException {
		//GradeAdmin 테이블 정보 변경
		memberDao.updateGradeAdmin(gradeAdmin);
		
		//스케줄러 설정
		//현재 스레스에서 작동하고 있는 스케줄러 멈추기
		Thread.sleep(1000);
		
		int criteriaPeriod = gradeAdmin.getCriteriaPeriod();
		
		//입력된 정보에 따라 스케줄러 수행 시간 변경
		if(criteriaPeriod==1) {
			//1일마다 변경
			ps.changeCronSet("0 0 0 * * *");
			//테스트 10초마다 
			//ps.changeCronSet("*/10 * * * * *");
		}else if(criteriaPeriod==2) {
			//1개월 마다 변경
			ps.changeCronSet("0 0 0 1 * ?");
			//테스트 20초마다
			//log.info("5초마다");
			//ps.changeCronSet("*/5 * * * * *");
		}else {
			//6개월 마다 변경
			ps.changeCronSet("0 0 0 0 0/6 ?");
			//테스트 30초마다
			//log.info("1분마다");
			//ps.changeCronSet("0 */1 * * * *");
		}
		
		//스케줄러 새로 시작
		ps.startScheduler();
	}
}
