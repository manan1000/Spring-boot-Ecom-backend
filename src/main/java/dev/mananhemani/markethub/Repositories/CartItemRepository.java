package dev.mananhemani.markethub.Repositories;

import dev.mananhemani.markethub.Models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
