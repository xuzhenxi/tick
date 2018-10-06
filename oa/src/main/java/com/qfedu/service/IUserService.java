package com.qfedu.service;

import com.qfedu.entity.User;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

public interface IUserService {

	/**
	 * 分页查询
	 * @param page 页码
	 * @param limit 每页数据数
	 * @return 返回 分页数据
	 */
	public PageBean<User> findUserByPage(int page, int limit, String no, int flag);
	
	/**
	 * 删除用户
	 * @param id 用户id
	 */
	public void delete(int id, String no);
	
	/**
	 * 根据id查询用户
	 * @param id 用户id
 	 */
	public User findById(int id);
	
	/**
	 * 删除判断
	 * @param id
	 * @param no
	 */
	public JsonBean deleteJudge(int id, String no); 
	
	/**
     * 根据账号查找用户
     * @param on 账号
     * @return 返回用户
     */
    public User findByNo(String on);
	
}
