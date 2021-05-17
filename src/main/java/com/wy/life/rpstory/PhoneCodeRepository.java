package com.wy.life.rpstory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wy.life.entity.PhoneCode;

public interface PhoneCodeRepository extends JpaRepository<PhoneCode,Long> {
	public PhoneCode findByPhoneAndVerifyCode(String phone,String code);
}
