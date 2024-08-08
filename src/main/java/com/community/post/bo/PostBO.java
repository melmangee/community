package com.community.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.community.post.domain.Post;
import com.community.post.mapper.PostMapper;

@Service
public class PostBO {

	@Autowired
	private PostMapper postMapper;
	
	/**
	 * 전체글 조회
	 * @return
	 */
	// input: X
	// output: List<Post>
	public List<Post> getPostList() {
		return postMapper.selectPostList();
	}
	
	// 이미지 말고 글 생성?
	// input: userId, subject, content
	// output: Post
	public Post addPost(int userId, String subject, String content) {
		return postMapper.insertPost(userId, subject, content);	
	}
	
}
