package com.wy.life.ctrl;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wy.life.entity.Project;
import com.wy.life.entity.User;
import com.wy.life.exception.MyException;
import com.wy.life.service.UserService;
import com.wy.life.utils.Result;
import com.wy.life.utils.ValidateCode;

@Controller
public class UserCtrl {
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/")
	public String index() {
		return "test";
	}
	@PostMapping("/login")
	@ResponseBody
	public Result login(String picCode,String phone,String verCode,HttpSession session) {
		
		try {
			User user = userService.login(phone, verCode);
			if(user != null) {
				session.setAttribute("login", user);
				return Result.success();
			}else {
				return Result.error("user为空");
			}
		} catch (MyException e) {
			return Result.error(e.getMessage());
		}
	}
	
	public void logout(HttpSession session) {
		session.removeAttribute("login");
	}
	
	@PostMapping("/send")
	@ResponseBody
	public Result send(@NotNull String picCode,@NotNull String phone,HttpSession session) {
		try {
			//check pic
			checkZym(picCode, session);
			userService.sendCode(phone);
		} catch (MyException e) {
			return Result.error(e.getMessage());
		}
		return Result.success();
	}
	@RequestMapping("/getZym")
	public void getZym(HttpServletResponse response,HttpSession session) throws Exception{
	    // 设置响应的类型格式为图片格式  
	    response.setContentType("image/jpeg");  
	    //禁止图像缓存。  
	    response.setHeader("Pragma", "no-cache");  
	    response.setHeader("Cache-Control", "no-cache");  
	    response.setDateHeader("Expires", 0);  

	    ValidateCode vCode = new ValidateCode(120,40,4,100);  
	    session.setAttribute("code", vCode.getCode());  
	    vCode.write(response.getOutputStream());  
	}
	
	private boolean checkZym(String zym,HttpSession session) throws MyException {
		String sessionCode = (String) session.getAttribute("code"); 
		if (!StringUtils.equalsIgnoreCase(zym, sessionCode)) {  //忽略验证码大小写  
			MyException.byMsg("图形验证码错误");  
		} 
		return true;
	}
	
	
	//报名
	@PostMapping("/signUp")
	@ResponseBody
	public Result signUp(Project project,@RequestParam("file") MultipartFile file, BindingResult result,HttpSession session) {
		
		
		//check login
		User user = (User)session.getAttribute("login");
		if(user == null) {
			return Result.error("未登录");
		}
		
		String fileName = file.getOriginalFilename();
		project.setFileName(fileName);
		try {
			project.setFileContent(file.getBytes());
		} catch (IOException e) {
			return Result.error("附件异常");
		}
		
		try {
			userService.signUp(project, user);
		} catch (MyException e) {
			return Result.error(e.getMessage());
		}
		return Result.success();
	}
}

