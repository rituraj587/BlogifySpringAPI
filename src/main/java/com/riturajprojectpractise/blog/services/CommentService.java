package com.riturajprojectpractise.blog.services;

import com.riturajprojectpractise.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto, Integer postId);
	void deleteComment(Integer commentId);
}
