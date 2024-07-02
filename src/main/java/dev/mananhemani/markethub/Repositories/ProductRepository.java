package dev.mananhemani.markethub.Repositories;

import dev.mananhemani.markethub.Models.Category;
import dev.mananhemani.markethub.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);

    Product findByProductName(String productName);

    Page<Product> findAllByCategoryOrderByProductId(Category category, Pageable pageDetails);
}
