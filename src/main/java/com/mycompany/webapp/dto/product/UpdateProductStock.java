package com.mycompany.webapp.dto.product;

import lombok.Data;

@Data
public class UpdateProductStock {
	String newPstockid;
	String oldPstockid;
	String pcolorid;
	String scode;
	Integer stock;
	String regDate;
	int sales;
}
