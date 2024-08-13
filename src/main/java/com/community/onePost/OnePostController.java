package com.community.onePost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.community.onePost.bo.OnePostBO;
import com.community.onePost.domain.PostView;

@RequestMapping("/post")
@Controller
public class OnePostController {

	@Autowired
	OnePostBO onePostBO;
	
	/**
	 * 글 상세 화면
	 * @param postId
	 * @param model
	 * @return
	 */
	@GetMapping("/post-detail-view")
	public String postDetailView(
			@RequestParam("postId") int postId,
			Model model) {
		
		PostView postView = onePostBO.generatePostView(postId);
			
		// model에 담기
		model.addAttribute("postView", postView);
		
		// 화면 이동
		return "post/postDetail";
	}
}
