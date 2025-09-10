# Employee Wellness Portal

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

-   `Survey`: surveyId, surveyTitle, questions

-   `SurveyResponse`: employeeId, surveyId, answers(mapped to separate table containing question and answer as fields)

-   `Resource`: title, type (video/article), content, tags (optional), category

-   `Admin`: for HR/admin access

-   `AuthUser`: for login credentials and roles

-   `Rewards` : for award of medals and ranks based on user-logged scores from wellnessmetric model

### Repositories

Use Spring Data JPA:

```java

public  interface  EmployeeRepository  extends  JpaRepository<Employee, Long> {}

public  interface  WellnessMetricRepository  extends  JpaRepository<WellnessMetric, Long> {}

```

### Services

Business logic:
AdminService: 
A package with exclusive access to admins to promote access control
 `AccountCreationService`: account creation and can only be accessed by the admin

 `SurveyResponseService`: Access user responses to the survey

Generic Services:

    `WellnessService`: CRUD for metrics, goals, and rewards. Reward assignment based on calculated scores.

-   `SurveyService`: create surveys, collect responses

-   `ResourceService`: manage wellness content

-   `GenAIService`: interface with Gemini API

-   `AuthService`: login, registration(request to admin. Admin adds from AuthUserRepository. This is optional at the current stage.), JWT token handling

   

### REST APIs

Organize by feature:

-   `/api/auth/login`: login
    `/api/auth/register`: register, refresh token

    # Will be updated along the cycle based on incrementally built code base
-   `/api/employees`: get/update employee info

-   `/api/wellness`: submit/view metrics

-   `/api/goals`: create/update/view goals

    `/api/employeeRewards`: assign new rewards with milestones

-   `/api/surveys`: get surveys, submit responses

-   `/api/resources`: fetch wellness content

-   `/api/genai`: get AI-generated tips, summaries

Utilities:
util package:
    ` JWTUtil` : configuring JWT Tokens for login and register functionality

Frontend dependencies:
    - User can login using employee id irrespective of his/her role. Same is reflected in each page.
    - Each survey, corresponding response and user's wellness metric has its own id
    - Admin has control over creating surveys and seeing responses. His/her functionality to add new users is optional and will be added in the future editions. 
    - Analytics of employees in admin panel and their logic will be communicated in future commits.
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
