package com.community.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {

	/**
	 * 회원가입 화면
	 * @return
	 */
	@GetMapping("/sign-up-view")
	public String signUpView() {
		return "user/signUp";
	}
	
	/**
	 * 로그인 화면
	 * @return
	 */
	@GetMapping("/sign-in-view")
	public String signInView() {
		return "user/signIn";
	}
	
	/**
	 * 로그아웃 API
	 * @param session
	 * @return
	 */
	@RequestMapping("/sign-out")
	public String signOut(HttpSession session) {
		// session 내용 비움
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");		
		
		// 로그인 페이지 이동
		return "redirect:/user/sign-in-view";
	}

	/**
	 * 아이디 비밀번호 찾기 화면
	 * @return
	 */
	@GetMapping("/find-view")
	public String findView() {
		return "user/find";
	}
	
}
