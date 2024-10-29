package com.joescaos.my_blog.controller;

import com.joescaos.my_blog.dto.CommentDTO;
import com.joescaos.my_blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PostMapping("/posts/{postId}/comments")
  public ResponseEntity<CommentDTO> createComment(
      @PathVariable Long postId, @Valid @RequestBody CommentDTO comment) {
    return new ResponseEntity<>(commentService.createComment(postId, comment), HttpStatus.CREATED);
  }

  @GetMapping("/posts/{postId}/comments")
  public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable long postId) {
    return ResponseEntity.ok(commentService.findCommentsByPostId(postId));
  }

  @GetMapping("/posts/{postId}/comments/{commentId}")
  public ResponseEntity<CommentDTO> getCommentById(
      @PathVariable long postId, @PathVariable long commentId) {
    return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
  }

  @PutMapping("/posts/{postId}/comments/{commentId}")
  public ResponseEntity<CommentDTO> updateComment(
      @PathVariable long postId,
      @PathVariable long commentId,
      @Valid @RequestBody CommentDTO commentRequest) {
    return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentRequest));
  }

  @DeleteMapping("/posts/{postId}/comments/{commentId}")
  public ResponseEntity<String> deleteComment(
          @PathVariable long postId,
          @PathVariable long commentId) {
    commentService.deleteComment(postId, commentId);
    return ResponseEntity.ok("comment deleted successfully");
  }
}
