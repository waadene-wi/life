package com.wy.life.service;

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
			MyException.byMsg("验证码错误");
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
	public void sendCode(String phone) {

		PhoneCode phoneCode = new PhoneCode();
		phoneCode.setPhone(phone);
		phoneCode.setVerifyCode(getCode(phone));
		
		//调用第三方短信接口
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
		Random r = new Random();
		String charValue = "";
		for (int i = 0; i < 6; i++) {
			char c = (char) (r.nextInt(0 - 9) + '0');
			charValue += String.valueOf(c);
		}	
		return charValue;
		
	}

	@Override
	public void signUp(Project project, User user) throws MyException{
		
		boolean exist = projectRepository.existsByUserId(user.getId());
		if(exist) {
			MyException.byMsg("已报名项目");
		}
		project.setUserId(user.getId());
		
		projectRepository.save(project);
	}

}
