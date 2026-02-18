package com.yuvin.ecomdemo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuvin.ecomdemo.entity.Product;
import com.yuvin.ecomdemo.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping()
  public Map<String, Object> getAllProducts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {
    return productService.getAllProducts(page, size);
  }

  @GetMapping("/{id}")
  public Product getProductById(@PathVariable Long id) {
    return productService.getProductById(id);
  }
}
