

````
# 🏨 Hotel Booking System – JavaFX & MySQL

A desktop application for managing hotel operations, built with **JavaFX**, **MySQL**, and **Gradle**.  
The system is designed for small to medium-sized hotels (20–80 rooms) and provides separate interfaces and privileges for **administrators** and **reception staff**.

---

## 📘 Overview

This project implements a hotel booking system where hotel staff can manage rooms, customers, and bookings in a secure and user-friendly JavaFX environment. The system follows real user stories and includes functionality for authentication, room management, booking management, and customer handling.

The application connects to a **MySQL database**, supports multiple staff roles, and provides real-time validation and data handling.

---

## 🚀 Features

### 🔑 User Management
- Login system with screen name and password
- Change password / update profile  
- Role-based access:
  - **Administrator**
  - **Reception Staff**

### 🛏 Room Management (Administrator)
- Add rooms with details (size, beds, number, location, etc.)
- Edit room information
- Delete rooms
- View all rooms

### 📅 Booking Management (Reception Staff)
- Book rooms for a selected date range
- View bookings for a specific day
- View bookings for a specific room
- See an overview of all bookings
- Modify an existing booking
- Mark booking as paid
- Search for bookings by name or date
- Search for available rooms for a date or date range

### 👤 Customer Management
- Create new customer profiles
- Update customer information
- Store customer payment methods

---

## 📚 User Stories (Implemented)

**As a user**
- Start the application and see a welcome screen  
- Set and change screen name + password  

**As an administrator**
- Add / update / delete rooms  
- Add new system users (reception or admin)  

**As reception staff**
- View room details  
- Book rooms  
- View bookings by day or room  
- Edit existing bookings  
- Mark bookings as paid  
- Create and edit customer records  
- Search bookings  
- Search available rooms  

Additional user stories were added during development for improved workflow and usability.

---

## 🛠 Tech Stack

- **JavaFX** – Desktop GUI  
- **MySQL** – Database  
- **JDBC** – Database connectivity  
- **Gradle** – Project build & dependency management  
- **Java** – Core application logic  
- **Scene Builder** – UI layout tool  
- **GitLab** – Version control & issue tracking  

---

## 🗄 Database Structure

The database includes (but is not limited to) the following tables:

- `users`
- `rooms`
- `customers`
- `bookings`
- `booking_payments`
- `user_roles`

A full SQL schema is included in the repository.

---

## ⚙️ Installation & Setup

### 1. Clone the repository
```bash
git clone https://gitlab.lnu.se/YOUR-PROJECT-URL
cd hotel-booking-system
````

### 2. Configure MySQL connection

Update your `config.properties` or environment variables with:

```
DB_HOST=
DB_PORT=
DB_NAME=
DB_USER=
DB_PASSWORD=
```

### 3. Run the application using Gradle

```bash
./gradlew run
```

---

## 🧪 Testing

The project includes unit tests for:

* Database connections
* Booking validation
* Room availability calculations
* User authentication

Run tests with:

```bash
./gradlew test
```

---

## 👥 Team Members

* **Hasan Yahia** – hy222ce
* **Abdullah Alkanj** – aa226va
* **Zaid Alajlani** – za222gp
* **Edmunds Beks** – eb224p
* **Vo Thi Tuyet Mai** – vt222ev
* **Xunwei Ma** – xm222ad

---

## 🤝 Collaboration Notes

The project was developed through multiple sprints. Team members selected issues based on interest and expertise. Some scheduling challenges occurred due to members being abroad, causing variations in sprint length. Contribution balance differed at times due to personal circumstances, but the group collaborated and completed the system successfully.

---

## 📄 License

This project is for educational purposes within the university course.


