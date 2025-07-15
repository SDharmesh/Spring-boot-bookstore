package com.dharmesh.bookstore.catalog.domain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    public PagedResult<Product> getAllProducts(int pageNo,int pageSize){

        Sort sort = Sort.by("name").ascending();

        pageNo=pageNo<=1?0:pageNo-1;

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize,sort);

        Page<Product> productDetails = productRepository.findAll(pageRequest)
                .map(ProductMapper::toProduct);

        return new PagedResult<>(
                productDetails.getContent(),
                productDetails.getTotalElements(),
                productDetails.getNumber() +1,
                productDetails.getTotalPages(),
                productDetails.isFirst(),
                productDetails.isLast(),
                productDetails.hasNext(),
                productDetails.hasPrevious()
        );
    }

}
