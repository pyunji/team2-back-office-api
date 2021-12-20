package com.mycompany.webapp.controller.product;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.dto.product.Brand;
import com.mycompany.webapp.dto.product.Depth1;
import com.mycompany.webapp.dto.product.Depth2;
import com.mycompany.webapp.dto.product.Depth3;
import com.mycompany.webapp.dto.product.ModifyForm;
import com.mycompany.webapp.dto.product.ProductDto;
import com.mycompany.webapp.dto.product.ProductModifyDto;
import com.mycompany.webapp.dto.product.ProductRegisterDto;
import com.mycompany.webapp.dto.product.ProductResult;
import com.mycompany.webapp.dto.product.SearchForm;
import com.mycompany.webapp.dto.product.Sizes;
import com.mycompany.webapp.service.product.AddService;
import com.mycompany.webapp.service.product.BinService;
import com.mycompany.webapp.service.product.ModifyService;
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
	
	@Resource BinService binService;
	
	@Resource SearchService searchService;
	
	@Resource AddService addService;
	
	@Resource ModifyService modifyService;
	
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

	@PostMapping("/bin/result")
	public ProductResult getBinResult(@RequestBody SearchForm searchForm) {
		log.info("searchForm = " + searchForm);
		ProductResult productResult = binService.selectBinList(searchForm);
		return productResult;
	}
	
	@PostMapping("/bin")
	public String goToBin(@RequestBody List<String> products) {
		log.info("products = " + products);
		binService.updateDelDate(products);
		return "ok";
	}
	
	@PostMapping("/returnfrombin")
	public String returnFromBin(@RequestBody List<String> products) {
		log.info("products = " + products);
		binService.removeDelDate(products);
		return "ok";
	}

	@PostMapping("/permdel")
	public String permDel(@RequestBody List<String> products) {
		log.info("products = " + products);
		binService.permDel(products);
		return "ok";
	}
	
	@GetMapping("/brand")
	public List<Brand> getBrandList() {
		return addService.getBrandList();
	}
	
	@GetMapping("/sizes")
	public List<Sizes> getSizeList() {
		return addService.getSizeList();
	}

	@PostMapping("/add")
	public ProductDto addProduct(
			@RequestPart(required = false) String pcommonid,
			@RequestPart(required = false) String pcolorid,
			@RequestPart(required = false) String pstockid,
			@RequestPart(required = false) String scode,
			@RequestPart(required = false) Integer stock,
			@RequestPart(required = false) String regDate,
			@RequestPart(required = false) MultipartFile img1,
			@RequestPart(required = false) MultipartFile img2,
			@RequestPart(required = false) MultipartFile img3,
			@RequestPart(required = false) MultipartFile colorImg,
			@RequestPart(required = false) String ccode,
			@RequestPart(required = false) Integer pprice,
			@RequestPart(required = false) String pname,
			@RequestPart(required = false) String pnote,
			@RequestPart(required = false) String bname,
			@RequestPart(required = false) String d1name,
			@RequestPart(required = false) String d2name,
			@RequestPart(required = false) String d3name,
			@RequestPart(required = false) String wcolorid
			) throws IllegalStateException, IOException {
		ProductRegisterDto productInfo = new ProductRegisterDto();
		productInfo.setPcommonid(pcommonid);
		productInfo.setPcolorid(pcolorid);
		productInfo.setPstockid(pstockid);
		productInfo.setScode(scode);
		productInfo.setStock(stock);
		productInfo.setRegDate(regDate);
		productInfo.setImg1(img1);
		productInfo.setImg2(img2);
		productInfo.setImg3(img3);
		productInfo.setColorImg(colorImg);
		productInfo.setCcode(ccode);
		productInfo.setPprice(pprice);
		productInfo.setPname(pname);
		productInfo.setPnote(pnote);
		productInfo.setBname(bname);
		productInfo.setD1name(d1name);
		productInfo.setD2name(d2name);
		productInfo.setD3name(d3name);
		productInfo.setWcolorid(wcolorid);
		
		log.info("productInfo = " + productInfo);
		
		return addService.addProduct(productInfo);
	}
	
	@PostMapping("/modify/process")
	public ProductDto modifyProduct(
			@RequestPart(required = false) String pcommonid,
			@RequestPart(required = false) String pcolorid,
			@RequestPart(required = false) String pstockid,
			@RequestPart(required = false) String scode,
			@RequestPart(required = false) Integer stock,
			@RequestPart(required = false) boolean regCheck,
			@RequestPart(required = false) MultipartFile img1,
			@RequestPart(required = false) MultipartFile img2,
			@RequestPart(required = false) MultipartFile img3,
			@RequestPart(required = false) MultipartFile colorImg,
			@RequestPart(required = false) String ccode,
			@RequestPart(required = false) Integer pprice,
			@RequestPart(required = false) String pname,
			@RequestPart(required = false) String pnote,
			@RequestPart(required = false) String bname,
			@RequestPart(required = false) String d1name,
			@RequestPart(required = false) String d2name,
			@RequestPart(required = false) String d3name,
			@RequestPart(required = false) String hiddenD1name,
			@RequestPart(required = false) String hiddenD2name,
			@RequestPart(required = false) String hiddenD3name,
			@RequestPart(required = false) String wcolorid
			) throws IllegalStateException, IOException {
		ProductModifyDto productInfo = new ProductModifyDto();
		productInfo.setPcommonid(pcommonid);
		productInfo.setPcolorid(pcolorid);
		productInfo.setPstockid(pstockid);
		productInfo.setScode(scode);
		productInfo.setStock(stock);
		productInfo.setRegCheck(regCheck);
		productInfo.setImg1(img1);
		productInfo.setImg2(img2);
		productInfo.setImg3(img3);
		productInfo.setColorImg(colorImg);
		productInfo.setCcode(ccode);
		productInfo.setPprice(pprice);
		productInfo.setPname(pname);
		productInfo.setPnote(pnote);
		productInfo.setBname(bname);
		productInfo.setD1name(d1name);
		productInfo.setD2name(d2name);
		productInfo.setD3name(d3name);
		productInfo.setHiddenD1name(hiddenD1name);
		productInfo.setHiddenD2name(hiddenD2name);
		productInfo.setHiddenD3name(hiddenD3name);
		productInfo.setWcolorid(wcolorid);
		
		log.info("ProductModifyDto = " + productInfo);
		
		modifyService.modifyProduct(productInfo);
		
		return new ProductDto();
	}

	@PostMapping("/modify")
	public ProductDto getOrgData(@RequestBody ModifyForm modifyForm) {
		return modifyService.getOrgData(modifyForm);
	}
}
