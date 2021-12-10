package com.mycompany.webapp.dto.product;

import com.mycompany.webapp.dto.Pager;

import lombok.Data;

@Data
public class SearchForm {
	String pstockid;
	String pname;
	String bname;
	String startRegDate;
	String endRegDate;
	Integer minStock;
	Integer maxStock;
	String d1name;
	String d2name;
	String d3name;
	int pageNo;
	Pager pager;
}
