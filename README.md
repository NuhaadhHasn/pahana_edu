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

- The history of this repository is the real history of the project: 25 commits by me, 10 to 28 August 2025, which the report links to. Nothing was rewritten.
- The `Assignment.rar` I submitted stops at commit `54dc14e` ("Bug fix in backend", 20 August 2025). Everything after that commit in this repository was added in November 2026 on top of your own five later commits (26 to 28 August 2025: notification update, print function, animations and loaders, charts for the reports, username check): the database script, the README, the screenshots and the bug fixes below, each in its own commit.
- **What was checked on which code.** The browser run, the screenshots and the manual checks in this README were made on the 20 August code, before the five later commits were merged in. After the merge, the program compiles and all 32 unit tests pass twice in a row on an empty database (31 of the earlier tests plus the username test from 28 August). The pages added later (profile, print, charts) were not run in a browser again.
- `database/CreateDB&TableScripts.sql` is the SQL script that lay next to the project in `Assignment.rar` (it was never inside the Git repository). It was committed as received, then fixed (see below).

## Verified run (18 November 2026)

| | |
|---|---|
| Server | Apache Tomcat 8.5.96 (the one that ships with XAMPP), servlet API 3.1, JSP compile level 1.8 |
| Database | a throw-away MariaDB 10.4.32 on port 3399, created from the SQL script. The table layout is the same as in my own `pahana_edu` database (compared with `mysqldump --no-data`) |
| Compiler | JDK 20, `--release 17`, no Maven (a Maven build needs a download that was not allowed, so `mvn package` is **not verified**); the four libraries (`mysql-connector-j 8.4.0`, `jstl 1.2`, `json 20231013`, `protobuf-java 4.28.2`) came from the project's own `target/` folder |
| Browser | headless Chromium, all addresses except `127.0.0.1` blocked |

The pom asks for Servlet 4.0 / Tomcat 9; the code compiles and runs unchanged on Tomcat 8.5 (it uses nothing newer than Servlet 3.1). The program (with the later commits) has 65 classes and 24 JSP pages.

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
| 5 | **A customer can read any bill, and pay any bill, by number** | `ViewBillDetailsController` has the comment "A security check should be here to ensure the logged-in customer owns this bill. We will add this later"; `/customer-make-payment` takes any `billId` | commit "Customers can only open and pay their own bills; payments must match an unpaid bill" |
| 6 | Staff get "403 Forbidden" on "View Details" in the bill history | `/view-bill-details` is only in the admin list | commit "Access filter: public pages matched exactly, customers lose /items, staff get bill details and the search API" |
| 7 | `/api/search-items` (the JSON web service) answers 403 to everybody | it is not in any role's list | commit "Access filter: public pages matched exactly, customers lose /items, staff get bill details and the search API" |
| 8 | The bill shown right after creating it says "Bill ID #0" | `BillingDAO.saveBill` reads the new id but never puts it on the `Bill` object | commit "Billing: validate the bill on the server, show the new bill number, make the item filter work" |
| 9 | Bills are not validated on the server | an empty bill, a negative service charge (-500) and 100 copies of a book with 23 in stock were all accepted (stock became -75); letters in a number field give a 500 error page | commit "Billing: validate the bill on the server, show the new bill number, make the item filter work" |
| 10 | Payments are not validated | paying Rs 1 on a bill of Rs 9,000 marked it PAID; an already paid bill can be paid again (a second payment row is stored) | commit "Customers can only open and pay their own bills; payments must match an unpaid bill" |
| 11 | The "Filter Books" box on the create-bill page does nothing | the script looks for elements of class `result-item`, the list items have class `list-group-item` | commit "Billing: validate the bill on the server, show the new bill number, make the item filter work" |
| 12 | 7 of the 31 unit tests (at that time) fail on a fresh database | they assume rows that only existed in my own database (customer 5, bill 2, promotion 8, ...) and depend on the order the tests run in | commit "Tests: every test makes the rows it needs (31 of 31 pass on an empty database, and again on the same one)" |
| 13 | IDE state (`.idea/`) was committed in the original history | | commit "Stop tracking the IDE state" |
| 14 | Left as they are (design limits, not fixed) | passwords are stored and compared in plain text; one shared JDBC connection is used by all requests while `saveBill` switches auto-commit off on it; stock is reduced after, not inside, the bill transaction; no CSRF protection; the Maven build is unverified (no download allowed) | not fixed |


## What the fixes changed, and how each was checked

All checks were repeated after the last fix with the three roles, against a freshly created database.

| Problem | Check | Result before | Result after |
|---|---|---|---|
| 1 | run `database/pahana_edu.sql` on an empty server | stops with `Unknown column 'b.tax_rate_applied'`, no sample rows | runs to the end; 3 users, 1 customer, 1 item, 1 promotion |
| 2 | start the program with `DB_URL=jdbc:mysql://127.0.0.1:3399/pahana_edu` | the setting did not exist | the program (and the tests) used that database; with nothing set it still uses `127.0.0.1:3306/pahana_edu`, user `root`, empty password |
| 3 | open `/login-history` | 200 without logging in, and 200 for staff and customers | 302 to the login page without a session; 403 for staff and customers; 200 for the admin |
| 4 | customer POSTs `action=add` to `/items`, opens `/items` | a book was created | 403, nothing created |
| 5 | customer opens or pays another customer's bill | 200 / payment stored | 403 / nothing stored; own bills still open |
| 6, 7 | staff open `/view-bill-details?id=1` and `/api/search-items?term=ja` | 403 / 403 | 200 / 200 |
| 8 | bill page right after creating a bill | "Bill ID #0" | "Bill ID #1" (screenshot [22](docs/screenshots/22-after-fix-bill-number.jpg)) |
| 9 | empty bill; service charge -500; 100 copies with 25 in stock; letters in a number; tax 150 | all saved (stock -75) or error 500 | each one stays on the form with a red message (screenshot [23](docs/screenshots/23-after-fix-validation-error.jpg)); nothing is saved; a walk-in customer is no longer created when the bill is refused |
| 10 | pay Rs 1 on a Rs 9,000 bill; pay a paid bill again | bill became PAID; second payment stored | refused with a message on the bill history / my bills page; only a payment equal to the total of an unpaid bill is accepted |
| 11 | type in "Filter Books" | the list did not change | the list is filtered while typing |
| 12 | run the tests on an empty database, then again on the same database | 7 of 31 failed on the first run | all pass both times (32 of 32 after the later commits were merged in) |
| 13 | | `.idea/` was in the repository | no longer tracked, listed in `.gitignore` (the old commits still contain it) |

Things the fixes do **not** change: after a refused bill the form comes back empty, so the lines have to be added again; a payment must be the full bill total (the database has no "part paid" status); the passwords are still plain text (problem 14).

## Settings and running

- Database: `DB_URL`, `DB_USER`, `DB_PASSWORD` (environment variables) or `-Dpahana.db.url`, `-Dpahana.db.user`, `-Dpahana.db.password`. The defaults are the old values.
- **Warning:** `database/pahana_edu.sql` starts with `DROP TABLE IF EXISTS` for every table of the `pahana_edu` database. Do not run it on a server whose `pahana_edu` you want to keep.
- The tests (`src/test/java/.../dao`) are integration tests: they need the database from the script, and they now add their own rows with names that cannot clash, so they can be run again and again.
- Build: `mvn clean package` (not run here, see above), then put `target/pahana-edu.war` into Tomcat 9.
