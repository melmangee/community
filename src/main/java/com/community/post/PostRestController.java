package com.community.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.community.like.bo.LikeBO;
import com.community.post.bo.PostBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {

	@Autowired
	PostBO postBO;

	@Autowired
	LikeBO likeBO;
	
	/**
	 * 글쓰기 API
	 * @param subject
	 * @param content
	 * @param files
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam(value = "content" , required = false) String content,
			@RequestParam(value = "files", required = false) MultipartFile[] files,
			HttpSession session) {
		
		// 글쓴이 번호를 session에서 꺼낸다.
		int userId = (int)session.getAttribute("userId"); 
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// DB insert
		postBO.addPost(userId, userLoginId ,subject, content, files);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
		
	}
	
	/**
	 * 글 삭제 API
	 * @param postId
	 * @param session
	 * @return
	 */
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("postId") int postId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인을 다시 해주세요.");
			return result;
		}
		
		postBO.deletePostByPostIdUserId(postId, userId);
		result.put("code", 200);
		result.put("result", "성공");
		
		return result;
	}
	
}
