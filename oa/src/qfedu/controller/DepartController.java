package com.qfedu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfedu.entity.Depart;
import com.qfedu.service.IDepartService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Controller
public class DepartController {

	@Autowired
	private IDepartService departService;
	
	/**
	 * 查询所有部门
	 * @return 
	 */
	@RequestMapping("/departall.do")
	@ResponseBody
	public JsonBean findAll() {
		List<Depart> list = null;
		
		try {
			list = departService.findAll();
			return JsonUtil.JsonBeanS(1, list);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, "加载部门信息失败！");
		}
		
	}
	
	/**
	 * 分页查询部门
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/departpage.do")
	@ResponseBody
	public Map<String, Object> findByPage(int page, int limit) {
		PageBean<Depart> pageInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			//查找分页数据
			pageInfo = departService.findDeptByPage(page, limit);
			List<Depart> list = pageInfo.getPageInfos();
			
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
			map.put("msg", "加载部门列表失败");
			return map;
		}
	}
	
	/**
	 * 添加部门
	 * @param dept
	 * @return
	 */
	@RequestMapping("/departadd.do")
	@ResponseBody
	public JsonBean add(Depart dept, HttpSession session) {
		
		String no = (String) session.getAttribute("no");
		
		try {
			//判断能否添加
			JsonBean bean = departService.addJudge(dept, no);
			//code不为一抛出异常信息code为一执行添加
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			
			dept.setFlag(1);
			departService.add(dept, no);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	/**
	 * 修改部门
	 * @param dept
	 * @return
	 */
	@RequestMapping("/departupdate.do")
	@ResponseBody
	public JsonBean update(Depart dept, HttpSession session) {
		
		String no = (String) session.getAttribute("no");
		
		try {
			
			//判断能否修改
			JsonBean bean = departService.updateJudge(dept, no);
			//code不为一抛出异常信息code为一执行修改
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			dept.setFlag(1);
			departService.update(dept, no);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	/**
	 * 删除部门
	 * @param id 部门id
	 * @return
	 */
	@RequestMapping("/departdelete.do")
	@ResponseBody
	public JsonBean delete(int id, HttpSession session) {
		
		String no = (String) session.getAttribute("no");
		
		try {
			//判断能否删除
			JsonBean bean = departService.deleteJudge(id, no);
			//code不为一抛出异常信息code为一执行删除
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			departService.delete(id, no);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
}
