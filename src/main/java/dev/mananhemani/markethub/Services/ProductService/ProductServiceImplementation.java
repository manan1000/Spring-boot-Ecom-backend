package dev.mananhemani.markethub.Services.ProductService;

import dev.mananhemani.markethub.DTOs.Product.ProductDTO;
import dev.mananhemani.markethub.DTOs.Product.ProductResponse;
import dev.mananhemani.markethub.Exceptions.ApiException;
import dev.mananhemani.markethub.Exceptions.ResourceNotFoundException;
import dev.mananhemani.markethub.Models.Category;
import dev.mananhemani.markethub.Models.Product;
import dev.mananhemani.markethub.Repositories.CategoryRepository;
import dev.mananhemani.markethub.Repositories.ProductRepository;
import dev.mananhemani.markethub.Services.FileService.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        Product product = modelMapper.map(productDTO,Product.class);
        product.setCategory(category);
        product.setImage("default.png");
        product.setSpecialPrice(product.getPrice() - (product.getDiscount()*0.01* product.getPrice()));
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> productPage = productRepository.findAll(pageDetails);

        List<Product> products= productPage.getContent();

        if(products.isEmpty()) throw new ApiException("Products list is empty");

        List<ProductDTO> productDTOS= products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();

        return ProductResponse.builder()
                .content(productDTOS)
                .pageNumber(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .lastPage(productPage.isLast())
                .build();
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> productPage = productRepository.findAll(pageDetails);

        List<Product> products= productPage.getContent();

        if(products.isEmpty()) throw new ApiException("Products list is empty");

        List<ProductDTO> productDTOS= products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();

        return ProductResponse.builder()
                .content(productDTOS)
                .pageNumber(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .lastPage(productPage.isLast())
                .build();
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%');

        if(products.isEmpty()) throw new ApiException("Products list is empty");

        List<ProductDTO> productDTOS= products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();

        return ProductResponse.builder()
                .content(productDTOS)
                .build();
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

        Product savedProduct = Product.builder()
                .productId(productFromDB.getProductId() )
                .productName(productDTO.getProductName())
                .image(productFromDB.getImage())
                .description(productDTO.getDescription())
                .quantity(productDTO.getQuantity())
                .price(productDTO.getPrice())
                .discount(productDTO.getDiscount())
                .specialPrice(productDTO.getPrice() - (productDTO.getDiscount()*0.01* productDTO.getPrice()))
                .build();

        savedProduct = productRepository.save(savedProduct);
        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

        productRepository.delete(product);
        return modelMapper.map(product,ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

        String path = "images/";
        String fileName = fileService.uploadImage(path,image);

        productFromDB.setImage(fileName);
        Product updateProduct = productRepository.save(productFromDB);
        return modelMapper.map(updateProduct, ProductDTO.class);
    }


}
