package com.community.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.community.common.EncryptUtils;
import com.community.user.bo.UserBO;
import com.community.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {
		
	@Autowired
	private UserBO userBO;
	
	/**
	 *  아이디 중복확인 API
	 * @param loginId
	 * @return
	 */
	@PostMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		// DB 조회
		UserEntity user = userBO.getUserEntityByLoginId(loginId); // 존재 중복
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		if (user != null) { // 중복
			result.put("is_duplicated_id", true);			
		} else {
			result.put("is_duplicated_id", false);
		}
		
		// 응답값
		return result;
	}
	
	
	/**
	 * 이메일 중복 확인 API
	 * @param email
	 * @return
	 */
	@PostMapping("/is-duplicated-email")
	public Map<String, Object> isDuplicatedEmail(
			@RequestParam("email") String email) {
		
		// DB 조회
		UserEntity user = userBO.getUserEntityByEmail(email); // 존재 중복
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		if (user != null) { // 중복
			result.put("is_duplicated_email", true);			
		} else {
			result.put("is_duplicated_email", false);
		}
		
		// 응답값
		return result;
	}
	
	/**
	 * 회원가입 API
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String,Object>signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email")String email) {
		
		// password sha256 알고리즘으로 만들기
		//aaaa -> 61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4
		String hashedPassword = EncryptUtils.ShaEncoder(password);
		
		// db insert
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, email);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			result.put("code", 200);
			result.put("result", "성공");			
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패했습니다.");
		}
		return result;
		
	}
	
	/**
	 * 로그인 API
	 * @param loginId
	 * @param password
	 * @return
	 */
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password")String password,
			HttpSession session) {
		
		// password 변환
		String hashedPassword = EncryptUtils.ShaEncoder(password);
		
		// DB 조회
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, hashedPassword);
		
		// 로그인 처리
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) { // 로그인 성공
			// 세션에 유저 정보 담기
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			
			result.put("code", 200);
			result.put("result", "성공");			
		} else { // 로그인 실패
			result.put("code", 500);
			result.put("error_message", "존재하지 않는 사용자 입니다.");
		}
		
		return result;
		
	}
	
}
