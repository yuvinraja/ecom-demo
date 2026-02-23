package com.yuvin.ecomdemo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuvin.ecomdemo.dto.CreateOrderRequest;
import com.yuvin.ecomdemo.dto.OrderCreated;
import com.yuvin.ecomdemo.dto.OrderItemDto;
import com.yuvin.ecomdemo.entity.Order;
import com.yuvin.ecomdemo.entity.OrderItem;
import com.yuvin.ecomdemo.entity.Product;
import com.yuvin.ecomdemo.entity.User;
import com.yuvin.ecomdemo.repository.OrderRepository;
import com.yuvin.ecomdemo.repository.ProductRepository;

@Service
public class OrderService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Transactional
  public OrderCreated createOrder(CreateOrderRequest orderRequest, User user) {
    Order order = new Order();
    order.setUser(user);
    order.setStatus("pending");
    double totalItemsAmount = 0;

    for (OrderItemDto item : orderRequest.getOrderItems()) {
      OrderItem orderItem = new OrderItem();
      orderItem.setName(item.getName());
      orderItem.setPrice(item.getPrice());
      orderItem.setImage(item.getImage());
      orderItem.setQuantity(item.getQuantity());

      Product product = productRepository.findById(item.getProductId())
          .orElseThrow(() -> new RuntimeException("Product not found with id: " + item.getProductId()));

      // Check stock availability
      if (product.getStock() < item.getQuantity()) {
        throw new IllegalArgumentException(
            "Insufficient stock for product: " + product.getName() + ". Available: " + product.getStock());
      }

      // Reduce stock
      product.setStock(product.getStock() - item.getQuantity());
      productRepository.save(product);

      orderItem.setProduct(product);

      totalItemsAmount += item.getPrice() * item.getQuantity();

      order.getOrderItems().add(orderItem);
    }

    order.setTotalItemsAmount(totalItemsAmount);

    double taxAmount = totalItemsAmount * 0.1; // 10% tax
    order.setTaxAmount(taxAmount);

    double totalAmount = totalItemsAmount + taxAmount;
    order.setTotalAmount(totalAmount);

    String orderNo = UUID.randomUUID().toString();
    order.setOrderNo(orderNo);

    orderRepository.save(order);

    OrderCreated orderCreated = new OrderCreated();
    orderCreated.setReferenceId(orderNo);
    return orderCreated;
  }

  public Order getOrder(String orderNo, User user) {
    return orderRepository.findByOrderNoAndUser(orderNo, user)
        .orElseThrow(() -> new RuntimeException("Order not found with order no: " + orderNo));
  }

  public Map<String, Object> getUserOrders(User user, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    Page<Order> ordersPage = orderRepository.findByUser(user, pageable);

    Map<String, Object> response = new HashMap<>();
    response.put("orders", ordersPage.getContent());
    response.put("totalOrders", ordersPage.getTotalElements());
    response.put("totalPages", ordersPage.getTotalPages());
    response.put("currentPage", ordersPage.getNumber());

    return response;
  }
}
