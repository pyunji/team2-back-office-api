package com.mycompany.webapp.dto.product;

import java.util.Date;

import lombok.Data;

@Data
public class Search {
	String pstockid;
	String pname;
	String bname;
	Date startRegDate;
	Date endRegDate;
	Integer minStock;
	Integer maxStock;
	String Depth1;
	String Depth2;
	String Depth3;
}
