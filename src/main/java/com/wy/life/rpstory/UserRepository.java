package com.wy.life.rpstory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wy.life.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	public User findByPhone(String phone);
}
