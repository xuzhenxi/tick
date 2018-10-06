package com.qfedu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfedu.entity.Role;
import com.qfedu.entity.User;
import com.qfedu.service.ILoginlogService;
import com.qfedu.service.IUserService;
import com.qfedu.util.IpGet;
import com.qfedu.util.JsonUtil;
import com.qfedu.util.MD5Utils;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Controller
public class UserController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ILoginlogService loginlogService;
	
	@RequestMapping("/login")
	public String login(String no, String password, HttpSession session, HttpServletRequest request){
		
		UsernamePasswordToken token = new UsernamePasswordToken(no, MD5Utils.getMd5(password, 1));
		// 设置 记住我=true
//		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();

		try {
			subject.login(token);
			session.setAttribute("no", no);
			IpGet ipget = new IpGet();
			//获取ip地址
			String ip = ipget.getIpAddr(request);
			//添加登录日志
			loginlogService.add(ip, no);
			return "redirect:index.html";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return "redirect:login.html";
		}
		
	}
	
	
	/**
	 * 分页查询用户
	 * @param page 页码
	 * @param limit 每页数据
	 * @return 返回用户
	 */
	@RequestMapping("/findUserByPage")
	@ResponseBody
	public Map<String, Object> findAllUser(int page, int limit, String no, Integer flag) {
		PageBean<User> pageInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> datamap = null;
		if (flag == null) {
			flag = 0;
		}
		try {
			//查找分页数据
			pageInfo = userService.findUserByPage(page, limit, no, flag);
			List<User> list = pageInfo.getPageInfos();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			for (User user : list) {
				datamap = new HashMap<String, Object>();
				datamap.put("id", user.getId());
				datamap.put("no", user.getNo());
				datamap.put("name", user.getName());
				datamap.put("flag", user.getFlag());
				datamap.put("headphoto", user.getHeadphoto());
				
				List<Role> roleList = user.getRole();
				
				String r = "";
				String rids = "";
				for (Role role : roleList) {
					if (!role.getInfo().equals("")) {
						//将用户所有角色组合成字符串
						r += '[' + role.getInfo() + ']';
						//将用户所有角色id组合成字符串以','分隔
						rids += role.getId() + ",";
					}
				}
				datamap.put("rids", rids);
				datamap.put("role", r);
				
				data.add(datamap);
			}
			//设置状态（查找成功）
			map.put("code", 0);
			//设置提示信息
			map.put("msg", "");
			//设置用户总数
			map.put("count", pageInfo.getCount());
			//设置用户信息
			map.put("data", data);
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
	 * 删除用户
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping("/userdel.do")
	@ResponseBody
	public JsonBean delete(int id, HttpSession session) {
		
		String no = (String) session.getAttribute("no");
		try {
			JsonBean bean = userService.deleteJudge(id, no);
			if(bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			
			userService.delete(id, no);
			return JsonUtil.JsonBeanS(1000, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
		
	}
	
	//注销
	@RequestMapping("/loginOut")
	@ResponseBody
	public JsonBean loginOut( HttpSession session, HttpServletRequest request){

		Subject subject = SecurityUtils.getSubject();

		try {
			subject.logout();

			return JsonUtil.JsonBeanS(1, "退出成功");
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}

	}
	
	@RequestMapping("/puser.do")
	@ResponseBody
	public JsonBean pUser(HttpSession session) {
		
		String no = (String) session.getAttribute("no");
		User u = null;
		try {
			u = userService.findByNo(no);
			
			return JsonUtil.JsonBeanS(1, u);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
		
}
