package com.community.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.community.post.domain.Post;

@Mapper
public interface PostMapper {
	
	public List<Map<String,Object>> selectPostListTest();
	
	public List<Post> selectPostList(
			@Param("standardId") Integer standardId,
			@Param("direction") String direction,
			@Param("limit") int limit);
			
	
	public int selectPostIdAsSort(String sort);
	
	public int insertPost(Post post);
	
	public Post selectPostByPostId (int postId);
	
	public void PostDelete(int postId);
	
	public void updatePostByPostId(
			@Param("postId") int postId,
			@Param("subject") String subject,
			@Param("content") String content);

}

