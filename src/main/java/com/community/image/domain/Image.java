package com.community.image.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Image {
	private int id;
	private int userId;
	private int postId;
	private String imagePath;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
