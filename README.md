# Library Management System

## Overview

The Library Management System is a Spring Boot-based web application that provides RESTful APIs for managing books, library members, and book transactions. The system helps automate library operations such as adding books, issuing books, returning books, and tracking book availability.

## Features

* Add, update, delete, and view books
* Manage library members
* Issue books to members
* Return issued books
* Track book availability
* Search books by title, author, or category
* RESTful API architecture
* Database integration using JPA/Hibernate
* Exception handling and validation

## Technologies Used

* Java 17+
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* REST API
* IntelliJ IDEA
* Lombok (Optional)

# Project Architecture

```text
src/main/java
│
├── controller
│   ├── BookController.java
│   ├── MemberController.java
│
├── service
│   ├── BookService.java
│   ├── MemberService.java
│
├── repository
│   ├── BookRepository.java
│   ├── MemberRepository.java
│
├── entity
│   ├── Book.java
│   ├── Member.java
│
├── exception
│   ├── ResourceNotFoundException.java
│
└── LibraryManagementApplication.java
```

# Database Configuration

Update the `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

# API Endpoints

### Book APIs

| Method | Endpoint        | Description    |
| ------ | --------------- | -------------- |
| GET    | /api/books      | Get all books  |
| GET    | /api/books/{id} | Get book by ID |
| POST   | /api/books      | Add a new book |
| PUT    | /api/books/{id} | Update book    |
| DELETE | /api/books/{id} | Delete book    |

### Member APIs

| Method | Endpoint          | Description     |
| ------ | ----------------- | --------------- |
| GET    | /api/members      | Get all members |
| POST   | /api/members      | Add a member    |
| PUT    | /api/members/{id} | Update member   |
| DELETE | /api/members/{id} | Delete member   |

# Running the Application

1. Clone the repository.

```bash
git clone <repository-url>
```

2. Navigate to the project directory.

```bash
cd LibraryManagementSystem
```

3. Configure MySQL in `application.properties`.

4. Run the application.

```bash
mvn spring-boot:run
```

5. Access the application:

```text
http://localhost:8080
```

# Future Enhancements

* JWT Authentication and Authorization
* Role-Based Access Control (Admin/User)
* Swagger API Documentation
* Email Notifications
* Fine Management System
* Book Reservation System
* Docker Containerization
* Deployment on AWS

## Author

Developed using Spring Boot and REST API architecture for efficient library management.

