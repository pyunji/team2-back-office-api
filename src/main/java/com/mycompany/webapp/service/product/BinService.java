package com.mycompany.webapp.service.product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db2product.BinDao;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.product.BinDto;
import com.mycompany.webapp.dto.product.ProductResult;
import com.mycompany.webapp.dto.product.SearchForm;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BinService {
	
	@Resource BinDao binDao;
	
	public ProductResult selectBinList(SearchForm searchForm) {
		int totalProduct = binDao.getTotalProductNum(searchForm);
		
		int rowsPerPage = searchForm.getPager().getRowsPerPage();
		int pagesPerGroup = searchForm.getPager().getPagesPerGroup();
		int pageNo = searchForm.getPager().getPageNo();
		Pager pager = new Pager(rowsPerPage, pagesPerGroup, totalProduct, pageNo);
		
		searchForm.setPager(pager);
		log.info("pname = " + searchForm.getPname());
		log.info("bname = " + searchForm.getBname());
		ProductResult productResult = new ProductResult();
		productResult.setProductList(binDao.selectProductList(searchForm));
		productResult.setPager(pager);
		return productResult;
	}
	
	/* 오늘 날짜 얻기 */
	public String getDelDate() {
		log.info("실행");
		Date date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		return fmt.format(date);
	}
	public void updateDelDate(List<String> pstockids) {
		BinDto binDto = new BinDto();
		binDto.setDelDate(getDelDate());
		binDto.setPstockids(pstockids);
		binDao.updateDelDate(binDto);
	}
	public void removeDelDate(List<String> pstockids) {
		BinDto binDto = new BinDto();
		binDto.setDelDate(null);
		binDto.setPstockids(pstockids);
		binDao.updateDelDate(binDto);
	}
}
