package com.mycompany.webapp.dao.db3orders;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.order.OrderDto;
import com.mycompany.webapp.dto.order.OrderSearchForm;

@Mapper

public interface OrderSearchDao {
	public List<OrderDto> selectOrderList(OrderSearchForm orderSearchForm);
	public int getTotalOrderNum(OrderSearchForm orderSearchForm);
}
