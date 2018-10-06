package com.qfedu.dao;

import java.util.List;
import java.util.Map;

import com.qfedu.entity.Role;


public interface IRoleDao {
    
    /**
     * 通过账号查找用户角色
     * @param no 账号
     * @return 返回用户角色信息
     */
    public List<String> findRoleByNo(String no);
    
    /**
     * 查询角色总数
     * @return 返回用户总数
     */
    public int count(Map<String, Object> map);
    
    /**
     * 分页查询角色
     * @param map 用来传递分页数据
     * @return 返回所有用户
     */
    public List<Role> findByIndexAndSize(Map<String, Object> map);
    
    /**
     * 查询所有角色
     * @return 返回角色
     */
    public List<Role> findAll();
    
    /**
     * 删除角色
     * @param rid 角色id
     */
    public void delete(int rid);
    
    /**
     * 查找角色
     * @param rid 角色id
     * @return
     */
    public Role findByRid(int rid);
    
    /**
     * 查找父角色
     * @param parentId
     * @return
     */
    public Role findProle(Map<String, Object> map);
}