package com.mycompany.webapp.dao.db2product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.product.Depth1;
import com.mycompany.webapp.dto.product.Depth2;
import com.mycompany.webapp.dto.product.Depth3;

@Mapper
public interface DepthDao {
	public List<Depth1> selectDepth1();
	public List<Depth2> selectDepth2(String d1name);
	public List<Depth3> selectDepth3(String d2name);
}
