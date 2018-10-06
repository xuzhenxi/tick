package com.qfedu.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qfedu.entity.Depart;
import com.qfedu.entity.Grade;
import com.qfedu.entity.Staff;
import com.qfedu.entity.Student;
import com.qfedu.service.IGradeService;
import com.qfedu.service.IStaffService;
import com.qfedu.service.IStudentService;
import com.qfedu.util.ExcelUtils;
import com.qfedu.util.JsonUtil;
import com.qfedu.vo.JsonBean;
import com.qfedu.vo.PageBean;

@Controller
public class StudentController {

	@Autowired
	private IStudentService studentService;
	
	@Autowired
	private IGradeService gradeService;
	
	@Autowired
	private IStaffService staffService;
	
	@RequestMapping("/studentpage.do")
	@ResponseBody
	public Map<String, Object> findByPage(int page, int limit) {
		PageBean<Student> pageInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			//查找分页数据
			pageInfo = studentService.findStuByPage(page, limit);
			List<Student> list = pageInfo.getPageInfos();
			
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
	 * 添加学员
	 * @param stu
	 * @param session
	 * @return
	 */
	@RequestMapping("/studentadd.do")
	@ResponseBody
	public JsonBean add(Student stu, HttpSession session) {
		String no = (String) session.getAttribute("no");
		
		try {
			JsonBean bean = studentService.addJudge(stu, no);
			
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			
			studentService.add(stu, no);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	/**
	 * 修改学员
	 * @param stu
	 * @param session
	 * @return
	 */
	@RequestMapping("/studentupdate.do")
	@ResponseBody
	public JsonBean update(Student stu, HttpSession session) {
		String no = (String) session.getAttribute("no");
		
		try {
			JsonBean bean = studentService.updateJudge(stu, no);
			
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			studentService.update(stu, no);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
	
	/**
	 * 删除学员
	 * @param no
	 * @param session
	 * @return
	 */
	@RequestMapping("/studentdelete.do")
	@ResponseBody
	public JsonBean delete(String no, HttpSession session) {
		String uno = (String) session.getAttribute("no");
		
		try {
			
			JsonBean bean = studentService.deleteJudge(no, uno);
			
			if (bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			studentService.delete(no, uno);
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
	@RequestMapping("/studentno.do")
	@ResponseBody
	public JsonBean findLastStuNo() {
		try {
			Student stu = studentService.findLastStu();
			if(stu != null) {
				String no = stu.getNo();
				no = no.substring(3);
				no = "1" + no;
			    no = "q" + (Integer.parseInt(no) + 1);
			    no = "stu" + no.substring(2);
			    System.out.println(no);
				return JsonUtil.JsonBeanS(1, no);
			} else {
				return JsonUtil.JsonBeanS(1, "stu000001");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, "获取账号时出现问题！");
		}
		
	}
	
	//批量上传的操作
	@RequestMapping("/studentbatch.do")
	@ResponseBody
	// 必须是@RequestParam MultipartFile
	public JsonBean upload(MultipartFile file){


		try {
			// 获取上传的文件的文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的输入流
			InputStream inputStream = file.getInputStream();

			// 解析exel文件，进行导入操作

			List<Map<String, Object>>list = new ArrayList<>();
			boolean ret = ExcelUtils.isXls(fileName);
			// 根据不同的后缀，创建不同的对象
			Workbook workBook = null;
			if(ret){
				workBook = new HSSFWorkbook(inputStream);// xls
			}else{
				workBook = new XSSFWorkbook(inputStream);// xlsx
			}

			Sheet sheet = workBook.getSheetAt(0);
			int num = sheet.getLastRowNum();

			NumberFormat nf = NumberFormat.getInstance();
			//這個for循環是行数，行循环
			for(int i = 1; i <= num; i++){
				Map<String, Object> map = new HashMap<>();
				Row row = sheet.getRow(i);
				//这个是第一列，0就是第一列，工号no
				Cell cell = row.getCell(0);
				if(cell != null){
					//1.1
					map.put("no", cell.getStringCellValue());
					//System.out.println(cell.getStringCellValue());

				}
				//这是第二列 姓名name
				cell = row.getCell(1);
				if(cell != null){
					//1.2
					map.put("name", cell.getStringCellValue());
					//System.out.println(cell.getStringCellValue());
				}
				//第三列 部门department
				cell = row.getCell(2);
				if(cell != null){
					//1.2

					map.put("gid", cell.getStringCellValue());

				}
				//4列 sex
				cell = row.getCell(3);
				if(cell != null){
					//
					map.put("sex", cell.getStringCellValue());
					//System.out.println(cell.getStringCellValue());
				}

				//5列 phone
				cell = row.getCell(4);
				if(cell != null){
					//1.2
					
					map.put("phone", nf.format(cell.getNumericCellValue()));
					//System.out.println(cell.getNumericCellValue());
				}
				//6列qq
				cell = row.getCell(5);
				if(cell != null){
					//1.2
					
					map.put("qq", nf.format(cell.getNumericCellValue()));
					//System.out.println(cell.getNumericCellValue());
				}

				//7email
				cell = row.getCell(6);
				if(cell != null){
					//1.2
					map.put("email", cell.getStringCellValue());
					//System.out.println(cell.getNumericCellValue());
				}
				//8身份证号
				cell = row.getCell(7);
				if(cell != null){
					//1.2
					map.put("cardno", nf.format(cell.getNumericCellValue()));
					//System.out.println(cell.getNumericCellValue());
				}

				//9毕业学校
				cell = row.getCell(8);
				if(cell != null){
					//1.2
					map.put("school", cell.getStringCellValue());
					//System.out.println(cell.getDateCellValue());
				}

				//10学历
				cell = row.getCell(9);
				if(cell != null){
					//1.2
					map.put("education", cell.getStringCellValue());
					//System.out.println(cell.getDateCellValue());
				}
				//11招生老师
				cell = row.getCell(10);
				if(cell != null){
					//1.2
					map.put("introno", cell.getStringCellValue());
					//System.out.println(cell.getDateCellValue());
				}
				//12出生日期
				cell = row.getCell(11);
				if(cell != null){
					//1.2
					map.put("birthday", cell.getDateCellValue());
					//System.out.println(cell.getDateCellValue());
				}

				//13入学日期
				cell = row.getCell(12);
				if(cell != null){
					//1.2
					map.put("createdate", cell.getDateCellValue());
					//System.out.println(cell.getDateCellValue());
				}
				//循环一次提交一次···,下一次就又覆盖掉了，在重新提交···

				list.add(map);

			}


			workBook.close();


			for (Map<String, Object> map : list) {
				String name =(String) map.get("gid");
				Grade grade = gradeService.findByName(name);
				int gid = grade.getId();
				map.put("gid",gid );
				//获取招生老师姓名
				String staffName = (String)map.get("introno");
				Staff staff = staffService.findByName(staffName);
				String introno = staff.getNo();
				map.put("introno", introno);
				//然后将数据添加入数据库中
				try {
					studentService.addStu(map);
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
	
	@RequestMapping("/studentcount.do")
	@ResponseBody
	public JsonBean studentCount() {
		
		int c = 0;
		try {
			c = studentService.count();
			return JsonUtil.JsonBeanS(1, c);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, "加载中...");
		}
	}
}
