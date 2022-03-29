package com.sputnik.springbootmall.dao;

import com.sputnik.springbootmall.dto.ProductRequest;
import com.sputnik.springbootmall.model.Product;

public interface ProductDao {


    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
