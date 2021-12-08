package com.mycompany.webapp.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.service.TestService;
import com.mycompany.webapp.vo.Member;
import com.mycompany.webapp.vo.Orders;
import com.mycompany.webapp.vo.ProductStock;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {
	@RequestMapping("")
	public String test() {
		return "It's OK";
	}
	
	@Resource TestService testService;
	
	@RequestMapping("/member")
	public List<Member> memberTest() {
		log.info("실행");
		return testService.showMember();
	}
	
	@RequestMapping("/product")
	public List<ProductStock> productTest() {
		return testService.showProductStock();
	}
	
	@RequestMapping("/orders")
	public List<Orders> ordersTest() {
		return testService.showOrders();
	}
}
