package com.mycompany.webapp.service.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db2product.DepthDao;
import com.mycompany.webapp.dao.db2product.SearchDao;
import com.mycompany.webapp.dto.product.Depth1;
import com.mycompany.webapp.dto.product.Depth2;
import com.mycompany.webapp.dto.product.Depth3;
import com.mycompany.webapp.dto.product.ProductDto;
import com.mycompany.webapp.dto.product.SearchForm;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchService {
	@Resource DepthDao depthDao;
	
	@Resource SearchDao searchDao;
	
	public List<Depth1> initSearch() {
		List<Depth1> selectDepth1 = depthDao.selectDepth1();
		log.info("selectDepth1" + selectDepth1);
		return selectDepth1;
	}
	
	public List<Depth2> selectDepth2(String d1name){
		return depthDao.selectDepth2(d1name);
	}
	
	public List<Depth3> selectDepth3(String d2name){
		return depthDao.selectDepth3(d2name);
	}
	
	public List<ProductDto> selectProductList(SearchForm searchForm) {
		return searchDao.selectProductList(searchForm);
	}
}
