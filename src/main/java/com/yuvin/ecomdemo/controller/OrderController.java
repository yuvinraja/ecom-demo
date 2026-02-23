package com.yuvin.ecomdemo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuvin.ecomdemo.dto.CreateOrderRequest;
import com.yuvin.ecomdemo.dto.OrderCreated;
import com.yuvin.ecomdemo.entity.Order;
import com.yuvin.ecomdemo.entity.User;
import com.yuvin.ecomdemo.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderCreated> createOrder(
      @Valid @RequestBody CreateOrderRequest orderRequest,
      @AuthenticationPrincipal User user) {
    OrderCreated orderCreated = orderService.createOrder(orderRequest, user);
    return ResponseEntity.ok(orderCreated);
  }

  @GetMapping("/{orderNo}")
  public ResponseEntity<Order> getOrder(
      @PathVariable String orderNo,
      @AuthenticationPrincipal User user) {
    Order order = orderService.getOrder(orderNo, user);
    return ResponseEntity.ok(order);
  }

  @GetMapping
  public ResponseEntity<Map<String, Object>> getUserOrders(
      @AuthenticationPrincipal User user,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Map<String, Object> orders = orderService.getUserOrders(user, page, size);
    return ResponseEntity.ok(orders);
  }
}
