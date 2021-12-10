package com.mycompany.webapp.controller.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.product.Depth1;
import com.mycompany.webapp.dto.product.Depth2;
import com.mycompany.webapp.dto.product.Depth3;
import com.mycompany.webapp.dto.product.ProductResult;
import com.mycompany.webapp.dto.product.SearchForm;
import com.mycompany.webapp.service.product.SearchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {
	
//	@PostMapping("/search")
//	public List<ProductDto> search(@RequestBody Search search) {
//		
//	}
	@Resource SearchService searchService;
	
	@GetMapping("/search")
	public List<Depth1> initSearch() {
		return searchService.initSearch();
	}
	
	@PostMapping("/setdepth2")
	public List<Depth2> setDepth2(String d1name) {
		log.info("d1name = " + d1name);
		List<Depth2> d2nameList = searchService.selectDepth2(d1name);
		log.info("searchService.selectDepth2(d1name);" + d2nameList);
		log.info("d2nameList.getClass() = " + d2nameList.getClass());
		return d2nameList;
	}
	
	@PostMapping("/setdepth3")
	public List<Depth3> setDepth3(String d2name) {
		log.info("d2name = " + d2name);
		List<Depth3> d3nameList = searchService.selectDepth3(d2name);
		log.info("searchService.selectDepth3(d2name);" + d3nameList);
		log.info("d3nameList.getClass() = " + d3nameList.getClass());
		return d3nameList;
	}
	
	@PostMapping("/search/result")
	public ProductResult getResult(@RequestBody SearchForm searchForm) {
		log.info("searchForm = " + searchForm);
		ProductResult productResult = searchService.selectProductList(searchForm);
//		log.info("productList = " + productList);
		return productResult;
	}
}
