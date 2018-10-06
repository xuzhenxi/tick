package com.qfedu.dao;

import java.util.List;
import java.util.Map;

import com.qfedu.entity.Depart;
import com.qfedu.entity.Staff;

public interface IDepartDao {
    
	/**
	 * 查询所有部门
	 * @return 返回部门集合
	 */
	public List<Depart> findAll();
	
	/**
	 * 查询员工所在部门
	 * @return
	 */
	public Depart findDeptByStaffDid(int did);
	
	  /**
     * 查询部门总数
     * @return 返回部门数
     */
    public int count();
    
    /**
     * 分页查询部门
     * @param map 用来传递分页数据
     * @return 返回所有部门
     */
    public List<Depart> findByIndexAndSize(Map<String, Object> map);
    
    /**
     * 添加部门
     * @param dept 新增部门
     */
    public void add(Depart dept);
    
    /**
     * 修改部门
     * @param dept 修改部门
     */
    public void update(Depart dept);
    
    /**
     * 删除部门
     * @param id 部门id
     */
    public void delete(int id);
    
    /**
     * 根据部门名称查找部门
     * @param name 部门名称
     * @return 返回部门集合
     */
    public List<Depart> findByName(String name);
    
    /**
     * 通过部门id查找部门
     * @param id 部门id
     * @return
     */
    public Depart findById(int id);
    
    /**
     * 通过部门名查找部门
     * @param name 部门名
     * @return 
     */
    public Depart findByDname(String name);
}