package com.community.post.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Post {
	private int id;
	private int userId;
	private String subject;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
