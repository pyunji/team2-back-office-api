package com.mycompany.webapp.dto.product;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductModifyDto {
	String pcommonid;
	String pcolorid;
	String pstockid;
	String scode;
	Integer stock;
	boolean regCheck;
	MultipartFile img1;
	MultipartFile img2;
	MultipartFile img3;
	MultipartFile colorImg;
	String ccode;
	Integer pprice;
	String pname;
	String pnote;
	String bname;
	String d1name;
	String d2name;
	String d3name;
	String hiddenD1name;
	String hiddenD2name;
	String hiddenD3name;
	String wcolorid;
}
