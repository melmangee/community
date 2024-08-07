package com.community.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component // spring bean
public class PermissionIntercepter implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		
		// 요청 url path를 꺼낸다.
		String uri = request.getRequestURI();
		log.info("[@@@@@@@@ preHandle] uri:{}", uri);
		
		return true; // 컨트롤러 수행 true
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView mav) {
		
		// view, model 객체가 있다는 건 html이 해석되기 전
		log.info("[$$$$$ postHandle]");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			Exception ex) {
		
		// html이 완성된 상태
		log.info("[#### afterCompletion");
	}
}
