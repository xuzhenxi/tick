package com.qfedu.dao;

import java.util.List;
import java.util.Map;

import com.qfedu.entity.User;

public interface IUserDao {
    
    /**
     * 添加新用户
     * @param user 新用户
     */
    public void add(User user);
    
    /**
     * 根据账号查找用户
     * @param on 账号
     * @return 返回用户
     */
    public User findByNo(String on);
    
    /**
     * 查询用户总数
     * @return 返回用户总数
     */
    public int count(Map<String, Object> map);
    
    /**
     * 分页查询用户
     * @param map 用来传递分页数据
     * @return 返回所有用户
     */
    public List<User> findByIndexAndSize(Map<String, Object> map);
    
    /**
     * 删除用户
     * @param id 用户id
     */
    public void delete(int id);
    
    /**
     * 根据id查询用户
     * @param id 用户id
     * @return 返回用户
     */
    public User findById(int id);
    
    /**
     * 搜索用户
     * @param map
     * @return
     */
    public List<User> search(Map<String, Object> map);
    
    /**
     * 通过角色查找使用该角色用户
     * @param id 角色id
     * @return
     */
    public List<User> findByRole(int id);
    
}