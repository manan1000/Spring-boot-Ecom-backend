package dev.mananhemani.markethub.Services.Cart;

import dev.mananhemani.markethub.DTOs.Cart.CartDTO;
import dev.mananhemani.markethub.DTOs.Product.ProductDTO;
import dev.mananhemani.markethub.Exceptions.ApiException;
import dev.mananhemani.markethub.Exceptions.ResourceNotFoundException;
import dev.mananhemani.markethub.Models.Cart;
import dev.mananhemani.markethub.Models.CartItem;
import dev.mananhemani.markethub.Models.Product;
import dev.mananhemani.markethub.Repositories.CartItemRepository;
import dev.mananhemani.markethub.Repositories.CartRepository;
import dev.mananhemani.markethub.Repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImplementation implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthUtil authUtil;

    @Override
    public CartDTO addProductToCart(Long productId, Long quantity) {

        // Find existing cart or create new one
        Cart cart = createCart();

        // Retrieve product details
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","ProductId",productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(),productId);

        if(cartItem!=null) throw new ApiException("Product "+product.getProductName()+" already exists in the cart");

        if(product.getQuantity()==0) throw new ApiException(product.getProductName()+" is not available!");

        if(product.getQuantity()<quantity){
            throw new ApiException("Only "+product.getQuantity()+" are available");
        }

        CartItem newCartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .discount(product.getDiscount())
                .productPrice(product.getSpecialPrice())
                .build();

        cartItemRepository.save(newCartItem);
        cart.setTotalPrice(cart.getTotalPrice()+product.getSpecialPrice()*quantity);
        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();
        Stream<ProductDTO> productStream = cartItems.stream().map(item->{
            ProductDTO map = modelMapper.map(item.getProduct(),ProductDTO.class);
            map.setQuantity(Math.toIntExact(item.getQuantity()));
            return map;
        });

        cartDTO.setProducts(productStream.toList());

        return cartDTO;
    }

    private Cart createCart() {
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if(userCart!=null) return userCart;

        Cart cart = Cart.builder()
                .user(authUtil.loggedInUser)
                .totalPrice(0.0)
                .build();
        return cartRepository.save(cart);
    }
}
