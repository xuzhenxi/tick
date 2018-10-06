package com.qfedu.service;

import java.util.List;
import java.util.Map;

import com.qfedu.entity.Staff;
import com.qfedu.entity.Student;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

public interface IStaffService {
	
	/**
	 * 分页查询
	 * @param page 页码
	 * @param limit 每页数据数
	 * @return 返回 分页数据
	 */
	public PageBean<Staff> findStaffByPage(int page, int limit, String info, String name);
	
	 /**
     * 查询最新添加的员工
     * @return 返回员工
     */
    public Staff findLastStaff();
    
    /**
     * 添加新员工
     */
    public void add(Staff staff, String uno);
    
    /**
     * 修改员工信息
     * @param staff 员工
     */
    public void update(Staff staff, String uno);

    /**
     * 删除员工
     * @param no 员工工号
     */
    public void delete(String no, String uno);
    
    /**
     * 判断能否添加
     * @param staff 员工
     * @param uno 当前登录账号
     * @return
     */
    public JsonBean addJudge(Staff staff, String uno);
    
    /**
     * 判断能否修改
     * @param staff 员工
     * @param uno 当前登录账号
     * @return
     */
    public JsonBean updateJudge(Staff staff, String uno);
    
    /**
     * 判断能否删除
     * @param no 员工账号
     * @param uno 当前登录账号
     * @return
     */
    public JsonBean deleteJudge(String no, String uno);
    
    /**
     * 查询所有员工
     * @return 返回员工集合
     */
    public List<Staff> findAll();
    
    /**
     * 添加读取excel中的员工信息
     * @param map 
     */
    public void addStaff(Map<String , Object> map);
    
    /**
     * 通过员工名查找员工
     * @return
     */
    public Staff findByName(String name);
    
    /**
     * 查询员工总数
     * @return 返回用户员工
     */
    public int count(Map<String, Object> map);
}
