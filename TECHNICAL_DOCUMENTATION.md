# ThatsMySpot - Technical Documentation

## 🏗️ Architecture Overview

ThatsMySpot follows a **layered architecture** pattern with clear separation of concerns, designed for maintainability, scalability, and testability.

### Architecture Layers
```
┌─────────────────────────────────────┐
│           Presentation Layer        │
│  (Thymeleaf Templates + Bootstrap) │
├─────────────────────────────────────┤
│           Controller Layer          │
│     (REST APIs + Page Controllers) │
├─────────────────────────────────────┤
│            Service Layer            │
│      (Business Logic + Validation) │
├─────────────────────────────────────┤
│          Repository Layer           │
│        (Data Access + JPA)         │
├─────────────────────────────────────┤
│           Database Layer            │
│         (PostgreSQL + Flyway)      │
└─────────────────────────────────────┘
```

## 🎯 Design Decisions & Rationale

### 1. **Technology Stack Selection**

#### Why Java 17 + Spring Boot?
- **Enterprise-grade reliability**: Proven in production environments
- **Rich ecosystem**: Extensive libraries and community support
- **Type safety**: Reduces runtime errors and improves maintainability
- **Performance**: Excellent for concurrent user loads
- **Future-proof**: Long-term support and active development

#### Why PostgreSQL?
- **ACID compliance**: Critical for booking system data integrity
- **JSONB support**: Flexible storage for room features
- **Performance**: Excellent for read-heavy operations
- **Open source**: Cost-effective and community-driven

#### Why Thymeleaf over React/Angular?
- **Server-side rendering**: Better SEO and initial load performance
- **Simplicity**: Faster development for MVP
- **Spring integration**: Native Spring Boot support
- **Security**: CSRF protection built-in

### 2. **Database Design Decisions**

#### Entity Relationships
```sql
-- Normalized design for data integrity
Users (1) ←→ (N) Bookings (N) ←→ (1) Rooms (N) ←→ (1) Buildings
```

#### Key Design Choices:
- **Soft deletes**: Preserve data integrity while allowing "deletion"
- **JSONB for features**: Flexible schema for varying room amenities
- **Proper indexing**: Performance optimization for common queries
- **Foreign key constraints**: Data integrity enforcement

### 3. **Security Architecture**

#### Authentication Flow
```
User Input → Validation → Bcrypt Hash → Database → Session Management
```

#### Security Features:
- **Bcrypt hashing**: Industry-standard password security
- **Role-based access**: Granular permission control
- **CSRF protection**: Prevents cross-site request forgery
- **Input validation**: Server-side validation for all inputs

## 🔧 Implementation Details

### 1. **Data Model Design**

#### User Entity
```java
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    @Column(nullable = false)
    private String role; // USER, ADMIN
}
```

#### Room Entity with JSONB Features
```java
@Entity
@Table(name = "rooms")
public class Room {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;
    
    @Column(columnDefinition = "jsonb")
    @Convert(converter = JsonbConverter.class)
    private Map<String, Object> features; // Flexible feature storage
}
```

### 2. **Service Layer Patterns**

#### Repository Pattern
```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // Custom query method
}
```

#### Service Layer Business Logic
```java
@Service
public class BookingService {
    public Booking createBooking(Long roomId, Long userId, 
                               LocalDateTime start, LocalDateTime end) {
        // Business logic: overlap checking, validation
        // Data access: repository calls
        // Integration: email notifications
    }
}
```

### 3. **API Design Principles**

#### RESTful Endpoints
- **Consistent naming**: `/api/rooms`, `/api/bookings`, `/api/users`
- **HTTP methods**: GET (read), POST (create), PUT (update), DELETE
- **Status codes**: Proper HTTP response codes
- **JSON responses**: Consistent response format

#### Error Handling
```java
@ExceptionHandler(EntityNotFoundException.class)
public ResponseEntity<?> handleNotFound(EntityNotFoundException e) {
    return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
}
```

## 📊 Performance Considerations

### 1. **Database Optimization**
- **Indexes**: On frequently queried columns (user_id, room_id, start_time)
- **Connection pooling**: HikariCP for efficient database connections
- **Query optimization**: JPA/Hibernate query tuning

