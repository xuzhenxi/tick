package com.qfedu.dao;

import java.util.List;
import java.util.Map;

import com.qfedu.entity.Check;

public interface ICheckDao {
   
	 /**
     * 查询学生待办事项
     * @return 返回学生总数
     */
    public int count(Map<String, Object> map);
    
    /**
     * 分页查询代办事项
     * @param map 用来传递分页数据
     * @return 返回所有学生
     */
    public List<Check> findByIndexAndSize(Map<String, Object> map);
    
    /**
     * 添加请假单
     * @param check
     */
    public void add(Check check);
    
    /**
     * 修改请假单
     * @param check
     */
    public void update(Check check);
}