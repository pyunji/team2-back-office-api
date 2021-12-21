package com.mycompany.webapp.dto.product;

import lombok.Data;

/* 수정할 catno를 특정지어서 product_category 테이블을 변경하는 Dto */
@Data
public class ProductCategoryDto {
	String pcommonid;
	Integer newCatno;
	Integer oldCatno;
}
