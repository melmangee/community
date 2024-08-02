package com.community.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {

	@GetMapping("/sign-in-view")
	public String signInView() {
		return "user/signIn";
	}
	
}
