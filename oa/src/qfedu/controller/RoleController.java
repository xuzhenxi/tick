package com.qfedu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfedu.entity.Authority;
import com.qfedu.entity.Role;
import com.qfedu.entity.User;
import com.qfedu.service.IAuthorityService;
import com.qfedu.service.IRoleService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Controller
public class RoleController {
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IAuthorityService autyService;

	private Object attribute;
	
	/**
	 * 分页查询用户
	 * @param page 页码
	 * @param limit 每页数据
	 * @return 返回用户
	 */
	@RequestMapping("/rolepage.do")
	@ResponseBody
	public Map<String, Object> findAllUser(int page, int limit, String info, String name) {
		PageBean<Role> pageInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Authority> listAuty = null;
		
		try {
			//查找分页数据
			pageInfo = roleService.findRoleByPage(page, limit, info, name);
			List<Role> list = pageInfo.getPageInfos();
			for (Role role : list) {
				listAuty = role.getAuty();
				//创建集合保存权限id
				List<Integer> listr = new ArrayList<Integer>();
				for (Authority auty : listAuty) {
					if(auty != null) {
						listr.add(auty.getId()); 
					}
				}
				//将保存权限id的数组放入role中
				role.setAids(listr);
			}
			
			//设置状态（查找成功）
			map.put("code", 0);
			//设置提示信息
			map.put("msg", "");
			//设置用户总数
			map.put("count", pageInfo.getCount());
			//设置用户信息
			map.put("data", list);
			//返回分页信息
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			//返回非成功状态
			map.put("code", 1);
			return map;
		}
	}
	
	@RequestMapping("/roleall.do")
	@ResponseBody
	public JsonBean findAll() {

		List<Role> list = null;
		try {
			list = roleService.findAll();
			return JsonUtil.JsonBeanS(0, list);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(1, e.getMessage());
		}
		
	}
	
	/**
	 * 删除角色
	 * @param rid 角色id
	 * @return
	 */
	@RequestMapping("/roledel.do")
	@ResponseBody
	public JsonBean delete(Integer id, HttpSession session) {
		//获取当前登录用户账号
		String no = (String) session.getAttribute("no");
		try {
			//删除判断， 判断是否符合删除条件
			JsonBean bean = roleService.deleteJudge(id, no);
			//不符合返回错误信息
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			
			roleService.delete(id, no);
			return JsonUtil.JsonBeanS(1000, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
		
	}

}
