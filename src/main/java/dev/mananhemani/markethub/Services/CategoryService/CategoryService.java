package dev.mananhemani.markethub.Services.CategoryService;

import dev.mananhemani.markethub.Models.Category;

import java.util.List;


public interface CategoryService {

    List<Category> getAllCategories();

    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    void updateCategory(Category category, Long categoryId);
}
