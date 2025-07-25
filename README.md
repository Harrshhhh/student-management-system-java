# Student Management System

## Project Overview
This is a **console-based Student Management System** built with Java 8, JDBC, PostgreSQL, and Maven. It allows basic management of student records, including user authentication, CRUD operations, and advanced searching/filtering capabilities.

The project is designed as a backend application to help you practice core Java backend development skills and database integration, serving as a strong portfolio project.

## Features
- User authentication (admin and regular user roles)
- Create, Read, Update, Delete (CRUD) operations for student records
- Advanced queries: filter students by branch, year, and marks
- Data persistence using PostgreSQL
- Uses Java 8 features like Streams and Lambdas
- Maven for build and dependency management

## Tech Stack
- Java 8
- PostgreSQL
- JDBC
- Maven
- Console-based user interface

## Getting Started

### Prerequisites
- Java 8 SDK installed
- PostgreSQL installed and running
- Maven installed
- Git (for cloning the repo)

### Setup Instructions
1. Clone this repository:git clone https://github.com/Harrshhhh/student-management-system-java.git
2. Configure your PostgreSQL database:
- Create a database (e.g. "student_db")
- Run the provided SQL scripts in "database" folder (if available) to create tables
3. Update the "db.properties" file with your PostgreSQL credentials and connection URL.
4. Build the project with Maven: mvn clean install
5. Run the application: mvn exec:java -Dexec.mainClass="com.yourpackage.MainClassName"
*(Replace `com.yourpackage.MainClassName` with your actual main class)*

## Usage
- Upon running, you will be prompted to log in.
- Admin users can perform full CRUD operations on students.
- Regular users can view and search student records.
- Use the console menus to navigate between options.

## Project Structure
- `src/main/java` — Source code (models, DAO, services, main app)
- `src/main/resources` — Configuration files (e.g., `db.properties`)
- `src/test/java` — Unit tests for DAO and business logic

## Future Enhancements
- Add RESTful API with Spring Boot
- Implement unit testing and integration testing with JUnit
- Add GUI using Swing or migrate to a web frontend
- Enhance security with password hashing and role management

## License
This project is currently unlicensed (all rights reserved).

---

Feel free to reach out if you have questions or want to contribute!



