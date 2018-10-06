package com.qfedu.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.dao.IUserDao;
import com.qfedu.dao.IUserRoleDao;
import com.qfedu.entity.User;
import com.qfedu.entity.UserRole;
import com.qfedu.service.IUserRoleService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;

@Service
public class UserRoleService implements IUserRoleService{
	
	@Autowired
	private IUserRoleDao userRoleDao;
	
	@Autowired
	private IUserDao userDao;

	@Override
	public void update(Integer[] rids, Integer id, String no) {

		UserRole userRole = new UserRole();
		
		//修改判断
		JsonBean bean = updateJudge(no);
		if (bean.getCode() != 1) {
			throw new RuntimeException((String) bean.getMsg());
		}
		
		try {
			//删除所有用户权限
			userRoleDao.delete(id);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if (rids != null) {
			for (Integer rid : rids) {
				userRole.setUid(id);
				userRole.setRid(rid);
				try {
					userRoleDao.add(userRole);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	//修改判断
	@Override
	public JsonBean updateJudge(String no) {
		User user = userDao.findByNo(no);
		//获取当前登录用角色id
		List<UserRole> roleList = userRoleDao.findByUserId(user.getId());
		int flag = 0;
		for (UserRole userRole2 : roleList) {
			if(userRole2.getRid() == 1) {
				flag = 1;
			}
			
		}
		
		if (flag != 1) {
			return JsonUtil.JsonBeanS(0, "没有管理员权限");
		}
		
		return JsonUtil.JsonBeanS(1, null);
	}


}
