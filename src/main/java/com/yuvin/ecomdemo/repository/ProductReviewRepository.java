package com.yuvin.ecomdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuvin.ecomdemo.entity.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long>{
  
}
