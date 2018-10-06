package com.qfedu.service;

import com.qfedu.entity.Loginlog;
import com.qfedu.vo.PageBean;

public interface ILoginlogService {

	/**
	 * 分页查询
	 * @param page 页码
	 * @param limit 每页数据数
	 * @return 返回 分页数据
	 */
	public PageBean<Loginlog> findLoginlogByPage(int page, int limit, String no); 
	
	/**
	 * 添加登录日志
	 * @param ip ip地址
	 * @param no 账号
	 */
	public void add(String ip, String no);
}
