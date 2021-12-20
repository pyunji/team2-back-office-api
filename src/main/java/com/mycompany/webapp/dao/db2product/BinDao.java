package com.mycompany.webapp.dao.db2product;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.product.BinDto;
import com.mycompany.webapp.dto.product.ProductDto;
import com.mycompany.webapp.dto.product.SearchForm;
import com.mycompany.webapp.vo.ProductColor;

@Mapper
public interface BinDao {
	public List<ProductDto> selectProductList(SearchForm searchForm);
	public int getTotalProductNum(SearchForm searchForm);
	public void updateDelDate(BinDto binDto);
	public int countPcommonidDependency(String pcommonid);
	public int countPcoloridDependency(String pcolorid);
	public void delProductCategory(String pcommonid);
	public void delProductCommon(String pcommonid);
	public void delWithProduct(String pcolorid);
	public ProductColor selectImages(String pcolorid); 
	public void delProductColor(String pcolorid);
	public void delProductStock(String pstockid);
	public List<String> selectDelTargetPstockids(Date delDay);
}
