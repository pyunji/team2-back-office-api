package com.mycompany.webapp.dao.db2product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.vo.ProductStock;

@Mapper
public interface TestProductDao {
	public List<ProductStock> getProductStock();
}
