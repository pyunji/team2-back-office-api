package com.mycompany.webapp.dto.member;

import java.util.List;

import com.mycompany.webapp.dto.Pager;

import lombok.Data;

@Data
public class MemberSearchForm {
	String mid;
	String memail;
	String mname;
	String mphone;
	String startDateforBirth;
	String endDateforBirth;
	String startDateforSignUp;
	String endDateforSignUp;
	Integer gender;
	Integer grade_level;
	Integer menabled;
	int pageNo;
	Pager pager;
}
