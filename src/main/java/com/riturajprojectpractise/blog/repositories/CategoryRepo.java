package com.riturajprojectpractise.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riturajprojectpractise.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
