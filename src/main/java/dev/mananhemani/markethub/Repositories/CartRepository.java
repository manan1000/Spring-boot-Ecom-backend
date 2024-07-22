package dev.mananhemani.markethub.Repositories;

import dev.mananhemani.markethub.Models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT c from Cart c where c.user.email = ?1")
    Cart findCartByEmail(String email);
}
