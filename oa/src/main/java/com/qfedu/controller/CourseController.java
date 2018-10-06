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
import com.qfedu.service.ICourseService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Controller
public class CourseController {

	@Autowired
	private ICourseService courseService;
	
	/**
	 * 分页查询学科
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/coursepage.do")
	@ResponseBody
	public Map<String, Object> findAllByPage(int page, int limit) {
		PageBean<Course> pageInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			//查找分页数据
			pageInfo = courseService.findCourseByPage(page, limit);
			List<Course> list = pageInfo.getPageInfos();
			
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
	 * 添加科目
	 * @param course
	 * @return
	 */
	@RequestMapping("/courseadd.do")
	@ResponseBody
	public JsonBean add(Course course, HttpSession session) {
		
		String no = (String) session.getAttribute("no");
		
		try {
			//判断能否添加
			JsonBean bean = courseService.addJudge(course, no);
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			courseService.add(course, no);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	/**
	 * 修改科目
	 * @param course
	 * @return
	 */
	@RequestMapping("/courseupdate.do")
	@ResponseBody
	public JsonBean update(Course course, HttpSession session) {
		String no = (String) session.getAttribute("no");
		try {
			//判断能否修改
			JsonBean bean = courseService.updateJudge(course, no);
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			courseService.update(course, no);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	/**
	 * 删除
	 * @param course
	 * @return
	 */
	@RequestMapping("/coursedelete.do")
	@ResponseBody
	public JsonBean delete(int id, HttpSession session) {
		
		String no = (String) session.getAttribute("no");
		try {
			//判断能否删除
			JsonBean bean = courseService.deleteJudge(id, no);
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			courseService.delete(id, no);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	@RequestMapping("/courseall.do")
	@ResponseBody
	public JsonBean findAll() {
		List<Course> list = null;
		try {
			list = courseService.findAll();
			return JsonUtil.JsonBeanS(1, list);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, null);
		}
	}
}
