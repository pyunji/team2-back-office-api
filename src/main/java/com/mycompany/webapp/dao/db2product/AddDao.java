package com.mycompany.webapp.dao.db2product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.product.Brand;
import com.mycompany.webapp.dto.product.ProductDto;
import com.mycompany.webapp.dto.product.Sizes;
import com.mycompany.webapp.vo.Category;
import com.mycompany.webapp.vo.ProductCategory;
import com.mycompany.webapp.vo.ProductColor;
import com.mycompany.webapp.vo.ProductCommon;
import com.mycompany.webapp.vo.ProductStock;
import com.mycompany.webapp.vo.WithProduct;

@Mapper
public interface AddDao {
	public List<Brand> selectBrand();
	public List<Sizes> selectSizes();
	public Integer selectCatno(Category category);
	public void insertProductCategory(ProductCategory productCategory);
	public Integer selectBno(String bname);
	public void insertProductCommon(ProductCommon productCommon);
	public void insertProductColor(ProductColor productColor);
	public void insertProductStock(ProductStock productStock);
	public void insertWithProduct(WithProduct withProduct);
}
