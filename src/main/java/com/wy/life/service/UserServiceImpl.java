package com.wy.life.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wy.life.entity.PhoneCode;
import com.wy.life.entity.Project;
import com.wy.life.entity.Role;
import com.wy.life.entity.User;
import com.wy.life.exception.MyException;
import com.wy.life.rpstory.PhoneCodeRepository;
import com.wy.life.rpstory.ProjectRepository;
import com.wy.life.rpstory.UserRepository;
import com.wy.life.utils.Xioo;
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private PhoneCodeRepository phoneCodeRepository;
	@Resource
	private UserRepository userRepository;
	@Resource
	private ProjectRepository projectRepository;

	@Override
	public User login(String phone, String verCode) throws MyException {

		PhoneCode phoneCode = phoneCodeRepository.findByPhoneAndVerifyCode(phone, verCode);
		if (phoneCode == null) {
			throw MyException.byMsg("验证码错误");
		}
		//第一次创建用户
		User user = userRepository.findByPhone(phone);
		if(user == null) {
			user = new User();
			user.setPhone(phone);
			user.setRole(Role.NORMAL);
			userRepository.save(user);
		}
		return user;
	}

	@Override
	public void sendCode(String phone) throws MyException{
		
		
		PhoneCode lastCode =  phoneCodeRepository.findTopByPhoneOrderByCreateDateDesc(phone);
		Calendar nowTime = Calendar.getInstance();
		nowTime.setTime(lastCode.getCreateDate());
		nowTime.add(Calendar.MINUTE, 1);
		if(lastCode != null && nowTime.getTime().after(new Date())) {
			throw MyException.byMsg("多次发送验证码间隔为一分钟");
		}

		PhoneCode phoneCode = new PhoneCode();
		phoneCode.setPhone(phone);
		String code = getCode(phone);
		phoneCode.setVerifyCode(code);
		
		//调用第三方短信接口
		try {
			String result = Xioo.send(phone, code);
			String resultCode = result.substring(0,result.indexOf(","));
			if(!resultCode.equals("0")) {
				throw MyException.byMsg("发送验证码失败");
			}
		} catch (IOException e) {
			throw MyException.byMsg("发送验证码失败");
		}
		phoneCodeRepository.save(phoneCode);
	}
	
	private String getCode(String phone) {
		String code = randomInt();
		PhoneCode phoneCode = phoneCodeRepository.findByPhoneAndVerifyCode(phone, code);
		
		if(phoneCode != null) {
			return getCode(phone);
		}
		return code;
	}

	/**
	 * 生成随机数
	 * 
	 */
	public String randomInt() {
		Random random = new Random();
		String result="";
		for (int i=0;i<6;i++)
		{
			result+=random.nextInt(10);
		}
		return result;
	}

	@Override
	public void signUp(Project project, User user) throws MyException{
		
		boolean exist = projectRepository.existsByUserId(user.getId());
		if(exist) {
			throw MyException.byMsg("已报名项目");
		}
		project.setUserId(user.getId());
		
		projectRepository.save(project);
	}

}
