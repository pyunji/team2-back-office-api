package com.mycompany.webapp.controller.account;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.service.account.AccountService;
import com.mycompany.webapp.vo.Member;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/account")
public class AccountController {
	
	@Resource AccountService accountService;
	
	@PostMapping("/accountcheck")
	public Member accountcheck(@RequestBody String mid) {
		Member member = accountService.getAdminInfo(mid);
		return member;
	}
}
