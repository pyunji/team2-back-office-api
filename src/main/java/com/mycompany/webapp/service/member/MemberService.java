package com.mycompany.webapp.service.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.MemberDao;
import com.mycompany.webapp.vo.Member;

@Service
public class MemberService {
	@Resource MemberDao memberDao;
	
	public List<Member> selectAllMembers() {
		return memberDao.selectAllMembers();
	}
}
