package com.community.onePost.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.community.comment.bo.CommentBO;
import com.community.comment.domain.CommentView;
import com.community.image.bo.ImageBO;
import com.community.image.domain.Image;
import com.community.like.bo.LikeBO;
import com.community.onePost.domain.PostView;
import com.community.post.bo.PostBO;
import com.community.post.domain.Post;
import com.community.user.bo.UserBO;
import com.community.user.entity.UserEntity;

@Service
public class OnePostBO {

	@Autowired
	private PostBO postBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;
	
	@Autowired
	private ImageBO imageBO;
	
	// input: postId, userId
	// output: PostView
	public PostView generatePostView(int postId, int userId) {
		PostView postView = new PostView();
		
		// 글 조회
		Post post = postBO.getPostByPostId(postId);
		postView.setPost(post);
		
		// 글쓴이
		UserEntity user = userBO.getUserEntityById(post.getUserId());
		postView.setUser(user);
		
		// 이미지 N 개
		List<Image> imageList = imageBO.getImageByPostId(postId);
		postView.setImageList(imageList);

		// 댓글 N 개
		List<CommentView> commentList = commentBO.getCommentByPostId(postId);
		postView.setCommentList(commentList);
		
		// 좋아요 개수
		int likeCount= likeBO.getLikeCountByPostId(postId) ;
		postView.setLikeCount(likeCount);
		
		// 좋아요 여부	채우기		
		Boolean isLike = likeBO.filledLikeByPostIdUserId(postId, userId);
		postView.setFilledLike(isLike);
		
		return postView;
	}
	
}