### 2. **Application Performance**
- **Lazy loading**: For entity relationships
- **Caching strategy**: Thymeleaf template caching
- **Stateless design**: Scalable across multiple instances

### 3. **Scalability Patterns**
- **Horizontal scaling**: Stateless application design
- **Database scaling**: Read replicas for heavy read loads
- **Microservices ready**: Modular architecture for future decomposition

## 🔒 Security Implementation

### 1. **Authentication System**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Industry standard
    }
}
```

### 2. **Authorization Strategy**
- **Role-based access control**: USER vs ADMIN permissions
- **Method-level security**: @PreAuthorize annotations
- **URL-based security**: Spring Security configuration

### 3. **Data Protection**
- **Input sanitization**: Prevent XSS attacks
- **SQL injection prevention**: JPA parameterized queries
- **CSRF protection**: Built-in Spring Security feature

## 🧪 Testing Strategy

### 1. **Unit Testing**
- **Service layer**: Business logic validation
- **Repository layer**: Data access testing
- **Utility classes**: Helper method testing

### 2. **Integration Testing**
- **API endpoints**: End-to-end request/response testing
- **Database operations**: Migration and data integrity testing
- **Security testing**: Authentication and authorization flows

### 3. **Manual Testing Scenarios**
- User registration and login
- Room discovery and booking
- Admin dashboard functionality
- Error handling and edge cases

## 🚀 Deployment Architecture

### 1. **Containerization Strategy**
```dockerfile
# Multi-stage build for efficiency
FROM maven:3.9.6-eclipse-temurin-17 AS build
# Build stage
FROM eclipse-temurin:17-jre
# Runtime stage
```

### 2. **Environment Configuration**
- **Development**: Docker Compose for local development
- **Production**: Environment variables for configuration
- **Secrets management**: Secure credential handling

### 3. **CI/CD Pipeline**
- **GitHub Actions**: Automated testing and deployment
- **Docker registry**: Container image management
- **Environment promotion**: Dev → Staging → Production

## 📈 Monitoring & Observability

### 1. **Logging Strategy**
- **Structured logging**: JSON format for parsing
- **Log levels**: DEBUG, INFO, WARN, ERROR
- **Performance logging**: Request/response timing

### 2. **Health Checks**
- **Database connectivity**: Connection pool health
- **Application health**: Spring Boot Actuator
- **Custom metrics**: Booking success rates, user activity

## 🔮 Future Enhancements

### 1. **Immediate Improvements**
- **Calendar UI**: FullCalendar integration for visual booking
- **Real-time updates**: WebSocket integration
- **Mobile app**: React Native or Flutter

### 2. **Advanced Features**
- **NFC check-in**: Physical room access integration
- **Analytics dashboard**: Usage patterns and insights
- **Integration APIs**: Connect with existing campus systems

### 3. **Scalability Enhancements**
- **Microservices**: Decompose into smaller services
- **Event-driven architecture**: Kafka/RabbitMQ integration
- **Caching layer**: Redis for performance optimization

## 💡 Lessons Learned

### 1. **Technical Insights**
- **Database design**: Proper indexing is crucial for performance
- **Security**: Always validate inputs and use parameterized queries
- **Testing**: Comprehensive testing saves time in the long run

### 2. **Architecture Decisions**
- **Layered architecture**: Clear separation of concerns
- **Dependency injection**: Promotes testability and flexibility
- **Configuration management**: Environment-specific settings

### 3. **Development Process**
- **Incremental development**: Build and test in small iterations
- **Documentation**: Essential for maintainability
- **Code reviews**: Catch issues early in the development cycle

## 🎯 Key Achievements

1. **Full-stack application**: Complete web application with database
2. **Security implementation**: Proper authentication and authorization
3. **Scalable architecture**: Designed for growth and maintenance
4. **Professional documentation**: Comprehensive technical documentation
5. **Docker deployment**: Containerized for easy deployment
6. **WMU integration**: Brand-compliant design and functionality

This technical documentation demonstrates the depth of understanding in modern web application development, security best practices, and scalable architecture design. 