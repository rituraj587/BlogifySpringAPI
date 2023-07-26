package com.riturajprojectpractise.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

	
	private List<PostDto> content;
	
	private int pageNumber;
	private int pageSize;
	private long totalElements;			// It represents Total number of records
	private int totalPages;
	
	private boolean lastPage;			// last page if true
}
