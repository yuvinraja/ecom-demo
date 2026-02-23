package com.yuvin.ecomdemo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuvin.ecomdemo.dto.CreateProductRequest;
import com.yuvin.ecomdemo.dto.ProductDto;
import com.yuvin.ecomdemo.dto.UpdateProductRequest;
import com.yuvin.ecomdemo.dto.UserDto;
import com.yuvin.ecomdemo.entity.Order;
import com.yuvin.ecomdemo.entity.Product;
import com.yuvin.ecomdemo.entity.ProductImage;
import com.yuvin.ecomdemo.entity.User;
import com.yuvin.ecomdemo.repository.OrderRepository;
import com.yuvin.ecomdemo.repository.ProductRepository;
import com.yuvin.ecomdemo.repository.UserRepository;

@Service
public class AdminService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductService productService;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private UserRepository userRepository;

  // ========== PRODUCT MANAGEMENT ==========

  @Transactional
  public ProductDto createProduct(CreateProductRequest request) {
    Product product = new Product();
    product.setName(request.getName());
    product.setPrice(request.getPrice());
    product.setDescription(request.getDescription());
    product.setCategory(request.getCategory());
    product.setSeller(request.getSeller());
    product.setStock(request.getStock());
    product.setRatings(0.0);
    product.setNumOfReviews(0);

    if (request.getImageIds() != null && !request.getImageIds().isEmpty()) {
      List<ProductImage> images = request.getImageIds().stream()
          .map(imageId -> {
            ProductImage img = new ProductImage();
            img.setPublicId(imageId);
            return img;
          })
          .collect(Collectors.toList());
      product.setImages(images);
    } else {
      product.setImages(new ArrayList<>());
    }

    product.setReviews(new ArrayList<>());

    Product savedProduct = productRepository.save(product);
    return productService.convertToDto(savedProduct);
  }

  @Transactional
  public ProductDto updateProduct(Long id, UpdateProductRequest request) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

    if (request.getName() != null) {
      product.setName(request.getName());
    }
    if (request.getPrice() != null) {
      product.setPrice(request.getPrice());
    }
    if (request.getDescription() != null) {
      product.setDescription(request.getDescription());
    }
    if (request.getCategory() != null) {
      product.setCategory(request.getCategory());
    }
    if (request.getSeller() != null) {
      product.setSeller(request.getSeller());
    }
    if (request.getStock() != null) {
      product.setStock(request.getStock());
    }

    Product savedProduct = productRepository.save(product);
    return productService.convertToDto(savedProduct);
  }

  @Transactional
  public void deleteProduct(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    productRepository.delete(product);
  }

  // ========== ORDER MANAGEMENT ==========

  public Map<String, Object> getAllOrders(int page, int size, String status) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    Page<Order> ordersPage;

    if (status != null && !status.isEmpty()) {
      ordersPage = orderRepository.findByStatus(status, pageable);
    } else {
      ordersPage = orderRepository.findAll(pageable);
    }

    Map<String, Object> response = new HashMap<>();
    response.put("orders", ordersPage.getContent());
    response.put("totalOrders", ordersPage.getTotalElements());
    response.put("totalPages", ordersPage.getTotalPages());
    response.put("currentPage", ordersPage.getNumber());

    return response;
  }

  @Transactional
  public Order updateOrderStatus(String orderNo, String status) {
    Order order = orderRepository.findByOrderNo(orderNo)
        .orElseThrow(() -> new RuntimeException("Order not found with order no: " + orderNo));

    // Validate status
    List<String> validStatuses = List.of("pending", "processing", "shipped", "delivered", "cancelled");
    if (!validStatuses.contains(status.toLowerCase())) {
      throw new IllegalArgumentException("Invalid status. Valid statuses are: " + validStatuses);
    }

    order.setStatus(status.toLowerCase());
    return orderRepository.save(order);
  }

  // ========== USER MANAGEMENT ==========

  public Map<String, Object> getAllUsers(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    Page<User> usersPage = userRepository.findAll(pageable);

    List<UserDto> userDtos = usersPage.getContent().stream()
        .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole().name()))
        .collect(Collectors.toList());

    Map<String, Object> response = new HashMap<>();
    response.put("users", userDtos);
    response.put("totalUsers", usersPage.getTotalElements());
    response.put("totalPages", usersPage.getTotalPages());
    response.put("currentPage", usersPage.getNumber());

    return response;
  }

  @Transactional
  public void deleteUser(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    userRepository.delete(user);
  }

  // ========== DASHBOARD STATS ==========

  public Map<String, Object> getDashboardStats() {
    Map<String, Object> stats = new HashMap<>();

    // Total counts
    stats.put("totalProducts", productRepository.count());
    stats.put("totalOrders", orderRepository.count());
    stats.put("totalUsers", userRepository.count());

    // Order statistics by status
    Map<String, Long> ordersByStatus = new HashMap<>();
    ordersByStatus.put("pending", orderRepository.countByStatus("pending"));
    ordersByStatus.put("processing", orderRepository.countByStatus("processing"));
    ordersByStatus.put("shipped", orderRepository.countByStatus("shipped"));
    ordersByStatus.put("delivered", orderRepository.countByStatus("delivered"));
    ordersByStatus.put("cancelled", orderRepository.countByStatus("cancelled"));
    stats.put("ordersByStatus", ordersByStatus);

    // Revenue (sum of all delivered orders)
    Double totalRevenue = orderRepository.calculateTotalRevenue();
    stats.put("totalRevenue", totalRevenue != null ? totalRevenue : 0.0);

    // Recent orders
    Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
    Page<Order> recentOrders = orderRepository.findAll(pageable);
    stats.put("recentOrders", recentOrders.getContent());

    return stats;
  }
}
