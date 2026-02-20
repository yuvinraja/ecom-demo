package com.yuvin.ecomdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuvin.ecomdemo.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
