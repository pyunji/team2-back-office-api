package com.mycompany.webapp.service.product;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.dao.db2product.AddDao;
import com.mycompany.webapp.dao.db2product.DepthDao;
import com.mycompany.webapp.dao.db2product.SearchDao;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.product.Brand;
import com.mycompany.webapp.dto.product.Depth1;
import com.mycompany.webapp.dto.product.Depth2;
import com.mycompany.webapp.dto.product.Depth3;
import com.mycompany.webapp.dto.product.ProductDto;
import com.mycompany.webapp.dto.product.ProductRegisterDto;
import com.mycompany.webapp.dto.product.ProductResult;
import com.mycompany.webapp.dto.product.SearchForm;
import com.mycompany.webapp.dto.product.Sizes;
import com.mycompany.webapp.vo.Category;
import com.mycompany.webapp.vo.ProductCategory;
import com.mycompany.webapp.vo.ProductColor;
import com.mycompany.webapp.vo.ProductCommon;
import com.mycompany.webapp.vo.ProductStock;
import com.mycompany.webapp.vo.WithProduct;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AddService {
	@Resource AddDao addDao;
	
	public List<Brand> getBrandList() {
		return addDao.selectBrand();
	}
	
	public List<Sizes> getSizeList() {
		return addDao.selectSizes();
	}
	
	/* 로컬에 파일 저장 & DB에 저장할 path 설정 */
	@Transactional
	public String getFilePath(MultipartFile toUploadFile) throws IllegalStateException, IOException {
		log.info("실행");
		if (toUploadFile.getOriginalFilename().equals("")) {
			return "";
		}
		else {
			String rootPath = "C:\\hyundai_itne\\eclipse-workspace\\team2-back-office-api\\src\\main\\resources\\static\\product";
			String localPath = "http://localhost:83/product";
			String attachPath = "/upload/";
			String saveName = new Date().getTime() + "-" + toUploadFile.getOriginalFilename();
			File file = new File(rootPath + attachPath + saveName);
			toUploadFile.transferTo(file);
			return localPath+attachPath + saveName;
		}
	}
	
	public String getRegDate() {
		log.info("실행");
		Date date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		return fmt.format(date);
	}
	
	@Transactional
	public ProductDto addProduct(ProductRegisterDto productInfo) throws IllegalStateException, IOException {
		log.info("실행");
		ProductDto product = new ProductDto();
		/* ProductRegisterDto에서 ProductDto로 데이터 이동 */
		product.setPcommonid(productInfo.getPcommonid());
		product.setPcolorid(productInfo.getPcommonid() + "_" + productInfo.getCcode());
		product.setPstockid(productInfo.getPcommonid() + "_" + productInfo.getCcode() + "_" + productInfo.getScode());
		product.setScode(productInfo.getScode());
		product.setStock(productInfo.getStock());
		product.setCcode(productInfo.getCcode());
		product.setPprice(productInfo.getPprice());
		product.setPname(productInfo.getPname());
		product.setPnote(productInfo.getPnote());
		product.setBname(productInfo.getBname());
		product.setD1name(productInfo.getD1name());
		product.setD2name(productInfo.getD2name());
		product.setD3name(productInfo.getD3name());
		
		String img1Path = getFilePath(productInfo.getImg1());
		String img2Path = getFilePath(productInfo.getImg2());
		String img3Path = getFilePath(productInfo.getImg3());
		String colorImgPath = getFilePath(productInfo.getColorImg());
		if (img1Path.equals("")) { product.setImg1(null); } 
		else { product.setImg1(img1Path); }
		if (img2Path.equals("")) { product.setImg2(null); } 
		else { product.setImg2(img2Path); }
		if (img3Path.equals("")) { product.setImg3(null); } 
		else { product.setImg3(img3Path); }
		if (colorImgPath.equals("")) { product.setColorImg(null); } 
		else { product.setColorImg(colorImgPath); }
		
		product.setRegDate(getRegDate());
		
		log.info("product = " + product);
		
		/* product_category 테이블에 데이터 삽입 */
		insertProductCategory(product);
		
		/* product_common 테이블에 데이터 삽입 */
		insertProductCommon(product);
		
		/* product_color 테이블에 데이터 삽입 */
		insertProductColor(product);
		
		/* product_stock 테이블에 데이터 삽입 */
		insertProductStock(product);
		
		/* with_product 테이블에 데이터 삽입 */
		if (productInfo.getWcolorid() != null && productInfo.getWcolorid() != "") {
			WithProduct withProduct = new WithProduct();
			withProduct.setPcolorid(product.getPcolorid());
			withProduct.setWcolorid(productInfo.getWcolorid());
			withProduct.setWcommonid(productInfo.getWcolorid().split("_")[0]);
			insertWithProduct(withProduct);
		}
		return product;
	}
	
	@Transactional
	public void insertProductCategory(ProductDto product) {
		log.info("실행");
		String d1name = product.getD1name();
		String d2name = product.getD2name();
		String d3name = product.getD3name();
		Category category = new Category();
		category.setD1name(d1name);
		category.setD2name(d2name);
		category.setD3name(d3name);
		Integer catno = addDao.selectCatno(category);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCatno(catno);
		productCategory.setPcommonid(product.getPcommonid());
		addDao.insertProductCategory(productCategory);
	}
	
	@Transactional
	public void insertProductCommon(ProductDto product) {
		ProductCommon productCommon = new ProductCommon();
		productCommon.setPcommonid(product.getPcommonid());
		productCommon.setPname(product.getPname());
		productCommon.setPnote(product.getPnote());
		Integer bno = addDao.selectBno(product.getBname());
		productCommon.setBno(bno);
		addDao.insertProductCommon(productCommon);
		
	}
	
	@Transactional
	public void insertProductColor(ProductDto product) {
		ProductColor productColor = new ProductColor();
		productColor.setPcolorid(product.getPcolorid());
		if (product.getImg1() != null) productColor.setImg1(product.getImg1());
		if (product.getImg2() != null) productColor.setImg2(product.getImg2());
		if (product.getImg3() != null) productColor.setImg3(product.getImg3());
		if (product.getColorImg() != null) productColor.setColorImg(product.getColorImg());
		productColor.setPcommonid(product.getPcommonid());
		productColor.setCcode(product.getCcode());
		productColor.setPprice(product.getPprice());
		addDao.insertProductColor(productColor);
	}
	
	@Transactional
	public void insertProductStock(ProductDto product) {
		ProductStock productStock = new ProductStock();
		productStock.setPstockid(product.getPstockid());
		productStock.setPcolorid(product.getPcolorid());
		productStock.setScode(product.getScode());
		productStock.setStock(product.getStock());
		productStock.setRegDate(product.getRegDate());
		addDao.insertProductStock(productStock);
	}
	
	@Transactional
	public void insertWithProduct(WithProduct withProduct) {
		addDao.insertWithProduct(withProduct);
	}
}
