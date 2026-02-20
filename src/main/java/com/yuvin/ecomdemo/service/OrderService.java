package com.yuvin.ecomdemo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuvin.ecomdemo.dto.CreateOrderRequest;
import com.yuvin.ecomdemo.dto.OrderCreated;
import com.yuvin.ecomdemo.dto.OrderItemDto;
import com.yuvin.ecomdemo.entity.Order;
import com.yuvin.ecomdemo.entity.OrderItem;
import com.yuvin.ecomdemo.entity.Product;
import com.yuvin.ecomdemo.repository.OrderRepository;
import com.yuvin.ecomdemo.repository.ProductRepository;

@Service
public class OrderService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private OrderRepository orderRepository;

  public OrderCreated createOrder(CreateOrderRequest orderRequest) {
    Order order = new Order();
    order.setStatus("pending");
    double totalItemsAmount = 0;

    for (OrderItemDto item : orderRequest.getOrderItems()) {
      OrderItem orderItem = new OrderItem();
      orderItem.setName(item.getName());
      orderItem.setPrice(item.getPrice());
      orderItem.setImage(item.getImage());
      orderItem.setQuantity(item.getQuantity());

      Product product = productRepository.findById(item.getProductId())
          .orElseThrow(() -> new RuntimeException("Product not found"));
      orderItem.setProduct(product);

      totalItemsAmount += item.getPrice() * item.getQuantity();

      order.getOrderItems().add(orderItem);
    }

    order.setTotalItemsAmount(totalItemsAmount);

    double taxAmount = 10;
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

  public Order getOrder(String orderNo) {
    return orderRepository.findByOrderNo(orderNo)
        .orElseThrow(() -> new RuntimeException("No order found with order no" + orderNo));
  }

}
