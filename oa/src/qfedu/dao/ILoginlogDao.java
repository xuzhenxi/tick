package com.qfedu.dao;

import java.util.List;
import java.util.Map;

import com.qfedu.entity.Loginlog;

public interface ILoginlogDao {
  
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
    public List<Loginlog> findByIndexAndSize(Map<String, Object> map);
    
    /**
     * 添加登录日志
     * @param l 登录日志
     */
    public void add(Loginlog l);
}