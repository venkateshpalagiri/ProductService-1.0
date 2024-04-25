package com.venkatesh.ProductService.controller;

import com.venkatesh.ProductService.model.ProductRequest;
import com.venkatesh.ProductService.model.ProductResponse;
import com.venkatesh.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest){
       String msg= productService.addProduct(productRequest);
       return new ResponseEntity<>(msg, HttpStatus.CREATED);

    }
    @PreAuthorize("hasAuthority('Admin') || hasAuthority('Customer') || hasAuthority('SCOPE_internal')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long productId){
        ProductResponse productResponse=productService.getProductById(productId);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);

    }
    @PutMapping("updateProduct/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") long prouductId,
                                                         @RequestBody ProductResponse productResponse){
       ProductResponse updatedProduct= productService.updateProduct(prouductId,productResponse);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId,@RequestParam long quantity){
        productService.reduceQuantity(productId,quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
