package com.community.like.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Like {
	private int postId;
	private int userId;
	private LocalDateTime createdAt;
}
