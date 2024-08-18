package com.community.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.community.comment.domain.Comment;
import com.community.comment.domain.CommentView;
import com.community.comment.mapper.CommentMapper;
import com.community.user.bo.UserBO;
import com.community.user.entity.UserEntity;

@Service
public class CommentBO {

	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private UserBO userBO;
	
	/**
	 * 댓글 추가
	 * @param postId
	 * @param userId
	 * @param content
	 */
	// input: postId, userId, content
	// output: X
	public void addComment(int postId, int userId, String content) {
		commentMapper.insertComment(postId, userId, content);
	}
	
	/**
	 * 댓글 목록
	 * @param postId
	 * @return
	 */
	public List<CommentView> getCommentByPostId(int postId) {
		List<CommentView> commentViewList = new ArrayList<>();
		
		// 댓글들 가져옴
		List<Comment> commentList = commentMapper.selectCommentByPostId(postId);
		
		// 반복문 순회 => Comment -> CommentView => list에 담음
		for (Comment comment : commentList) {
			
			CommentView commentView = new CommentView();
			
			// 댓글 1개
			commentView.setComment(comment);
			
			// 댓글쓴이
			UserEntity user = userBO.getUserEntityById(comment.getUserId());
			commentView.setUser(user);
				
			// 댓글 뷰 리스트 추가
			commentViewList.add(commentView);
		}
			return commentViewList;
	}
	
	/**
	 * 댓글 삭제
	 * @param id
	 */
	public void deleteCommentById(int id) {
		commentMapper.deleteCommentById(id);
	}
	
	public void deleteCommentsByPostId(int postId) {
		commentMapper.deleteCommentsByPostId(postId);
	}
	
}
