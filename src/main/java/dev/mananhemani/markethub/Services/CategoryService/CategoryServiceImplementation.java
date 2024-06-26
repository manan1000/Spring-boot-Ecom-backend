package dev.mananhemani.markethub.Services.CategoryService;

import dev.mananhemani.markethub.Exceptions.ApiException;
import dev.mananhemani.markethub.Exceptions.ResourceNotFoundException;
import dev.mananhemani.markethub.Models.Category;
import dev.mananhemani.markethub.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryServiceImplementation implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()) {
            throw new ApiException("Categories list is empty");
        }
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        // check this api will have to change its request param need only the name, db will set id
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory!=null){
            throw new ApiException("Category with name: " + category.getCategoryName() + " already exists");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));

        categoryRepository.delete(category);
        return "Category with id " + category.getCategoryId() + " deleted successfully";
    }

    @Override
    public void updateCategory(Category category, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));
        existingCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(existingCategory);
    }
}
