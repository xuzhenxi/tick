package com.qfedu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfedu.entity.Check;
import com.qfedu.entity.User;
import com.qfedu.service.ICheckService;
import com.qfedu.service.IUserService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Controller
public class CheckController {
	
	@Autowired
	private ICheckService checkService;
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/processnolist.do")
	@ResponseBody
	public Map<String, Object> findByPage(int page, int limit) {
		PageBean<Check> pageInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			//查找分页数据
			pageInfo = checkService.findCheckByPage(page, limit, null);
			List<Check> list = pageInfo.getPageInfos();
			
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
	
	@RequestMapping("/processlist.do")
	@ResponseBody
	public Map<String, Object> findByPage(int page, int limit, HttpSession session) {
		
		String no = (String) session.getAttribute("no");
		
		PageBean<Check> pageInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			//查找分页数据
			pageInfo = checkService.findCheckByPage(page, limit, no);
			List<Check> list = pageInfo.getPageInfos();
			
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
	
	@RequestMapping("/processadd.do")
	@ResponseBody
	public JsonBean add(Check check, HttpSession session) {
		String no = (String) session.getAttribute("no");
		User u = userService.findByNo(no);
		
		try {
			check.setFlag(1);
			check.setStartname(u.getName());
			check.setStartno(no);
			checkService.add(check);
			return JsonUtil.JsonBeanS(1, "已提交请假条");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}

	@RequestMapping("/")
	@ResponseBody
	public JsonBean update(Check check) {
		
		
		try {
			checkService.update(check);
			return JsonUtil.JsonBeanS(1, "已提交请假条");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
}
