package com.mycompany.webapp.dao.db1member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.member.MemberSearchForm;
import com.mycompany.webapp.vo.Grade;
import com.mycompany.webapp.vo.Member;

@Mapper
public interface MemberDao {
	public List<Member> selectAllMembers();
	public int getTotalMemberNum(MemberSearchForm searchForm);
	public List<Member> selectMemberList(MemberSearchForm searchForm);
	public Member selectMember(String mid);
	public void updateMember(Member member);
	public List<Grade> getGrades();
}
