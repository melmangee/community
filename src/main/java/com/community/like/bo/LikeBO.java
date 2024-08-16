package com.community.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.community.like.mapper.LikeMapper;

@Service
public class LikeBO {

	@Autowired
	private LikeMapper likeMapper;
	
	// input: postId, userId   
	// output: X
	public void likeToggle(int postId, int userId) {
		// 조회
		int count = likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
		
		// 여부 => 삭제 or 추가
		if (count > 0) {
			// 있으면 삭제
			likeMapper.deleteLikeByPostIdUserId(postId, userId);
		} else {
			// 없으면 추가
			likeMapper.insertLike(postId, userId);
		}
	}
	
	/**
	 * 글에 좋아요 여부 int 로
	 * @param postId
	 * @param userId
	 * @return
	 */
	// input: postId, userId
	// output: int
	public int getLikeCountByPostIdUserId(int postId, int userId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
	}
	
	// 좋아요 채울지 여부
	public boolean filledLikeByPostIdUserId(int postId, Integer userId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) == 1 ? true : false;
	}
	
	/**
	 * 좋아요 개수
	 * @param postId
	 * @return
	 */
	//input: postId
	//output: int 개수
	public int getLikeCountByPostId(int postId) {
		return likeMapper.selectLikeCountByPostId(postId);
	}
}
