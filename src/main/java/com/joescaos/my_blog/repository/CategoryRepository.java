package com.joescaos.my_blog.repository;

import com.joescaos.my_blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
