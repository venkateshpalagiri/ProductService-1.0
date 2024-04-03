package com.venkatesh.ProductService.service;

import com.venkatesh.ProductService.model.ProductRequest;
import com.venkatesh.ProductService.model.ProductResponse;

public interface ProductService {
    String addProduct(ProductRequest productRequest);
    ProductResponse getProductById(Long productId);
    void reduceQuantity(long productId,long quantity);
    ProductResponse updateProduct(long productId,ProductResponse productResponse);
}
