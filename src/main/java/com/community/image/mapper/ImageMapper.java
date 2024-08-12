package com.community.image.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ImageMapper {

	public void insertImagePath(
			@Param("userId") int userId,
			@Param("postId") int postId, 
			@Param("imagePath") String imagePath);
}
