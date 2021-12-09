package com.mycompany.webapp.service.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db2product.DepthDao;
import com.mycompany.webapp.dto.product.Depth1;
import com.mycompany.webapp.dto.product.Depth2;
import com.mycompany.webapp.dto.product.Depth3;

@Service
public class SearchService {
	@Resource DepthDao depthDao;
	
	public List<Depth1> initSearch() {
		List<Depth1> selectDepth1 = depthDao.selectDepth1();
		return selectDepth1;
	}
	
	public List<Depth2> selectDepth2(String depth1){
		return depthDao.selectDepth2(depth1);
	}
	
	public List<Depth3> selectDepth3(String depth2){
		return depthDao.selectDepth3(depth2);
	}
}
