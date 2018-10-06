package com.qfedu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfedu.service.IUserRoleService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
@Controller
public class UserRoleController {

	@Autowired
	private IUserRoleService userRoleService;
	
	/**
	 * 修改用户权限
	 * @param rids 给与的权限
	 * @param id 用户id
	 * @return 
	 */
	@RequestMapping("/userroleedit.do")
	@ResponseBody
	public JsonBean updateRole(Integer[] rids, Integer uid, HttpSession session) {
		
		//获取用户账号
		String no = (String) session.getAttribute("no");
		
		try {
			JsonBean bean = userRoleService.updateJudge(no);
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			userRoleService.update(rids, uid, no);
			return JsonUtil.JsonBeanS(1000, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}

}
