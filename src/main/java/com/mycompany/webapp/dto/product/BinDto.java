package com.mycompany.webapp.dto.product;

import java.util.List;

import lombok.Data;

@Data
public class BinDto {
	String delDate;
	List<String> pstockids;
}
