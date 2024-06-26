package dev.mananhemani.markethub.Service.CategoryService;

import dev.mananhemani.markethub.Model.Category;

import java.util.List;


public interface CategoryService {

    List<Category> getAllCategories();

    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    void updateCategory(Category category, Long categoryId);
}
