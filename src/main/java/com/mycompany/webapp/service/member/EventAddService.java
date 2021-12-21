package com.mycompany.webapp.service.member;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.commons.S3Uploader;
import com.mycompany.webapp.dao.db1member.EventDao;
import com.mycompany.webapp.dto.event.NewEvent;
import com.mycompany.webapp.vo.Event;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventAddService {
	@Resource EventDao eventDao;
	
	@Autowired
	private S3Uploader s3Uploader;
	
	/* AWS 디렉토리 설정 */
	private static final String DIR_PATH = "event/";
	
	@Transactional
	public String getFilePath(MultipartFile toUploadFile) throws IllegalStateException, IOException {
		log.info("실행");
		if (toUploadFile.getOriginalFilename().equals("")) {
			return "";
		}
		
		String s3Url = s3Uploader.uploadFile(toUploadFile, DIR_PATH);
		log.info("s3Url = " + s3Url);
		return s3Url;
	}
	
	@Transactional
	public void addEvent(NewEvent newEvent) throws ParseException, IllegalStateException, IOException {
		log.info("실행");
		Event event = new Event();
		//event.setEno(newEvent.getEno());
		event.setEtitle(newEvent.getEtitle());
		event.setEcontent(newEvent.getEcontent());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(sdf.parse(newEvent.getEissueDate()).getTime());
		event.setEissueDate(date);
		date = new Date(sdf.parse(newEvent.getEexpireDate()).getTime());
		event.setEexpireDate(date);
		event.setElimitCount(newEvent.getElimitCount());
		//event.setEcount(newEvent.getEcount());
		//event.setEstatus(newEvent.getEstatus());
		log.info(event.toString());
		
		String ethumbnailPath = getFilePath(newEvent.getEthumbnail());
		if (ethumbnailPath.equals("")) { event.setEthumbnail(null); } 
		else { event.setEthumbnail(ethumbnailPath); }
		
		String eimgPath = getFilePath(newEvent.getEimg());
		if (eimgPath.equals("")) { event.setEimg(null); } 
		else { event.setEimg(eimgPath); }
		
		log.info(event.toString());
		
		eventDao.addEvent(event);
	}
}
