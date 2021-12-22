package com.mycompany.webapp.dto.order;

import com.mycompany.webapp.dto.Pager;

import lombok.Data;

@Data
public class OrderSearchForm {
	String startRegDate;
	String endRegDate;
	String oProduct;
	String oDeliveryState0;
	String oDeliveryState1;
	String oDeliveryState2;
	String oDeliveryState3;
	String oDeliveryState4;
	String  oCsState0;
	String  oCsState1; 
	String  oCsState2;
	String  oCsState3; 
	String oid;
	String mid;
	int pageNo;
	Pager pager;
}
