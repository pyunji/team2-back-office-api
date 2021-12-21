package com.mycompany.webapp.dto.order;

import lombok.Data;

@Data
public class OrderDto {
	//OrderItems Dto
	String pstockid;
	String oid;
	int ocount;
	int totalPrice;
	String odate;
	String odelDate;
	//Orders Dto
	String mid;
	String paymentMethodCode;
	String ostatus;
	String deliveryStatus;
	String oemail;
	String memo;
	String ozipcode;
	String oaddress;
	String oreceiver;
	String ophone;
	String otel;
	
	int usedMileage;
	int beforeDcPrice;
	int afterDcPrice;
	String paymentInfo;
	

	
	
}
