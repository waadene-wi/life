package com.wy.life.service;

import com.wy.life.entity.Project;
import com.wy.life.entity.User;
import com.wy.life.exception.MyException;

public interface UserService {
	User login(String phone, String verCode) throws MyException;

	void sendCode(String phone);

	void signUp(Project project, User user) throws MyException;
}
