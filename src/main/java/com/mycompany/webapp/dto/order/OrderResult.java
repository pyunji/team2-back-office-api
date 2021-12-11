package com.mycompany.webapp.dto.order;

import java.util.List;

import com.mycompany.webapp.dto.Pager;

import lombok.Data;

@Data
public class OrderResult {
	List<OrderDto> orderList;
	Pager pager;

}
