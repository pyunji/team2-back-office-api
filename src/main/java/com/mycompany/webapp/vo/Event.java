package com.mycompany.webapp.vo;

import java.util.Date;

import com.mycompany.webapp.vo.Event;

import lombok.Data;

@Data
public class Event {
	private int eno;
	private String etitle;
	private String econtent;
	private Date eissueDate;
	private Date eexpireDate;
	private int elimitCount;
	private int ecount;
	private String eimg;
	private int estatus;
	private String ethumbnail;
}