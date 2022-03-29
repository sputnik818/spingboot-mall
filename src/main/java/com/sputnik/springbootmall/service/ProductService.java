package com.sputnik.springbootmall.service;

import com.sputnik.springbootmall.dto.ProductRequest;
import com.sputnik.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
}
