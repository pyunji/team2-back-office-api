package com.mycompany.webapp.service.product;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.commons.S3Uploader;
import com.mycompany.webapp.dao.db2product.AddDao;
import com.mycompany.webapp.dao.db2product.ModifyDao;
import com.mycompany.webapp.dto.product.ModifyForm;
import com.mycompany.webapp.dto.product.ProductCategoryDto;
import com.mycompany.webapp.dto.product.ProductDto;
import com.mycompany.webapp.dto.product.ProductIds;
import com.mycompany.webapp.dto.product.ProductModifyDto;
import com.mycompany.webapp.dto.product.UpdateProductStock;
import com.mycompany.webapp.vo.Category;
import com.mycompany.webapp.vo.ProductCategory;
import com.mycompany.webapp.vo.ProductColor;
import com.mycompany.webapp.vo.ProductCommon;
import com.mycompany.webapp.vo.WithProduct;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ModifyService {
	
	@Resource ModifyDao modifyDao;
	
	@Resource AddDao addDao;
	
	public ProductDto getOrgData(ModifyForm modifyForm) {
		log.info("실행");
		return modifyDao.selectOrgData(modifyForm);
	}
	
	@Autowired
	private S3Uploader s3Uploader;
	
	/* AWS 디렉토리 설정 */
	private static final String DIR_PATH = "product/";
	
	/* 로컬에 파일 저장 & DB에 저장할 path 설정 */
	@Transactional
	public String getFilePath(MultipartFile toUploadFile) throws IllegalStateException, IOException {
		log.info("실행");
		try {
			if (toUploadFile.getOriginalFilename() == null || toUploadFile.getOriginalFilename().equals("")) {
				return "";
			}
		} catch (NullPointerException e) {
			return "";
		}
		String s3Url = s3Uploader.uploadFile(toUploadFile, DIR_PATH);
		log.info("s3Url = " + s3Url);
		return s3Url;
	}
	
	/* 오늘 날짜 얻기 */
	public String getRegDate() {
		log.info("실행");
		Date date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		return fmt.format(date);
	}
	
//	public void deleteFile(String oldPath) {
//		log.info("실행");
//		String rootPath = "C:\\hyundai_itne\\eclipse-workspace\\team2-back-office-api\\src\\main\\resources\\static\\product";
//		String attachPath = "/upload/";
//		String[] splits = oldPath.split("/");
//		String saveName = splits[splits.length - 1];
//		File file = new File(rootPath + attachPath + saveName);
//		if (file.exists()) {
//			file.delete();
//		}
//	}
	
	@Transactional
	public void updateImages(ProductDto oldProductInfo, ProductModifyDto newProductInfo) throws IllegalStateException, IOException {
		log.info("실행");
		ProductColor productColor = new ProductColor();
		productColor.setPcolorid(newProductInfo.getPcolorid());
		
		if (newProductInfo.getImg1() != null) {
			String img1Path = getFilePath(newProductInfo.getImg1());
			productColor.setImg1(img1Path);
//			deleteFile(oldProductInfo.getImg1());
		}
		if (newProductInfo.getImg2() != null) {
			String img2Path = getFilePath(newProductInfo.getImg2());
			productColor.setImg2(img2Path);
//			deleteFile(oldProductInfo.getImg2());
		}
		if (newProductInfo.getImg3() != null) {
			String img3Path = getFilePath(newProductInfo.getImg3());
			productColor.setImg3(img3Path);
//			deleteFile(oldProductInfo.getImg3());
		}
		if (newProductInfo.getColorImg() != null) {
			String colorImgPath = getFilePath(newProductInfo.getColorImg());
			productColor.setColorImg(colorImgPath);
//			deleteFile(oldProductInfo.getColorImg());
		}
		modifyDao.updateImages(productColor);
	}
	
	@Transactional
	public void updateProductCategory(ProductDto oldProductInfo, ProductModifyDto newProduct) {
		log.info("실행");
		Category oldCategory = new Category();
		oldCategory.setD1name(oldProductInfo.getD1name());
		oldCategory.setD2name(oldProductInfo.getD2name());
		oldCategory.setD3name(oldProductInfo.getD3name());
		Integer oldCatno = addDao.selectCatno(oldCategory);
		Category newCategory = new Category();
		/* 이전 카테고리를 특정지어서 바꿔주어야함 */
		newCategory.setD1name(newProduct.getD1name());
		newCategory.setD2name(newProduct.getD2name());
		newCategory.setD3name(newProduct.getD3name());
		Integer newCatno = addDao.selectCatno(newCategory);
		log.info("newCatno = " + newCatno);
		ProductCategoryDto productCategory = new ProductCategoryDto();
		productCategory.setPcommonid(oldProductInfo.getPcommonid());
		productCategory.setNewCatno(newCatno);
		productCategory.setOldCatno(oldCatno);
		modifyDao.updateProductCategory(productCategory);
		
	}
	
	@Transactional
	public void updateProductCommon(ProductDto oldProductInfo, ProductModifyDto newProductInfo) {
		log.info("실행");
		String oldPname = oldProductInfo.getPname();
		String oldPnote = oldProductInfo.getPnote();
		String oldBname = oldProductInfo.getBname();
		
		String newPname = newProductInfo.getPname();
		String newPnote = newProductInfo.getPnote();
		String newBname = newProductInfo.getBname();
		
		ProductCommon productCommon = new ProductCommon();
		productCommon.setPcommonid(oldProductInfo.getPcommonid());
		if (!oldPname.equals(newPname)) { productCommon.setPname(newPname); }
		if (!oldPnote.equals(newPnote)) { productCommon.setPnote(newPnote); }
		if (!oldBname.equals(newBname)) {
			Integer bno = addDao.selectBno(newBname);
			productCommon.setBno(bno);
		}
		modifyDao.updateProductCommon(productCommon);
	}
	
	/* Main */
	@Transactional
	public ProductDto modifyProduct(ProductModifyDto newProductInfo) throws IllegalStateException, IOException {
		log.info("실행");
		/* modify.html에서 oldPstockid를 hidden 처리 후 그대로 가져옴 */
		String oldPstockid = newProductInfo.getPstockid();
		ModifyForm modifyForm = new ModifyForm();
		modifyForm.setHiddenPstockid(oldPstockid);
		modifyForm.setHiddenD1name(newProductInfo.getHiddenD1name());
		modifyForm.setHiddenD2name(newProductInfo.getHiddenD2name());
		modifyForm.setHiddenD3name(newProductInfo.getHiddenD3name());
		log.info("modifyForm = " + modifyForm);
		ProductDto oldProductInfo = getOrgData(modifyForm);
		log.info("oldProductInfo = " + oldProductInfo);
		
		newProductInfo.setPcolorid(oldProductInfo.getPcolorid());
		newProductInfo.setPcommonid(oldProductInfo.getPcommonid());;
		
		String pcommonid = oldProductInfo.getPcommonid();
		
		String oldD1name = oldProductInfo.getD1name();
		String oldD2name = oldProductInfo.getD2name();
		String oldD3name = oldProductInfo.getD3name();
		
		String newD1name = newProductInfo.getD1name();
		String newD2name = newProductInfo.getD2name();
		String newD3name = newProductInfo.getD3name();
		
		if (!(
				oldD1name.equals(newD1name) 
				&& oldD2name.equals(newD2name) 
				&& oldD3name.equals(newD3name)
				)) {
			/* Product_Category 테이블 업데이트 */
			updateProductCategory(oldProductInfo, newProductInfo);
		}
		
		String oldPname = oldProductInfo.getPname();
		String oldPnote = oldProductInfo.getPnote();
		String oldBname = oldProductInfo.getBname();
		
		String newPname = newProductInfo.getPname();
		String newPnote = newProductInfo.getPnote();
		String newBname = newProductInfo.getBname();
		if (!(
				oldPname.equals(newPname)
				&& oldPnote.equals(newPnote)
				&& oldBname.equals(newBname)
				)) {
			/* product_common 테이블 업데이트 */
			updateProductCommon(oldProductInfo, newProductInfo);
		}
		
		String oldCcode = oldProductInfo.getCcode();
		Integer oldPprice = oldProductInfo.getPprice();

		String newCcode = newProductInfo.getCcode();
		Integer newPprice = newProductInfo.getPprice();
		
		if (!(
				oldCcode.equals(newCcode)
				&& oldPprice.equals(newPprice)
				&& newProductInfo.getImg1() == null
				&& newProductInfo.getImg2() == null
				&& newProductInfo.getImg3() == null
				&& newProductInfo.getColorImg() == null
				)) {
			/* 컬러코드 변경된 경우 */
			if (!oldCcode.equals(newCcode)) {
				String newPcolorid = updatePcolorid(oldProductInfo, newProductInfo);
				newProductInfo.setPcolorid(newPcolorid);
				oldProductInfo.setPcolorid(newPcolorid);
			}
			
			if (oldPprice.equals(newPprice)) {
				log.info("equal oldPprice = " + oldPprice);
				log.info("equal newPprice = " + newPprice);
			}
			
			/* 가격 변경된 경우 */
			if (!oldPprice.equals(newPprice)) {
				log.info("not equal oldPprice = " + oldPprice);
				log.info("not equal newPprice = " + newPprice);
				updatePprice(newProductInfo);
			}
			
			if (
					newProductInfo.getImg1() != null 
					|| newProductInfo.getImg2() != null
					|| newProductInfo.getImg3() != null
					|| newProductInfo.getColorImg() != null
					) {
				updateImages(oldProductInfo, newProductInfo);
			}

		}
		
		String oldScode = oldProductInfo.getScode();
		Integer oldStock = oldProductInfo.getStock();

		String newScode = newProductInfo.getScode();
		Integer newStock = newProductInfo.getStock();
		
		boolean regCheck = newProductInfo.isRegCheck(); // 오늘 날짜로 업데이트 체크박스
		
		if (!(
				oldScode.equals(newScode)
				&& oldStock.equals(newStock)
				&& !regCheck
				)) {
			/* product_stock 테이블 업데이트 */
			updateProductStock(oldProductInfo, newProductInfo);
		}
		
		String oldWcolorid = oldProductInfo.getWcolorid();
		String newWcolorid = newProductInfo.getWcolorid();
		if (newWcolorid != null) {
			if(oldWcolorid == null || !oldWcolorid.equals(newWcolorid))
			/* with_product 테이블 업데이트 */
			updateWithProduct(oldProductInfo, newProductInfo);
		}
		
		return new ProductDto();
	}

	@Transactional
	public void updateWithProduct(ProductDto oldProductInfo, ProductModifyDto newProductInfo) {
		log.info("실행");
		String oldWcolorid = oldProductInfo.getWcolorid();
		String newWcolorid = newProductInfo.getWcolorid();

		WithProduct withProduct = new WithProduct();
		
		String pcolorid = newProductInfo.getPcolorid();
		withProduct.setPcolorid(pcolorid);
		
		String wcolorid = newProductInfo.getWcolorid();
		String wcommonid = wcolorid.split("_")[0];

		withProduct.setWcolorid(wcolorid);
		withProduct.setWcommonid(wcommonid);
		
		if (oldWcolorid == null) {
			addDao.insertWithProduct(withProduct);
		} else if (!oldWcolorid.equals(newWcolorid)) {
			modifyDao.updateWithProduct(withProduct);
		}
	}

	@Transactional
	public void updateProductStock(ProductDto oldProductInfo, ProductModifyDto newProductInfo) {
		log.info("실행");
		UpdateProductStock productStock = new UpdateProductStock();
		String oldScode = oldProductInfo.getScode();
		Integer oldStock = oldProductInfo.getStock();

		String newScode = newProductInfo.getScode();
		Integer newStock = newProductInfo.getStock();
		
		productStock.setOldPstockid(oldProductInfo.getPstockid());
		boolean regCheck = newProductInfo.isRegCheck(); // 오늘 날짜로 업데이트 체크박스
		/* 사이즈 코드가 달라진 경우 */
		if (!oldScode.equals(newScode)) {
			String newPstockid = oldProductInfo.getPcolorid() + "_" + newScode;
			productStock.setNewPstockid(newPstockid);
			productStock.setScode(newScode);
		}
		
		/* 재고가 달라진 경우 */
		if (oldStock != newStock) {
			productStock.setStock(newStock);
		}
		
		/* 오늘 날짜로 업데이트 체크한 경우 */
		if (regCheck) {
			String regDate = getRegDate();
			productStock.setRegDate(regDate);
		}
		
		modifyDao.updateProductStock(productStock);
	}

	@Transactional
	public void updatePprice(ProductModifyDto newProductInfo) {
		log.info("실행");
		ProductColor productColor = new ProductColor();
		productColor.setPcolorid(newProductInfo.getPcolorid());
		productColor.setPprice(newProductInfo.getPprice());
		modifyDao.updatePprice(productColor);
	}

	@Transactional
	public String updatePcolorid(ProductDto oldProductInfo, ProductModifyDto newProductInfo) {
		log.info("실행");
		String pcommonid = oldProductInfo.getPcommonid();
		String oldPcolorid = oldProductInfo.getPcolorid();
		String oldPstockid = oldProductInfo.getPstockid();
		
		String newCcode = newProductInfo.getCcode();
		String newPcolorid = pcommonid + "_" + newCcode;
		String newPstockid = newPcolorid + "_" + oldProductInfo.getScode();
		ProductIds productIds = new ProductIds();
		productIds.setOldPcolorid(oldPcolorid);
		productIds.setOldPstockid(oldPstockid);
		productIds.setNewPcolorid(newPcolorid);
		productIds.setNewPstockid(newPstockid);
		productIds.setNewCcode(newCcode);
		
		modifyDao.updateProductColorPcolorid(productIds);
		/* product_stock 테이블 pcolorid 업데이트 */
		modifyDao.updateProductStockPids(productIds);
		/* with_product 테이블 pcolorid 업데이트 */
		modifyDao.updateWithProductPcolorid(productIds);
		
		return newPcolorid;
	}
}
