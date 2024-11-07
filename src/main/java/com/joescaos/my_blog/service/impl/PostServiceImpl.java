package com.joescaos.my_blog.service.impl;

import com.joescaos.my_blog.dto.PostDto;
import com.joescaos.my_blog.dto.PostListResponseDTO;
import com.joescaos.my_blog.entity.Category;
import com.joescaos.my_blog.entity.Post;
import com.joescaos.my_blog.exceptions.ResourceNotFoundException;
import com.joescaos.my_blog.repository.CategoryRepository;
import com.joescaos.my_blog.repository.PostRepository;
import com.joescaos.my_blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;

  private final ModelMapper mapper;

  private final CategoryRepository categoryRepository;

  public PostServiceImpl(
      PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
    this.postRepository = postRepository;
    this.mapper = mapper;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public PostDto createPost(PostDto postDto) {
    Category category =
        categoryRepository
            .findById(postDto.getCategoryId())
            .orElseThrow(
                () -> new ResourceNotFoundException("category", "id", postDto.getCategoryId()));

    Post post = buildPost(postDto);
    post.setCategory(category);
    Post postSaved = postRepository.save(post);

    return buildPostDto(postSaved);
  }

  @Override
  public PostListResponseDTO getAllPosts(int pageNo, int pageSize, String sortBy, String orderBy) {

    Sort sort =
        orderBy.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(Sort.Direction.ASC, sortBy)
            : Sort.by(Sort.Direction.DESC, sortBy);

    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
    Page<Post> postPage = postRepository.findAll(pageable);
    return buildPostResponseDTO(postPage);
  }

  @Override
  public PostDto getPostById(Long id) {
    return buildPostDto(
        postRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)));
  }

  @Override
  public PostDto updatePost(Long id, PostDto postDto) {
    Post postDB =
        postRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postDto.getId()));

    Category category =
        categoryRepository
            .findById(postDto.getCategoryId())
            .orElseThrow(
                () -> new ResourceNotFoundException("category", "id", postDto.getCategoryId()));

    postDB.setTitle(postDto.getTitle());
    postDB.setDescription(postDto.getDescription());
    postDB.setContent(postDto.getContent());
    postDB.setCategory(category);
    return buildPostDto(postRepository.save(postDB));
  }

  @Override
  public void deletePost(Long id) {
    postRepository.deleteById(id);
  }

  @Override
  public List<PostDto> getPostsByCategory(Long categoryId) {

    Category category =
        categoryRepository
            .findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));

    List<Post> postList = postRepository.findByCategoryId(categoryId);
    return postList.stream().map(this::buildPostDto).toList();
  }

  private Post buildPost(PostDto postDto) {
    return mapper.map(postDto, Post.class);
  }

  private PostDto buildPostDto(Post post) {
    return mapper.map(post, PostDto.class);
  }

  private PostListResponseDTO buildPostResponseDTO(Page<Post> postPage) {

    return PostListResponseDTO.builder()
        .content(postPage.getContent().stream().map(this::buildPostDto).toList())
        .pageNo(postPage.getNumber())
        .pageSize(postPage.getSize())
        .totalElements(postPage.getTotalElements())
        .totalPages(postPage.getTotalPages())
        .isLast(postPage.isLast())
        .build();
  }
}
