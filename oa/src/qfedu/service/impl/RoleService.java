package com.qfedu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.dao.IRoleDao;
import com.qfedu.dao.IRoleauthorityDao;
import com.qfedu.dao.IUserDao;
import com.qfedu.dao.IUserRoleDao;
import com.qfedu.entity.Role;
import com.qfedu.entity.RoleAuthority;
import com.qfedu.entity.User;
import com.qfedu.entity.UserRole;
import com.qfedu.service.IRoleService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Service
public class RoleService implements IRoleService{

	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserRoleDao userRoleDao;
	
	@Autowired
	private IRoleauthorityDao roleAutyDao;
	
	@Override
	public PageBean<Role> findRoleByPage(int page, int limit, String info, String name) {
		if (info != null && !info.equals("")) {
			info = "%" + info + "%";
		} else {
			info = null;
		}
		
		if (name != null && !name.equals("")) {
			name = "%" + name + "%";
		} else {
			name = null;
		}
		Map<String, Object> map = new HashMap<>();
		
		PageBean<Role> pageInfo = new PageBean<>();

		pageInfo.setPageSize(limit);

		pageInfo.setCurrentPage(page);

		map.put("info", info);
		map.put("name", name);
		// 获取表中的记录总数
		int count = roleDao.count(map);
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
		List<Role> list = roleDao.findByIndexAndSize(map);
		pageInfo.setPageInfos(list);

		return pageInfo;
	}

	@Override
	public List<Role> findAll() {
		List<Role> list = null;
		
		try {
			
			list = roleDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void delete(int rid, String no) {
		
		try {
			JsonBean bean = deleteJudge(rid, no);
			if(bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			roleDao.delete(rid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public JsonBean deleteJudge(int rid, String no) {
		Role role = null;
		List<UserRole> userRoleList = null;
		User user = null;
		
		try {
			//查询用户是否存在
			role = roleDao.findByRid(rid);
			if (role == null) {
				return JsonUtil.JsonBeanS(0, "该职位不存在，删除失败");
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
			//判断删除的是否管理员
			
			if (rid == 1) {
				return JsonUtil.JsonBeanS(0, "不能删除管理员职位");
			}
			//根据角色id查询用户角色
			userRoleList = userRoleDao.findByRid(rid);
			if (userRoleList.size() > 0) {
				return JsonUtil.JsonBeanS(0, "有用户正该职位任职");
			}
			
			//根据角色id查询角色权限
			List<RoleAuthority> roleAutyList = roleAutyDao.findByRid(rid);
			if (roleAutyList.size() > 0) {
				return JsonUtil.JsonBeanS(0, "该职位权限不为空");
			}
			
			//判断通过 可以删除
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	

}
