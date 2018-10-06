package com.qfedu.service;

import java.util.List;

import com.qfedu.entity.Depart;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

public interface IDepartService {
	
	/**
	 * 查询所有部门
	 * @return 返回部门集合
	 */
	public List<Depart> findAll();
	
	/**
	 * 分页查询
	 * @param page 页码
	 * @param limit 每页数据数
	 * @return 返回 分页数据
	 */
	public PageBean<Depart> findDeptByPage(int page, int limit);

	 /**
     * 添加部门
     * @param dept 新增部门
     */
    public void add(Depart dept, String no);
    
    /**
     * 修改部门
     * @param dept 修改部门
     */
    public void update(Depart dept, String no);
    
    /**
     * 删除部门
     * @param id 部门id
     */
    public void delete(int id, String no);
    
    /**
     * 判断能否添加能
     * @param dept 部门
     * @return 返回code=1能添加 返回code=0不能添加
     */
    public JsonBean addJudge(Depart dept, String no);
    
    /**
     * 判断能否修改
     * @param dept 部门
     * @return 返回code=1能修改 返回code=0不能修改
     */
    public JsonBean updateJudge(Depart dept, String no);
    
    /**
     * 判断能否删除
     * @param id 部门id
     * @return
     */
    public JsonBean deleteJudge(int id, String no);
    
    /**
     * 通过部门名查找部门
     * @param name 部门名
     * @return 
     */
    public Depart findByDname(String name);
}
