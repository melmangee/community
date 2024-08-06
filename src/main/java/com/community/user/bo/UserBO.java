package com.community.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.community.user.entity.UserEntity;
import com.community.user.repository.UserRepository;

@Service
public class UserBO {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 로그인 아이디 조회
	 * @param loginId
	 * @return
	 */
	// input: loginId
	// output: UserEntity or null
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);		
	}
	
	/**
	 * 로그인 아이디 비밀번호 조회
	 * @param loginId
	 * @param password
	 * @return
	 */
	// input: loginId, password
	// output: UserEntity or null
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {
		return userRepository.findByLoginIdAndPassword(loginId, password);
	}
	
	
	/**
	 * 로그인 이메일 조회, 아이디 찾기
	 * @param email
	 * @return
	 */
	// input: email
	// output: UserEntity or null
	public UserEntity getUserEntityByEmail(String email) {
		return userRepository.findByEmail(email);		
	}
	
	/**
	 * 회원가입 
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	// input: String loginId, String password, String name, String email
	// output: UserEntity
	public UserEntity addUser(String loginId, String password, String name, String email) {
		return userRepository.save(UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build());
	}
	
	
}
