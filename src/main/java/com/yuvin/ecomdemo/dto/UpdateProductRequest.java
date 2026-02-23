package com.yuvin.ecomdemo.dto;

import jakarta.validation.constraints.PositiveOrZero;

public class UpdateProductRequest {

  private String name;

  @PositiveOrZero(message = "Price must be zero or greater")
  private Double price;

  private String description;

  private String category;

  private String seller;

  @PositiveOrZero(message = "Stock must be zero or greater")
  private Integer stock;

  public UpdateProductRequest() {
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
}
