CREATE DATABASE IF NOT EXISTS pahana_edu;
USE pahana_edu;

-- Drop existing tables and views in the correct order to avoid foreign key errors
DROP VIEW IF EXISTS v_bill_details;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS login_history;
DROP TABLE IF EXISTS bill_items;
DROP TABLE IF EXISTS bills;
DROP TABLE IF EXISTS promotions;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS notifications;

DROP VIEW IF EXISTS v_bill_details;

-- 1. Users Table (for all login accounts)
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    role ENUM('ADMIN', 'STAFF', 'CUSTOMER') NOT NULL
);

-- 2. Customers Table
CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    address TEXT,
    phone_number VARCHAR(20),
    user_id INT NULL UNIQUE, -- A customer can optionally have a user account
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 3. Items Table (Books)
CREATE TABLE items (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0
);

-- 4. Promotions Table
CREATE TABLE promotions (
    promo_id INT PRIMARY KEY AUTO_INCREMENT,
    promo_code VARCHAR(20) NOT NULL UNIQUE,
    discount_percentage DECIMAL(5, 2) NOT NULL,
    start_date DATE,
    end_date DATE,
    is_active BOOLEAN DEFAULT TRUE
);

-- 5. Bills Table
CREATE TABLE bills (
    bill_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    bill_date DATETIME NOT NULL,
    sub_total DECIMAL(10, 2) NOT NULL,
    promo_id INT NULL, -- Promotion applied to this bill
    discount_amount DECIMAL(10, 2) DEFAULT 0.00,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('DRAFT', 'ISSUED', 'PAID', 'CANCELLED') NOT NULL DEFAULT 'DRAFT',
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (promo_id) REFERENCES promotions(promo_id)
);

-- 6. Bill Items Table (Junction Table)
CREATE TABLE bill_items (
    bill_item_id INT PRIMARY KEY AUTO_INCREMENT,
    bill_id INT NOT NULL,
    item_id INT NOT NULL,
    quantity INT NOT NULL,
    price_at_purchase DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id),
    FOREIGN KEY (item_id) REFERENCES items(item_id)
);

-- 7. Payments Table
CREATE TABLE payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    bill_id INT NOT NULL,
    payment_date DATETIME NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method ENUM('CASH', 'CREDIT_CARD', 'BANK_TRANSFER') NOT NULL,
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id)
);

-- 8. Login History Table
CREATE TABLE login_history (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('SUCCESS', 'FAILED') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 9. Notification Table
CREATE TABLE notifications (
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    message TEXT NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ---------------------------------
-- Create the Database View for Readable Reports
-- ---------------------------------

-- CREATE VIEW v_bill_details AS
-- SELECT
--     b.bill_id,
--     b.bill_date,
--     c.account_number,
--     c.full_name AS customer_name,
--     i.item_name,
--     bi.quantity,
--     bi.price_at_purchase,
--     (bi.quantity * bi.price_at_purchase) AS item_total,
--     b.total_amount AS bill_total,
--     b.status AS bill_status
-- FROM bills b
-- JOIN customers c ON b.customer_id = c.customer_id
-- JOIN bill_items bi ON b.bill_id = bi.bill_id
-- JOIN items i ON bi.item_id = i.item_id;

CREATE VIEW v_bill_details AS
SELECT
    b.bill_id,
    b.bill_date,
    b.sub_total,
    b.promo_id,
    b.discount_amount,
    b.total_amount,      -- Use the original column name
    b.status,            -- Use the original column name
    b.tax_rate_applied,
    b.service_charge,
    c.customer_id,
    c.account_number,
    c.full_name AS customer_name
FROM bills b
LEFT JOIN customers c ON b.customer_id = c.customer_id;

-- ---------------------------------
-- Insert Sample Data for the New Structure
-- ---------------------------------
INSERT INTO users (username, password, full_name, role) VALUES
('admin', 'admin123', 'Administrator', 'ADMIN'),
('staff', 'staff123', 'Cashier', 'STAFF'),
('kamal', 'kamal123', 'Kamal Perera', 'CUSTOMER');

INSERT INTO customers (account_number, full_name, address, phone_number, user_id) VALUES
('ACC001', 'Kamal Perera', '12 Temple Lane, Kandy', '071-1234567', 3);

INSERT INTO items (item_name, description, price, stock_quantity) VALUES
('Java: The Complete Reference', 'An in-depth guide to Java.', 4500.00, 25);

INSERT INTO promotions (promo_code, discount_percentage, is_active) VALUES
('SAVE10', 10.00, TRUE);

-- -----------------
ALTER TABLE bills ADD COLUMN tax_rate_applied DOUBLE DEFAULT 0.00;
ALTER TABLE bills ADD COLUMN service_charge DOUBLE DEFAULT 0.00;