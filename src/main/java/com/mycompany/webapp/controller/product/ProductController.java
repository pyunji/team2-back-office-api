package com.mycompany.webapp.controller.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.product.Depth1;
import com.mycompany.webapp.dto.product.Depth2;
import com.mycompany.webapp.dto.product.Depth3;
import com.mycompany.webapp.service.product.SearchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {
	
//	@PostMapping("/search")
//	public List<ProductList> search(@RequestBody Search search) {
//		
//	}
	@Resource SearchService searchService;
	
	@GetMapping("/search")
	public List<Depth1> initSearch() {
		return searchService.initSearch();
	}
	
	@PostMapping("/setdepth2")
	public List<Depth2> setDepth2(String depth1) {
		log.info("depth1 = " + depth1);
		List<Depth2> depth2List = searchService.selectDepth2(depth1);
		log.info("searchService.selectDepth2(depth1);" + depth2List);
		log.info("depth2List.getClass() = " + depth2List.getClass());
		return depth2List;
	}
	
	@PostMapping("/setdepth3")
	public List<Depth3> setDepth3(String depth2) {
		log.info("depth2 = " + depth2);
		List<Depth3> depth3List = searchService.selectDepth3(depth2);
		log.info("searchService.selectDepth3(depth2);" + depth3List);
		log.info("depth3List.getClass() = " + depth3List.getClass());
		return depth3List;
	}
}
