package com.mycompany.webapp.service.member;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.dao.db1member.EventDao;
import com.mycompany.webapp.dto.event.NewEvent;
import com.mycompany.webapp.vo.Event;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventAddService {
	@Resource EventDao eventDao;
	
	/* 로컬에 파일 저장 & DB에 저장할 path 설정 */
	@Transactional
	public String getFilePath(MultipartFile toUploadFile) throws IllegalStateException, IOException {
		
		if (toUploadFile.getOriginalFilename().equals("")) {
			return "";
		}
		else {
			String rootPath = "C:\\Users\\Eunsol\\Desktop\\현대2차 프로젝트\\FINAL\\team2-back-office-api\\src\\main\\resources\\static\\event";  
			String attachPath = "/upload/";
			String saveName = new Date().getTime() + "-" + toUploadFile.getOriginalFilename();
			File file = new File(rootPath + attachPath + saveName);
			toUploadFile.transferTo(file);
			return attachPath + saveName;
		}
	}
	
	@Transactional
	public void addEvent(NewEvent newEvent) throws ParseException, IllegalStateException, IOException {
		log.info("실행");
		Event event = new Event();
		event.setEno(newEvent.getEno());
		event.setEtitle(newEvent.getEtitle());
		event.setEcontent(newEvent.getEcontent());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(sdf.parse(newEvent.getEissueDate()).getTime());
		event.setEissueDate(date);
		date = new Date(sdf.parse(newEvent.getEexpireDate()).getTime());
		event.setEexpireDate(date);
		event.setElimitCount(newEvent.getElimitCount());
		event.setEcount(newEvent.getEcount());
		event.setEstatus(newEvent.getEstatus());
		
		String eimgPath = getFilePath(newEvent.getEimg());
		if (eimgPath.equals("")) { event.setEimg(null); } 
		else { event.setEimg(eimgPath); }
		
		log.info(event.toString());
		
		eventDao.addEvent(event);
	}
}
