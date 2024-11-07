package com.joescaos.my_blog.repository;

import com.joescaos.my_blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCategory(Long categoryId);
}
