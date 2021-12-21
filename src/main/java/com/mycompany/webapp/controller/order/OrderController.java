package com.mycompany.webapp.controller.order;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.display.ShareByBrandResult;
import com.mycompany.webapp.dto.order.CancelOid;
import com.mycompany.webapp.dto.order.OrderResult;
import com.mycompany.webapp.dto.order.OrderSearchForm;
import com.mycompany.webapp.service.order.BrandService;
import com.mycompany.webapp.service.order.OrderCancelService;
import com.mycompany.webapp.service.order.OrderSearchService;
import com.mycompany.webapp.service.product.SearchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

	@Resource
	OrderSearchService orderSearchService;
	@Resource
	SearchService searchService;
	
	@Resource
	OrderCancelService orderCancelService;
	
	@Resource
	BrandService brandService;
	
	@PostMapping("/search/result")
	public OrderResult getResult(@RequestBody OrderSearchForm orderSearchForm) {
		log.info("searchForm = " + orderSearchForm);

		OrderResult orderResult = orderSearchService.selectOrderList(orderSearchForm);
		return orderResult;
	}
	
	@PostMapping("/cancelOrder")
	public String cancelOrder(@RequestBody CancelOid cancelOid) {
		log.info("CancelOid= " + cancelOid);
		
		String orderCancelState = orderCancelService.cancelOrder(cancelOid);
		return orderCancelState;
	}
	
	@PostMapping("/getShareByBrand")
	public ShareByBrandResult getShareByBrand() {
		
		ShareByBrandResult shareByBrandResult = new ShareByBrandResult(); 
		shareByBrandResult = brandService.getShareByBrand();
		
	return 	shareByBrandResult;
	}
	  
}
