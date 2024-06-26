package dev.mananhemani.markethub.Repository;

import dev.mananhemani.markethub.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
