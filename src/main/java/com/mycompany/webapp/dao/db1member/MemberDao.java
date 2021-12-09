package com.mycompany.webapp.dao.db1member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.vo.Member;

@Mapper
public interface MemberDao {
	public List<Member> selectAllMembers();
}
