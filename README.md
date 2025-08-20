# Pahana Edu - Online Billing System

## Project Overview

This project is a comprehensive, web-based online billing system for **Pahana Edu Bookshop**, developed as a primary assessment for the Advanced Programming module (CIS6003). The application is built using Java Enterprise Edition (Java EE) technologies, follows a robust 3-Tier MVC architecture, and adheres to modern software engineering principles like SOLID and Test-Driven Development (TDD).

The system allows Admins and Staff to manage users, customers, inventory, and promotions, while providing a dedicated portal for customers to view their purchase history.

---

## 🚀 Technology Stack

This project utilizes a full-stack, industry-standard set of technologies:

| Layer       | Technology                                                                                                                                  | Purpose                                                                               |
| :---------- | :------------------------------------------------------------------------------------------------------------------------------------------ | :------------------------------------------------------------------------------------ |
| **Backend** | ![Java](https://img.shields.io/badge/Java-17-orange)                                                                                        | Core programming language for the application logic.                                  |
|             | ![Servlets](https://img.shields.io/badge/Jakarta%20Servlets-4.0-red)                                                                        | Handle HTTP requests and act as the Controllers in the MVC pattern.                   |
| **Frontend**| ![JSP](https://img.shields.io/badge/JSP-2.3-blue)                                                                                           | JavaServer Pages for creating dynamic web content and views.                          |
|             | ![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3-purple?logo=bootstrap&logoColor=white)                                            | CSS framework for modern, responsive, and professional UI styling.                    |
|             | ![JavaScript](https://img.shields.io/badge/JavaScript-ES6-yellow?logo=javascript&logoColor=white)                                         | Client-side scripting for dynamic UI interactions (e.g., live totals, API calls).     |
| **Database**| ![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql&logoColor=white)                                                            | Relational database for all data persistence.                                         |
| **Server**  | ![Apache Tomcat](https://img.shields.io/badge/Apache%20Tomcat-9.0-lightgrey?logo=apache&logoColor=red)                                      | Web server and Servlet container for running the application.                         |
| **Build**   | ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-3.8-red?logo=apache-maven&logoColor=white)                                      | Dependency management and project build automation.                                   |
| **Testing** | ![JUnit4](https://img.shields.io/badge/JUnit-4-green)                                                                                       | Framework for Test-Driven Development (TDD) of the data access layer.                 |

---

## ✨ Core Features

-   **Role-Based Access Control (RBAC):** Distinct interfaces and permissions for `Admin`, `Staff`, and `Customer` roles.
-   **Full CRUD Functionality:** Complete Create, Read, Update, and Delete operations for Users, Customers, Items, and Promotions.
-   **Dynamic Billing System:** An interactive point-of-sale interface for creating bills with live calculation of totals, taxes, and promotions.
-   **Customer Portal:** A dedicated dashboard for customers to view their personal bill history.
-   **Automated Alerts:** A low-stock notification system for administrators on the main dashboard.
-   **Login Auditing:** All successful and failed login attempts are logged for security review.
-   **Business Reporting:** A dedicated page for admins to view key metrics like total sales and top-selling products.

---

## 🏗️ Architectural Design

The application is built on a **3-Tier MVC (Model-View-Controller)** architecture, ensuring a clear separation of concerns.

-   **SOLID Principles:** The design strictly adheres to SOLID principles:
    -   **SRP:** Each class (Controller, Service, DAO) has a single, well-defined responsibility.
    -   **OCP:** Controllers and Services are open for extension but closed for modification.
    -   **LSP:** The `DiscountStrategy` interface allows different promotion types to be used interchangeably.
    -   **DIP:** Controllers depend on Service interfaces, not concrete classes.
-   **Design Patterns:**
    -   **Singleton Pattern:** Used for the `DBConnection` to ensure a single, efficient database connection.
    -   **Factory Pattern:** The `ServiceFactory` decouples controllers from service implementations.
    -   **Strategy Pattern:** Used for applying different promotional discounts.

---

## 🛠️ Setup and Installation

1.  **Database:** Ensure you have a MySQL server running. Execute the `pahana_edu.sql` script to create the database, tables, and sample data.
2.  **Configuration:** Update the database credentials (`DB_URL`, `DB_USER`, `DB_PASSWORD`) in the `src/main/java/com/example/pahanaedu/dao/DBConnection.java` file.
3.  **Build:** Build the project using Maven (`mvn clean package`).
4.  **Deploy:** Deploy the generated `.war` file to an Apache Tomcat 9.0 server.