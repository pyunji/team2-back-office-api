package com.mycompany.webapp.dto.event;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NewEvent {
	private Integer eno;
	private String etitle;
	private String econtent;
	private String eissueDate;
	private String eexpireDate;
	private Integer elimitCount;
	private Integer ecount;
	private MultipartFile eimg;
	private Integer estatus;
	private MultipartFile ethumbnail;
}
