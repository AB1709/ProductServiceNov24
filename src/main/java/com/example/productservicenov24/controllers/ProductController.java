package com.example.productservicenov24.controllers;

import com.example.productservicenov24.dtos.ProductNotFoundExceptionDto;
import com.example.productservicenov24.exceptions.ProductNotFoundException;
import com.example.productservicenov24.models.Product;
import com.example.productservicenov24.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        ResponseEntity<Product> productResponseEntity;
//        if (product == null) {
//            productResponseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            return productResponseEntity;
//        }
        productResponseEntity = new ResponseEntity<>(product, HttpStatus.OK);
        return productResponseEntity;
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return productService.replaceProduct(id, product);
    }
}
