package com.mycompany.webapp.dao.db1member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.display.ContextDto;
import com.mycompany.webapp.dto.member.MemberSearchForm;
import com.mycompany.webapp.vo.Member;

@Mapper
public interface DisplayDao {
	public void updateContext(ContextDto contextDto);
	
	public ContextDto selectContext();
	
	
	
}
