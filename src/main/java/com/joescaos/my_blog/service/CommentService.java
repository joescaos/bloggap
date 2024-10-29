package com.joescaos.my_blog.service;

import com.joescaos.my_blog.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(long postId, CommentDTO commentDTO);
    List<CommentDTO> findCommentsByPostId(long postId);
    CommentDTO getCommentById(long postId, long commentId);
    CommentDTO updateComment(long postId, long commentId, CommentDTO commentRequest);
    void deleteComment(long postId, long commentId);


}
