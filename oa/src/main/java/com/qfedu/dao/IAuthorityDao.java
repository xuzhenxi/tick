package com.qfedu.dao;

import java.util.List;
import java.util.Map;

import com.qfedu.entity.Authority;

public interface IAuthorityDao {
    
    /**
     * 通过账号查找用户许可
     * @param no 账号
     * @return 返回操作许可
     */
    public List<String> findPermitByNo(String no);
    
    /**
     * 根据账号查找左侧菜单栏
     * @return 返回Authority集合
     */
    public List<Authority> findTitleByNo(String no);
    
    /**
     * 查询用户总数
     * @return 返回用户总数
     */
    public int count();
    
    /**
     * 分页查询权限
     * @param map 用来传递分页数据
     * @return 返回所有权限
     */
    public List<Authority> findByIndexAndSize(Map<String, Object> map);
    
    /**
     * 通过角色id查询角色权限
     * @param rid 角色id
     * @return 返回权限 id集合
     */
    public List<Integer> findTitlByRid(int rid);
    
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
    public void add(Authority auty);
    
    /**
     * 删除权限
     * @param aid 权限id
     */
    public void delete(Integer aid);
    
    /**
     * 修改权限
     * @param auty 权限
     */
    public void update(Authority auty);
    
    /**
     * 通过id查找权限
     * @param aid 权限id
     * @return 返回权限
     */
    public Authority findByAid(int aid);  
    
    /**
     * 通过权限名称查找
     * @param title 权限名称
     * @return 权限
     */
    public List<Authority> findByTitle(String title);
    
    
    public List<Authority> findSonTitle(Map<String, Object> map);
}