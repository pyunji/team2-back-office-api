package com.mycompany.webapp.dto.member;

import java.util.List;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.vo.Member;

import lombok.Data;

@Data
public class MemberResult {
	List<Member> memberList;
	Pager pager;
}
