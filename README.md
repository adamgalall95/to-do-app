# Full-Stack Todo Management App

![Backend Tests](https://github.com/adamgalall95/to-do-app/actions/workflows/maven.yaml/badge.svg)

![Frontend Tests](https://github.com/adamgalall95/to-do-app/actions/workflows/node.js.yaml/badge.svg)

A full-stack **Todo Management System** built with React, TypeScript, Spring Boot and MySQL.

The system allows users to create new todo tasks, manage categories, update tasks, mark tasks as complete, duplicate tasks and remove tasks when required.

## Demo & Snippets

### Todo List

![Todo List](screenshot.png)

- **Hosted link:** TBC

The app allows users to create, view, update, categorise, complete, duplicate and delete todos.

---

## Requirements / Purpose

### MVP

The MVP provides users with the ability to:

- Add new todo tasks
- Create new categories
- View todo records
- Assign todos to categories
- Update todo information
- Change todo categories
- Mark todos as complete or incomplete
- Duplicate todo tasks
- Delete todo records
- Validate todo and category information
- Handle API errors

### Tech Stack

**Frontend**

- React
- TypeScript
- React Query
- React Hook Form
- Zod
- SCSS Modules
- Vite

**Backend**

- Java
- Spring Boot
- Spring Data JPA
- MySQL

**Testing**

- Vitest
- React Testing Library
- JUnit
- Mockito
- REST Assured
- H2

### Why this stack?

React and TypeScript provide the frontend, while React Query handles communication with the backend.

Spring Boot provides the REST API and JPA handles database access.

Zod and React Hook Form are used for form validation. The backend also validates requests and handles business rules before todo information is saved.

## Database Schema

### Category

| Field          | Data Type |
| -------------- | --------- |
| `categoryId`   | `BIGINT`  |
| `categoryName` | `VARCHAR` |

### Todo

| Field        | Data Type |
| ------------ | --------- |
| `id`         | `BIGINT`  |
| `task`       | `VARCHAR` |
| `categoryId` | `BIGINT`  |
| `completed`  | `BOOLEAN` |

---

## Build Steps

### Backend

From the project root:

```bash
./mvnw spring-boot:run
```

Make sure MySQL is running and the database configuration in `application.properties` matches your local setup.

### Frontend

```bash
cd frontend
npm install
npm run dev
```

The frontend uses the backend API to connect to the backend.

### Tests

Frontend:

```bash
npm test -- --run
```

Backend:

```bash
./mvnw test
```

---

## Design Goals / Approach

The main goal was to build a practical Todo management system and understand how todo information moves through a complete full-stack application.

```text
React Form
    ↓
Zod Validation
    ↓
React Query
    ↓
API Request
    ↓
Spring Boot Controller
    ↓
Service
    ↓
JPA Repository
    ↓
MySQL
```

Key design decisions:

- Keep frontend and backend validation separate.
- Use React Query for server state.
- Use React Hook Form for form management.
- Use Zod for frontend validation.
- Keep business rules in the backend service layer.
- Use a global exception handler for consistent API errors.
- Keep components and services separated so the code is easier to test and maintain.

---

## Features

- Todo CRUD operations
- Category creation
- Todo categorisation
- Todo completion
- Todo duplication
- React Query for API state
- React Hook Form
- Zod validation
- Backend validation
- Global API error handling
- Unit tests
- REST API tests
- Frontend component tests
- MySQL persistence

### Validation Rules

Examples of rules implemented include:

- Todo descriptions cannot be empty.
- Todo descriptions are trimmed before validation.
- A category must be selected.
- Category names cannot be empty.
- Category names containing only whitespace are rejected.
- Duplicate categories are prevented.

---

## Known Issues

- The application is not currently deployed.
- Delete todo currently does not have a confirmation step.
- Search, filtering and pagination are not yet implemented.

---

## Future Goals

- Add todo search and filtering.
- Add pagination.
- Add confirmation before deleting todos.
- Add authentication and authorisation.
- Deploy the frontend and backend.
- Add more frontend and backend test coverage.

---

## Change Logs

### 28/09/2026 — Frontend Testing

- Added frontend tests.
- Added component tests using React Testing Library.
- Added unit tests for todo validation.
- Added unit tests for category validation.
- Added unit tests for frontend utility functions.

### 26/09/2026 — Backend Testing & Error Handling

- Added backend testing.
- Added service unit tests.
- Added REST API tests.
- Added JSON response validation.
- Added H2 test database configuration.
- Added custom exceptions.
- Added global exception handling.

### 22/08/2026 — README & Project Polish

- Added project documentation.
- Added README.
- Polished the project structure and presentation.

### 19/08/2026 — Todo Management

- Added todo update functionality.
- Added todo delete functionality.
- Added todo duplication functionality.
- Added todo completion functionality.
- Added styling and UI improvements.

### 18/08/2026 — Categories & Todo Creation

- Added category creation functionality.
- Added todo creation functionality.
- Connected the frontend to the backend API.

### 18/08/2026 — Project Initialisation

- Created the initial full-stack Todo application.
- Set up the React frontend.
- Set up the Spring Boot backend.
- Connected the application to MySQL.

---

## What did you struggle with?

### Working with related data

Managing the relationship between todos and categories.

Each todo needs a valid category, so the frontend needs to retrieve the available categories, display them to the user and send the correct `categoryId` when creating or updating a todo.

### Frontend testing

Testing the frontend required understanding which parts of the application were worth testing rather than trying to test every line of code.

Component tests were used for important user interactions, while unit tests were used for isolated functionality such as validation schemas and utility functions.

This helped develop a more focused approach to testing based on user behaviour and application functionality.

---

## Licensing Details

No open-source license has currently been added to the project.

---
