package com.mycompany.webapp.service.member;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.DisplayDao;
import com.mycompany.webapp.dao.db1member.MemberDao;
import com.mycompany.webapp.dto.display.ContextDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DisplayService {
	@Resource MemberDao memberDao;
	@Resource DisplayDao displayDao;

	
	public ContextDto transContextResult(ContextDto contextDto) {
		//받아온 구조를 결과를 넣음
		displayDao.updateContext(contextDto);
		log.info("display서비스 실행");
		//현재 구조를 받아옴
		ContextDto contextResult = displayDao.selectContext();
		System.out.println(contextResult.getContext0());
		System.out.println(contextResult.getContext1());
		System.out.println(contextResult.getContext2());
		System.out.println(contextResult.getIndex0());
		System.out.println(contextResult.getIndex1());
		System.out.println(contextResult.getIndex2());
		return contextResult;
	}
	
}
