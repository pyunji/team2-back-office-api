package com.mycompany.webapp.dao.db2product;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.product.ProductDto;
import com.mycompany.webapp.dto.product.ProductIds;
import com.mycompany.webapp.dto.product.UpdateProductStock;
import com.mycompany.webapp.vo.ProductCategory;
import com.mycompany.webapp.vo.ProductColor;
import com.mycompany.webapp.vo.ProductCommon;
import com.mycompany.webapp.vo.ProductStock;
import com.mycompany.webapp.vo.WithProduct;

@Mapper
public interface ModifyDao {
	public ProductDto selectOrgData(String pstockid);
	public void updateProductCategory(ProductCategory productCategory);
	public void updateProductCommon(ProductCommon productCommon);
	public void updateProductColorPcolorid(ProductIds productIds);
	public void updateProductStockPids(ProductIds productIds);
	public void updateWithProductPcolorid(ProductIds productIds);
	public void updatePprice(ProductColor productColor);
	public void updateImages(ProductColor productColor);
	public void updateProductStock(UpdateProductStock productStock);
	public void updateWithProduct(WithProduct withProduct);
}
