# Employee Wellness Portal

FRONT END FOR DEV REFERENCE: [Credentials: admin@company.com, admin access](https://speck-twice-43718343.figma.site/)

This README serves as a conceptual model of the project for reference.

## Concept Overview

An **Employee Wellness Portal** will include:

-   Health tracking (physical, emotional)

-   Wellness resources (articles, videos, AI-generated tips)

-   Surveys and feedback

-   Goal setting and progress tracking

-   Admin dashboard for HR

-   Authentication and role-based access

-   GenAI-powered suggestions and chat (optional)

---

## Backend: Spring Boot + MySQL

### Models (Entities)

Define JPA entities for:

-   `Employee`: ID, name, email, designation

-   `WellnessMetric`: employeeId, date, mood, sleepHours, activityLevel

-   `Goal`: employeeId, goalType, title, targetDate, status

-   `Survey`: surveyId, title, questions

-   `SurveyResponse`: employeeId, surveyId, answers

-   `Resource`: title, type (video/article), content, tags (optional)

-   `Admin`: for HR/admin access

-   `AuthUser`: for login credentials and roles

### Repositories

Use Spring Data JPA:

```java

public  interface  EmployeeRepository  extends  JpaRepository<Employee, Long> {}

public  interface  WellnessMetricRepository  extends  JpaRepository<WellnessMetric, Long> {}

```

### Services

Business logic:

-   `WellnessService`: CRUD for metrics, goals

-   `SurveyService`: create surveys, collect responses

-   `ResourceService`: manage wellness content

-   `GenAIService`: interface with Gemini API

-   `AuthService`: login, registration, JWT token handling

### REST APIs

Organize by feature:

-   `/api/auth`: login, register, refresh token

-   `/api/employees`: get/update employee info

-   `/api/wellness`: submit/view metrics

-   `/api/goals`: create/update/view goals

-   `/api/surveys`: get surveys, submit responses

-   `/api/resources`: fetch wellness content

-   `/api/genai`: get AI-generated tips, summaries

---

## Frontend: AngularJS

### Pages/Views

-   **Login/Register**

-   **Dashboard** (personalized wellness overview)

-   **Track Wellness** (form for daily metrics)

-   **Goals** (set/view progress)

-   **Surveys** (take surveys, view results)

-   **Resources** (browse articles/videos)

-   **Admin Panel** (HR tools, analytics)

-   **AI Assistant** (Gemini-powered chat)

### Components

-   `navbar`, `footer`, `card`, `modal`, `form-group`, etc.

-   `wellness-form`, `goal-tracker`, `survey-viewer`, `resource-browser`, `ai-widget`

### Auth Integration

-   JWT-based auth interceptor

-   Role-based routing guards

-   Secure token storage (localStorage/sessionStorage)

### Gemini GenAI Integration (optional)

We can use Gemini to:

-   Generate personalized wellness tips

-   Summarize articles

-   Chatbot for wellness Q&A

-   Suggest goals based on user data

We’ll call Gemini via a backend proxy (Spring Boot) to keep API keys secure. Alternatively, we can always use a simple client side API to call the Gemini chat if necessary.

---

## Authentication & Authorization

### Backend

-   Spring Security with JWT

-   Role-based access (`ROLE_EMPLOYEE`, `ROLE_ADMIN`)

-   Password hashing (maybe BCrypt?)

### Frontend

-   Login form → token → store in browser

-   AuthGuard for protected routes

-   Logout and token refresh logic

---

## Other Optional Enhancements

-   Charts for wellness trends (Chart.js or D3.js)

-   Calendar integration for goal deadlines (Google Calendar is easier, we can look into Outlook)

-   Email notifications (Spring Mail)

-   Mobile responsiveness (shadCN or Angular Material)

-   AI feedback loop: Gemini suggests goals → user accepts → tracked
