package com.qfedu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.qfedu.dao.ICheckDao;
import com.qfedu.entity.Check;
import com.qfedu.entity.Student;
import com.qfedu.service.ICheckService;
import com.qfedu.vo.PageBean;

@Service
public class CheckService implements ICheckService {

	@Autowired
	private ICheckDao checkDao;
	
	@Override
	public PageBean<Check> findCheckByPage(int page, int limit, String no) {
		if (no == null || no == "") {
			no = null;
		} 
		
		Map<String, Object> map = new HashMap<>();
		
		PageBean<Check> pageInfo = new PageBean<>();

		pageInfo.setPageSize(limit);

		pageInfo.setCurrentPage(page);
		
		map.put("startno", no);
		// 获取表中的记录总数
		int count = checkDao.count(map);
		pageInfo.setCount(count);
		// 计算总页数
		if(count % pageInfo.getPageSize() == 0){
			pageInfo.setTotalPage(count / pageInfo.getPageSize());
		}else{
			pageInfo.setTotalPage(count / pageInfo.getPageSize()+ 1);
		}
		// 根据页码计算分页查询时的开始索引
		int index = (page - 1) * pageInfo.getPageSize();
		map.put("index", index);
		map.put("size", pageInfo.getPageSize());
		List<Check> list = checkDao.findByIndexAndSize(map);
		pageInfo.setPageInfos(list);

		return pageInfo;
	}

	//添加假条
	@Override
	public void add(Check check) {
		
		try {
			checkDao.add(check);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//修改假条
	@Override
	public void update(Check check) {
		
		try {
			checkDao.update(check);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
