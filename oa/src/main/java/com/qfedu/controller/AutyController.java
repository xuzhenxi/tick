package com.qfedu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfedu.dao.IRoleauthorityDao;
import com.qfedu.entity.Authority;
import com.qfedu.entity.User;
import com.qfedu.service.IAuthorityService;
import com.qfedu.service.IRoleAutyService;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Controller
public class AutyController {

	@Autowired
	private IAuthorityService autyService;
	
	/**
	 * 查找左侧菜单栏
	 * @return 返回authority集合
	 */
	@RequestMapping("/leftTitle")
	@ResponseBody
	public JsonBean findTitleByNo(HttpSession session) {
		//获得域中的账号
		String no = (String) session.getAttribute("no");
		List<Authority> list = null;
		try {
			list = autyService.findTitleByNo(no);
			return JsonUtil.JsonBeanS(1, list);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	/**
	 * 分页查询用户
	 * @param page 页码
	 * @param limit 每页数据
	 * @return 返回用户
	 */
	@RequestMapping("/authoritylist.do")
	@ResponseBody
	public Map<String, Object> findAllUser(int page, int limit) {
		PageBean<Authority> pageInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			//查找分页数据
			pageInfo = autyService.findAuthorityByPage(page, limit);
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
	
	/**
	 * 查出所有权限
	 * @return
	 */
	@RequestMapping("/autyall.do")
	@ResponseBody
	public JsonBean findAllTitle() {
		List<Authority> list = null;
		
		try {
			list = autyService.findAllTitle();
			return JsonUtil.JsonBeanS(1, list);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(1, e.getMessage());
		}
		
	}
	
	/**
	 * 查询所有一级权限
	 * @return
	 */
	@RequestMapping("/authorityroot.do")
	@ResponseBody
	public JsonBean findAutyByParent0() {
		List<Authority> list = null;
		
		try {
			list = autyService.findByParentId(0);
			return JsonUtil.JsonBeanS(1, list);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	/**
	 * 添加权限
	 * @return
	 */
	@RequestMapping("/authorityadd.do")
	@ResponseBody
	public JsonBean add(Authority auty, HttpSession session) {
		
		//获取当前登录用户账号
		String no = (String) session.getAttribute("no");
		
		try {
			JsonBean bean = autyService.addJudge(auty, no);
			//如果返回bean.code不为1则判断失败不能添加返回错误信息
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			
			auty.setType(1);
			autyService.add(auty, no);
			return JsonUtil.JsonBeanS(1000, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtil.JsonBeanS(0, null);
		
	}
	
	/**
	 * 修改权限
	 * @param auty 权限
	 * @return
	 */
	@RequestMapping("/autyedit.do")
	@ResponseBody
	public JsonBean update(Authority auty, HttpSession session) {
		//获取当前登录用户账号
		String no = (String) session.getAttribute("no");
		
		try {
			JsonBean bean = autyService.updateJudge(auty, no);
			//如果返回bean.code不为1则判断失败不能修改返回错误信息
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			
			autyService.update(auty, no);
			return JsonUtil.JsonBeanS(1000, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtil.JsonBeanS(0, null);
		
	}
	
	/**
	 * 删除权限
	 * @param id 权限id
	 * @return
	 */
	@RequestMapping("/autydelete.do")
	@ResponseBody
	public JsonBean delete(Integer id, HttpSession session) {
		
		String no = (String) session.getAttribute("no");
		try {
			//判断能否删除返回code=1能删除
			JsonBean bean = autyService.deleteJudge(id, no);
			
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			
			autyService.delete(id, no);
			return JsonUtil.JsonBeanS(1000, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtil.JsonBeanS(0, null);
		
	}
}
