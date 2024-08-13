package com.community.comment.domain;

import com.community.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

//댓글 1개
@ToString
@Data
public class CommentView {

	// 댓글 1개
	private Comment comment;

	// 댓글쓴이
	private UserEntity user;
}
