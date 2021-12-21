package com.mycompany.webapp.dao.db3orders;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.product.OrderItemDto;

@Mapper
public interface OrderCancelDao {
	public void cancelOrder(String cancelOid);
	public List<OrderItemDto> selectCancelOrderItemList(String cancelOid);
}
