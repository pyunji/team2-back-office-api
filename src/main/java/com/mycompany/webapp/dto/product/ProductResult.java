package com.mycompany.webapp.dto.product;

import java.util.List;

import com.mycompany.webapp.dto.Pager;

import lombok.Data;

@Data
public class ProductResult {
	List<ProductDto> productList;
	Pager pager;
}
