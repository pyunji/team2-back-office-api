package com.mycompany.webapp.service.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db2product.DepthDao;
import com.mycompany.webapp.dao.db2product.SearchDao;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.order.OrderProductResult;
import com.mycompany.webapp.dto.order.OrderSearchForm;
import com.mycompany.webapp.dto.product.Depth1;
import com.mycompany.webapp.dto.product.Depth2;
import com.mycompany.webapp.dto.product.Depth3;
import com.mycompany.webapp.dto.product.ProductResult;
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
	
	public ProductResult selectProductList(SearchForm searchForm) {
		int totalProduct = searchDao.getTotalProductNum(searchForm);
		log.info("searchForm = " +searchForm);
		int rowsPerPage = searchForm.getPager().getRowsPerPage();
		int pagesPerGroup = searchForm.getPager().getPagesPerGroup();
		int pageNo = searchForm.getPager().getPageNo();
		Pager pager = new Pager(rowsPerPage, pagesPerGroup, totalProduct, pageNo);
		
		searchForm.setPager(pager);
		log.info("pname = " + searchForm.getPname());
		log.info("bname = " + searchForm.getBname());
		ProductResult productResult = new ProductResult();
		productResult.setProductList(searchDao.selectProductList(searchForm));
		productResult.setPager(pager);
		return productResult;
	}
	
//	public OrderProductResult selectOrderProductList(OrderSearchForm orderSearchForm) {
//		OrderProductResult orderProductResult = new OrderProductResult();
//		orderProductResult.setPname(SearchDao.selectOrderProductDetail(orderSearchForm));
//		return orderProductResult;
//	}
}
