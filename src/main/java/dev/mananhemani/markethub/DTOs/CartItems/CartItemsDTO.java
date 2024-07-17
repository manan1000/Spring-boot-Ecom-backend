package dev.mananhemani.markethub.DTOs.CartItems;

import dev.mananhemani.markethub.DTOs.Cart.CartDTO;
import dev.mananhemani.markethub.DTOs.Product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsDTO {

    private Long cartItemId;
    private CartDTO cart;
    private ProductDTO product;
    private Long quantity;
    private Double discount;
    private Double productPrice;
}
