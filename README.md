# User Management

A Spring Boot web application for managing users with full CRUD operations via a browser-based UI.

## What it does

Provides a simple admin interface to create, view, edit, and delete users. Each user has a name, email, phone number, and a role (Admin, Manager, or Employee). Email addresses must be unique across all users.

## Tech stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.5 / Java 17 |
| Web | Spring MVC + Thymeleaf |
| Persistence | Spring Data JPA + H2 (in-memory) |
| Validation | Jakarta Bean Validation |
| UI | Bootstrap 5.3 + Bootstrap Icons |
| Build | Maven |

## Project structure

```
src/main/java/com/usermgmt/
├── UserManagementApplication.java   # Entry point
├── entity/User.java                 # JPA entity (id, firstName, lastName, email, role, phone)
├── enums/Role.java                  # ADMIN | MANAGER | EMPLOYEE
├── repository/UserRepository.java   # Spring Data repository
├── service/UserService.java         # Service interface
├── service/UserServiceImpl.java     # Service implementation
└── controller/
    ├── HomeController.java          # Redirects / → /users
    └── UserController.java          # CRUD endpoints under /users

src/main/resources/
├── application.properties           # Config (H2, JPA, Thymeleaf, port 8080)
└── templates/users/
    ├── list.html                    # User table with edit/delete actions
    └── form.html                   # Add/edit form with validation feedback
```

## Endpoints

| Method | Path | Description |
|---|---|---|
| GET | `/` | Redirect to `/users` |
| GET | `/users` | List all users |
| GET | `/users/new` | Show add-user form |
| POST | `/users/new` | Create a user |
| GET | `/users/{id}/edit` | Show edit form |
| POST | `/users/{id}/edit` | Update a user |
| POST | `/users/{id}/delete` | Delete a user |
| GET | `/h2-console` | H2 database console |

## Running the app

```bash
./mvnw spring-boot:run
```

Open `http://localhost:8080` in a browser.

> Data is stored in-memory and resets on every restart (`ddl-auto=create-drop`).
