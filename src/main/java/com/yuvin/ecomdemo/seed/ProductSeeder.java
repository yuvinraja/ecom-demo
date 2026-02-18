package com.yuvin.ecomdemo.seed;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.yuvin.ecomdemo.entity.Product;
import com.yuvin.ecomdemo.repository.ProductRepository;

@Component
public class ProductSeeder implements CommandLineRunner {

  @Autowired
  private ProductRepository productRepository;

  @Override
  public void run(String... args) throws Exception {
    if (productRepository.count() == 0) {
      List<Product> demoProducts = List.of(
          new Product(null, "Apple iPhone 15", 799.0, "Smartphone with A16 Bionic Chip", 4.8, "Amazon", 5),
          new Product(null, "Samsung Galaxy S24", 899.0, "Premium Android smartphone with 200MP camera", 4.7,
              "Best Buy", 8),
          new Product(null, "Sony WH-1000XM5", 399.0, "Wireless Noise-Cancelling Headphones", 4.9, "Amazon", 15),
          new Product(null, "Dell XPS 13", 1299.0, "Ultra-portable laptop with Intel Core i7", 4.6, "Dell", 3),
          new Product(null, "Apple AirPods Pro", 249.0, "Active Noise Cancellation earbuds", 4.8, "Apple Store", 20),
          new Product(null, "Nintendo Switch OLED", 349.0, "Gaming console with vibrant OLED screen", 4.7, "GameStop",
              12),
          new Product(null, "Levi's 501 Original Jeans", 69.0, "Classic straight fit denim jeans", 4.5, "Levi's", 25),
          new Product(null, "Nike Air Max 270", 150.0, "Men's running shoes with Air cushioning", 4.6, "Nike", 18),
          new Product(null, "KitchenAid Stand Mixer", 379.0, "5-Quart tilt-head stand mixer", 4.9, "Williams Sonoma",
              7),
          new Product(null, "Dyson V15 Detect", 649.0, "Cordless vacuum with laser dust detection", 4.8, "Dyson", 4));

      productRepository.saveAll(demoProducts);
      System.out.println("Seeded " + demoProducts.size() + " demo products");
    } else {
      System.out.println("Products already exists! Skipping Seed.");
    }
  }
}
