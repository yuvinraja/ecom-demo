package com.yuvin.ecomdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuvin.ecomdemo.entity.Product;
import com.yuvin.ecomdemo.repository.ProductRepository;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  public List<Product> getAllProducts() {
    List<Product> products =  productRepository.findAll();
    return products;
  }
}
