package com.joescaos.my_blog.service.impl;

import com.joescaos.my_blog.dto.CommentDTO;
import com.joescaos.my_blog.entity.Comment;
import com.joescaos.my_blog.entity.Post;
import com.joescaos.my_blog.exceptions.BlogAPIException;
import com.joescaos.my_blog.exceptions.ResourceNotFoundException;
import com.joescaos.my_blog.repository.CommentRepository;
import com.joescaos.my_blog.repository.PostRepository;
import com.joescaos.my_blog.service.CommentService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

  private final PostRepository postRepository;

  private final ModelMapper mapper;

  public CommentServiceImpl(
      CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.mapper = mapper;
  }

  @Override
  public CommentDTO createComment(long postId, CommentDTO commentDTO) {
    Comment comment = mapToComment(commentDTO);
    Post post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    comment.setPost(post);

    Comment newComment = commentRepository.save(comment);
    return mapToCommentDTO(newComment);
  }

  @Override
  public List<CommentDTO> findCommentsByPostId(long postId) {
    List<Comment> commentsByPost = commentRepository.findCommentsByPostId(postId);
    return commentsByPost.stream().map(this::mapToCommentDTO).toList();
  }

  @Override
  public CommentDTO getCommentById(long postId, long commentId) {
    Post post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

    if (!comment.getPost().getId().equals(post.getId())) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
    }

    return mapToCommentDTO(comment);
  }

  @Override
  public CommentDTO updateComment(long postId, long commentId, CommentDTO commentRequest) {
    Post post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

    if (!comment.getPost().getId().equals(post.getId())) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
    }

    comment.setName(commentRequest.getName());
    comment.setEmail(commentRequest.getEmail());
    comment.setBody(commentRequest.getBody());

    return mapToCommentDTO(commentRepository.save(comment));
  }

  @Override
  public void deleteComment(long postId, long commentId) {
    Post post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

    if (!comment.getPost().getId().equals(post.getId())) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
    }

    commentRepository.delete(comment);
  }

  private CommentDTO mapToCommentDTO(Comment comment) {
    return mapper.map(comment, CommentDTO.class);
  }

  private Comment mapToComment(CommentDTO commentDTO) {
    return mapper.map(commentDTO, Comment.class);
  }
}