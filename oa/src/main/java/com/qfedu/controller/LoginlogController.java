package com.qfedu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfedu.entity.Loginlog;
import com.qfedu.entity.User;
import com.qfedu.service.ILoginlogService;
import com.qfedu.vo.PageBean;

@Controller
public class LoginlogController {

	@Autowired
	private ILoginlogService loginlogService;
	
	/**
	 * 分页查询用户
	 * @param page 页码
	 * @param limit 每页数据
	 * @return 返回用户
	 */
	@RequestMapping("/loginloglist")
	@ResponseBody
	public Map<String, Object> findAllUser(int page, int limit, HttpSession session) {
		PageBean<Loginlog> pageInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		String no = (String) session.getAttribute("no");
		try {
			//查找分页数据
			pageInfo = loginlogService.findLoginlogByPage(page, limit, no);
			//设置状态（查找成功）
			map.put("code", 0);
			//设置提示信息
			map.put("msg", "");
			//设置用户总数
			map.put("count", pageInfo.getCount());
			//设置用户信息
			map.put("data", pageInfo.getPageInfos());
			//返回分页信息
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			//返回非成功状态
			map.put("code", 1);
			return map;
		}
	}
}
