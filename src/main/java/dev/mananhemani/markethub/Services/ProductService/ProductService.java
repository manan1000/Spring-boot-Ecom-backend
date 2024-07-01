package dev.mananhemani.markethub.Services.ProductService;

import dev.mananhemani.markethub.DTOs.Product.ProductDTO;
import dev.mananhemani.markethub.DTOs.Product.ProductResponse;
import dev.mananhemani.markethub.Models.Product;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductDTO getProductById(Long productId);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);


    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);
}
