package com.mycompany.webapp.dao.db2product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.order.OrderSearchForm;
import com.mycompany.webapp.dto.product.ProductDto;
import com.mycompany.webapp.dto.product.SearchForm;

@Mapper
public interface SearchDao {
	public List<ProductDto> selectProductList(SearchForm searchForm);
	public int getTotalProductNum(SearchForm searchForm);
	public List<ProductDto> selectOrderProductDetail(OrderSearchForm orderSearchForm);
}
