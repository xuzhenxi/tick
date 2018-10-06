package com.qfedu.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.dao.IStudentDao;
import com.qfedu.dao.IUserDao;
import com.qfedu.dao.IUserRoleDao;
import com.qfedu.entity.Grade;
import com.qfedu.entity.Staff;
import com.qfedu.entity.Student;
import com.qfedu.entity.User;
import com.qfedu.entity.UserRole;
import com.qfedu.service.IStudentService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Service
public class StudentService implements IStudentService{

	@Autowired
	private IStudentDao studentDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserRoleDao userRoleDao;
	
	@Override
	public PageBean<Student> findStuByPage(int page, int limit) {
		Map<String, Object> map = new HashMap<>();
		
		PageBean<Student> pageInfo = new PageBean<>();

		pageInfo.setPageSize(limit);

		pageInfo.setCurrentPage(page);
		
		// 获取表中的记录总数
		int count = studentDao.count();
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
		List<Student> list = studentDao.findByIndexAndSize(map);
		pageInfo.setPageInfos(list);

		return pageInfo;
	}

	@Override
	public void add(Student stu, String no) {
		
		try {
			JsonBean bean = this.addJudge(stu, no);
			
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			
			stu.setFlag(1);
			studentDao.add(stu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Student stu, String no) {
		
		try {
			JsonBean bean = this.updateJudge(stu, no);
			
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			studentDao.update(stu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String no, String uno) {
		
		try {
			JsonBean bean = this.deleteJudge(no, uno);
			
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			studentDao.delete(no);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Student findLastStu() {
		Student stu = null;
		
		try {
			stu = studentDao.findLastStu();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return stu;
	}

	@Override
	public JsonBean addJudge(Student stu, String no) {
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
			if (stu == null) {
				return JsonUtil.JsonBeanS(0, "员工为空，添加失败");
			}
			//名称不能为空
			if (stu.getName() == null || stu.getName().equals("")) {
				return JsonUtil.JsonBeanS(0, "请填写姓名");
			}
			//部门必须选
			if (stu.getGid() == -1) {
				return JsonUtil.JsonBeanS(0, "班级没选");
			}
			//生日不能为空
			if (stu.getBirthday() == null) {
				return JsonUtil.JsonBeanS(0, "入学时间必须填");
			}
			//时间不能为空
			if (stu.getCreatedate() == null) {
				return JsonUtil.JsonBeanS(0, "入学时间必须填");
			}
			//手机号不能为空
			if (stu.getPhone() == null) {
				return JsonUtil.JsonBeanS(0, "手机号必须填");
			}
			//身份证不能为空
			if (stu.getCardno() == null) {
				return JsonUtil.JsonBeanS(0, "身份号必须填");
			}
			
			//招生老师
			if (stu.getIntrono() == null || stu.getIntrono().equals("")) {
				return JsonUtil.JsonBeanS(0, "请选择招生老师");
			}
			
			
			//判断通过 可以添加
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}

	@Override
	public JsonBean updateJudge(Student stu, String no) {
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
			if (stu == null) {
				return JsonUtil.JsonBeanS(0, "员工为空，添加失败");
			}
			//名称不能为空
			if (stu.getName() == null || stu.getName().equals("")) {
				return JsonUtil.JsonBeanS(0, "请填写姓名");
			}
			//部门必须选
			if (stu.getGid() == -1) {
				return JsonUtil.JsonBeanS(0, "班级没选");
			}
			//生日不能为空
			if (stu.getBirthday() == null) {
				return JsonUtil.JsonBeanS(0, "入学时间必须填");
			}
			//时间不能为空
			if (stu.getCreatedate() == null) {
				return JsonUtil.JsonBeanS(0, "入学时间必须填");
			}
			//手机号不能为空
			if (stu.getPhone() == null) {
				return JsonUtil.JsonBeanS(0, "手机号必须填");
			}
			//身份证不能为空
			if (stu.getCardno() == null) {
				return JsonUtil.JsonBeanS(0, "身份号必须填");
			}
			
			//招生老师
			if (stu.getIntrono() == null || stu.getIntrono().equals("")) {
				return JsonUtil.JsonBeanS(0, "请选择招生老师");
			}
			//判断通过 可以添加
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
	}

	@Override
	public JsonBean deleteJudge(String no, String uno) {
		try {
			
			//判读是否是管理员
			User user = userDao.findByNo(uno);
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
			Student stu = studentDao.findByNo(no);
			
			if (stu == null) {
				return JsonUtil.JsonBeanS(0, "学员不存在或已被删除");
			}
			
			//判断通过 可以添加
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
	}

	@Override
	public void addStu(Map<String, Object> map) {
		Student stu = new Student();
		
		stu.setNo((String)map.get("no"));
		stu.setName((String)map.get("name"));
		stu.setGid((Integer)map.get("gid"));
		stu.setSex((String)map.get("sex"));
		stu.setPhone((String)map.get("phone"));
		stu.setQq((String)map.get("qq"));
		stu.setEmail((String)map.get("email"));
		stu.setCardno((String)map.get("cardno"));
		stu.setSchool((String)map.get("school"));
		stu.setEducation((String)map.get("education"));
		stu.setIntrono((String)map.get("introno"));
		stu.setBirthday((Date)map.get("birthday"));
		stu.setCreatedate((Date)map.get("createdate"));
		
		try {
			stu.setFlag(1);
			studentDao.add(stu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int count() {
		int c = 0;
		
		try {
			c = studentDao.count();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return c;
	}

}
