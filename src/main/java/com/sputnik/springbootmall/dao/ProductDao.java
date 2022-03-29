package com.sputnik.springbootmall.dao;

import com.sputnik.springbootmall.model.Product;

public interface ProductDao {


    Product getProductById(Integer productId);

}
