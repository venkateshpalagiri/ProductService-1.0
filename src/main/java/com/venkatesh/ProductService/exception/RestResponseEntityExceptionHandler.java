package com.venkatesh.ProductService.exception;

import com.venkatesh.ProductService.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handleProductServiceCustomException(ProductServiceCustomException productServiceCustomException){
        return new ResponseEntity<>(new ErrorResponse().builder()
                .errorCode(productServiceCustomException.getErrorCode())
                .errorMessage(productServiceCustomException.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }
}
