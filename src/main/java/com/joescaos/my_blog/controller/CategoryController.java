package com.joescaos.my_blog.controller;

import com.joescaos.my_blog.dto.CategoryDTO;
import com.joescaos.my_blog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {
    CategoryDTO response = categoryService.addCategory(categoryDTO);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
    return ResponseEntity.ok(categoryService.getCategory(id));
  }

  @GetMapping
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {
    return ResponseEntity.ok(categoryService.getAllCategories());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("{id}")
  public ResponseEntity<CategoryDTO> updateCategory(
      @PathVariable long id, @RequestBody CategoryDTO categoryDTO) {
    return ResponseEntity.ok(categoryService.updateCategory(categoryDTO, id));
  }
}
