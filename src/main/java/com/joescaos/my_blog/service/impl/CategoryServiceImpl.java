package com.joescaos.my_blog.service.impl;

import com.joescaos.my_blog.dto.CategoryDTO;
import com.joescaos.my_blog.entity.Category;
import com.joescaos.my_blog.exceptions.ResourceNotFoundException;
import com.joescaos.my_blog.repository.CategoryRepository;
import com.joescaos.my_blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final ModelMapper modelMapper;

  public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
    this.categoryRepository = categoryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public CategoryDTO addCategory(CategoryDTO categoryDTO) {

    Category category = modelMapper.map(categoryDTO, Category.class);

    Category categorySaved = categoryRepository.save(category);

    return modelMapper.map(categorySaved, CategoryDTO.class);
  }

  @Override
  public CategoryDTO getCategory(Long categoryId) {

    Category category =
        categoryRepository
            .findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));

    return modelMapper.map(category, CategoryDTO.class);
  }

  @Override
  public List<CategoryDTO> getAllCategories() {
    return categoryRepository.findAll().stream()
        .map(category -> modelMapper.map(category, CategoryDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));

    category.setId(categoryId);
    category.setName(categoryDTO.getName());
    category.setDescription(categoryDTO.getDescription());

    Category categoryUpdated = categoryRepository.save(category);

    return modelMapper.map(categoryUpdated, CategoryDTO.class);
  }
}
