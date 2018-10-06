package com.qfedu.service;

import java.util.List;

import com.qfedu.entity.Course;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

public interface ICourseService {

	/**
	 * 分页查询
	 * @param page 页码
	 * @param limit 每页数据数
	 * @return 返回 分页数据
	 */
	public PageBean<Course> findCourseByPage(int page, int limit);
	
	 /**
     * 添加新科目
     * @param course
     */
    public void add(Course course, String no);
    
    /**
     * 修改科目
     * @param course
     */
    public void update(Course course, String no);
    
    /**
     * 删除科目
     * @param id科目id
     */
    public void delete(int id, String no);
    
    /**
     * 判断能否添加
     * @param course 学科
     * @param no 当前登录用户账号
     */
    public JsonBean addJudge(Course course, String no); 
    
    /**
     * 判断能否修改
     * @param course 学科
     * @param no 当前登录用户账号
     */
    public JsonBean updateJudge(Course course, String no); 
    
    /**
     * 判断能否删除
     * @param id 学科id
     * @param no 当前登录用户账号
     */
    public JsonBean deleteJudge(int id, String no); 
    
    /**
     * 查询所有学科
     * @return 返回学科集合
     */
    public List<Course> findAll();
}
