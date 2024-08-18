package com.community.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.community.common.FileManagerService;
import com.community.image.bo.ImageBO;
import com.community.post.domain.Post;
import com.community.post.mapper.PostMapper;

@Service
public class PostBO {

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private ImageBO imageBO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	
	/**
	 * 전체글 조회
	 * 
	 * @return
	 */
	// input: X
	// output: List<Post>
	public List<Post> getPostList() {
		return postMapper.selectPostList();
	}
	
	/**
	 * 글 상세 글 번호로 글 조회
	 * @param postId
	 * @return
	 */
	// input: postId
	// output: Post or null
	public Post getPostByPostId (int postId) {
		return postMapper.selectPostByPostId(postId);
	}

	/**
	 * 게시물 생성 및 이미지 파일 저장
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
			for(MultipartFile file : files) { // 파일 쪼개기
				if (!file.isEmpty()) { // 파일이 비어있지 않은지 확인
					String imagePath = fileManagerService.uploadFile(file, loginId); // 비어있지 않으면 파일 업로드
					
					// 이미지 DB에 저장
					imageBO.insertImagePath(userId, postId, imagePath);
				}
			}
		}
	}
}
