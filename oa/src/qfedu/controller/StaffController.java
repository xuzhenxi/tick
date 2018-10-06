package com.qfedu.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qfedu.entity.Depart;
import com.qfedu.entity.Staff;
import com.qfedu.service.IDepartService;
import com.qfedu.service.IStaffService;
import com.qfedu.util.ExcelUtils;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Controller
public class StaffController {
	
	@Autowired
	private IStaffService staffService;
	
	@Autowired
	private IDepartService deptService;
	
	
	/**
	 * 分页查询用户
	 * @param page 页码
	 * @param limit 每页数据
	 * @return 返回用户
	 */
	@RequestMapping("/staffpage.do")
	@ResponseBody
	public Map<String, Object> findAllUser(int page, int limit, String name, String sex) {
		PageBean<Staff> pageInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			//查找分页数据
			pageInfo = staffService.findStaffByPage(page, limit, name, sex);
			List<Staff> list = pageInfo.getPageInfos();
			
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
	 * 添加新员工
	 * @param staff 新员工
	 * @return
	 */
	@RequestMapping("/staffadd.do")
	@ResponseBody
	public JsonBean add(Staff staff, HttpSession session) {
		//获取当前登录账号
		String uno = (String) session.getAttribute("no");
		
		try {
			JsonBean bean = staffService.addJudge(staff, uno);
			
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			
			staff.setFlag(1);
			staffService.add(staff, uno);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
		
	}
	
	/**
	 * 获得新员工工号用于注册新员
	 * @return 
	 */
	@RequestMapping("/staffno.do")
	@ResponseBody
	public JsonBean findLastStaffNo() {
		try {
			Staff staff = staffService.findLastStaff();
			if(staff != null) {
				String no = staff.getNo();
				no = no.substring(2);
				no = "1" + no;
			    no = "q" + (Integer.parseInt(no) + 1);
			    no = "qf" + no.substring(2);
			    System.out.println(no);
				return JsonUtil.JsonBeanS(1, no);
			} else {
				return JsonUtil.JsonBeanS(1, "qf000001");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, "获取账号时出现问题！");
		}
		
	}
	
	/**
	 * 员工图片上传
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/photoupload.do", method=RequestMethod.POST)
	@ResponseBody
	public JsonBean uploadImg(MultipartFile file) {
		// 获取上传的文件的文件名
		String fileName = file.getOriginalFilename();
		
		// 获取uuid
		String uuid = UUID.randomUUID().toString().replace("-", "");
		// 生成新的文件名
		String newFileName = fileName.substring(0, fileName.lastIndexOf(".")) + 
				"_" + uuid + fileName.substring(fileName.lastIndexOf("."));
		
		//设置时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//格式化当前时间
		String dateDir = sdf.format(new Date());
		// 将上传的文件保存到指定的路径
		String path = "D:/upload/" + dateDir;
		
		File pathFile = new File(path);
		if (!pathFile.exists()) {
			pathFile.mkdir();
		}
		
		String p = dateDir + "/" + newFileName;
		
		File file1 = new File(path, newFileName);
		
		try {
			file.transferTo(file1);
			return JsonUtil.JsonBeanS(1000, p);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, "图片上传失败");
		}
		
	}
	
	/**
	 * 修改员工信息
	 * @param staff 员工
	 * @return
	 */
	@RequestMapping("/staffupdate.do")
	@ResponseBody
	public JsonBean update(Staff staff, HttpSession session) {
		//获取当前登录账号
		String uno = (String) session.getAttribute("no");
		
		try {
			JsonBean bean = staffService.updateJudge(staff, uno);
			
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			
			staffService.update(staff, uno);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	/**
	 * 删除员工
	 * @param id 员工id
	 * @return 返回员工
	 */
	@RequestMapping("/staffdelete.do")
	@ResponseBody
	public JsonBean delete(String no, HttpSession session) {
		//获取当前登录账号
		String uno = (String) session.getAttribute("no");
		
		try {
			JsonBean bean = staffService.deleteJudge(no, uno);
			
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			
			staffService.delete(no, uno);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	/**
	 * 查询所有员工
	 * @return
	 */
	@RequestMapping("/staffall.do")
	@ResponseBody
	public JsonBean findAll() {
		
		List<Staff> list = null;
		try {
			list = staffService.findAll();
			return JsonUtil.JsonBeanS(1, list);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(1, e.getMessage());
		}
		
		
	}
	
	//批量上传的操作
	@RequestMapping("/staffbatch.do")
	@ResponseBody
	// 必须是@RequestParam MultipartFile
	public JsonBean upload(MultipartFile file){


		try {
			// 获取上传的文件的文件名
			String filename = file.getOriginalFilename();
			// 获取文件的输入流
			InputStream inputStream = file.getInputStream();

			// 解析exel文件，进行导入操作
			List<Map<String,Object>> list = ExcelUtils.importExcel(filename, inputStream);
			for (Map<String, Object> map : list) {
				String name =(String) map.get("did");
				Depart depart = deptService.findByDname(name);
				int did = depart.getId();
				map.put("did",did );
				//然后将数据添加入数据库中
				try {
					staffService.addStaff(map);
				} catch (Exception e) {
					e.printStackTrace();
					return JsonUtil.JsonBeanS(0, e.getMessage());
				}
			}
			return JsonUtil.JsonBeanS(1, null);

		} catch (IOException e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}

	}

	@RequestMapping("/staffcount.do")
	@ResponseBody
	public JsonBean staffCount() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			int count = staffService.count(map);
			return JsonUtil.JsonBeanS(1, count);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, "加载中...");
		}
		
		
		
	}

	

}
