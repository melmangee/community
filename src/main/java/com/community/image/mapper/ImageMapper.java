package com.community.image.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.community.image.domain.Image;

@Mapper
public interface ImageMapper {

	public void insertImagePath(
			@Param("userId") int userId,
			@Param("postId") int postId, 
			@Param("imagePath") String imagePath);
	
	public List<Image>selectImageByPostId(int postId);
	
	public int deleteImageByPostId(int postId);
}
