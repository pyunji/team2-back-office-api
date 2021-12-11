package com.mycompany.webapp.dto.event;

import java.util.List;

import com.mycompany.webapp.dto.Pager;

import lombok.Data;

@Data
public class EventSearchForm {
	Integer eno;
	String etitle;
	String startDate;
	String endDate;
	List<Integer> estatus;
	int pageNo;
	Pager pager;
}
