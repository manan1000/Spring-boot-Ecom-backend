package dev.mananhemani.markethub.Repositories;

import dev.mananhemani.markethub.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByProductNameLikeIgnoreCase(String keyword);
}
