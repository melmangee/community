package com.community.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.community.post.domain.Post;

@Mapper
public interface PostMapper {
	
	public List<Map<String,Object>> selectPostListTest();
	
	public List<Post> selectPostList();
	
	public Post insertPost(
			@Param("userId") int userId, 
			@Param("subject") String subject, 
			@Param("content") String content);
	
}

