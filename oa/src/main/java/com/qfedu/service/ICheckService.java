package com.qfedu.service;

import com.qfedu.entity.Check;
import com.qfedu.vo.PageBean;

public interface ICheckService {

	/**
	 * 分页查询
	 * @param page 页码
	 * @param limit 每页数据数
	 * @return 返回 分页数据
	 */
	public PageBean<Check> findCheckByPage(int page, int limit, String no);
	
	/**
     * 添加请假单
     * @param check
     */
    public void add(Check check);
    
    /**
     * 修改请假单
     * @param check
     */
    public void update(Check check);
}
