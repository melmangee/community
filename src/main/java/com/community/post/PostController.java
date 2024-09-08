package com.community.post;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
			@RequestParam(value = "prevId", required = false) Integer prevIdParam,
			@RequestParam(value = "nextId", required = false) Integer nextIdParam,
			HttpSession session,
			Model model) {

		// 로그인 여부 확인
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			// 비 로그인
			return "redirect:/user/sign-in-view";
		}
           
		// DB 조회 - 글 목록
		
		List<Post> postList = postBO.getPostList(prevIdParam, nextIdParam);
	    int prevId = 0;
	    int nextId = 0;
	    if (postList.isEmpty() == false) { // 글목록이 비어있지 않을 때 페이징 정보 세팅
	    	prevId = postList.get(0).getId(); // 첫번째 칸 id
	    	nextId = postList.get(postList.size() - 1).getId();
	    	
	    	// 이전 방향의 끝인가? 그러면 0
	    	// prevId와 테이블의 제일 큰 아이디와 같으면 끝페이지
	    	if (postBO.isPrevLastPage(prevId)) {
	    		prevId = 0;
	    	}
	    	
	    	// 다음 방향의 끝인가? 그러면 0
	    	// nextId와 테이블의 작은 숫자와 같으면 끝
	    	if (postBO.isNextLastPage(nextId)) {
	    		nextId = 0;
	    	}
	    }
	   
		// 모델에 담기
	    model.addAttribute("prevId", prevId);
	    model.addAttribute("nextId", nextId);
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
