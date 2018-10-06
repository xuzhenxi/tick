package com.qfedu.dao;

import java.util.List;
import java.util.Map;

import com.qfedu.entity.Staff;

public interface IStaffDao {
	
	  /**
     * 查询员工总数
     * @return 返回用户员工
     */
    public int count(Map<String, Object> map);
    
    /**
     * 分页查询员工
     * @param map 用来传递分页数据
     * @return 返回所有员工
     */
    public List<Staff> findByIndexAndSize(Map<String, Object> map);
    
    /**
     * 查询最新添加的员工
     * @return 返回员工
     */
    public Staff findLastStaff();
    
    /**
     * 查询部门员工总数
     * @param did 部门id
     * @return
     */
    public int Dcount(int did);
    
    /**
     * 添加新员工
     * @param staff 员工
     */
    public void add(Staff staff);
    
    /**
     * 修改员工信息
     * @param staff 员工
     */
    public void update(Staff staff);
    
    /**
     * 删除员工
     * @param no 员工工号
     */
    public void delete(String sno);
    
    /**
     * 通过工号查找员工
     * @param no
     * @return
     */
    public Staff findByNo(String no);
    
    /**
     * 查询所有员工
     * @return 返回员工集合
     */
    public List<Staff> findAll();
    
    /**
     * 通过员工名查找员工
     * @return
     */
    public Staff findByName(String name);
}