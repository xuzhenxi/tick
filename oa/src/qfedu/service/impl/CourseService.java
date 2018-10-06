package com.qfedu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.dao.ICourseDao;
import com.qfedu.dao.IGradeDao;
import com.qfedu.dao.IUserDao;
import com.qfedu.dao.IUserRoleDao;
import com.qfedu.entity.Course;
import com.qfedu.entity.User;
import com.qfedu.entity.UserRole;
import com.qfedu.service.ICourseService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Service
public class CourseService implements ICourseService {

	@Autowired
	private ICourseDao courseDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserRoleDao userRoleDao;
	
	@Autowired
	private IGradeDao gradeDao;
	
	@Override
	public PageBean<Course> findCourseByPage(int page, int limit) {
		Map<String, Object> map = new HashMap<>();
		
		PageBean<Course> pageInfo = new PageBean<>();

		pageInfo.setPageSize(limit);

		pageInfo.setCurrentPage(page);
		
		// 获取表中的记录总数
		int count = courseDao.count();
		pageInfo.setCount(count);
		// 计算总页数
		if(count % pageInfo.getPageSize() == 0){
			pageInfo.setTotalPage(count / pageInfo.getPageSize());
		}else{
			pageInfo.setTotalPage(count / pageInfo.getPageSize()+ 1);
		}
		// 根据页码计算分页查询时的开始索引
		int index = (page - 1) * pageInfo.getPageSize();
		map.put("index", index);
		map.put("size", pageInfo.getPageSize());
		List<Course> list = courseDao.findByIndexAndSize(map);
		pageInfo.setPageInfos(list);

		return pageInfo;
	}

	//添加科目
	@Override
	public void add(Course course, String no) {
		
		try {
			JsonBean bean = this.addJudge(course, no);
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			course.setFlag(1);
			courseDao.add(course);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//修改科目
	@Override
	public void update(Course course, String no) {
		
		try {
			JsonBean bean = this.updateJudge(course, no);
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			course.setFlag(1);
			courseDao.update(course);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//删除科目
	@Override
	public void delete(int id, String no) {
		try {
			
			JsonBean bean = this.deleteJudge(id, no);
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			courseDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//添加判断
	@Override
	public JsonBean addJudge(Course course, String no) {
			try {
			
			//判读是否是管理员
			User user = userDao.findByNo(no);
			List<UserRole> userRole = userRoleDao.findByUserId(user.getId());
			int i = 0;
			for (UserRole userRole2 : userRole) {
				if (userRole2.getRid() == 1) {
					i = 1;
				}
			}
			if (i != 1) {
				return JsonUtil.JsonBeanS(0, "抱歉，你不是管理员");
			}
			//判断是否为空
			if (course == null) {
				return JsonUtil.JsonBeanS(0, "学科为空，添加失败");
			}
			//名称不能为空
			if (course.getName() == null || course.getName().equals("")) {
				return JsonUtil.JsonBeanS(0, "学科名称必须填");
			}
			
			//判断是否存在同名学科
			//通过权限名称查找所有权限
			int count = courseDao.findCountByName(course.getName());
			//通过名称搜索到的权限个数大于0则存在同名不能添加
			if (count > 0) {
				return JsonUtil.JsonBeanS(0, "存在同名学科");
			}
			//时间不能为空
			if (course.getCreatedate() == null) {
				return JsonUtil.JsonBeanS(0, "创建时间必须填");
			}
			//周期必须选
			if (course.getWeek() <= 0 || course.getWeek() == null) {
				return JsonUtil.JsonBeanS(0, "学习周期必须填且大于0");
			}
			//类型不能为空
			if (course.getType() == null) {
				return JsonUtil.JsonBeanS(0, "学科类型必须填");
			}
			
			//判断通过 可以添加
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
	}

	//修改判断
	@Override
	public JsonBean updateJudge(Course course, String no) {
			try {
			
			//判读是否是管理员
			User user = userDao.findByNo(no);
			List<UserRole> userRole = userRoleDao.findByUserId(user.getId());
			int i = 0;
			for (UserRole userRole2 : userRole) {
				if (userRole2.getRid() == 1) {
					i = 1;
				}
			}
			if (i != 1) {
				return JsonUtil.JsonBeanS(0, "抱歉，你不是管理员");
			}
			//判断是否为空
			if (course == null) {
				return JsonUtil.JsonBeanS(0, "学科为空，添加失败");
			}
			
			//名称不能为空
			if (course.getName() == null || course.getName().equals("")) {
				return JsonUtil.JsonBeanS(0, "学科名必须填");
			}
			//判断是否存在同名学科
			//通过学科名称此名称学科数量
			int count = courseDao.findCountByName(course.getName());
			Course course1 = courseDao.findById(course.getId());
			if (course1.getName().equals(course.getName())) {
				//如果修改的名字与原名相同则查到的权限个数大于一个是判定存在同名
				if (count > 1) {
					return JsonUtil.JsonBeanS(0, "存在同名学科");
				}
			} else {
				//如果修改的名字与原名不相同则查到的权限个数大于0个是判定存在同名
				if (count > 0) {
					return JsonUtil.JsonBeanS(0, "存在同名学科");
				}
			}
			
			//时间不能为空
			if (course.getCreatedate() == null) {
				return JsonUtil.JsonBeanS(0, "创建时间必须填");
			}
			//学习周期
			if (course.getWeek() <= 0 || course.getWeek() == null) {
				return JsonUtil.JsonBeanS(0, "学习周期必须大于0");
			}
			//类型不能为空
			if (course.getType() == null) {
				return JsonUtil.JsonBeanS(0, "学科类型必须填");
			}
			
			//判断通过 可以添加
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
	}

	//删除判断
	@Override
	public JsonBean deleteJudge(int id, String no) {
		try {
			//查询是否存在
			Course course = courseDao.findById(id);
			if (course == null) {
				return JsonUtil.JsonBeanS(0, "该学科不存在，删除失败");
			}
			
			//判读是否是管理员
			User user = userDao.findByNo(no);
			List<UserRole> userRole = userRoleDao.findByUserId(user.getId());
			int i = 0;
			for (UserRole userRole2 : userRole) {
				if (userRole2.getRid() == 1) {
					i = 1;
				}
			}
			if (i != 1) {
				return JsonUtil.JsonBeanS(0, "抱歉，你不是管理员");
			}
			
			//根据学科id查询班级
			int gCount = gradeDao.findCountByCid(id);
			if (gCount != 0) {
				return JsonUtil.JsonBeanS(0, "有班级正在学习此学科");
			}
			
			
			//判断通过 可以删除
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}

	//查找所有学科
	@Override
	public List<Course> findAll() {
		List<Course> list = null;
		
		try {
			list = courseDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	

}
