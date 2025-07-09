# ThatsMySpot

A campus-wide study space scheduling tool designed for Western Michigan University. This application helps students and staff discover and reserve available study spaces on campus, with support for room searches based on time, building, and amenities.

## 🎯 Project Overview

ThatsMySpot is a full-stack web application built with modern Java technologies, featuring a clean, responsive UI and robust backend architecture. The system supports multiple user roles, real-time booking management, and is designed for scalability and maintainability.

## ✨ Key Features

### 🔐 Authentication & Authorization
- **Multi-role system**: Guest, Authenticated User, and Admin roles
- **Secure login/logout** with bcrypt password hashing
- **Role-based access control** for different user permissions
- **Session management** with Spring Security

### 🏢 Room Management
- **Room discovery** with search and filter capabilities
- **Building-based organization** with room-to-building relationships
- **Feature tracking** (whiteboard, projector, charging outlets, etc.)
- **Access level control** (Open/Restricted rooms)
- **Soft delete** functionality for room deactivation

### 📅 Booking System
- **Real-time availability** checking
- **Double-booking prevention** with server-side validation
- **Flexible booking windows** (configurable time slots)
- **Booking history** for users
- **Email confirmation** system (configurable)

### 👥 User Management
- **User registration** and profile management
- **Admin dashboard** for user and booking oversight
- **Role promotion/demotion** capabilities
- **Usage reporting** and analytics

### 🎨 WMU Branding
- **Official WMU color scheme** (Gold: #F1C500, Brown: #532E1F)
- **WMU logo integration** following brand guidelines
- **Responsive design** optimized for all devices

## 🛠️ Tech Stack

### Backend
- **Java 17** with Spring Boot 3.x
- **Spring Data JPA** for database operations
- **Spring Security** for authentication and authorization
- **PostgreSQL** as the primary database
- **Flyway** for database migrations
- **Thymeleaf** for server-side templating
- **Jackson** for JSON processing
- **Bcrypt** for password hashing

### Frontend
- **Thymeleaf** templates with Bootstrap 5
- **Responsive design** for mobile and desktop
- **Bootstrap components** for consistent UI
- **WMU brand colors** and styling

### DevOps & Infrastructure
- **Docker** containerization
- **Docker Compose** for local development
- **GitHub Actions** ready for CI/CD
- **Maven** for dependency management

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- Docker and Docker Compose

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd first_Cursor
   ```

2. **Start the application with Docker**
   ```bash
   docker-compose up --build
   ```

3. **Access the application**
   - Main app: [http://localhost:8080](http://localhost:8080)
   - API docs: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Manual Setup (Alternative)

1. **Start PostgreSQL**
   ```bash
   docker run --name thatsmyspot-db \
     -e POSTGRES_DB=thatsmyspot \
     -e POSTGRES_USER=postgres \
     -e POSTGRES_PASSWORD=postgres \
     -p 5432:5432 -d postgres:15
   ```

2. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

## 📊 Database Schema

### Core Entities
- **Users**: Authentication, roles, and profile information
- **Buildings**: Campus building information
- **Rooms**: Study spaces with features and capacity
- **Bookings**: Reservation system with time slots

### Key Relationships
- Rooms belong to Buildings (mandatory)
- Bookings link Users to Rooms
- Soft deletes for data integrity
- JSONB storage for flexible room features

## 🔌 API Endpoints

### Authentication
- `POST /api/register` - User registration
- `POST /api/login` - User authentication

### Rooms
- `GET /api/rooms` - List all rooms
- `GET /api/rooms/{id}` - Get specific room
- `POST /api/rooms` - Create room (Admin)
- `PUT /api/rooms/{id}` - Update room (Admin)
- `DELETE /api/rooms/{id}` - Deactivate room (Admin)

### Bookings
- `GET /api/bookings?userId={id}` - Get user's bookings
- `POST /api/bookings` - Create booking
- `DELETE /api/bookings/{id}` - Cancel booking

### Buildings
- `GET /api/buildings` - List all buildings
- `POST /api/buildings` - Create building (Admin)
- `PUT /api/buildings/{id}` - Update building (Admin)

## 🎨 UI Features

### Public Pages
- **Home**: Search interface with room discovery
- **Room Listings**: Grid view of available rooms
- **Login/Signup**: User authentication forms

### Admin Dashboard
- **Room Management**: CRUD operations for rooms
- **User Management**: User oversight and role management
- **Booking Overview**: All system bookings
- **Reports**: Usage analytics and exports

## 🔒 Security Features

- **Bcrypt password hashing** for secure authentication
- **CSRF protection** enabled
- **Role-based route guarding** for admin functions
- **Input validation** and sanitization
- **SQL injection prevention** via JPA

## 📈 Scalability Considerations

- **Database indexing** for performance
- **Connection pooling** with HikariCP
- **Stateless authentication** design
- **Modular architecture** for easy scaling
- **Docker containerization** for deployment flexibility

## 🧪 Testing

### Manual Testing
1. **User Registration**: Create a new user account
2. **Room Discovery**: Browse available rooms
3. **Booking Creation**: Reserve a room for a time slot
4. **Admin Functions**: Access admin dashboard and manage rooms/users

### API Testing
- Use Swagger UI at `/swagger-ui.html`
- Test all endpoints with sample data
- Verify authentication and authorization

## 🚀 Deployment

### Docker Deployment
```bash
# Build and run
docker-compose up --build

# Production build
docker build -t thatsmyspot .
docker run -p 8080:8080 thatsmyspot
```

### Environment Configuration
- Database connection via `application.yml`
- Email settings for notifications
- JWT secret for production
- Logging configuration

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📝 License

This project is developed for Western Michigan University. Please contact the development team for licensing information.

## 📞 Support

For technical support or questions about the project:
- Check the API documentation at `/swagger-ui.html`
- Review the database migrations in `src/main/resources/db/migration/`
- Contact the development team

---

**ThatsMySpot** - Making campus study space management simple and efficient. 