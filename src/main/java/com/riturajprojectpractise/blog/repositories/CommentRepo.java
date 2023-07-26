package com.riturajprojectpractise.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riturajprojectpractise.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment , Integer>{

}
