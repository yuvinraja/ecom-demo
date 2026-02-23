package com.yuvin.ecomdemo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuvin.ecomdemo.dto.CreateProductRequest;
import com.yuvin.ecomdemo.dto.ProductDto;
import com.yuvin.ecomdemo.dto.UpdateProductRequest;
import com.yuvin.ecomdemo.entity.Order;
import com.yuvin.ecomdemo.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  @Autowired
  private AdminService adminService;

  // ========== PRODUCT MANAGEMENT ==========

  @PostMapping("/products")
  public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody CreateProductRequest request) {
    ProductDto product = adminService.createProduct(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(product);
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<ProductDto> updateProduct(
      @PathVariable Long id,
      @Valid @RequestBody UpdateProductRequest request) {
    ProductDto product = adminService.updateProduct(id, request);
    return ResponseEntity.ok(product);
  }

  @DeleteMapping("/products/{id}")
  public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
    adminService.deleteProduct(id);
    return ResponseEntity.ok(Map.of("message", "Product deleted successfully"));
  }

  // ========== ORDER MANAGEMENT ==========

  @GetMapping("/orders")
  public ResponseEntity<Map<String, Object>> getAllOrders(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(required = false) String status) {
    Map<String, Object> orders = adminService.getAllOrders(page, size, status);
    return ResponseEntity.ok(orders);
  }

  @PutMapping("/orders/{orderNo}/status")
  public ResponseEntity<Order> updateOrderStatus(
      @PathVariable String orderNo,
      @RequestParam String status) {
    Order order = adminService.updateOrderStatus(orderNo, status);
    return ResponseEntity.ok(order);
  }

  // ========== USER MANAGEMENT ==========

  @GetMapping("/users")
  public ResponseEntity<Map<String, Object>> getAllUsers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Map<String, Object> users = adminService.getAllUsers(page, size);
    return ResponseEntity.ok(users);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
    adminService.deleteUser(id);
    return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
  }

  // ========== DASHBOARD STATS ==========

  @GetMapping("/dashboard/stats")
  public ResponseEntity<Map<String, Object>> getDashboardStats() {
    Map<String, Object> stats = adminService.getDashboardStats();
    return ResponseEntity.ok(stats);
  }
}
