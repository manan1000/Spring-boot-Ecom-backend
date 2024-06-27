package dev.mananhemani.markethub.Services.CategoryService;

import dev.mananhemani.markethub.DTOs.Category.CategoryDTO;
import dev.mananhemani.markethub.DTOs.Category.CategoryResponse;


public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
