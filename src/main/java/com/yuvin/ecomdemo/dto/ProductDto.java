package com.yuvin.ecomdemo.dto;

import java.util.List;

import com.yuvin.ecomdemo.entity.ProductReview;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class ProductDto {
  private Long id;

  @Column(nullable = false)
  @NotBlank(message = "Name field is required")
  private String name;

  private Double price;

  private String description;

  private String category;

  private Double ratings = 0.0;

  private String seller;

  private Integer stock;

  private Integer numOfReviews = 0;

  private List<ProductReview> reviews;

  public List<ProductReview> getReviews() {
    return reviews;
  }

  public void setReviews(List<ProductReview> reviews) {
    this.reviews = reviews;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Double getRatings() {
    return ratings;
  }

  public void setRatings(Double ratings) {
    this.ratings = ratings;
  }

  public String getSeller() {
    return seller;
  }

  public void setSeller(String seller) {
    this.seller = seller;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public Integer getNumOfReviews() {
    return numOfReviews;
  }

  public void setNumOfReviews(Integer numOfReviews) {
    this.numOfReviews = numOfReviews;
  }

  public ProductDto() {
  }

  public ProductDto(Long id, String name, Double price, String description, String category, Double ratings,
      String seller, Integer stock) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.description = description;
    this.category = category;
    this.ratings = ratings;
    this.seller = seller;
    this.stock = stock;
  }

}
