package com.sputnik.springbootmall.dao.impl;

import com.sputnik.springbootmall.dao.ProductDao;
import com.sputnik.springbootmall.model.Product;

import com.sputnik.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Component
public class ProductDaoImpl implements ProductDao {



    @Autowired
   private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
}
