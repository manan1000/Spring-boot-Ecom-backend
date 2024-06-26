package dev.mananhemani.markethub.Service.CategoryService;

import dev.mananhemani.markethub.Model.Category;
import dev.mananhemani.markethub.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@Service
public class CategoryServiceImplementation implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        // check this api will have to change its request param need only the name, db will set id
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category with id "+ categoryId + " does not exist"));

        categoryRepository.delete(category);
        return "Category with id " + category.getCategoryId() + " deleted successfully";
    }

    @Override
    public void updateCategory(Category category, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category with id "+ categoryId + " not found"));
        existingCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(existingCategory);
    }
}
