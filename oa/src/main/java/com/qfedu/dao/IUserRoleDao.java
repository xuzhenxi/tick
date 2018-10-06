package com.qfedu.dao;

import java.util.List;

import com.qfedu.entity.UserRole;

public interface IUserRoleDao {
   
	/**
	 * 添加用户权限
	 * @param userRole 用户角色
	 */
	public void add(UserRole userRole);
	
	/**
	 * 删除用户权限
	 * @param userRole 用户角色
	 */
	public void delete(int id);
	
	/**
	 * 根据用户id查询用户角色
	 * @param id
	 * @return
	 */
	public List<UserRole> findByUserId(int id);
	
	/**
	 * 根据角色id查询用户角色
	 * @param rid 角色id
	 * @return
	 */
	public List<UserRole> findByRid(int rid);
	
	
	
}