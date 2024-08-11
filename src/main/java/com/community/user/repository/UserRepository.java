package com.community.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.community.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	// JPQL
	public UserEntity findByLoginId(String loginId); // 로그인 아이디(중복) 조회
	
	public UserEntity findByEmail(String email); // 이메일(중복) 조회
	
	public UserEntity findByLoginIdAndPassword(String loginId, String password); // 로그인 아이디 비밀번호 조회
	
}
