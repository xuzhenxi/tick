package com.qfedu.service;

import java.util.List;

import com.qfedu.entity.Grade;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

public interface IGradeService {

	/**
	 * 分页查询
	 * @param page 页码
	 * @param limit 每页数据数
	 * @return 返回 分页数据
	 */
	public PageBean<Grade> findGradeByPage(int page, int limit);
	
	 /**
     * 添加班级
     * @param grade 班级
     */
    public void add(Grade grade, String no);
    
    /**
     * 添加班级
     * @param grade 班级
     */
    public void update(Grade grade, String no);
    
    /**
     * 添加班级
     * @param grade 班级
     */
    public void delete(int id, String no);
    
    /**
     * 添加判断
     * @param grade 班级
     * @param no 当前登录账号
     * @return
     */
    public JsonBean addJudge(Grade grade, String no);
    
    /**
     * 修改判断
     * @param grade 班级
     * @param no 当前登录账号
     * @return
     */
    public JsonBean updateJudge(Grade grade, String no);
    
    /**
     * 删除判断
     * @param id 班级id
     * @param no 当前登录账号
     * @return
     */
    public JsonBean deleteJudge(int id, String no);
    
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
    
    /**
     * 查询班级总数
     * @return 返回用户员工
     */
    public int count();
}
