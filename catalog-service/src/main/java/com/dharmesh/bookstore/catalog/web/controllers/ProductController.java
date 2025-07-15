package com.dharmesh.bookstore.catalog.web.controllers;

import com.dharmesh.bookstore.catalog.ApplicationProperties;
import com.dharmesh.bookstore.catalog.domain.PagedResult;
import com.dharmesh.bookstore.catalog.domain.Product;
import com.dharmesh.bookstore.catalog.domain.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
class ProductController {
    private final ProductService productService;
    private final ApplicationProperties properties;

    ProductController(ProductService productService,ApplicationProperties properties){
        this.productService=productService;
        this.properties=properties;
    }

    @GetMapping("/products")
    PagedResult<Product> getProducts(@RequestParam(name = "page",defaultValue = "1")int pageNo){

        return productService.getAllProducts(pageNo, properties.pageSize());
    }

}
