package dev.mananhemani.markethub.DTOs.Cart;

import dev.mananhemani.markethub.DTOs.Product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long cartId;
    private Double totalPrice=0.0;
    private List<ProductDTO> products = new ArrayList<>();
}
