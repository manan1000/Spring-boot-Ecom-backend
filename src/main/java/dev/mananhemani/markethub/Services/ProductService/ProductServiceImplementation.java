package dev.mananhemani.markethub.Services.ProductService;

import dev.mananhemani.markethub.DTOs.Product.ProductDTO;
import dev.mananhemani.markethub.DTOs.Product.ProductResponse;
import dev.mananhemani.markethub.Exceptions.ResourceNotFoundException;
import dev.mananhemani.markethub.Models.Category;
import dev.mananhemani.markethub.Models.Product;
import dev.mananhemani.markethub.Repositories.CategoryRepository;
import dev.mananhemani.markethub.Repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImplementation implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        product.setCategory(category);
        product.setImage("default.png");
        product.setSpecialPrice(product.getPrice() - (product.getDiscount()*0.01* product.getPrice()));
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        return null;
    }
}
