# Library Management System

A Java-based library management system that allows librarians to manage books, members, and lending operations. Built using Java Swing for the GUI and MySQL for data persistence.

## Features

- **Book Management**
  - Add new books to the library
  - Update existing book details
  - View all books in the system
  - Track book availability status
  - Make books available/unavailable

- **Member Management**
  - Register new library members
  - Update member information
  - View all registered members
  - Store member contact details

- **Lending Operations**
  - Issue books to members
  - Return books from members
  - View current lending records
  - Automatic return date calculation
  - Prevent lending of unavailable books

## Technical Details

### Prerequisites
- Java JDK 11 or higher
- MySQL Server 5.7 or higher
- Apache NetBeans IDE
- XAMPP (for MySQL server)

### Database Setup
1. Start XAMPP and ensure MySQL service is running
2. Create a new user:
   - Username: librarydbuser
   - Password: Library@Co2210
3. Import the database schema from `librarydb.sql`

### Dependencies
- mysql-connector-j-9.1.0.jar

### Project Structure
```
src/
├── main/
│   └── java/
│       └── com/
│           └── library/
│               ├── controller/
│               │   ├── BookController.java
│               │   ├── MemberController.java
│               │   └── LendingController.java
│               ├── model/
│               │   ├── Book.java
│               │   ├── Member.java
│               │   └── Lending.java
│               ├── view/
│               │   ├── MainFrame.java
│               │   ├── BookPanel.java
│               │   ├── MemberPanel.java
│               │   └── LendingPanel.java
│               └── util/
│                   └── DatabaseUtil.java
```

## Installation & Setup

1. Clone the repository
2. Open project in NetBeans
3. Install MySQL Connector/J
4. Set up the database:
   ```sql
   CREATE DATABASE librarydb;
   CREATE USER 'librarydbuser'@'localhost' IDENTIFIED BY 'Library@Co2210';
   GRANT ALL PRIVILEGES ON librarydb.* TO 'librarydbuser'@'localhost';
   ```
5. Import `librarydb.sql`
6. Build and run the project

## Usage

1. **Managing Books**
   - Use the Books tab
   - Enter book details and click "Add Book"
   - Select a book to update its information
   - Use availability buttons to change book status

2. **Managing Members**
   - Use the Members tab
   - Enter member details and click "Add Member"
   - Select a member to update their information

3. **Lending Operations**
   - Use the Lending tab
   - Enter book and member numbers
   - Click "Issue Book" to lend
   - Select a lending record and click "Return Book" to process returns
