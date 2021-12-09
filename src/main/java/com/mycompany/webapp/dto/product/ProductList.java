package com.mycompany.webapp.dto.product;

import lombok.Data;

@Data
public class ProductList {
	String pstockId;
	String productName;
	String sizeCode;
	String colorCode;
	Integer price;
	Integer stock;
	String brandName;
	String depth1Name;
	String depth2Name;
	String depth3Name;
}
