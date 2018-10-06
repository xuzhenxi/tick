package com.qfedu.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.dao.ILoginlogDao;
import com.qfedu.entity.Loginlog;
import com.qfedu.service.ILoginlogService;
import com.qfedu.util.AddressUtils;
import com.qfedu.vo.PageBean;

@Service
public class LoginlogService implements ILoginlogService{

	@Autowired
	private ILoginlogDao loginlogDao;
	
	@Override
	public PageBean<Loginlog> findLoginlogByPage(int page, int limit, String no) {
		PageBean<Loginlog> pageInfo = new PageBean<>();

		pageInfo.setPageSize(limit);

		pageInfo.setCurrentPage(page);

		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		// 获取表中的记录总数
		int count = loginlogDao.count(map);
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
		List<Loginlog> list = loginlogDao.findByIndexAndSize(map);
		pageInfo.setPageInfos(list);

		return pageInfo;
	}

	@Override
	public void add(String ip, String no) {
		Loginlog l = new Loginlog();

		AddressUtils addressUtils = new AddressUtils();
		try {
			String address = addressUtils.getAddresses("ip="+ip, "utf-8");
			System.out.println(address);
			Date createtime = new Date();
			l.setCreatetime(createtime);
			l.setIp(ip);
			l.setLocation(address);
			l.setNo(no);
			loginlogDao.add(l);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

}
