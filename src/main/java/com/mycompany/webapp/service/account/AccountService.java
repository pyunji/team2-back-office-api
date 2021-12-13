package com.mycompany.webapp.service.account;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.AccountDao;
import com.mycompany.webapp.dao.db1member.MemberDao;
import com.mycompany.webapp.vo.Member;

@Service
public class AccountService {
	@Resource
	private AccountDao accountDao;
	
	public Member getAdminInfo(String mid) {
		Member member = accountDao.selectMemberByMid(mid);
		return member;
	}
}
