package com.EcomFashion.Ecommorse.Service;
import com.EcomFashion.Ecommorse.Dto.ProductDTO;
import com.EcomFashion.Ecommorse.Entity.Product.Category;
import com.EcomFashion.Ecommorse.Entity.Product.Product;
import com.EcomFashion.Ecommorse.Entity.Product.ProductionCreationRequest;
import com.EcomFashion.Ecommorse.Entity.User.User;
import com.EcomFashion.Ecommorse.Repositories.Product.CategoryRepository;
import com.EcomFashion.Ecommorse.Repositories.Product.ProductImageRepository;
import com.EcomFashion.Ecommorse.Repositories.User.UserRepository;
import org.modelmapper.ModelMapper;
import com.EcomFashion.Ecommorse.Repositories.Product.ProductRepository;
import com.EcomFashion.Ecommorse.Service.ServiceImpl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceImpl {
    private final Pageable pageable;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final ModelMapper modelMapper;

    // product -> create / update / delete / get / search / filter
 // variant -> add / update / delete / inventory update / SKU validation
 // inventory -> reserve / release / low stock / out of stock
 // images -> add / remove / update / thumbnail
 // category -> create / update / delete / fetch products
 // validation -> seller ownership / stock / pricing / category / role
 // status -> activate / deactivate / draft / archive
 // review -> rating / average rating / verified reviews
 // advanced -> wishlist / recommendations / analytics / caching / SEO

    @Override
    public User getCurrentSeller(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
   @Override
   public ProductDTO createProduct(ProductionCreationRequest request) {

       User seller = getCurrentSeller();
       Category category = categoryRepository
               .findById(request.getCategoryId())
               .orElseThrow(() ->
                       new RuntimeException("Category not found"));
       if (request.getBasePrice() == null
               || request.getBasePrice().signum() <= 0) {
           throw new RuntimeException(
                   "Price must be greater than zero"
           );
       }

       Product product = modelMapper.map(request, Product.class);
       product.setSeller(seller);
       product.setCategory(category);
       Product savedProduct = productRepository.save(product);
       return modelMapper.map(savedProduct, ProductDTO.class);
   }
    @Override
    public ProductDTO getProductById(Long id) {

        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return modelMapper.map(product, ProductDTO.class);
    }
   @Override
   public ProductDTO updateProduct(
           Long productId,
           ProductionCreationRequest request
   ) {
       User seller = getCurrentSeller();
       Product product = productRepository
               .findById(productId)
               .orElseThrow(() ->
                       new RuntimeException("Product not found with id: " + productId));

       if (!product.getSeller()
               .getId()
               .equals(seller.getId())) {
           throw new RuntimeException("You are not authorized to update this product");
       }

       Category category = categoryRepository
               .findById(request.getCategoryId())
               .orElseThrow(() -> new RuntimeException("Category not found"));
       if (request.getBasePrice() == null
               || request.getBasePrice().signum() <= 0) {
           throw new RuntimeException("Price must be greater than zero");
       }

       product.setName(request.getName());
       product.setDescription(request.getDescription());
       product.setBrand(request.getBrand());
       product.setBasePrice(request.getBasePrice());
       product.setCategory(category);

       Product updatedProduct = productRepository.save(product);

       return modelMapper.map(
               updatedProduct,
               ProductDTO.class
       );
   }
    @Override
    public void deleteProduct(Long productId) {

        User seller = getCurrentSeller();
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with id: " + productId));
        if (!product.getSeller()
                .getId()
                .equals(seller.getId())) {
            throw new RuntimeException("You are not authorized to delete this product");
        }
        productRepository.delete(product);
    }
    @Override
    public List<ProductDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(product ->
                        modelMapper.map(product, ProductDTO.class)
                )
                .toList();
    }
    @Override
    public Page<ProductDTO> getProducts(
            int page,
            int size,
            String sortBy,
            String direction,
            String category,
            String keyword
    ) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products;
        if (category != null && !category.isBlank()
                && keyword != null
                && !keyword.isBlank()) {
            products = productRepository
                    .findByCategory_NameIgnoreCaseAndNameContainingIgnoreCase(
                            category,
                            keyword,
                            pageable
                    );
        } else if (category != null && !category.isBlank()) {
            products = productRepository.findByCategory_NameIgnoreCase(
                            category,
                            pageable
                    );
        } else if (keyword != null && !keyword.isBlank()) {
            products = productRepository.findByNameContainingIgnoreCase(
                            keyword,
                            pageable);
        } else {
            products = productRepository.findAll(pageable);
        }
        return products.map(product -> modelMapper.map(product, ProductDTO.class));
    }








}