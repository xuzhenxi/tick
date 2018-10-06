package com.qfedu.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.dao.IStaffDao;
import com.qfedu.dao.IUserDao;
import com.qfedu.dao.IUserRoleDao;
import com.qfedu.entity.Staff;
import com.qfedu.entity.User;
import com.qfedu.entity.UserRole;
import com.qfedu.service.IStaffService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Service
public class StaffService implements IStaffService {

	@Autowired
	private IStaffDao staffDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserRoleDao userRoleDao;
	
	//分页查询员工
	@Override
	public PageBean<Staff> findStaffByPage(int page, int limit, String name, String sex) {
		if ( sex != null && !sex.equals("")) {
		} else {
			sex = null;
		}
		
		if (name != null && !name.equals("")) {
			name = "%" + name + "%";
		} else {
			name = null;
		}
		Map<String, Object> map = new HashMap<>();
		
		PageBean<Staff> pageInfo = new PageBean<>();

		pageInfo.setPageSize(limit);

		pageInfo.setCurrentPage(page);

		map.put("sex", sex);
		map.put("name", name);
		// 获取表中的记录总数
		int count = staffDao.count(map);
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
		List<Staff> list = staffDao.findByIndexAndSize(map);
		pageInfo.setPageInfos(list);

		return pageInfo;
	}

	//查询最新添加的员工
	@Override
	public Staff findLastStaff() {
		Staff staff = null;
		
		try {
			staff = staffDao.findLastStaff();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return staff;
	}

	//添加新员工
	@Override
	public void add(Staff staff, String uno) {
		
		try {
			JsonBean bean = this.addJudge(staff, uno);
			
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			
			staffDao.add(staff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	//修改员工
	@Override
	public void update(Staff staff, String uno) {
		
		try {
			JsonBean bean = this.updateJudge(staff, uno);
			
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			
			staffDao.update(staff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//删除员工
	@Override
	public void delete(String no, String uno) {
		
		try {
			
			JsonBean bean = this.deleteJudge(no, uno);
			
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			staffDao.delete(no);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//添加判断
	@Override
	public JsonBean addJudge(Staff staff, String uno) {
		
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
			//判断是否为空
			if (staff == null) {
				return JsonUtil.JsonBeanS(0, "员工为空，添加失败");
			}
			//部门必须选
			if (staff.getDid() == -1) {
				return JsonUtil.JsonBeanS(0, "所属部门必须填");
			}
			//名称不能为空
			if (staff.getName() == null || staff.getName().equals("")) {
				return JsonUtil.JsonBeanS(0, "员工姓名必须填");
			}
			//时间不能为空
			if (staff.getCreatedate() == null) {
				return JsonUtil.JsonBeanS(0, "创建时间必须填");
			}
			//手机号不能为空
			if (staff.getPhone() == null) {
				return JsonUtil.JsonBeanS(0, "手机号必须填");
			}
			//手机号不能为空
			if (staff.getPhoto() == null) {
				return JsonUtil.JsonBeanS(0, "必须上传照片");
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
	public JsonBean updateJudge(Staff staff, String uno) {
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
			//判断是否为空
			if (staff == null) {
				return JsonUtil.JsonBeanS(0, "员工为空，添加失败");
			}
			//部门必须选
			if (staff.getDid() == -1) {
				return JsonUtil.JsonBeanS(0, "所属部门必须填");
			}
			//名称不能为空
			if (staff.getName() == null || staff.getName().equals("")) {
				return JsonUtil.JsonBeanS(0, "员工姓名必须填");
			}
			//时间不能为空
			if (staff.getCreatedate() == null) {
				return JsonUtil.JsonBeanS(0, "创建时间必须填");
			}
			//手机号不能为空
			if (staff.getPhone() == null) {
				return JsonUtil.JsonBeanS(0, "手机号必须填");
			}
			//手机号不能为空
			if (staff.getPhoto() == null) {
				return JsonUtil.JsonBeanS(0, "必须上传照片");
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
	public JsonBean deleteJudge(String no, String uno) {
		try {
			//判断是否存在
			Staff staff = staffDao.findByNo(no);
			if (staff == null) {
				return JsonUtil.JsonBeanS(0, "员工不存在或已被删除！请刷新后重试");
			}
			
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
			
			
			//判断通过 可以添加
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
	}

	@Override
	public List<Staff> findAll() {
		List<Staff> list = null;
		
		try {
			list = staffDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	//添加读取excel中的员工信息
	@Override
	public void addStaff(Map<String, Object> map) {
		Staff staff = new Staff();
		
		staff.setNo((String)map.get("no"));
		staff.setName((String)map.get("name"));
		staff.setDid((Integer)map.get("did"));
		staff.setSex((String)map.get("sex"));
		staff.setPhone((String)map.get("phone"));
		staff.setEmail((String)map.get("email"));
		staff.setQq((String)map.get("qq"));
		staff.setCreatedate((Date)map.get("createdate"));
		try {
			staff.setFlag(1);
			staffDao.add(staff);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
	}

	@Override
	public Staff findByName(String name) {
		Staff staff = null;
		
		try {
			staff = staffDao.findByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return staff;
	}

	@Override
	public int count(Map<String, Object> map) {
		
		int count = 0;
		try {
			count = staffDao.count(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return count;
	}

}
