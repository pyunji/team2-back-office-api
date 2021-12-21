package com.mycompany.webapp.dao.db2product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.order.OrderDto;
import com.mycompany.webapp.dto.product.ProductDto;
import com.mycompany.webapp.dto.product.SearchForm;

@Mapper
public interface BrandDao {
	public List<ProductDto> selectProductList(SearchForm searchForm);
	public String selectOrderBrand(OrderDto orderDto);

}
