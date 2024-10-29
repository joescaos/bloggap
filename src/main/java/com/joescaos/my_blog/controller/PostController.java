package com.joescaos.my_blog.controller;

import com.joescaos.my_blog.constants.AppConstants;
import com.joescaos.my_blog.dto.PostDto;
import com.joescaos.my_blog.dto.PostListResponseDTO;
import com.joescaos.my_blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  private PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
    return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<PostListResponseDTO> getAllPost(
      @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNo,
      @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIE) int pageSize,
      @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
      @RequestParam(name = "orderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY)
          String orderBy) {
    return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, orderBy));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
    return ResponseEntity.ok(postService.getPostById(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("{id}")
  public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @Valid @RequestBody PostDto postDto) {
    return ResponseEntity.ok(postService.updatePost(id, postDto));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePost(@PathVariable Long id) {
    postService.deletePost(id);
    return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
  }
}
