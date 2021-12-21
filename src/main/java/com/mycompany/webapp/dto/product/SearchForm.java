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
	String startDelDate;
	String endDelDate;
	Integer minStock;
	Integer maxStock;
	String d1name;
	String d2name;
	String d3name;
	int pageNo;
	String sortBy;
	Pager pager;
}
