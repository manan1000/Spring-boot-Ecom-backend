package dev.mananhemani.markethub.Repositories;

import dev.mananhemani.markethub.Models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
