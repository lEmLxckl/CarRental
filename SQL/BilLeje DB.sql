DROP DATABASE IF EXISTS billeje;
CREATE DATABASE billeje;
USE billeje;

-- Create Table
CREATE TABLE car (
    vehicle_number INT PRIMARY KEY,
    frame_number VARCHAR(50) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    make YEAR NOT NULL,
    color VARCHAR(30),
    price DECIMAL(10, 2),
    flow INT NOT NULL,
    odometer INT,
    fuel_type VARCHAR(20),
    motor VARCHAR(50),
    gear_type VARCHAR(20)
);

CREATE TABLE customer (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255),
    cpr VARCHAR(11) UNIQUE NOT NULL
);

CREATE TABLE employee (
    username VARCHAR(50) PRIMARY KEY,
    user_password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    usertype ENUM('DATAREGISTRERING', 'SKADE OG UDBEREDNING', 'FORRETNINGSUDVIKLERE', 'ADMIN'),
    is_active BOOLEAN NOT NULL,
    is_admin BOOLEAN NOT NULL
);

CREATE TABLE leasing_contract (
    contract_id INT PRIMARY KEY AUTO_INCREMENT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    vehicle_number INT,
    username VARCHAR(50),
    customer_id INT,
    FOREIGN KEY (vehicle_number) REFERENCES car(vehicle_number),
    FOREIGN KEY (username) REFERENCES employee(username),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE damage_report (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    total_price DECIMAL(10, 2),
    contract_id INT,
    FOREIGN KEY (contract_id) REFERENCES leasing_contract(contract_id)
);

CREATE TABLE damage_category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL DEFAULT 0.0
);

INSERT INTO car (vehicle_number, frame_number, brand, model, make, color, price, flow, odometer, fuel_type, motor, gear_type)
VALUES 
    (1, 'ABC123', 'Toyota', 'Corolla', 2020, 'Red', 20000.00, 0, 15000, 'Petrol', 'V4', 'Automatic'),
    (2, 'DEF456', 'Toyota', 'Camry', 2021, 'Blue', 22000.00, 0, 18000, 'Petrol', 'V6', 'Automatic'),
    (3, 'GHI789', 'Honda', 'Civic', 2022, 'Black', 20000.00, 0, 15000, 'Petrol', 'V4', 'Automatic'),
    (4, 'JKL012', 'Ford', 'Fusion', 2023, 'Silver', 19000.00, 0, 20000, 'Petrol', 'V6', 'Automatic'),
    (5, 'MNO345', 'Chevrolet', 'Malibu', 2022, 'White', 21000.00, 0, 22000, 'Petrol', 'V4', 'Automatic'),
    (6, 'PQR678', 'Nissan', 'Altima', 2023, 'Red', 23000.00, 0, 17000, 'Petrol', 'V6', 'Automatic');

INSERT INTO customer (customer_id, full_name, email, phone, address, cpr)
VALUES 
    (1, 'John Doe', 'johndoe@example.com', '12345678', '123 Main St', '1234567890'),
    (2, 'Mary Johnson', 'mary@example.com', '23456789', '123 Elm St', '2345678901'),
    (3, 'William Smith', 'william@example.com', '34567890', '234 Oak St', '3456789012'),
    (4, 'Sophia Williams', 'sophia@example.com', '45678901', '345 Main St', '4567890123'),
    (5, 'James Brown', 'james@example.com', '56789012', '456 Pine St', '5678901234'),
    (6, 'Olivia Miller', 'olivia@example.com', '67890123', '567 Cedar St', '6789012345');

-- Insert employees with different usertypes
INSERT INTO employee (username, user_password, full_name, email, phone, usertype, is_active, is_admin)
VALUES 
    ('admin', 'admin', 'Admin User', 'admin@example.com', '87654321', 'ADMIN', TRUE, TRUE),
    ('eser', 'eser', 'Eser User', 'eser@example.com', '12345678', 'DATAREGISTRERING', TRUE, FALSE),
    ('emre', 'emre', 'Emre User', 'emre@example.com', '23456789', 'SKADE OG UDBEREDNING', TRUE, FALSE),
    ('jafar', 'jafar', 'Jafar User', 'jafar@example.com', '34567890', 'FORRETNINGSUDVIKLERE', TRUE, FALSE),
    ('arfi', 'arfi', 'Arfi User', 'arfi@example.com', '45678901', 'SKADE OG UDBEREDNING', TRUE, FALSE);

INSERT INTO leasing_contract (start_date, end_date, price, vehicle_number, username, customer_id)
VALUES 
    ('2024-01-01', '2024-12-31', 10000.00, 1, 'admin', 1),
    ('2024-01-01', '2024-12-31', 10000.00, 1, 'admin', 1),
    ('2024-01-01', '2024-12-31', 10000.00, 2, 'admin', 2),
    ('2024-01-01', '2024-12-31', 10000.00, 3, 'admin', 3),
    ('2024-01-01', '2024-12-31', 10000.00, 4, 'admin', 4),
    ('2024-01-01', '2024-12-31', 10000.00, 5, 'admin', 5);

INSERT INTO damage_report (report_id, total_price, contract_id)
VALUES 
    (1, 500.00, 1),
    (2, 750.00, 2),
    (3, 1000.00, 3),
    (4, 1250.00, 4),
    (5, 1500.00, 5),
    (6, 1750.00, 6);

INSERT INTO damage_category (id, name, price)
VALUES 
    (1, 'Collision', 10000),
    (2, 'Scratch', 5000),
    (3, 'Dent', 500),
    (4, 'Broken Glass', 500),
    (5, 'Paint Damage', 500),
    (6, 'Interior Damage', 500);

-- Query data
SELECT * FROM car LIMIT 0, 10;
SELECT * FROM customer LIMIT 0, 10;
SELECT * FROM employee LIMIT 0, 10;
SELECT * FROM leasing_contract LIMIT 0, 10;
SELECT * FROM damage_report LIMIT 0, 10;
SELECT * FROM damage_category LIMIT 0, 10;
