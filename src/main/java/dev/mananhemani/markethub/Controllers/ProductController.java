package dev.mananhemani.markethub.Controllers;

import dev.mananhemani.markethub.DTOs.Product.ProductDTO;
import dev.mananhemani.markethub.DTOs.Product.ProductResponse;
import dev.mananhemani.markethub.Models.Product;
import dev.mananhemani.markethub.Services.ProductService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@PathVariable Long categoryId,
                                                 @RequestBody Product product){

        ProductDTO savedProductDTO = productService.addProduct(categoryId,product);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }
}
