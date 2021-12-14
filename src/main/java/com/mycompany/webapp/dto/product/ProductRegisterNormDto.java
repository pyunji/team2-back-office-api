package com.mycompany.webapp.dto.product;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductRegisterNormDto {
	String pcommonid;
	String pcolorid;
	String pstockid;
	String scode;
	Integer stock;
	String regDate;
	String ccode;
	Integer pprice;
	String pname;
	String pnote;
	String bname;
	String d1name;
	String d2name;
	String d3name;
	String wcolorid;
}
