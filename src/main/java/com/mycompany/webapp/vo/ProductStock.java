package com.mycompany.webapp.vo;

import lombok.Data;

@Data
public class ProductStock {
	String pstockid;
	String pcolorid;
	String scode;
	Integer stock;
	String regDate;
	Integer sales;
}
