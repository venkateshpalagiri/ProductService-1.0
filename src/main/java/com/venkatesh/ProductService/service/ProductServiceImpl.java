package com.venkatesh.ProductService.service;

import com.venkatesh.ProductService.entity.Product;
import com.venkatesh.ProductService.model.ProductRequest;
import com.venkatesh.ProductService.model.ProductResponse;
import com.venkatesh.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public String addProduct(ProductRequest productRequest) {
        log.info("Adding product..."+productRequest.getProductName());
        Product product= Product.builder()
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepository.save(product);
        log.info(product.getProductName()+" Product added.");
        return "Product added successfully";
    }
    @Override
    public ProductResponse getProductById(Long productId){
        log.info("Getting product...");
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("Product with given id not found."));
        ProductResponse productResponse=new ProductResponse();
//        BeanUtils.copyProperties(product,productResponse);
        copyProperties(product,productResponse);
        log.info("Product retrived with given Id:"+productId);
        return productResponse;
    }
}
