package com.qfedu.service;

import java.util.List;

import com.qfedu.entity.Authority;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

public interface IAuthorityService {

	  /**
     * 根据账号查找左侧菜单栏
     * @return 返回Authority集合
     */
    public List<Authority> findTitleByNo(String no);
    
    /**
	 * 分页查询
	 * @param page 页码
	 * @param limit 每页数据数
	 * @return 返回 分页数据
	 */
	public PageBean<Authority> findAuthorityByPage(int page, int limit);
	
    /**
     * 查询所有权限
     * @return
     */
    public List<Authority> findAllTitle();
    
    /**
     * 按父级权限id查询权限
     * @param parentid 父级权限id
     * @return 返回权限集合
     */
    public List<Authority> findByParentId(int parentId);
    
    /**
     * 添加权限
     * @param auty 权限
     */
    public void add(Authority auty, String no);
    
    /**
     * 删除权限
     * @param aid 权限id
     */
    public void delete(int aid, String no);
    
    /**
     * 修改权限
     * @param auty 权限
     */
    public void update(Authority auty, String no);
    
    /**
     * 删除判断
     * @param aid 权限id
     * @param no 用户账号
     * @return
     */
    public JsonBean deleteJudge(int aid, String no);
    
    /**
     * 修改判断
     * @param auty 权限
     * @param no 当前登录用户账号
     * @return
     */
    public JsonBean updateJudge(Authority auty, String no);
    
    /**
     * 添加判断
     * @param auty 权限
     * @param no 当前登录用户账号
     * @return
     */
    public JsonBean addJudge(Authority auty, String no);
}


