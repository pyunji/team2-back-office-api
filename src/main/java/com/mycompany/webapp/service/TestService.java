package com.mycompany.webapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.TestMemberDao;
import com.mycompany.webapp.dao.db2product.TestProductDao;
import com.mycompany.webapp.dao.db3orders.TestOrdersDao;
import com.mycompany.webapp.vo.Member;
import com.mycompany.webapp.vo.Orders;
import com.mycompany.webapp.vo.ProductStock;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestService {
	
	@Resource TestMemberDao testMemberDao;
	
	@Resource TestProductDao testProductDao;
	
	@Resource TestOrdersDao testOrdersDao;
	
	public List<Member> showMember() {
		return testMemberDao.getMember();
	}
	
	public List<ProductStock> showProductStock() {
		return testProductDao.getProductStock();
	}
	
	public List<Orders> showOrders() {
		return testOrdersDao.getOrders();
	}
}
