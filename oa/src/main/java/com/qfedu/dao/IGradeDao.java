package com.qfedu.dao;

import java.util.List;
import java.util.Map;

import com.qfedu.entity.Grade;

public interface IGradeDao {
   
	/**
	 * 通过学科id查找班级数量
	 * @param cid 学科id
	 * @return
	 */
	public int findCountByCid(int cid);
	
	  /**
     * 查询班级总数
     * @return 返回用户员工
     */
    public int count();
    
    /**
     * 分页查询班级
     * @param map 用来传递分页数据
     * @return 返回所有员工
     */
    public List<Grade> findByIndexAndSize(Map<String, Object> map);
    
    /**
     * 添加班级
     * @param grade 班级
     */
    public void add(Grade grade);
    
    /**
     * 添加班级
     * @param grade 班级
     */
    public void update(Grade grade);
    
    /**
     * 添加班级
     * @param grade 班级
     */
    public void delete(int id);
    
    /**
     * 查询指定名字班级个数
     * @param name 班级名字
     * @return 班级个数
     */
    public int findCountByName(String name); 
    
    /**
     * 通过id查找班级
     * @param id 班级id
     * @return 返回班级
     */
    public Grade findById(int id);
    
    /**
     * 查找所有班级
     * @return
     */
    public List<Grade> findAll();
    
    /**
     * 根据名字查询班级
     * @param name 班级名字
     * @return 
     */
    public Grade findByName(String name); 
}