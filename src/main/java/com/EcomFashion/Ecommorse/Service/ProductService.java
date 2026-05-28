package com.EcomFashion.Ecommorse.Service;

import com.EcomFashion.Ecommorse.Repositories.ProductRepository;
import com.EcomFashion.Ecommorse.Service.ServiceImpl.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService implements ProductServiceImpl {

 private final ProductRepository productRepository;









}
