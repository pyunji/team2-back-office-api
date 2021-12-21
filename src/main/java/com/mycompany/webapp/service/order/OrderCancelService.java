package com.mycompany.webapp.service.order;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.mycompany.webapp.dao.db2product.StockDao;
import com.mycompany.webapp.dao.db3orders.OrderCancelDao;
import com.mycompany.webapp.dto.order.CancelOid;
import com.mycompany.webapp.dto.order.CancelOrderItemResult;
import com.mycompany.webapp.dto.product.OrderItemDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderCancelService {
	@Resource OrderCancelDao orderCancelDao; 
	@Resource StockDao stockDao;
	
	public String cancelOrder(CancelOid cancelOid) {
		log.info(Arrays.toString(cancelOid.getCancelOid()));
		String [] inputArray = cancelOid.getCancelOid();		
		CancelOrderItemResult cancelOrderItemResult = new CancelOrderItemResult();
		
		for(int i = 0; i < cancelOid.getCancelOid().length; i++) {
			String inputCancelOid = inputArray[i];
			
			orderCancelDao.cancelOrder(inputCancelOid);
			
			cancelOrderItemResult.setCancelOrderItemList(orderCancelDao.selectCancelOrderItemList(inputCancelOid));
			System.out.println( cancelOrderItemResult.getCancelOrderItemList().size());
			for(int j = 0; j < cancelOrderItemResult.getCancelOrderItemList().size();j++) {
				System.out.println("test1");
				OrderItemDto orderItem = cancelOrderItemResult.getCancelOrderItemList().get(j);
				System.out.println(cancelOrderItemResult.getCancelOrderItemList().get(j));
				OrderItemDto orderItemDto = new OrderItemDto();
				orderItemDto.setPstockid(orderItem.getPstockid());
				orderItemDto.setOid(orderItem.getOid());
				orderItemDto.setOcount(orderItem.getOcount());
				orderItemDto.setTotalPrice(orderItem.getTotalPrice());
				orderItemDto.setOdate(orderItem.getOdate());
				orderItemDto.setOdelDate(orderItem.getOdelDate());
				System.out.println("test2");
				System.out.println(orderItemDto);
				int stock = stockDao.selectStock(orderItemDto);
				System.out.println("stock = " + stock);
				System.out.println("old stock = " + stock);
				orderItemDto.setOcount(stock + orderItemDto.getOcount());
				System.out.println("new stock = " + orderItemDto.getOcount());
				stockDao.updateStock(orderItemDto);
				//System.out.println(test);
				System.out.println("test3");
				System.out.println("getPstockid: "+cancelOrderItemResult.getCancelOrderItemList().get(j).getPstockid());
			}
			 
		} 
		
		String orderCancelState = "success";
		

		return orderCancelState; 
	}

}
