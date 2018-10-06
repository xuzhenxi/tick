package com.qfedu.service;

import com.qfedu.vo.JsonBean;

public interface IUserRoleService {

	/**
	 * 修改用户权限
	 * @param rids 权限id
	 * @param id 用户id
	 */
	public void update(Integer[] rids, Integer id, String no);
	
	/**
	 * 修改判断
	 * @param no
	 * @return
	 */
	public JsonBean updateJudge(String no);
	
	
	
	
}
