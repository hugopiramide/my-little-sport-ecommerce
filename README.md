# My Little Sport Ecommerce Backend

A robust e-commerce platform built with Spring Boot, featuring a dual-purpose architecture that serves both a headless REST API and a server-side rendered administration dashboard.

## Project Structure

The project is primarily contained within the `backend/` directory and follows standard Spring Boot conventions:

```text
backend/
├── src/main/java/com/ecommerce/backend/
│   ├── config/          
│   ├── controller/
│   │   ├── mvc/         
│   │   └── rest/        
│   ├── dto/             
│   ├── mapper/          
│   ├── model/           
│   ├── repository/      
│   └── service/         
├── src/main/resources/
│   ├── static/          
│   ├── templates/       
│   ├── application.properties
│   └── data.sql        
└── pom.xml              
```

## Internal Architecture & Patterns

### 1. Generic Standardized CRUD
The application uses a robust generic architecture to ensure consistency and minimize boilerplate code:
- **`BaseCrudService<T, CreateIn, UpdateIn, ID>`**: A generic service interface that standardizes find, create, update, and delete operations across all entities.
- **`BaseRestController<T, CreateIn, UpdateIn>`**: An abstract controller that automatically provides REST endpoints for CRUD operations, including integrated support for:
    - **Pagination**: Defaulting to 10 items per page with Spring Data.
    - **HATEOAS**: Providing hypermedia links for navigable APIs.
    - **Validation**: Automatic `@Valid` checking for request DTOs.

### 2. Compiled Entity Mapping
Instead of manual mapping or reflection-based tools, we use **MapStruct**. This ensures:
- **Zero Runtime Overhead**: Mappers are generated as plain Java classes during the compile phase.
- **Type Safety**: Errors in mapping are caught at compile-time, not runtime.
- **Decoupling**: The persistence layer (Entities) is never exposed directly through the API.

---

## Security & Authentication

The project implements a **Dual-Filter Chain** security model through `SecurityConfig`:

### State-less REST API (`/api/**`)
- **JWT Authentication**: Uses a custom `JwtAuthenticationFilter` to validate Bearer tokens.
- **Header-based**: No sessions are created on the server for these requests.
- **Role-based Access**: Specific endpoints restricted to `ADMIN` only.

### State-full Admin Management (`/`, `/login`, `/management/**`)
- **Form Login**: Standard server-side login flow with session management.
- **CSRF Protection**: Default enabled for the MVC templates.
- **Access Control**: The `/` path redirecting to management is strictly restricted to users with the `ADMIN` role.

> [!IMPORTANT]
> JWT secrets are handled via `SecurityProperties` and should be defined in `application-secrets.properties`. Never commit actual secrets to the repository, if you are junior, it's ok... 

---

## Administration Dashboard

The built-in management panel is a full-featured UI for store operators:

- **Entity Listings**: Tabular views with built-in pagination and sorting functionality.
- **Advanced Searching**: Integrated search bars in templates to filter by product name, user, or status.
- **Inline Management**: Dedicated views for managing complex relationships (e.g., viewing Order Items within a specific Order).
- **Responsive Design**: Custom CSS in `static/css/` provides a premium, responsive experience without heavy external libraries.

---

## API Overview

All REST endpoints are prefixed with `/api`. Most follow the standard REST pattern:

- `GET /api/{entity}`: Paged list with HATEOAS links.
- `GET /api/{entity}/all`: Full list without pagination.
- `GET /api/{entity}/{id}`: Detail view.
- `POST /api/{entity}`: Create new entry.
- `PUT /api/{entity}/{id}`: Update existing entry.
- `DELETE /api/{entity}/{id}`: Soft or hard removal.

### Example: Product API
`GET /api/products?page=0&size=5`
Returns a JSON payload with the product list, total pages, and hypermedia links.

---

## Getting Started

### Prerequisites
- **Java 21**: Required for modern language features and record support.
- **MySQL**: A local instance on port `3306`.
- **Database Schema**: Ensure a database named `eShop` exists.

### Startup Instructions
1. **Move to backend**: `cd backend`
2. **Compile**: `./mvnw compile` (Crucial for generating mappers).
3. **Run**: `./mvnw spring-boot:run`
4. **Login**: Access `http://localhost:8080/login` with:
   - **User**: `admin`
   - **Password**: `AdminAa1!23dmin` (See `data.sql` for the Bcrypt hash).

---

## Development Workflow

To add a new feature or entity to the system, follow these steps:

### 1. Define the Model
Create the JPA entity in `com.ecommerce.backend.model`. If the entity has sensitive data, use **Value Objects** (VOs) like `PersonalData` or `Password` to encapsulate logic and validation.

### 2. Create the Repository
Extend `JpaRepository` or `PagingAndSortingRepository` in `com.ecommerce.backend.repository`.

### 3. Implement DTOs & Mappers
- Create Request/Response DTOs in `com.ecommerce.backend.dto`.
- Define a MapStruct interface in `com.ecommerce.backend.mapper`. Ensure you run `./mvnw compile` to generate the implementation.

### 4. Service Layer
- Create a service interface extending `BaseCrudService`.
- Implement the service by extending `BaseCrudServiceImpl`. This provides the default CRUD logic automatically.

### 5. Controller Layer
- **REST**: Extend `BaseRestController` to expose the API under `/api`.
- **MVC**: Create a controller in `controller/mvc` and a corresponding Thymeleaf template in `templates/` to add it to the Admin Panel.

---

## Data Model

The application manages several core entities to support a complete e-commerce lifecycle:

- **User**: Handles authentication and profile data. Supports roles (`ADMIN`, `USER`) and uses Value Objects for `PersonalData`, `Birthday`, and `Password`.
- **Product & ProductVariant**: Products contain base info, while Variants handle specific sizes, stock levels, and price modifiers.
- **Category**: Hierarchical organization for products.
- **Order & OrderItem**: Tracks purchases, shipping addresses, and status (PENDING, SENT, DELIVERED, CANCELLED).
- **Cart & CartItems**: Manages the user's active shopping session.
- **UserFavorite**: Allows users to save products and request notifications for restocks.
- **ProductReview**: User-generated content with a moderation status (`PENDING`, `APPROVED`, `REJECTED`).

---

## Design Principles & Quality

The codebase is built with a focus on long-term maintainability and modern software engineering standards:

### 1. SOLID Principles
- **Single Responsibility**: Content and logic are strictly decoupled across Controllers, Services, and Repositories.
- **Open/Closed**: Our generic base classes allow for extending functionality without modifying existing core logic.
- **Interface Segregation**: Services are split into fine-grained interfaces (BaseRead, BaseCreate, etc.) to ensure clients only depend on what they use.

### 2. Clean Code & Readability
- **Self-Documenting Code**: Meaningful variable names and clear package structures reduce the need for excessive comments.
- **Immutability**: DTOs and Value Objects are designed to be as immutable as possible, reducing side effects and thread-safety issues.

### 3. Modern Design System
The Admin UI doesn't rely on heavy external frameworks. Instead, it uses a **Modular CSS** approach:
- **`management.css`**: Core layouts and theme tokens.
- **`management-list.css`**: Specific optimizations for tabular data and filtering controls.
- **Glassmorphism & Micro-animations**: Subtle UI enhancements to provide a premium feel to the management experience.

---
*Created by the hugopiramide*
