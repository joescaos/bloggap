package com.joescaos.my_blog.repository;

import com.joescaos.my_blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
