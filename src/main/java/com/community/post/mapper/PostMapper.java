package com.community.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.community.post.domain.Post;

@Mapper
public interface PostMapper {
	
	public List<Map<String,Object>> selectPostListTest();
	
	public List<Post> selectPostList();
	
	public int insertPost(Post post);
	
	public Post selectPostByPostId (int postId);
}

