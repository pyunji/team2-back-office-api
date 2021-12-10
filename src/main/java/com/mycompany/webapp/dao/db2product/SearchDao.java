package com.mycompany.webapp.dao.db2product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.product.Depth1;
import com.mycompany.webapp.dto.product.Depth2;
import com.mycompany.webapp.dto.product.Depth3;
import com.mycompany.webapp.dto.product.ProductDto;
import com.mycompany.webapp.dto.product.SearchForm;

@Mapper
public interface SearchDao {
	public List<ProductDto> selectProductList(SearchForm searchForm);
}
