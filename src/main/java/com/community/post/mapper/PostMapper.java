package com.community.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.community.post.domain.Post;

@Mapper
public interface PostMapper {
	
	public List<Map<String,Object>> selectPostListTest();
	
	public List<Post> selectPostList();
	
	public int insertPost(Post post);
	
	public void insertImagePath(
			@Param("userId") int userId,
			@Param("postId") int postId, 
			@Param("imagePath") String imagePath);
	
	public Post selectPostByPostId (int postId);
}

