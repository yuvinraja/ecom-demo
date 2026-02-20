package com.yuvin.ecomdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuvin.ecomdemo.dto.CreateOrderRequest;
import com.yuvin.ecomdemo.dto.OrderCreated;
import com.yuvin.ecomdemo.entity.Order;
import com.yuvin.ecomdemo.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping
  public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest orderRequest) {
    OrderCreated orderCreated = orderService.createOrder(orderRequest);
    return ResponseEntity.ok().body(orderCreated);
  }

  @GetMapping("/{orderNo}")
  public ResponseEntity<?> getOrder(@PathVariable String orderNo) {
    Order order = orderService.getOrder(orderNo);
    return ResponseEntity.ok().body(order);
  }

}
