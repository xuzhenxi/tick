package com.qfedu.service;

import java.util.Map;

import com.qfedu.entity.Student;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

public interface IStudentService {
	
	/**
	 * 分页查询
	 * @param page 页码
	 * @param limit 每页数据数
	 * @return 返回 分页数据
	 */
	public PageBean<Student> findStuByPage(int page, int limit);
	
	/**
     * 添加学生
     * @param stu 学生
     */
    public void add(Student stu, String no);
    
    /**
     * 修改学生
     * @param stu 学生
     */
    public void update(Student stu, String no);
    
    /**
     * 删除学生
     * @param stu 学生
     */
    public void delete(String no, String uno);
    
    /**
     * 查找最后一个学生
     * @return
     */
    public Student findLastStu();
    
    /**
     * 添加判断
     * @param stu 学生
     * @param no 当前登录账号
     * @return
     */
    public JsonBean addJudge(Student stu, String no);
    
    /**
     * 修改判断
     * @param stu 学生
     * @param no 当前登录账号
     * @return
     */
    public JsonBean updateJudge(Student stu, String no);
    
    /**
     * 添加判断
     * @param stu 学生
     * @param no 当前登录账号
     * @return
     */
    public JsonBean deleteJudge(String no, String uno);
    
    /**
     * 添加读取excel中的学员信息
     * @param map 
     */
    public void addStu(Map<String , Object> map);
    
    /**
     * 查询学生总数
     * @return 返回学生总数
     */
    public int count();
}
