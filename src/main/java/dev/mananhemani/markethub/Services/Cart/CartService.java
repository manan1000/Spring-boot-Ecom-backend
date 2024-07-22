package dev.mananhemani.markethub.Services.Cart;

import dev.mananhemani.markethub.DTOs.Cart.CartDTO;


public interface CartService {
    CartDTO addProductToCart(Long productId, Long quantity);
}
