package com.mycompany.webapp.dao.db3orders;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao {
	public List<Integer> selectPriceByMid(String mid);
}
