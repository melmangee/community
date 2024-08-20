package com.community.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.community.post.bo.PostBO;
import com.community.post.domain.Post;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {

	@Autowired
	PostBO postBO;

	/**
	 * 글 목록 화면
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/post-list-view")
	public String postListView(
			@RequestParam(value = "orderby", required = false) String orderby
			, HttpSession session
			, Model model) {

		// 로그인 여부 확인
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			// 비 로그인
			return "redirect:/user/sign-in-view";
		}

		// DB 조회 - 글 목록
		List<Post> postList;  
		if ("likeRank".equals(orderby)) { // 
			postList = postBO.getPostListByLikeRank(); // 인기순 정렬
		} else if ("recentRank".equals(orderby)) {
	        postList = postBO.getPostList(); // '최신순'
	    } else {
	        postList = postBO.getPostList(); // 기본 정렬 최신순
	    }
		
		// 모델에 담기
		 model.addAttribute("postList", postList);

		return "post/postList";
	}

	/**
	 * 글쓰기 화면
	 * 
	 * @return
	 */
	@GetMapping("/post-create-view")
	public String postCreateView() {
		return "post/postCreate";
	}

}
