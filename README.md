<div align="center">

  <h1 align="center">Employee Wellness Portal</h1>

  <p align="center">
    A comprehensive platform designed to promote and track employee well-being through health metrics, goal setting, and engagement.
    <br />
    <a href="#about-the-project"><strong>Explore the docs »</strong></a>
    <br />
  </p>
</div>

<!-- Badges -->
<div align="center">
  <img src="https://img.shields.io/badge/Java-17-blue.svg?style=for-the-badge&logo=java" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F.svg?style=for-the-badge&logo=spring-boot" alt="Spring Boot 3.x">
  <img src="https://img.shields.io/badge/Angular-17-DD0031.svg?style=for-the-badge&logo=angular" alt="Angular 17">
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1.svg?style=for-the-badge&logo=mysql" alt="MySQL 8.0">
</div>

---

## About The Project

The Employee Wellness Portal is a full-stack web application built to empower organizations in fostering a healthier and more engaged workforce. Employees can track daily wellness metrics, set personal goals, and earn achievements, while administrators gain insights through a dedicated dashboard.

This project leverages a modern tech stack to deliver a robust, scalable, and user-friendly experience.

---

## ✨ Key Features

-   **👤 Role-Based Authentication:** Secure login and registration for Employees and Administrators.
-   **📊 Daily Wellness Tracking:** Log daily metrics for mood, sleep, steps, and water intake.
-   **🎯 Personalized Goal Setting:** Create, update, and track progress on personal wellness goals.
-   **🏆 Automated Rewards System:** Earn badges and achievements for meeting health milestones.
-   **🎛️ Admin Dashboard:** View and manage users and their activities.
-   **📝 Survey Management:** Admins can create surveys and view employee responses.
-   **🤖 GenAI Ready:** Designed with future integration for AI-powered wellness tips in mind.


---

## 🛠️ Tech Stack

This project is built with a modern and powerful set of technologies:

| Backend                                                                                                                                        | Frontend                                                                                                                        | Database                                                                                                         |
| ---------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------- |
| <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot">             | <img src="https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white" alt="Angular">          | <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"> |
| <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white" alt="Java">                                  | <img src="https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=typescript&logoColor=white" alt="TypeScript"> |                                                                                                                  |
| <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white" alt="Spring Security"> | <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white" alt="HTML5">                |                                                                                                                  |
| <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white" alt="Hibernate">                   | <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white" alt="CSS3">                   |                                                                                                                  |
| <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven">                        |                                                                                                                                 |                                                                                                                  |

---

## 🚀 Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

-   **Java (JDK)**: Version 17 or higher
-   **Maven**: Version 3.8 or higher
-   **Node.js**: Version 18.x or higher
-   **Angular CLI**: Version 17 or higher
-   **MySQL Server**: Version 8.0 or higher

### Installation

1.  **Clone the repository**

    ```sh
    git clone https://github.com/kshitijsinghcts/wellness-portal.git
    cd wellness-portal
    ```

2.  **Backend Setup (Spring Boot)**

    -   Navigate to the `backend` directory.
    -   Create a MySQL database named `wellness_portal`.
    -   Update the database credentials in `src/main/resources/application.properties`:
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/wellness_portal
        spring.datasource.username=your_mysql_username
        spring.datasource.password=your_mysql_password
        ```
    -   Run the application. The `DataInitializer` will automatically seed the database on the first run.
        ```sh
        mvn spring-boot:run
        ```
    -   The backend will be running on `http://localhost:8080`.

3.  **Frontend Setup (Angular)**
    -   Navigate to the `frontend` directory.
    -   Install NPM packages:
        ```sh
        npm install
        ```
    -   Start the development server:
        ```sh
        ng serve
        ```
    -   The frontend will be running on `http://localhost:4200`.

---

## 📋 API Endpoints

Here is a summary of the core API endpoints available:

| Endpoint                       | Method | Description                                  |
| ------------------------------ | ------ | -------------------------------------------- |
| `/api/auth/login`              | `POST` | Authenticates a user and returns a token.    |
| `/api/auth/register`           | `POST` | Registers a new employee.                    |
| `/api/auth/register-admin`     | `POST` | Registers a new administrator.               |
| `/api/employees`               | `GET`  | Retrieves a list of all employees.           |
| `/api/employees/{id}`          | `GET`  | Retrieves a single employee by ID.           |
| `/api/wellness/submit-metrics` | `POST` | Submits daily wellness metrics.              |
| `/api/wellness/{employeeId}`   | `GET`  | Retrieves all wellness logs for an employee. |
| `/api/goals/create`            | `POST` | Creates a new wellness goal.                 |
| `/api/goals/update`            | `PUT`  | Updates an existing goal.                    |
| `/api/goals/{employeeId}`      | `GET`  | Retrieves all goals for an employee.         |
| `/api/employeeRewards/{id}`    | `GET`  | Retrieves all rewards for an employee.       |

