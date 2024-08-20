package com.community.onePost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.community.onePost.bo.OnePostBO;
import com.community.onePost.domain.PostView;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/onePost")
@Controller
public class OnePostController {

	@Autowired
	OnePostBO onePostBO;
	
	/**
	 * 글 상세 화면
	 * @param postId
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/post-detail-view")
	public String postDetailView(
			@RequestParam("postId") int postId,
			Model model,
			HttpSession session) {
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			return "user/sign-in-view";
		}
		
		PostView postView = onePostBO.generatePostView(postId, userId);
			
		// model에 담기
		model.addAttribute("postView", postView);
		
		// 화면 이동
		return "post/postDetail";
	}
	
	/**
	 * 글 수정 화면
	 * @param postId
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/post-update-view")
	public String postUpdateView(
			@RequestParam("postId") int postId,
			Model model,
			HttpSession session) {
		
		Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/sign-in-view"; // 비 로그인 시 로그인 페이지로
        }
		
		PostView postUpdateView = onePostBO.generatePostView(postId, userId);
			
		// model에 담기
		model.addAttribute("postUpdateView", postUpdateView);
		
		// 화면 이동
		return "post/postUpdate";
	}
}
