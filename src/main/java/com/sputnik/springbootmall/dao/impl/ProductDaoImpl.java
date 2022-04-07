package com.sputnik.springbootmall.dao.impl;

import com.sputnik.springbootmall.dao.ProductDao;
import com.sputnik.springbootmall.dto.ProductRequest;
import com.sputnik.springbootmall.model.Product;

import com.sputnik.springbootmall.rowmapper.ProductRowMapper;
import org.apache.catalina.util.ParameterMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Component
public class ProductDaoImpl implements ProductDao {



    @Autowired
   private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Product> getProducts(){
        String sql = "Select product_id, product_name, category, image_url,"
                + " price, stock, description, created_date, last_modified_date "
                + " from product";
        Map<String, Object> map = new HashMap<>();
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList;
    }

    @Override
    public Product getProductById(Integer productId) {

        String sql = "Select product_id, product_name, category, image_url,"
                + " price, stock, description, created_date, last_modified_date "
                + " from product where product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(productList.size() != 0){
            return productList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, "
                + "description, created_date, last_modified_date) "
                + "VALUES(:productName, :category, :imageUrl, :price, :stock, :description, "
                + ":createDate, :lastModifiedDate)";

                Map<String, Object> map = new HashMap<>();
                map.put("productName", productRequest.getProductName());
                map.put("category", productRequest.getCategory().toString());
                map.put("imageUrl", productRequest.getImageUrl());
                map.put("price",productRequest.getPrice());
                map.put("stock",productRequest.getStock());
                map.put("description", productRequest.getDescription());

                Date now = new Date();
                map.put("createDate", now);
                map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer productId = keyHolder.getKey().intValue();
        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
         String sql = "Update Product set product_name = :productName, category = :category, image_url = :imageUrl,  "
                 + "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate "
                 + "where product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "Delete From Product where product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }
}
