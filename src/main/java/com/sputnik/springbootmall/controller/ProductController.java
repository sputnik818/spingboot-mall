package com.sputnik.springbootmall.controller;

import com.sputnik.springbootmall.constant.ProductCategory;
import com.sputnik.springbootmall.dao.ProductDao;
import com.sputnik.springbootmall.dto.ProductQueryParams;
import com.sputnik.springbootmall.dto.ProductRequest;
import com.sputnik.springbootmall.model.Product;
import com.sputnik.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            //Query Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            //order by
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort
    ){
        ProductQueryParams  productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        List<Product> productList = productService.getProducts(productQueryParams);

        return ResponseEntity.status(HttpStatus.OK).body(productList);

    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product>createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product>updateProduct(@PathVariable Integer productId,
                                                @RequestBody @Valid ProductRequest productRequest){

        //PreCheck if the product exists or not
        Product product = productService.getProductById(productId);
        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productService.updateProduct(productId, productRequest);

        Product updProduct = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?>deleteProduct(@PathVariable Integer productId){
        //PreCheck if the product exists or not
        Product product = productService.getProductById(productId);
        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
