package com.qfedu.dao;

import java.util.List;
import java.util.Map;

import com.qfedu.entity.Student;

public interface IStudentDao {
   
	/**
	 * 根据班级id查找学生数量
	 * @param gid 班级id
	 * @return 返回学生数量
	 */
	public int findCountByGid(int gid);
	
	  /**
     * 查询学生总数
     * @return 返回学生总数
     */
    public int count();
    
    /**
     * 分页查询学生
     * @param map 用来传递分页数据
     * @return 返回所有学生
     */
    public List<Student> findByIndexAndSize(Map<String, Object> map);
    
    /**
     * 添加学生
     * @param stu 学生
     */
    public void add(Student stu);
    
    /**
     * 修改学生
     * @param stu 学生
     */
    public void update(Student stu);
    
    /**
     * 删除学生
     * @param stu 学生
     */
    public void delete(String no);
    
    /**
     * 查找最后一个学生
     * @return
     */
    public Student findLastStu();
    
    /**
     * 根据账号查找学员
     * @param no
     * @return
     */
    public Student findByNo(String no);
}