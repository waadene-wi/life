package com.wy.life.rpstory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wy.life.entity.Project;

public interface ProjectRepository extends JpaRepository<Project,Long> {
	public boolean existsByUserId(Long userId);
}
