package com.qfedu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.dao.IAuthorityDao;
import com.qfedu.dao.IRoleauthorityDao;
import com.qfedu.dao.IUserDao;
import com.qfedu.dao.IUserRoleDao;
import com.qfedu.entity.Authority;
import com.qfedu.entity.Role;
import com.qfedu.entity.RoleAuthority;
import com.qfedu.entity.User;
import com.qfedu.entity.UserRole;
import com.qfedu.service.IAuthorityService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Service
public class AuthorityService implements IAuthorityService {

	@Autowired
	private IAuthorityDao autyDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserRoleDao userRoleDao;
	
	@Autowired
	private IRoleauthorityDao roleAutyDao;
	
	@Override
	public List<Authority> findTitleByNo(String no) {
		
		List<Authority> list = null;
		
		try {
			list = autyDao.findTitleByNo(no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	//分页查询权限
	@Override
	public PageBean<Authority> findAuthorityByPage(int page, int limit) {
		PageBean<Authority> pageInfo = new PageBean<>();

		pageInfo.setPageSize(limit);

		pageInfo.setCurrentPage(page);

		// 获取表中的记录总数
		int count = autyDao.count();
		pageInfo.setCount(count);
		// 计算总页数
		if(count % pageInfo.getPageSize() == 0){
			pageInfo.setTotalPage(count / pageInfo.getPageSize());
		}else{
			pageInfo.setTotalPage(count / pageInfo.getPageSize()+ 1);
		}
		// 根据页码计算分页查询时的开始索引
		int index = (page - 1) * pageInfo.getPageSize();
		Map<String, Object> map = new HashMap<>();
		map.put("index", index);
		map.put("size", pageInfo.getPageSize());
		List<Authority> list = autyDao.findByIndexAndSize(map);
		pageInfo.setPageInfos(list);

		return pageInfo;
	}


	//查询所有权限
	@Override
	public List<Authority> findAllTitle() {
		List<Authority> list = null;
		
		try {
			list = autyDao.findAllTitle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	//按父级id查询权限
	@Override
	public List<Authority> findByParentId(int parentId) {
		
		List<Authority> list = null;
		
		try {
			list = autyDao.findByParentId(parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	//添加权限
	@Override
	public void add(Authority auty, String no) {
		
		
		try {
			//判断能否添加返回code=1能添加
			JsonBean bean = addJudge(auty, no);
			
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			
			autyDao.add(auty);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//删除权限
	@Override
	public void delete(int aid, String no) {
		
		try {
			//判断能否删除返回code=1能删除
			JsonBean bean = deleteJudge(aid, no);
			
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			
			autyDao.delete(aid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//修改权限
	@Override
	public void update(Authority auty, String no) {
		
		
		try {
			//判断能否修改返回code=1能修改
			JsonBean bean = updateJudge(auty, no);
			
			if (bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			autyDao.update(auty);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//删除权限判断
	@Override
	public JsonBean deleteJudge(int aid, String no) {
		Authority auty = null;
		List<RoleAuthority> roleAutyList = null;
		User user = null;
		
		try {
			//查询是否存在
			auty = autyDao.findByAid(aid);
			if (auty == null) {
				return JsonUtil.JsonBeanS(0, "该权限不存在，删除失败");
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
			
			//根据权限id查询角色权限
			roleAutyList = roleAutyDao.findByAid(aid);
			if (roleAutyList.size() > 0) {
				return JsonUtil.JsonBeanS(0, "有职位正在使用该权限");
			}
			
			//判断通过 可以删除
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	@Override
	public JsonBean updateJudge(Authority auty, String no) {
		User user = null;
		
		try {
			//判断是否为空
			if (auty == null) {
				return JsonUtil.JsonBeanS(0, "权限为空，添加失败");
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
			//判断父目录是否是自己
			
			if (auty.getId() == auty.getParentId()) {
				return JsonUtil.JsonBeanS(0, "不能设自己为父目录");
			}
			//判断是否存在同名权限
			//通过权限名称查找所有权限
			List<Authority> autyList =autyDao.findByTitle(auty.getTitle());
			//通过要修改的权限id查找该权限的名称
			Authority auty1 = autyDao.findByAid(auty.getId());
			if (auty1.getTitle().equals(auty.getTitle())) {
				//如果修改的名字与原名相同则查到的权限个数大于一个是判定存在同名
				if (autyList.size() > 1) {
					return JsonUtil.JsonBeanS(0, "存在同名权限");
				}
			} else {
				//如果修改的名字与原名不相同则查到的权限个数大于0个是判定存在同名
				if (autyList.size() > 0) {
					return JsonUtil.JsonBeanS(0, "存在同名权限");
				}
			}
			//名称不能为空
			if (auty.getTitle() == null || auty.getTitle().equals("")) {
				return JsonUtil.JsonBeanS(0, "名称不能为空");
			}
			//路径不能为空
			if (auty.getAurl() == null || auty.getAurl().equals("")) {
				return JsonUtil.JsonBeanS(0, "路径不能为空");
			}
			
			
			//判断通过 可以修改
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	@Override
	public JsonBean addJudge(Authority auty, String no) {
		User user = null;
		
		try {
			//判断是否为空
			if (auty == null) {
				return JsonUtil.JsonBeanS(0, "权限为空，添加失败");
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
			
			//判断是否存在同名权限
			//通过权限名称查找所有权限
			List<Authority> autyList = autyDao.findByTitle(auty.getTitle());
			//通过名称搜索到的权限个数大于0则存在同名不能添加
			if (autyList.size() > 0) {
				return JsonUtil.JsonBeanS(0, "存在同名权限");
			}
			
			//名称不能为空
			if (auty.getTitle() == null || auty.getTitle().equals("")) {
				return JsonUtil.JsonBeanS(0, "名称不能为空");
			}
			//路径不能为空
			if (auty.getAurl() == null || auty.getAurl().equals("")) {
				return JsonUtil.JsonBeanS(0, "路径不能为空");
			}
			
			//判断通过 可以添加
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}

}
