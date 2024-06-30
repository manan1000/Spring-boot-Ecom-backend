package dev.mananhemani.markethub.Services.ProductService;

import dev.mananhemani.markethub.DTOs.Product.ProductDTO;
import dev.mananhemani.markethub.DTOs.Product.ProductResponse;
import dev.mananhemani.markethub.Models.Product;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();
}
