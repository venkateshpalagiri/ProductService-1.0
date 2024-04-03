package com.venkatesh.ProductService.service;

import com.venkatesh.ProductService.entity.Product;
import com.venkatesh.ProductService.exception.ProductServiceCustomException;
import com.venkatesh.ProductService.model.ProductRequest;
import com.venkatesh.ProductService.model.ProductResponse;
import com.venkatesh.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Objects;

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
                .orElseThrow(()->new ProductServiceCustomException("Product with given id not found","PRODUCT_NOT_FOUND"));
        ProductResponse productResponse=new ProductResponse();
//        BeanUtils.copyProperties(product,productResponse);
        copyProperties(product,productResponse);
        log.info("Product retrived with given Id:"+productId);
        return productResponse;
    }
    @Override
    public ProductResponse updateProduct(long productId,ProductResponse productResponse){
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceCustomException("Product not found with id:"+productId, "PRODUCT_NOT_FOUND"));

        if(Objects.nonNull(productResponse.getProductName())&&!"".equalsIgnoreCase(productResponse.getProductName())){
            product.setProductName(productResponse.getProductName());
        }
        if(product.getQuantity()<productResponse.getQuantity()){
            product.setQuantity(productResponse.getQuantity());
        }
        if(productResponse.getPrice()!=0){
            if(productResponse.getPrice()<1200){
                throw new ProductServiceCustomException("Price can't be < 1200","ADJUST_PRICE_TO_1200_OR_MAKE_IT_NULL");
            }
            product.setPrice(productResponse.getPrice());
        }
        productRepository.save(product);
        BeanUtils.copyProperties(product,productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity){
        log.info("Reducing quantity for product with id:{}",productId);
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceCustomException("Product not found with id:"+productId, "PRODUCT_NOT_FOUND"));
        if(product.getQuantity()<quantity){
            throw new ProductServiceCustomException("Product doesn't have sufficient quantity","INSUFFICIENT_QUANTITY");
        }
            product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        log.info("Reduced quantity for {} to {}",product.getProductName(),product.getQuantity());
    }
}
