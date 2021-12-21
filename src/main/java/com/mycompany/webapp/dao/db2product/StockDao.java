package com.mycompany.webapp.dao.db2product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.product.OrderItemDto;


@Mapper
public interface StockDao {
	public void updateStock(OrderItemDto orderItemDto);
	public int selectStock(OrderItemDto orderItemDto);
}
