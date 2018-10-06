package com.qfedu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.dao.IRoleauthorityDao;
import com.qfedu.dao.IUserDao;
import com.qfedu.dao.IUserRoleDao;
import com.qfedu.entity.RoleAuthority;
import com.qfedu.entity.User;
import com.qfedu.entity.UserRole;
import com.qfedu.service.IRoleAutyService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;

@Service
public class RoleAutyService implements IRoleAutyService {

	@Autowired
	private IRoleauthorityDao roleAutyDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserRoleDao userRoleDao;
	
	@Override
	public void update(Integer[] aids, Integer rid, String no) {
		
		RoleAuthority roleAuty = new RoleAuthority();
		
		try {
			JsonBean bean = this.updateJudge(no);
			if (bean.getCode() != 1) {
				throw new RuntimeException("你不是管理员");
			}
			//删除所有用户权限
			roleAutyDao.delete(rid);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if (aids != null) {
			for (Integer aid : aids) {
				roleAuty.setAid(aid);
				roleAuty.setRid(rid);
				try {
					roleAutyDao.add(roleAuty);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
	
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
