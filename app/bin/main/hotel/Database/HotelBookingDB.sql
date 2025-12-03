CREATE DATABASE HotelBooking;

USE HotelBooking;

CREATE TABLE User (
    user_id INT AUTO_INCREMENT UNIQUE,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL,
    role enum('administrator', 'receptionist') NOT NULL,
    PRIMARY KEY(user_id)
);

CREATE TABLE Room (
    room_id INT AUTO_INCREMENT UNIQUE,
    size VARCHAR(255),
    bed_count INT,
    room_number INT UNIQUE,
    location VARCHAR(255),
    additional_info TEXT,
    PRIMARY KEY(room_id)
);

CREATE TABLE Customer (
    customer_id INT AUTO_INCREMENT unique,
    name VARCHAR(255),
    address VARCHAR(255),
    payment_method VARCHAR(255),
    additional_info TEXT,
    PRIMARY KEY(customer_id)
);

CREATE TABLE Booking (
    booking_id INT AUTO_INCREMENT UNIQUE,
    customer_id INT,
    room_id INT,
    check_in_date DATE,
    check_out_date DATE,
    booking_date DATE,
    paid_status BOOLEAN,
    special_requests VARCHAR(255),
    PRIMARY KEY(booking_id),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
    FOREIGN KEY (room_id) REFERENCES Room(room_id)
);