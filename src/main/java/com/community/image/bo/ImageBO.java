package com.community.image.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.community.image.domain.Image;
import com.community.image.mapper.ImageMapper;

@Service
public class ImageBO {

	@Autowired
	private ImageMapper imageMapper;
	
	/**
	 * 이미지 저장
	 * @param userId
	 * @param postId
	 * @param imagePath
	 */
	public void insertImagePath(int userId, int postId, String imagePath) {
		imageMapper.insertImagePath(userId, postId, imagePath);
	}
	
	/**
	 * 이미지 조회 (글번호)
	 * @param postId
	 */
	 public List<Image> getImageByPostId(int postId) {
		return imageMapper.selectImageByPostId(postId);
	}
}
