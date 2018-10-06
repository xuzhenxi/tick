package com.qfedu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfedu.entity.Course;
import com.qfedu.entity.Grade;
import com.qfedu.service.IGradeService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Controller
public class GradeController {

	@Autowired
	private IGradeService gradeService;
	
	@RequestMapping("/gradepage.do")
	@ResponseBody
	public Map<String, Object> findByPage(int page, int limit) {
		PageBean<Grade> pageInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			//查找分页数据
			pageInfo = gradeService.findGradeByPage(page, limit);
			List<Grade> list = pageInfo.getPageInfos();
			
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
	
	/**
	 * 添加班级
	 * @param grade 班级 
	 * @return
	 */
	@RequestMapping("/gradeadd.do")
	@ResponseBody
	public JsonBean add(Grade grade, HttpSession session) {
		String no = (String) session.getAttribute("no");
		
		try {
			//判断能否删除
			JsonBean bean = gradeService.addJudge(grade, no);
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			
			gradeService.add(grade, no);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	
	@RequestMapping("/gradeupdate.do")
	@ResponseBody
	public JsonBean update(Grade grade, HttpSession session) {
		String no = (String) session.getAttribute("no");
		
		try {
			//判断能否修改
			JsonBean bean = gradeService.updateJudge(grade, no);
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			gradeService.update(grade, no);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(1, e.getMessage());
		}
	}
	
	@RequestMapping("/gradedelete.do")
	@ResponseBody
	public JsonBean delete(int id, HttpSession session) {
		String no = (String) session.getAttribute("no");
		
		try {
			//判断能否删除
			JsonBean bean = gradeService.deleteJudge(id, no);
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			gradeService.delete(id, no);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	@RequestMapping("/gradeall.do")
	@ResponseBody
	public JsonBean findAll() {
		List<Grade> list = null;
		
		try {
			list = gradeService.findAll();
			return JsonUtil.JsonBeanS(1, list);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(1, e.getMessage());
		}
		
		
	}
	
	@RequestMapping("/readgrade.do")
	@ResponseBody
	public JsonBean readGrade() {
		int c = 0;
		
		try {
			c = gradeService.count();
			return JsonUtil.JsonBeanS(1, c);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, "加载中...");
		}
		
		
	}
}
