package com.community.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.community.comment.bo.CommentBO;
import com.community.common.FileManagerService;
import com.community.image.bo.ImageBO;
import com.community.image.domain.Image;
import com.community.like.bo.LikeBO;
import com.community.post.domain.Post;
import com.community.post.mapper.PostMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostBO {

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private ImageBO imageBO;

	@Autowired
	private FileManagerService fileManagerService;

	@Autowired
	private CommentBO commentBO;

	@Autowired
	private LikeBO likeBO;

	/**
	 * 전체글 조회(최신 순)
	 * 
	 * @return
	 */
	// input: X
	// output: List<Post>
	public List<Post> getPostList() {
		return postMapper.selectPostList();
	}

	/**
	 * 전체글 좋아요 순 조회
	 * @return
	 */
	public List<Post> getPostListByLikeRank() {
		return postMapper.selectPostListByLikeRank();
	}
	
	
	/**
	 * 글 상세 글 번호로 글 조회
	 * 
	 * @param postId
	 * @return
	 */
	// input: postId
	// output: Post or null
	public Post getPostByPostId(int postId) {
		return postMapper.selectPostByPostId(postId);
	}

	/**
	 * 게시물 생성 및 이미지 파일 저장
	 * 
	 * @param userId
	 * @param loginId
	 * @param subject
	 * @param content
	 * @param files
	 */
	// input: userId, subject, content
	// output: X
	public void addPost(int userId, String loginId, String subject, String content, MultipartFile[] files) {

		// 게시물 생성
		Post post = new Post();
		post.setUserId(userId);
		post.setSubject(subject);
		post.setContent(content);

		// 게시물 insert 하고 아이디 저장
		postMapper.insertPost(post);
		int postId = post.getId();

		// 이미지 파일이 있는경우 이미지 저장
		if (files != null) {
			for (MultipartFile file : files) { // 파일 쪼개기
				if (!file.isEmpty()) { // 파일이 비어있지 않은지 확인
					String imagePath = fileManagerService.uploadFile(file, loginId); // 비어있지 않으면 파일 업로드

					// 이미지 DB에 저장
					imageBO.insertImagePath(userId, postId, imagePath);
				}
			}
		}
	}

	/**
	 * 글삭제 API
	 * @param postId
	 * @param userId
	 */
	public void deletePostByPostIdUserId(int postId, int userId) {
		// 기존 글 가져오기
		Post post = postMapper.selectPostByPostId(postId);
		if (post == null) {
			log.error("[delete post] postId:{}, userId:{}", postId, userId);
			return;
		}

		// 이미지 삭제
		List<Image> imagePaths = imageBO.getImageByPostId(postId); // 게시물에 연결된 이미지 경로 가져오기
		if (imagePaths != null) { // imagePaths가 null일 경우 대비
			for (Image image : imagePaths) {
				String imagePath = image.getImagePath();
				if (imagePath != null && !imagePath.isEmpty()) { // null이 아니거나 또는 안 비어있는지
					fileManagerService.deleteFile(imagePath); // 파일 시스템에서 이미지 삭제
				}
			}
			// 데이터 베이스에서 이미지 삭제
			imageBO.deleteImageByPostId(postId);
		}

		// 댓글 삭제
		commentBO.deleteCommentsByPostId(postId);

		// 좋아요 삭제
		likeBO.deleteLikeByPostId(postId);

		// 글 삭제
		postMapper.PostDelete(postId);
	}

	/**
	 * 글 수정 API
	 * @param userId
	 * @param loginId
	 * @param postId
	 * @param subject
	 * @param content
	 * @param files
	 */
	// input: 파라미터 6개
	// output: X
	public void updatePostByPostId(int userId, String loginId, int postId, String subject, String content,
			MultipartFile[] files) {

		// 기존 글 가져오기
		Post post = postMapper.selectPostByPostId(postId);
		if (post == null) {
			log.error("[update post] postId:{}, userId:{}", postId, userId);
			return;
		}

		// 파일이 있으면
		String imagePath = null;

		if (files != null) {
			for (MultipartFile file : files) { // 파일 쪼개기
				if (!file.isEmpty()) { // 파일이 비어있지 않은지 확인
					imagePath = fileManagerService.uploadFile(file, loginId); // 비어있지 않으면 파일 업로드
				}
			}

				// 업로드 성공시 null 아니면 기존 이미지 제거
				List<Image> imagePaths = imageBO.getImageByPostId(postId); // 이미지 postId로 조회 후 imagePaths 저장
				if (imagePath != null && imagePaths != null) { // 업로드 성공, imagePaths가 null 이 아닐때
					// 폴더와 이미지 제거(서버에서)
					for (Image image : imagePaths) {
						String DeleteImagePath = image.getImagePath();
						if (DeleteImagePath != null && !DeleteImagePath.isEmpty()) { // null이 아니거나 또는 안 비어있는지
							fileManagerService.deleteFile(DeleteImagePath);
						}
					}
					// 데이터 베이스에서 이미지 삭제
					imageBO.deleteImageByPostId(postId);
				}
			// 파일 DB 업로드
			for (MultipartFile file : files) { // 파일 쪼개기
				if (!file.isEmpty()) { // 파일이 비어있지 않은지 확인
					String AfterImagePath = fileManagerService.uploadFile(file, loginId); // 비어있지 않으면 파일 업로드
					// 이미지
					imageBO.insertImagePath(userId, postId, AfterImagePath);
				}
			}
		}
		
		// 글 업로드
		postMapper.updatePostByPostId(postId, subject, content);
		
	}
}