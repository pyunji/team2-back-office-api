package com.mycompany.webapp.dto.product;

import lombok.Data;

@Data
public class OrderItemDto {
	String pstockid;
	String oid;
	int ocount;
	int totalPrice;
	String odate;
	String odelDate;
}
