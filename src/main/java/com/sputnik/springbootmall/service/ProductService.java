package com.sputnik.springbootmall.service;

import com.sputnik.springbootmall.constant.ProductCategory;
import com.sputnik.springbootmall.dto.ProductQueryParams;
import com.sputnik.springbootmall.dto.ProductRequest;
import com.sputnik.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);

}
