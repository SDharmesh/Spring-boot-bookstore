package com.dharmesh.bookstore.catalog.web.controllers.Product;

import com.dharmesh.bookstore.catalog.ApplicationProperties;
import com.dharmesh.bookstore.catalog.domain.Product.PagedResult;
import com.dharmesh.bookstore.catalog.domain.Product.Product;
import com.dharmesh.bookstore.catalog.domain.Product.ProductNotFoundException;
import com.dharmesh.bookstore.catalog.domain.Product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
class ProductController {
    private final ProductService productService;
    private final ApplicationProperties properties;

    ProductController(ProductService productService, ApplicationProperties properties) {
        this.productService = productService;
        this.properties = properties;
    }

    @GetMapping("/products")
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {

        return productService.getAllProducts(pageNo, properties.pageSize());
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable("code") String code) {
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
