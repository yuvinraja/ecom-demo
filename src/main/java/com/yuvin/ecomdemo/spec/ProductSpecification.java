package com.yuvin.ecomdemo.spec;

import org.springframework.data.jpa.domain.Specification;

import com.yuvin.ecomdemo.entity.Product;

public class ProductSpecification {
  public static Specification<Product> hasCategory(String category) {
    return (root, query, cb) -> category == null ? null : cb.equal(root.get("category"), category);
  }

  public static Specification<Product> hasPriceGreaterThanOrEqual(Double minPrice) {
    return (root, query, cb) -> minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
  }

  public static Specification<Product> hasPriceLessThanOrEqual(Double maxPrice) {
    return (root, query, cb) -> maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
  }

  public static Specification<Product> hasKeywordInName(String keyword) {
    return (root, query, cb) -> keyword == null ? null
        : cb.or(
            cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
            cb.like(cb.lower(root.get("description")), "%" + keyword.toLowerCase() + "%"));
  }

  public static Specification<Product> priceBetween(Double min, Double max) {
    return (root, query, cb) -> {
      if (min == null && max == null)
        return null;
      else if (min == null)
        return cb.lessThanOrEqualTo(root.get("price"), max);
      else if (max == null)
        return cb.greaterThanOrEqualTo(root.get("price"), min);
      return cb.between(root.get("price"), min, max);
    };
  }
}
