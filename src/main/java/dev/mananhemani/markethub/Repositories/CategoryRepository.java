package dev.mananhemani.markethub.Repositories;

import dev.mananhemani.markethub.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByCategoryName(String categoryName);
}
