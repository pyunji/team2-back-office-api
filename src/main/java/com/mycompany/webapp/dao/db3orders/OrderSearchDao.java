package com.mycompany.webapp.dao.db3orders;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.display.StatDto;
import com.mycompany.webapp.dto.order.OrderDto;
import com.mycompany.webapp.dto.order.OrderSearchForm;

@Mapper

public interface OrderSearchDao {
	public List<OrderDto> selectOrderList(OrderSearchForm orderSearchForm);
	public int getTotalOrderNum(OrderSearchForm orderSearchForm);
	//public String getOrderProductName(OrderSearchForm orderSearchForm);
	public List<OrderDto> selectOrderListAll();
	
	//통계용 DAO
	public List<StatDto> selectStatByDay(String day);
	public StatDto selectStatByMonth(String day);
	public List<StatDto> selectStatByYear(String day);
	
	//스케줄링 DAO
	public StatDto selectScheduled(String day);
	public void insertScheduled(StatDto statDto);
	
}
