# Spring Boot Annotations Cheatsheet

A quick reference guide for all annotations used in this ecommerce project.

---

## Table of Contents

1. [Core Spring Boot](#core-spring-boot)
2. [Web / REST Controller](#web--rest-controller)
3. [Dependency Injection](#dependency-injection)
4. [JPA / Hibernate (Entity)](#jpa--hibernate-entity)
5. [Validation](#validation)
6. [Security](#security)
7. [Configuration](#configuration)
8. [Testing](#testing)
9. [Jackson (JSON)](#jackson-json)

---

## Core Spring Boot

| Annotation | Description | Example |
|------------|-------------|---------|
| `@SpringBootApplication` | Main entry point. Combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan` | `@SpringBootApplication public class EcomdemoApplication` |
| `@Component` | Marks a class as a Spring-managed bean | `@Component public class ProductSeeder` |
| `@Service` | Specialized `@Component` for service layer classes | `@Service public class AuthService` |
| `@Repository` | Specialized `@Component` for data access layer | `@Repository public interface UserRepository` |

---

## Web / REST Controller

| Annotation | Description | Example |
|------------|-------------|---------|
| `@RestController` | Marks a class as REST controller (combines `@Controller` + `@ResponseBody`) | `@RestController public class ProductController` |
| `@RequestMapping` | Maps HTTP requests to handler methods/classes | `@RequestMapping("/api/products")` |
| `@GetMapping` | Shortcut for `@RequestMapping(method = GET)` | `@GetMapping("/{id}")` |
| `@PostMapping` | Shortcut for `@RequestMapping(method = POST)` | `@PostMapping("/register")` |
| `@PutMapping` | Shortcut for `@RequestMapping(method = PUT)` | `@PutMapping("/products/{id}")` |
| `@DeleteMapping` | Shortcut for `@RequestMapping(method = DELETE)` | `@DeleteMapping("/products/{id}")` |
| `@PathVariable` | Binds URI template variable to method parameter | `@PathVariable Long id` |
| `@RequestParam` | Binds query parameter to method parameter | `@RequestParam(defaultValue = "0") int page` |
| `@RequestBody` | Binds HTTP request body to a Java object | `@RequestBody CreateOrderRequest request` |

---

## Dependency Injection

| Annotation | Description | Example |
|------------|-------------|---------|
| `@Autowired` | Injects dependencies automatically by type | `@Autowired private ProductService productService;` |
| `@Bean` | Declares a bean to be managed by Spring container | `@Bean public PasswordEncoder passwordEncoder()` |
| `@Value` | Injects values from properties file | `@Value("${jwt.secret}") private String secretKey;` |

---

## JPA / Hibernate (Entity)

| Annotation | Description | Example |
|------------|-------------|---------|
| `@Entity` | Marks class as JPA entity (database table) | `@Entity public class Product` |
| `@Table` | Specifies table name | `@Table(name = "users")` |
| `@Id` | Marks field as primary key | `@Id private Long id;` |
| `@GeneratedValue` | Configures auto-generation strategy for primary key | `@GeneratedValue(strategy = GenerationType.IDENTITY)` |
| `@Column` | Customizes column mapping | `@Column(nullable = false, unique = true)` |
| `@OneToMany` | Defines one-to-many relationship | `@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)` |
| `@ManyToOne` | Defines many-to-one relationship | `@ManyToOne(fetch = FetchType.LAZY)` |
| `@JoinColumn` | Specifies foreign key column | `@JoinColumn(name = "user_id")` |
| `@Enumerated` | Maps enum to database column | `@Enumerated(EnumType.STRING)` |
| `@PrePersist` | Callback before entity is inserted | `@PrePersist protected void onCreate()` |
| `@PreUpdate` | Callback before entity is updated | `@PreUpdate protected void onUpdate()` |

---

## Validation

| Annotation | Description | Example |
|------------|-------------|---------|
| `@Valid` | Triggers validation on nested object | `@Valid @RequestBody RegisterRequest request` |
| `@NotBlank` | Field must not be null/empty/whitespace (String) | `@NotBlank(message = "Name is required")` |
| `@NotNull` | Field must not be null | `@NotNull(message = "Price is required")` |
| `@Size` | String/collection size constraints | `@Size(min = 2, max = 100)` |
| `@Email` | Must be valid email format | `@Email(message = "Please provide a valid email")` |
| `@Pattern` | Must match regex pattern | `@Pattern(regexp = "^[a-zA-Z\\s]+$")` |
| `@Min` | Minimum numeric value | `@Min(value = 1)` |
| `@Max` | Maximum numeric value | `@Max(value = 5)` |
| `@PositiveOrZero` | Number must be >= 0 | `@PositiveOrZero(message = "Price must be zero or greater")` |

---

## Security

| Annotation | Description | Example |
|------------|-------------|---------|
| `@EnableWebSecurity` | Enables Spring Security web configuration | `@EnableWebSecurity public class SecurityConfig` |
| `@EnableMethodSecurity` | Enables method-level security annotations | `@EnableMethodSecurity` |
| `@PreAuthorize` | Authorization check before method execution | `@PreAuthorize("hasRole('ADMIN')")` |
| `@AuthenticationPrincipal` | Injects currently authenticated user | `@AuthenticationPrincipal User user` |

---

## Configuration

| Annotation | Description | Example |
|------------|-------------|---------|
| `@Configuration` | Marks class as source of bean definitions | `@Configuration public class SecurityConfig` |
| `@Transactional` | Defines transaction boundaries | `@Transactional public void createProduct()` |

---

## Testing

| Annotation | Description | Example |
|------------|-------------|---------|
| `@SpringBootTest` | Loads full application context for integration tests | `@SpringBootTest class EcomdemoApplicationTests` |
| `@Test` | Marks a method as a test case | `@Test void contextLoads()` |

---

## Jackson (JSON)

| Annotation | Description | Example |
|------------|-------------|---------|
| `@JsonIgnore` | Excludes field from JSON serialization/deserialization | `@JsonIgnore private User user;` |

---

## Java Core

| Annotation | Description | Example |
|------------|-------------|---------|
| `@Override` | Indicates method overrides superclass method | `@Override protected void doFilterInternal()` |

---

## Quick Examples

### Creating an Entity
```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ProductImage> images;
}
```

### Creating a REST Controller
```java
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody CreateProductRequest request) {
        return productService.create(request);
    }
}
```

### Creating a Service
```java
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Product create(CreateProductRequest request) {
        // business logic
    }
}
```

### DTO with Validation
```java
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 128)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;
}
```

---

## Annotation Categories Summary

| Category | Common Annotations |
|----------|-------------------|
| **Stereotype** | `@Component`, `@Service`, `@Repository`, `@RestController` |
| **Request Mapping** | `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` |
| **Request Data** | `@PathVariable`, `@RequestParam`, `@RequestBody` |
| **JPA Relationships** | `@OneToMany`, `@ManyToOne`, `@JoinColumn` |
| **Validation** | `@Valid`, `@NotBlank`, `@NotNull`, `@Size`, `@Email`, `@Pattern` |
| **Security** | `@PreAuthorize`, `@AuthenticationPrincipal` |
