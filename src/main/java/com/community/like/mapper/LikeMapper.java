package com.community.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
	
		public int selectLikeCountByPostIdOrUserId(
				@Param("postId") int postId,
				@Param("userId") int userId);
		
		/**
		 * 좋아요 개수
		 * @param postId
		 * @return
		 */
		public int selectLikeCountByPostId(int postId);

		public void insertLike(
				@Param("postId") int postId,
				@Param("userId") int userId);
		
		public void deleteLikeByPostIdUserId(
				@Param("postId") int postId,
				@Param("userId") int userId);
		
		public void deleteLikeByPostId(int postId);
		
}