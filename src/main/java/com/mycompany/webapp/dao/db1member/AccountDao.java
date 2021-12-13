package com.mycompany.webapp.dao.db1member;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.vo.Member;

@Mapper
public interface AccountDao {
	public Member selectMemberByMid(String mid);
}
