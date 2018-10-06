package com.qfedu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.dao.IGradeDao;
import com.qfedu.dao.IStudentDao;
import com.qfedu.dao.IUserDao;
import com.qfedu.dao.IUserRoleDao;
import com.qfedu.entity.Grade;
import com.qfedu.entity.User;
import com.qfedu.entity.UserRole;
import com.qfedu.service.IGradeService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Service
public class GradeService implements IGradeService {

	@Autowired
	private IGradeDao gradeDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserRoleDao userRoleDao;
	
	@Autowired
	private IStudentDao studentDao;
	
	@Override
	public PageBean<Grade> findGradeByPage(int page, int limit) {
		Map<String, Object> map = new HashMap<>();
		
		PageBean<Grade> pageInfo = new PageBean<>();

		pageInfo.setPageSize(limit);

		pageInfo.setCurrentPage(page);
		
		// 获取表中的记录总数
		int count = gradeDao.count();
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
		List<Grade> list = gradeDao.findByIndexAndSize(map);
		pageInfo.setPageInfos(list);

		return pageInfo;
	}

	//添加班级
	@Override
	public void add(Grade grade, String no) {
		
		try {
			//判断能否添加
			JsonBean bean = this.addJudge(grade, no);
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			grade.setFlag(1);
			gradeDao.add(grade);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//修改班级
	@Override
	public void update(Grade grade, String no) {
		
		try {
			//判断能否修改
			JsonBean bean = this.updateJudge(grade, no);
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			gradeDao.update(grade);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//删除班级
	@Override
	public void delete(int id, String no) {
		
		try {
			//判断能否删除
			JsonBean bean = this.deleteJudge(id, no);
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			gradeDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//添加判断
	@Override
	public JsonBean addJudge(Grade grade, String no) {
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
			if (grade == null) {
				return JsonUtil.JsonBeanS(0, "班级为空，添加失败");
			}
			//名称不能为空
			if (grade.getName() == null || grade.getName().equals("")) {
				return JsonUtil.JsonBeanS(0, "班级名称必须填");
			}
			
			//判断是否存在同名学科
			//通过权限名称查找所有权限
			int count = gradeDao.findCountByName(grade.getName());
			//通过名称搜索到的权限个数大于0则存在同名不能添加
			if (count > 0) {
				return JsonUtil.JsonBeanS(0, "班级名重复");
			}
			//学科必须选
			if (grade.getCid() == -1) {
				return JsonUtil.JsonBeanS(0, "未选择学科");
			}
			
			//时间不能为空
			if (grade.getCreatedate() == null) {
				return JsonUtil.JsonBeanS(0, "创建时间必须填");
			}
			//周期必须选
			if (grade.getWeek() <= 0 || grade.getWeek() == null) {
				return JsonUtil.JsonBeanS(0, "班级周期必须填且大于0");
			}
			//类型不能为空
			if (grade.getLocation() == null) {
				return JsonUtil.JsonBeanS(0, "班级地址必须填写");
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
	public JsonBean updateJudge(Grade grade, String no) {
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
			if (grade == null) {
				return JsonUtil.JsonBeanS(0, "班级为空，添加失败");
			}
			//名称不能为空
			if (grade.getName() == null || grade.getName().equals("")) {
				return JsonUtil.JsonBeanS(0, "班级名称必须填");
			}
			
			//判断是否存在同名学科
			//通过班级名称查找所有班级
			int count = gradeDao.findCountByName(grade.getName());
			Grade grade1 = gradeDao.findById(grade.getId());
			//通过名称搜索到的权限个数大于0则存在同名不能添加
			if (grade1.getName().equals(grade.getName())) {
				if (count > 1) {
					return JsonUtil.JsonBeanS(0, "班级名重复");
				}
			} else {
				if (count > 0) {
					return JsonUtil.JsonBeanS(0, "班级名重复");
				}
			}
			//学科必须选
			if (grade.getCid() == -1) {
				return JsonUtil.JsonBeanS(0, "未选择学科");
			}
			
			//时间不能为空
			if (grade.getCreatedate() == null) {
				return JsonUtil.JsonBeanS(0, "创建时间必须填");
			}
			//周期必须选
			if (grade.getWeek() <= 0 || grade.getWeek() == null) {
				return JsonUtil.JsonBeanS(0, "班级周期必须填且大于0");
			}
			//类型不能为空
			if (grade.getLocation() == null) {
				return JsonUtil.JsonBeanS(0, "班级地址必须填写");
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
			//判断是否存在
			Grade grade = gradeDao.findById(id);
			if (grade == null) {
				return JsonUtil.JsonBeanS(0, "班级不存在，删除失败");
			}
			//判断班级是否有学生
			int count = studentDao.findCountByGid(id);
			if (count > 0) {
				return JsonUtil.JsonBeanS(0, "班级有学生学习");
			}
			
			//判断通过 可以添加
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
	}

	@Override
	public List<Grade> findAll() {
		List <Grade> list = null;
		
		try {
			list = gradeDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Grade findByName(String name) {
		Grade grade = null;
		
		try {
			grade = gradeDao.findByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return grade;
	}

	@Override
	public int count() {
		int c = 0;
		
		try {
			c = gradeDao.count();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return c;
	}

}
