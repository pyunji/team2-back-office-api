package com.mycompany.webapp.controller.order;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.order.OrderResult;
import com.mycompany.webapp.dto.order.OrderSearchForm;
import com.mycompany.webapp.service.order.OrderSearchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

	@Resource
	OrderSearchService orderSearchService;
	
	@PostMapping("/search/result")
	public OrderResult getResult(@RequestBody OrderSearchForm orderSearchForm) {
		log.info("searchForm = " + orderSearchForm);
		OrderResult orderResult = orderSearchService.selectOrderList(orderSearchForm);
		return orderResult;
	}
	 
}
