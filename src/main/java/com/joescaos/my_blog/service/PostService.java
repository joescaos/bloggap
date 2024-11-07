package com.joescaos.my_blog.service;

import com.joescaos.my_blog.dto.PostDto;
import com.joescaos.my_blog.dto.PostListResponseDTO;

import java.util.List;

public interface PostService {
  PostDto createPost(PostDto postDto);

  PostListResponseDTO getAllPosts(int pageNo, int pageSize, String sortBy, String orderBy);

  PostDto getPostById(Long id);

  PostDto updatePost(Long id, PostDto postDto);

  void deletePost(Long id);

  List<PostDto> getPostsByCategory(Long categoryId);
}
