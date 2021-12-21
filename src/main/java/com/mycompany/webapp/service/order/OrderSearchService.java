package com.mycompany.webapp.service.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db3orders.OrderSearchDao;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.order.OrderProductResult;
import com.mycompany.webapp.dto.order.OrderResult;
import com.mycompany.webapp.dto.order.OrderSearchForm;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderSearchService {
	@Resource OrderSearchDao orderSearchDao; 
	public OrderResult selectOrderList(OrderSearchForm orderSearchForm) {
		//주문별 조회
		log.info("oid = " + orderSearchForm.getOid());
		//멤버별 조회
		log.info("mid = " + orderSearchForm.getMid());

		
		int totalOrder = orderSearchDao.getTotalOrderNum(orderSearchForm);
		log.info("totalOrder: " + Integer.toString(totalOrder));
		//String OrderProduct = orderSearchDao.getOrderProductName(orderSearchForm);
		//log.info("OrderProduct: " + OrderProduct);
		int rowsPerPage = orderSearchForm.getPager().getRowsPerPage();
		int pagesPerGroup = orderSearchForm.getPager().getPagesPerGroup();
		int pageNo = orderSearchForm.getPager().getPageNo();
		Pager pager = new Pager(rowsPerPage, pagesPerGroup, totalOrder, pageNo);
		log.info(pager.toString());
		orderSearchForm.setPager(pager);
		
		OrderResult orderResult = new OrderResult();
		
		orderResult.setOrderList(orderSearchDao.selectOrderList(orderSearchForm));
		orderResult.setPager(pager);
		log.info(orderResult.toString());
		return orderResult;
	}

}
