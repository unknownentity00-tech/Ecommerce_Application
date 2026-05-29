package com.EcomFashion.Ecommorse.Service.ServiceImpl;

import com.EcomFashion.Ecommorse.Dto.ProductDTO;
import com.EcomFashion.Ecommorse.Entity.Product.ProductionCreationRequest;
import com.EcomFashion.Ecommorse.Entity.User.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductServiceImpl {
    // product -> create / update / delete / get / search / filter
 // variant -> add / update / delete / inventory update / SKU validation
 // inventory -> reserve / release / low stock / out of stock
 // images -> add / remove / update / thumbnail
 // category -> create / update / delete / fetch products
 // validation -> seller ownership / stock / pricing / category / role
 // status -> activate / deactivate / draft / archive
 // review -> rating / average rating / verified reviews
 // advanced -> wishlist / recommendations / analytics / caching / SEO
    User getCurrentSeller();

    ProductDTO createProduct(ProductionCreationRequest request);

    ProductDTO getProductById(Long id);

    ProductDTO updateProduct(Long id, ProductionCreationRequest request);

    void deleteProduct(Long productId);

    List<ProductDTO> getAllProducts();



    Page<ProductDTO> getProducts(
            int page,
            int size,
            String sortBy,
            String direction,
            String category,
            String keyword
    );
}
