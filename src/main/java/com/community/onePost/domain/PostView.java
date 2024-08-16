package com.community.onePost.domain;

import java.util.List;

import com.community.comment.domain.CommentView;
import com.community.post.domain.Post;
import com.community.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class PostView {
	// 글 1개
	private Post post;

	// 글쓴이 정보
	private UserEntity user;
	
	// 댓글 N개
	private List<CommentView> commentList;

	// 이미지
	
	// 좋아요 N개
	private int likeCount;
	
	// 좋아요를 누른지 여부
	private boolean filledLike;

}
