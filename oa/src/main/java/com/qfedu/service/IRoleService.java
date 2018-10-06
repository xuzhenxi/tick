package com.qfedu.service;

import java.util.List;

import com.qfedu.entity.Role;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

public interface IRoleService {

	/**
	 * 分页查询
	 * @param page 页码
	 * @param limit 每页数据数
	 * @return 返回 分页数据
	 */
	public PageBean<Role> findRoleByPage(int page, int limit, String info, String name);
	
	/**
	 * 查询所有角色
	 * @return 返回角色
	 */
	public List<Role> findAll();
	
	/**
	 * 删除角色
	 * @param rid 角色id
	 */
	public void delete(int rid, String no);
	
	/**
	 * 删除判断
	 * @param rid 角色id
	 * @param no 当前登录用户账号
	 * @return
	 */
	public JsonBean deleteJudge(int rid, String no);
}
