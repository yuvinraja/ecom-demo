package com.yuvin.ecomdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuvin.ecomdemo.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
  Optional<Order> findByOrderNo(String orderNo);
}
