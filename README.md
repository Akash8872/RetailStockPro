# Inventory Management System

A production-ready Inventory Management System built with Spring Boot, Thymeleaf, and PostgreSQL.

## Features
- **Product Management**: CRUD operations for products, suppliers, and categories.
- **Inventory Tracking**: Real-time stock updates and low-stock alerts.
- **Reporting**: Dashboard with key metrics (Total Products, Low Stock, Inventory Value).
- **Security**: Role-based authentication (Admin/User).
- **REST API**: Documented API for integration.

## Tech Stack
- **Backend**: Java 17, Spring Boot 3.3.0
- **Database**: H2 (Dev), PostgreSQL (Prod)
- **Frontend**: Thymeleaf, Bootstrap 5
- **Build**: Maven
- **DevOps**: Docker, Docker Compose, GitHub Actions

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- Docker (optional)

### Local Development (H2 Database)
1. Clone the repository.
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```
3. Access the app at `http://localhost:8080`.
4. Login with default credentials:
   - **Admin**: `admin` / `admin`
   - **User**: `user` / `user`

### Running with Docker (PostgreSQL)
1. Build and start containers:
   ```bash
   docker-compose up --build
   ```
2. The app will be available at `http://localhost:8080`.

## API Documentation
Once the application is running, access Swagger UI at:
`http://localhost:8080/swagger-ui/index.html`

## Deployment
The application is containerized and ready for deployment on platforms like Render, Heroku, or AWS.

### Deploy to Render/Heroku
1. Push the code to GitHub.
2. Connect your repository to Render/Heroku.
3. Set environment variables:
   - `SPRING_DATASOURCE_URL`: Your PostgreSQL URL
   - `SPRING_DATASOURCE_USERNAME`: DB User
   - `SPRING_DATASOURCE_PASSWORD`: DB Password

### Deploy to AWS EC2
For detailed instructions on deploying to AWS EC2, please refer to the [EC2 Deployment Guide](deployment/EC2_GUIDE.md).


## CI/CD
A GitHub Actions workflow is included in `.github/workflows/maven.yml` which:
- Compiles the code
- Runs unit and integration tests
- Builds the Docker image

## Project Structure
```
src/
├── main/
│   ├── java/com/retailstockpro/inventory/
│   │   ├── config/       # Security & App Config
│   │   ├── controller/   # Web & API Controllers
│   │   ├── model/        # JPA Entities
│   │   ├── repository/   # Data Access Layer
│   │   ├── service/      # Business Logic
│   │   └── security/     # Auth Logic
│   └── resources/
│       ├── templates/    # Thymeleaf Views
│       └── application.properties
```
