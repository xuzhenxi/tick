package com.qfedu.dao;

import java.util.List;
import java.util.Map;

import com.qfedu.entity.Course;

public interface ICourseDao {
	
	  /**
     * 查询员工总数
     * @return 返回用户员工
     */
    public int count();
    
    /**
     * 分页查询员工
     * @param map 用来传递分页数据
     * @return 返回所有员工
     */
    public List<Course> findByIndexAndSize(Map<String, Object> map);
    
    /**
     * 添加新科目
     * @param course
     */
    public void add(Course course);
    
    /**
     * 修改科目
     * @param course
     */
    public void update(Course course);
    
    /**
     * 删除科目
     * @param id科目id
     */
    public void delete(int id);
    
    /**
     * 查找同名学科数量
     * @param name 学科名称
     * @return 数量
     */
    public int findCountByName(String name);
    
    /**
     * 通过id查找学科
     * @param id 学科id
     * @return 返回学科
     */
    public Course findById(int id);
    
    /**
     * 查询所有学科
     * @return 返回学科集合
     */
    public List<Course> findAll();
    
}