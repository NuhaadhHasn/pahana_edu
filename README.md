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


---

# Notes added when this project was put on GitHub (November 2026)

The text above is my own README, unchanged. Everything below was added later, when I ran the program again and checked it.

## Where this code comes from

- The history of this repository is the real history of the project: 20 commits by me, 10 to 20 August 2025, taken from the `.git` folder inside `Assignment.rar`. Nothing was rewritten.
- The `Assignment.rar` snapshot stops at commit `54dc14e` ("Bug fix in backend", 20 August 2025). The public repository [`NuhaadhHasn/pahana_edu`](https://github.com/NuhaadhHasn/pahana_edu) that the report links to has five more commits on top (26 to 28 August 2025: notification update, print function, animations and loaders, charts for the reports, username check). **Those five commits are not in this private copy.**
- `database/CreateDB&TableScripts.sql` is the SQL script that lay next to the project in `Assignment.rar` (it was never inside the Git repository). It was committed as received, then fixed (see below).

## Verified run (18 November 2026)

| | |
|---|---|
| Server | Apache Tomcat 8.5.96 (the one that ships with XAMPP), servlet API 3.1, JSP compile level 1.8 |
| Database | a throw-away MariaDB 10.4.32 on port 3399, created from the SQL script. The table layout is the same as in my own `pahana_edu` database (compared with `mysqldump --no-data`) |
| Compiler | JDK 20, `--release 17`, no Maven (a Maven build needs a download that was not allowed, so `mvn package` is **not verified**); the four libraries (`mysql-connector-j 8.4.0`, `jstl 1.2`, `json 20231013`, `protobuf-java 4.28.2`) came from the project's own `target/` folder |
| Browser | headless Chromium, all addresses except `127.0.0.1` blocked |

The pom asks for Servlet 4.0 / Tomcat 9; the code compiles and runs unchanged on Tomcat 8.5 (it uses nothing newer than Servlet 3.1). The program has 61 classes, 25 pages and about 5,200 lines of Java.

Logins in the sample data (from the SQL script): `admin` / `admin123`, `staff` / `staff123`, `kamal` / `kamal123` (a customer). They are demo passwords stored in plain text.

### Screenshots

Taken from the program exactly as received. **The pages look unstyled**: the program loads Bootstrap 5.3.3 and Bootstrap Icons from `cdn.jsdelivr.net` (about 0.5 MB), and no downloads were allowed, so the browser was not allowed to fetch them. The layout, the data and the behaviour are real; only the styling is missing. The styled screens are in my report (login, dashboard, user management, add item, create bill, bill view).

| | |
|---|---|
| [login](docs/screenshots/01-login.jpg) · [failed login](docs/screenshots/02-login-failed.jpg) | the login page, and the message after a wrong password |
| [admin dashboard](docs/screenshots/03-admin-dashboard.jpg) | counts, low-stock notices and the action links |
| [customers](docs/screenshots/04-customers.jpg) · [add customer](docs/screenshots/05-add-customer-form.jpg) | customer management |
| [items](docs/screenshots/06-items.jpg) · [promotions](docs/screenshots/07-promotions.jpg) · [users](docs/screenshots/08-users.jpg) | item, promotion and user management (admin) |
| [create bill](docs/screenshots/09-create-bill-empty.jpg) · [filled in](docs/screenshots/10-create-bill-filled.jpg) · [bill just created](docs/screenshots/11-bill-just-created.jpg) | the billing screen: 20 copies of one book, a 10 % promotion, 5 % tax. The bill says "Bill ID #0" (see problem 8) |
| [bill history](docs/screenshots/12-bill-history.jpg) · [record payment](docs/screenshots/13-record-payment.jpg) | staff: all bills, and paying one |
| [reports](docs/screenshots/14-reports.jpg) · [login history](docs/screenshots/15-login-history.jpg) · [low-stock notifications](docs/screenshots/16-low-stock-notifications.jpg) · [help](docs/screenshots/17-help.jpg) | reports, log of logins, notification history, help page |
| [customer dashboard](docs/screenshots/18-customer-dashboard.jpg) · [available items](docs/screenshots/19-customer-view-items.jpg) · [my bills](docs/screenshots/20-customer-my-bills.jpg) · [make payment](docs/screenshots/21-customer-make-payment.jpg) | what the customer role sees |

The data in the screenshots is test data made during the run (one extra customer, one extra item); the books and the customer `Kamal Perera` come from the sample data in the SQL script.

### What works

Login with the three roles and their different menus, customer, item, promotion and user management (add, edit, delete), billing with a walk-in or existing customer, a promotion, tax and service charge, stock going down and the low-stock notification appearing, bill history, recording a payment (status changes to PAID), customer portal with "my bills" and paying a bill, reports, login history, help page.

## Problems found when running it

Found by running it with the three roles and by sending requests by hand. The "Fixed in" column names the commit that fixes it (all after the first two commits, which are the work as received and this README).

| # | Problem | How it shows | Fixed in |
|---|---|---|---|
| 1 | The SQL script fails on an empty server | the view `v_bill_details` is created before the `ALTER TABLE` that adds `tax_rate_applied` and `service_charge`, so MariaDB stops with `Unknown column 'b.tax_rate_applied'` and none of the sample rows are inserted | commit "Database script: create the columns before the view that uses them" |
| 2 | Database address, user and password are written into `DBConnection.java`; the one shared connection is never replaced if it is closed or times out | to use another database the source has to be edited; after a database restart the whole site keeps failing until Tomcat is restarted | commit "DBConnection: read address, user and password from the environment; reconnect when the connection is dead" |
| 3 | **Anyone, without logging in, can open `/login-history`** | the filter treats every path that starts with `/login` as public, and `/login-history` starts with `/login` | commit "Access filter: public pages matched exactly, customers lose /items, staff get bill details and the search API" |
| 4 | **A customer can add, edit and delete items** | `/items` is in the customers' list of allowed paths (they should only have `/view-items`); a POST of `action=add` from a customer session creates a book | commit "Access filter: public pages matched exactly, customers lose /items, staff get bill details and the search API" |
| 5 | **A customer can read any bill, and pay any bill, by number** | `ViewBillDetailsController` has the comment "A security check should be here to ensure the logged-in customer owns this bill. We will add this later"; `/customer-make-payment` takes any `billId` | open |
| 6 | Staff get "403 Forbidden" on "View Details" in the bill history | `/view-bill-details` is only in the admin list | commit "Access filter: public pages matched exactly, customers lose /items, staff get bill details and the search API" |
| 7 | `/api/search-items` (the JSON web service) answers 403 to everybody | it is not in any role's list | commit "Access filter: public pages matched exactly, customers lose /items, staff get bill details and the search API" |
| 8 | The bill shown right after creating it says "Bill ID #0" | `BillingDAO.saveBill` reads the new id but never puts it on the `Bill` object | open |
| 9 | Bills are not validated on the server | an empty bill, a negative service charge (-500) and 100 copies of a book with 23 in stock were all accepted (stock became -75); letters in a number field give a 500 error page | open |
| 10 | Payments are not validated | paying Rs 1 on a bill of Rs 9,000 marked it PAID; an already paid bill can be paid again (a second payment row is stored) | open |
| 11 | The "Filter Books" box on the create-bill page does nothing | the script looks for elements of class `result-item`, the list items have class `list-group-item` | open |
| 12 | 7 of the 31 unit tests fail on a fresh database | they assume rows that only existed in my own database (customer 5, bill 2, promotion 8, ...) and depend on the order the tests run in | open |
| 13 | IDE state (`.idea/`) was committed in the original history | | open |
| 14 | Left as they are (design limits, not fixed) | passwords are stored and compared in plain text; one shared JDBC connection is used by all requests while `saveBill` switches auto-commit off on it; stock is reduced after, not inside, the bill transaction; no CSRF protection; the Maven build is unverified (no download allowed) | not fixed |
