package dev.mananhemani.markethub.Repositories;

import dev.mananhemani.markethub.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
