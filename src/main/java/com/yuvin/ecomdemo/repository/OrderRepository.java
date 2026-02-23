package com.yuvin.ecomdemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yuvin.ecomdemo.entity.Order;
import com.yuvin.ecomdemo.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  Optional<Order> findByOrderNo(String orderNo);

  Page<Order> findByStatus(String status, Pageable pageable);

  long countByStatus(String status);

  @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'delivered'")
  Double calculateTotalRevenue();

  // User order history
  List<Order> findByUserOrderByCreatedAtDesc(User user);

  Page<Order> findByUser(User user, Pageable pageable);

  Optional<Order> findByOrderNoAndUser(String orderNo, User user);
}
