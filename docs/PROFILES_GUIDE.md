# Spring Boot Profiles Configuration

This application uses Spring Boot profiles to manage different configurations for development and production environments.

## Available Profiles

### 1. **Development Profile** (`dev`)
- **Purpose**: Used during local development
- **Default Database**: `jdbc:postgresql://localhost:5432/ecomdemo_dev`
- **Features**:
  - Detailed SQL logging enabled
  - Debug-level logging for application and Spring components
  - Stack traces included in error responses
  - Auto-schema update enabled (`ddl-auto=update`)
  - Spring DevTools enabled for hot reload

### 2. **Production Profile** (`prod`)
- **Purpose**: Used in production deployment
- **Features**:
  - SQL logging disabled for performance
  - Minimal logging (WARN level)
  - No stack traces in error responses for security
  - Schema validation only (`ddl-auto=validate`)
  - Connection pool optimization
  - Response compression enabled
  - Secure session cookies

## How to Use

### Running with a Specific Profile

#### Method 1: Using Maven
```bash
# Development
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Production
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

#### Method 2: Using Environment Variable
```bash
# Windows PowerShell
$env:SPRING_PROFILE="prod"; ./mvnw spring-boot:run

# Windows CMD
set SPRING_PROFILE=prod && mvnw spring-boot:run

# Linux/Mac
export SPRING_PROFILE=prod
./mvnw spring-boot:run
```

#### Method 3: Using JAR File
```bash
java -jar -Dspring.profiles.active=prod target/ecomdemo-0.0.1-SNAPSHOT.jar
```

#### Method 4: Using application.properties
The default profile is set to `dev` in `application.properties`:
```properties
spring.profiles.active=${SPRING_PROFILE:dev}
```

### IntelliJ IDEA / Eclipse
1. Open Run/Debug Configurations
2. Add VM options or Program arguments:
   - VM options: `-Dspring.profiles.active=dev`
   - Environment variables: `SPRING_PROFILE=dev`

## Environment Variables

### Development Profile (Optional)
```properties
DB_URL=jdbc:postgresql://localhost:5432/ecomdemo_dev
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=YourSecretKey
```

### Production Profile (Required)
```properties
DB_URL=jdbc:postgresql://your-prod-host:5432/ecomdemo_prod
DB_USERNAME=your_prod_username
DB_PASSWORD=your_prod_password
JWT_SECRET=YourProductionSecretKey
PORT=8080  # Optional, defaults to 8080
```

## Key Differences Between Profiles

| Feature | Development | Production |
|---------|-------------|------------|
| SQL Logging | ✅ Enabled | ❌ Disabled |
| Log Level | DEBUG | WARN/INFO |
| DDL Auto | update | validate |
| Error Details | Full stack trace | Minimal |
| Connection Pool | Default | Optimized (5-10) |
| Compression | ❌ | ✅ Enabled |
| DevTools | ✅ Enabled | ❌ Disabled |
| Session Cookies | Standard | Secure + HttpOnly |

## Best Practices

1. **Never commit sensitive data**: Use environment variables for credentials
2. **Production checklist**:
   - Ensure all required environment variables are set
   - Database schema should be managed with migrations (Flyway/Liquibase)
   - Use `validate` mode for `ddl-auto` in production
   - Set strong JWT secret key
3. **Development**: 
   - Keep local database separate from production
   - Use descriptive naming (e.g., `ecomdemo_dev`)

## Troubleshooting

### Profile not loading
- Check the active profile: Look for log line at startup:
  ```
  The following 1 profile is active: "dev"
  ```
- Verify environment variable is set correctly
- Check for typos in profile name

### Database connection issues
- Verify environment variables are set
- Check database is running and accessible
- Ensure credentials are correct for the active profile
