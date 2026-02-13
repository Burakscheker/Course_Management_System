# 🎓 Course Management System (LMS)

A robust, console-based **Learning Management System** built with **Java**. This project demonstrates **Object-Oriented Analysis and Design (OOAD)** principles, simulating core functionalities of platforms like Udemy or Coursera without a graphical user interface (GUI).

The system features a layered architecture separating **Models**, **Services**, and **Data**, ensuring clean code and scalability.

## 🚀 Key Features

* **👥 Role-Based Access Control:**
    * **Admin:** Create courses, add content (PDF/Video), view system-wide reports.
    * **Student:** Browse catalog, enroll in courses, track progress, view certificates.
* **📜 Dynamic Certificate Generation:** Automatically generates a personalized `.png` certificate upon 100% course completion using `java.awt`.
* **📂 Native Content Delivery:** Opens actual course materials (PDFs, Videos) stored locally using the OS's default applications.
* **💾 In-Memory Data Management:** Uses a mock database structure to simulate persistence during runtime.
* **🔄 Auto-Registration:** New users are automatically registered if they don't exist in the database.

## 🛠️ Tech Stack & Architecture

* **Language:** Java (JDK 8+)
* **Architecture:** Layered (Model - Service - Data)
* **Concepts:** OOP (Inheritance, Polymorphism, Encapsulation), File I/O, Java 2D Graphics.

### Project Structure
```text
src/
 ├── model/          # Entities (User, Student, Course, Lesson, Enrollment)
 ├── service/        # Business Logic (CourseManager, CertificateService)
 ├── data/           # Mock Database (Static Lists)
 └── main/           # Entry Point (Console UI & Menu Logic)
