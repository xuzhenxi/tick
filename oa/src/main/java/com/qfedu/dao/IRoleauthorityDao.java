package com.qfedu.dao;

import java.util.List;

import com.qfedu.entity.RoleAuthority;

public interface IRoleauthorityDao {
    
	/**
	 * 添加角色权限
	 * @param roleAuty
	 */
	public void add(RoleAuthority roleAuty);
	
	/**
	 * 根据角色id删除角色权限
	 * @param id
	 */
	public void delete(int rid);
	
	/**
	 * 通过角色id查询角色权限
	 * @param rid 角色id
	 * @return
	 */
	public List<RoleAuthority> findByRid(int rid);
	
	/**
	 * 通过角色id查询角色权限
	 * @param rid 角色id
	 * @return
	 */
	public List<RoleAuthority> findByAid(int aid);
}