# Ticket Management System (CLI)

A command-line based Ticket Management System built with Java, designed for educational institutions to manage support tickets between students, staff, and administrators.

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Default Credentials](#default-credentials)
- [User Roles & Permissions](#user-roles--permissions)
- [Design Patterns](#design-patterns)
- [Database Schema](#database-schema)
- [Contributing](#contributing)
- [License](#license)

## Features

### Student Features
- Register new account
- Login/Logout
- Forgot password recovery
- Create support tickets
- View tickets by status (Open, Closed, Resolved, On Hold)
- View notifications

### Staff Features
- Login/Logout
- View assigned tickets
- Change ticket status
- Resolve tickets
- Close tickets
- View notifications

### Admin Features
- Login/Logout
- Register new staff members
- Register new students
- Manage users (update/delete)
- Create tickets on behalf of users
- Assign tickets to staff
- Change ticket priority
- Change ticket status
- Resolve/Close/Delete tickets
- Send notifications to users (individual, by role, or all)
- View all tickets with filters

## Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 25 | Core programming language |
| Maven | 3.x | Build automation & dependency management |
| SQLite | 3.51.1.0 | Embedded database |
| JUnit 5 | 5.10.1 | Unit testing framework |

## Architecture

The application follows a **layered architecture** with clear separation of concerns:

```
┌─────────────────────────────────────┐
│           UI Layer (Pages)          │
│   LoginPage, DashboardPages, etc.   │
├─────────────────────────────────────┤
│          Service Layer              │
│  UserService, TicketService, etc.   │
├─────────────────────────────────────┤
│         Repository Layer            │
│  UserRepo, TicketRepo, NotifRepo    │
├─────────────────────────────────────┤
│          Database Layer             │
│      SQLite (DBConnection)          │
└─────────────────────────────────────┘
```

## Project Structure

```
ticket-management-system-cli/
├── pom.xml                          # Maven configuration
├── README.md                        # Project documentation
└── src/
    ├── main/
    │   ├── java/com/junaidsultan/
    │   │   ├── Application.java     # Main entry point
    │   │   ├── config/
    │   │   │   └── DBConnection.java
    │   │   ├── entity/
    │   │   │   ├── User.java
    │   │   │   ├── Ticket.java
    │   │   │   └── Notification.java
    │   │   ├── enums/
    │   │   │   ├── Role.java        # STUDENT, STAFF, ADMIN
    │   │   │   ├── Priority.java    # LOW, MEDIUM, HIGH
    │   │   │   └── Status.java      # OPEN, RESOLVED, CLOSED, ON_HOLD
    │   │   ├── repository/
    │   │   │   ├── BaseRepository.java
    │   │   │   ├── IUserRepository.java
    │   │   │   ├── ITicketRepository.java
    │   │   │   ├── INotificationRepository.java
    │   │   │   ├── UserRepository.java
    │   │   │   ├── TicketRepository.java
    │   │   │   └── NotificationRepository.java
    │   │   ├── service/
    │   │   │   ├── IUserService.java
    │   │   │   ├── ITicketService.java
    │   │   │   ├── INotificationService.java
    │   │   │   ├── UserService.java
    │   │   │   ├── TicketService.java
    │   │   │   ├── NotificationService.java
    │   │   │   └── ServiceLocator.java
    │   │   ├── util/
    │   │   │   ├── PasswordUtil.java
    │   │   │   └── DatabaseSeeder.java
    │   │   └── ui/
    │   │       ├── core/
    │   │       │   ├── input/InputReader.java
    │   │       │   ├── panels/
    │   │       │   └── screen/Screen.java
    │   │       ├── dashboards/
    │   │       │   ├── admin/
    │   │       │   ├── auth/
    │   │       │   ├── shared/
    │   │       │   ├── staff/
    │   │       │   └── student/
    │   │       ├── scene_manager/
    │   │       │   ├── Page.java
    │   │       │   └── Session.java
    │   │       └── shared/
    │   │           └── DisplayHelper.java
    │   └── resources/
    └── test/
        └── java/com/junaidsultan/
```

## Prerequisites

- **Java 25** or higher (with preview features enabled)
- **Maven 3.6+**
- Terminal/Command Prompt with UTF-8 support (optional, for best display)

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/ticket-management-system-cli.git
   cd ticket-management-system-cli
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.junaidsultan.Application"
   ```

   Or compile and run directly:
   ```bash
   mvn clean compile
   java --enable-preview -cp target/classes com.junaidsultan.Application
   ```

## Usage

### Starting the Application

When you start the application, you'll see the login screen:

```
========================================
      TICKET MANAGEMENT SYSTEM
========================================

1. Login
2. Register (Student)
3. Forgot Password
0. Exit

Select option:
```

### Navigation

- Enter the number corresponding to your choice
- Press `0` to go back or exit
- Follow on-screen prompts for input

### Creating a Ticket (Student)

1. Login with student credentials
2. Select "Create Ticket" from the dashboard
3. Enter ticket title
4. Enter ticket description
5. Select priority (LOW, MEDIUM, HIGH)

### Managing Tickets (Staff/Admin)

1. Login with staff/admin credentials
2. View assigned/all tickets
3. Select a ticket to view details
4. Choose an action (Change Status, Resolve, Close, etc.)

## Default Credentials

The database is seeded with test accounts on first run:

| Role | Username | Password |
|------|----------|----------|
| Admin | `admin` | `admin123` |
| Staff | `staff1` | `staff123` |
| Student | `student1` | `student123` |

> **Note:** These are for testing purposes. Change them in production.

## User Roles & Permissions

### Student
| Permission | Allowed |
|------------|---------|
| Create Ticket | ✓ |
| View Own Tickets | ✓ |
| View Notifications | ✓ |
| Reset Password | ✓ |

### Staff
| Permission | Allowed |
|------------|---------|
| View Assigned Tickets | ✓ |
| Change Ticket Status | ✓ |
| Resolve Tickets | ✓ |
| Close Tickets | ✓ |
| View Notifications | ✓ |

### Admin
| Permission | Allowed |
|------------|---------|
| All Staff Permissions | ✓ |
| Register Staff/Students | ✓ |
| Manage Users | ✓ |
| Delete Tickets | ✓ |
| Assign Tickets | ✓ |
| Change Priority | ✓ |
| Send Notifications | ✓ |
| View All Tickets | ✓ |

## Design Patterns

This project implements several design patterns for maintainability and scalability:

### 1. Repository Pattern
Abstracts data access logic, providing a clean API for CRUD operations.
```java
public interface IUserRepository extends BaseRepository<User> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(Role role);
}
```

### 2. Service Layer Pattern
Encapsulates business logic separate from data access and presentation.
```java
public interface ITicketService {
    Ticket createTicket(String title, String description, Priority priority, String createdBy);
    boolean assignToStaff(String ticketId, String staffId);
}
```

### 3. Service Locator Pattern
Provides a central registry for service instances (lightweight dependency injection).
```java
ServiceLocator.getInstance().getTicketService();
```

### 4. State/Strategy Pattern (Page Navigation)
Each page implements the `Page` interface, allowing dynamic navigation.
```java
public interface Page {
    Page show(Screen screen, InputReader input);
}
```

### 5. Singleton Pattern
Used for database connection and service locator.
```java
DBConnection.getInstance().getConnection();
```

## Database Schema

### Users Table
| Column | Type | Description |
|--------|------|-------------|
| id | TEXT | Primary key (UUID) |
| username | TEXT | Unique username |
| email | TEXT | Unique email |
| name | TEXT | Full name |
| password_hash | TEXT | Hashed password with salt |
| role | TEXT | STUDENT, STAFF, or ADMIN |

### Tickets Table
| Column | Type | Description |
|--------|------|-------------|
| id | TEXT | Primary key (UUID) |
| title | TEXT | Ticket title |
| description | TEXT | Ticket description |
| status | TEXT | OPEN, RESOLVED, CLOSED, ON_HOLD |
| priority | TEXT | LOW, MEDIUM, HIGH |
| created_by | TEXT | User ID of creator |
| assigned_to | TEXT | User ID of assignee (nullable) |
| created_at | TEXT | ISO timestamp |
| updated_at | TEXT | ISO timestamp |

### Notifications Table
| Column | Type | Description |
|--------|------|-------------|
| id | TEXT | Primary key (UUID) |
| receiver_id | TEXT | User ID of recipient |
| title | TEXT | Notification title |
| message | TEXT | Notification content |
| is_read | INTEGER | 0 = unread, 1 = read |
| created_by | TEXT | User ID of sender |
| created_at | TEXT | ISO timestamp |

## Security

- **Password Hashing:** SHA-256 with random 16-byte salt
- **Password Storage:** Base64 encoded as `salt:hash`
- **Session Management:** In-memory session tracking

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

**Author:** Junaid Sultan  
**Version:** 1.0.0  
**Last Updated:** January 2026
