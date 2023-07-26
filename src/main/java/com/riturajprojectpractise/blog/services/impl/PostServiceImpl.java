package com.riturajprojectpractise.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riturajprojectpractise.blog.entities.Category;
import com.riturajprojectpractise.blog.entities.Post;
import com.riturajprojectpractise.blog.entities.User;
import com.riturajprojectpractise.blog.exceptions.ResourceNotFoundException;
import com.riturajprojectpractise.blog.payloads.PostDto;
import com.riturajprojectpractise.blog.payloads.PostResponse;
import com.riturajprojectpractise.blog.repositories.CategoryRepo;
import com.riturajprojectpractise.blog.repositories.PostRepo;
import com.riturajprojectpractise.blog.repositories.UserRepo;
import com.riturajprojectpractise.blog.services.PostService;
@Service
public class PostServiceImpl implements PostService {

    private static final Post Null = null;

	@Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    
    
    //create posts
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User ", "User id", userId));

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id ", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);

        return this.modelMapper.map(newPost, PostDto.class);
    }

    
    //update posts
    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

  //      Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
    //    post.setCategory(category);


        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    
    // delete posts
    @Override
    public void deletePost(Integer postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

        this.postRepo.delete(post);

    }
    
    //Get all posts

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize , String sortBy ,String sortDir) {
    	
    	Sort sort =null;
    	if(sortDir.equalsIgnoreCase("asc"))
    	{
    		sort =Sort.by(sortBy).ascending();
    	}
    	else
    	{
    		sort =Sort.by(sortBy).descending();
    	}
    	
    	Pageable p =PageRequest.of(pageNumber, pageSize ,sort);
    	
    	
       Page<Post> pagePost = this.postRepo.findAll(p);
       List<Post> allPosts = pagePost.getContent();
       List<PostDto> postsDto = allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        
       PostResponse postResponse = new PostResponse();
       
       postResponse.setContent(postsDto);
       postResponse.setPageNumber(pagePost.getNumber());
       postResponse.setPageSize(pagePost.getNumber());
       postResponse.setTotalElements(pagePost.getTotalElements());
       postResponse.setTotalPages(pagePost.getTotalPages());
       postResponse.setLastPage(pagePost.isLast());
       
       return postResponse;
    }

    
    //get posts by user
    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    
    //get posts by category
    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {

        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        List<Post> posts = this.postRepo.findByCategory(cat);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
    }

    
    //get posts by user
    
    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
    	
    	
   
    	

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User ", "userId ", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
        
    }
    
    
    
    //search

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.searchByTitle("%" + keyword + "%");
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

}
