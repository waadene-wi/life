package com.wy.life.rpstory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wy.life.entity.PhoneCode;

public interface PhoneCodeRepository extends JpaRepository<PhoneCode,Long> {
	
	
	PhoneCode findByPhoneAndVerifyCode(String phone,String code);
	
	
	PhoneCode findTopByPhoneOrderByCreateDateDesc(String phone);
}
