package com.mycompany.webapp.service.product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.commons.S3Uploader;
import com.mycompany.webapp.dao.db2product.BinDao;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.product.BinDto;
import com.mycompany.webapp.dto.product.ProductResult;
import com.mycompany.webapp.dto.product.SearchForm;
import com.mycompany.webapp.vo.ProductColor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BinService {
	
	@Resource BinDao binDao;
	
	@Autowired
	private S3Uploader s3Uploader;
	
	/* AWS 디렉토리 설정 */
	private static final String DIR_PATH = "product/";
	
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
		log.info("실행");
		BinDto binDto = new BinDto();
		binDto.setDelDate(getDelDate());
		binDto.setPstockids(pstockids);
		binDao.updateDelDate(binDto);
	}
	
	public void removeDelDate(List<String> pstockids) {
		log.info("실행");
		BinDto binDto = new BinDto();
		binDto.setDelDate(null);
		binDto.setPstockids(pstockids);
		binDao.updateDelDate(binDto);
	}
	
	/* AWS S3에서 데이터를 삭제합니다. */
	@Transactional
	public void delAwsImages(String pcolorid) {
		log.info("실행");
		log.info("pcolorid = " + pcolorid);
		ProductColor images = binDao.selectImages(pcolorid);
		log.info("images = " + images);
		if (images.getImg1() != null && !images.getImg1().equals("")) {
			String img1Url = images.getImg1();
			if(img1Url.startsWith("https://hyundai-team2")) {
				log.info("AWS S3에 저장되어있는 데이터입니다. 삭제 요청합니다.");
				String[] strs = img1Url.split("/");
				String fileName = strs[strs.length-2] + "/" + strs[strs.length-1];
				log.info("fileName = " + fileName);
				s3Uploader.deleteFile(fileName);
			}
		}
		if (images.getImg2() != null && !images.getImg2().equals("")) {
			String img2Url = images.getImg2();
			if(img2Url.startsWith("https://hyundai-team2")) {
				log.info("AWS S3에 저장되어있는 데이터입니다. 삭제 요청합니다.");
				String[] strs = img2Url.split("/");
				String fileName = strs[strs.length-2] + "/" + strs[strs.length-1];
				log.info("fileName = " + fileName);
				s3Uploader.deleteFile(fileName);
			}
		}
		if (images.getImg3() != null && !images.getImg3().equals("")) {
			String img3Url = images.getImg3();
			if(img3Url.startsWith("https://hyundai-team2")) {
				log.info("AWS S3에 저장되어있는 데이터입니다. 삭제 요청합니다.");
				String[] strs = img3Url.split("/");
				String fileName = strs[strs.length-2] + "/" + strs[strs.length-1];
				log.info("fileName = " + fileName);
				s3Uploader.deleteFile(fileName);
			}
		}
		if (images.getColorImg() != null && !images.getColorImg().equals("")) {
			String colorUrl = images.getColorImg();
			if(colorUrl.startsWith("https://hyundai-team2")) {
				log.info("AWS S3에 저장되어있는 데이터입니다. 삭제 요청합니다.");
				String[] strs = colorUrl.split("/");
				String fileName = strs[strs.length-2] + "/" + strs[strs.length-1];
				log.info("fileName = " + fileName);
				s3Uploader.deleteFile(fileName);
			}
		}
	}
	
	/* 영구 삭제 메서드 */
	@Transactional
	public void permDel(List<String> pstockids) {
		log.info("실행");
		for(String pstockid : pstockids) {
			String[] strs = pstockid.split("_");
			String pcommonid = strs[0];
			String pcolorid = pcommonid + "_" + strs[1];
			log.info("pcolorid = " + pcolorid);
			/* product_common 테이블에 종속성이 없으면 */
			if (!isDependentPcommon(pcommonid) ) {
				// pcommonid 관련된 테이블에 접근 & 삭제
				binDao.delProductCategory(pcommonid);
				binDao.delProductCommon(pcommonid);
				/* product_color 테이블에 종속성이 없으면 */
				if(!isDependentPcolor(pcolorid)) {
					binDao.delWithProduct(pcolorid);
					// AWS 이미지 삭제
					delAwsImages(pcolorid);
					// product_color 테이블 삭제
					binDao.delProductColor(pcolorid);
				}
			}
			binDao.delProductStock(pstockid);
		}
	}
	
	/* 삭제하려는 상품의 pcommonid로 조회 시 상품이 2개이상 나오면 true */
	public boolean isDependentPcommon(String pcommonid) {
		log.info("실행");
		int pcommonDependency = binDao.countPcommonidDependency(pcommonid);
		log.info("pcommonDependency = " + pcommonDependency);
		return  pcommonDependency > 1;
	}
	
	/* 삭제하려는 상품의 pcolorid로 조회 시 상품이 2개이상 나오면 true */
	public boolean isDependentPcolor(String pcolorid) {
		log.info("실행");
		int pcolorDependency = binDao.countPcoloridDependency(pcolorid);
		log.info("pcolorDependency = " + pcolorDependency);
		return  pcolorDependency > 1;
	}
}
