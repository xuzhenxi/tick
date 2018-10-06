package com.qfedu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.dao.IDepartDao;
import com.qfedu.dao.IStaffDao;
import com.qfedu.dao.IUserDao;
import com.qfedu.dao.IUserRoleDao;
import com.qfedu.entity.Depart;
import com.qfedu.entity.User;
import com.qfedu.entity.UserRole;
import com.qfedu.service.IDepartService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Service
public class DepartService implements IDepartService {

	@Autowired
	private IDepartDao departDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserRoleDao userRoleDao;
	
	@Autowired
	private IStaffDao staffDao;
	
	
	@Override
	public List<Depart> findAll() {
		List<Depart> list = null;
		
		try {
			list = departDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public PageBean<Depart> findDeptByPage(int page, int limit) {
		Map<String, Object> map = new HashMap<>();
		
		PageBean<Depart> pageInfo = new PageBean<>();

		pageInfo.setPageSize(limit);

		pageInfo.setCurrentPage(page);
		
		// 获取表中的记录总数
		int count = departDao.count();
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
		List<Depart> list = departDao.findByIndexAndSize(map);
		pageInfo.setPageInfos(list);

		return pageInfo;
	}

	//添加部门
	@Override
	public void add(Depart dept, String no) {
		
		
		try {
			//判断能否添加
			JsonBean bean = this.addJudge(dept, no);
			//code不为一抛出异常信息code为一执行添加
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			departDao.add(dept);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//修改部门
	@Override
	public void update(Depart dept, String no) {
		
		try {
			//判断能否修改
			JsonBean bean = this.updateJudge(dept, no);
			//code不为一抛出异常信息code为一执行添加
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			departDao.update(dept);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//删除部门
	@Override
	public void delete(int id, String no) {
		
		
		try {
			departDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//判断能否添加
	@Override
	public JsonBean addJudge(Depart dept, String no) {
		User user = null;

		try {
			//判断是否为空
			if (dept == null) {
				return JsonUtil.JsonBeanS(0, "部门为空，添加失败");
			}
			
			//判读是否是管理员
			user = userDao.findByNo(no);
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
			
			//判断是否存在同名部门
			//通过部门名称查找所有同名部门
			List<Depart> deptList =departDao.findByName(dept.getName());
			//通过名称搜索到的权限个数大于0则存在同名不能添加
			if (deptList.size() > 0) {
				return JsonUtil.JsonBeanS(0, "存在同名部门");
			}
			
			//名称不能为空
			if (dept.getName() == null || dept.getName().equals("")) {
				return JsonUtil.JsonBeanS(0, "部门名称不能为空");
			}
			//时间不能为空
			if (dept.getCreatetime() == null) {
				return JsonUtil.JsonBeanS(0, "创建时间不能为空");
			}
			
			//判断通过 可以添加
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	
	//判断能否添加
		@Override
		public JsonBean updateJudge(Depart dept, String no) {
			User user = null;

			try {
				//判断是否为空
				if (dept == null) {
					return JsonUtil.JsonBeanS(0, "部门为空，修改失败");
				}
				
				//判读是否是管理员
				user = userDao.findByNo(no);
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
				
				//判断是否存在同名部门
				//通过部门名称查找所有同名部门
				List<Depart> deptList =departDao.findByName(dept.getName());
				//通过名称搜索到的权限个数大于0则存在同名不能添加
				Depart dept1 = departDao.findById(dept.getId());
				if (dept1.getName().equals(dept.getName())) {
					//如果修改的名字与原名相同则查到的权限个数大于一个是判定存在同名
					if (deptList.size() > 1) {
						return JsonUtil.JsonBeanS(0, "存在同名部门");
					}
				} else {
					//如果修改的名字与原名不相同则查到的权限个数大于0个是判定存在同名
					if (deptList.size() > 0) {
						return JsonUtil.JsonBeanS(0, "存在同名部门");
					}
				}
				
				//名称不能为空
				if (dept.getName() == null || dept.getName().equals("")) {
					return JsonUtil.JsonBeanS(0, "部门名称不能为空");
				}
				
				//判断通过 可以添加
				return JsonUtil.JsonBeanS(1, null);
				
			} catch (Exception e) {
				e.printStackTrace();
				return JsonUtil.JsonBeanS(0, e.getMessage());
			}
			
		}
		
		//判断能否删除
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
				
				//判断要删除的部门是否存在
				Depart dept = departDao.findById(id);
				if (dept == null) {
					return JsonUtil.JsonBeanS(0, "部门不存在，删除失败");
				}
				
				//判断该部门是否还有员工
				int count = staffDao.Dcount(dept.getId());
				if (count != 0) {
					return JsonUtil.JsonBeanS(0, "有员工在该部门上班");
				}
				
				//判断通过 可以添加
				return JsonUtil.JsonBeanS(1, null);
				
			} catch (Exception e) {
				e.printStackTrace();
				return JsonUtil.JsonBeanS(0, e.getMessage());
			}
		}

		//通过部门名查部门
		@Override
		public Depart findByDname(String name) {
			Depart dept = null;
			
			try {
				dept = departDao.findByDname(name);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return dept;
		}
	
}
