package com.yuvin.ecomdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuvin.ecomdemo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
  
}
