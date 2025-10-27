# ⚙️ **Auto Repair Management System**

> A Java-based desktop application designed to streamline and automate the daily operations of an auto repair shop.

---

## 🚀 **Introduction**

The **Auto Repair Management System** is designed to manage various operations of a car repair shop efficiently.  
It allows **mechanics** and **shop administrators** to keep track of:  
- Customer details 👥  
- Vehicle service history 🚗  
- Appointments 📅  
- Inventory of parts ⚙️  
- Billing and invoicing 💰  

The system is **user-friendly**, **modular**, and **scalable**, ensuring long-term maintainability and smooth management of repair operations.

---

## 🎯 **Project Objectives**

- ✅ To manage key auto repair shop processes such as customer and vehicle data, appointments, inventory tracking, and billing.  
- ✅ To store and manage vehicle repair history efficiently.  
- ✅ To improve operational efficiency by reducing paperwork, enhancing customer service, and automating administrative tasks.  

---
## 📂 Project Structure

```
mainproj/
├── entities/              # Entity classes (Customer, Vehicle, BaseEntity)
├── exceptions/           # Custom exception classes
├── interfaces/           # Interface definitions
├── utils/               # Utility classes
├── database/            # Database connection classes
├── lib/                 # External libraries (MySQL Connector)
├── Login.java           # Main entry point (Login screen)
├── MainDashboard.java   # Main dashboard after login
├── Appointments.java    # Appointment management
├── AccessInformation.java  # Information access module
├── ViewAllInformation.java # View all records
├── RepairStatus.java    # Repair status management
├── GenerateBill.java    # Bill generation
├── RepairShopApp.java   # Legacy application
├── UIStyle.java         # UI styling utilities
└── DatabaseConnection.java # Database connection utility
```

## 📝 Key Classes

- **Login.java**: Authentication and login functionality
- **MainDashboard.java**: Main application dashboard
- **DatabaseConnection.java**: Database connection management
- **Customer.java**: Customer entity
- **Vehicle.java**: Vehicle entity
- **Appointments.java**: Appointment management
- **GenerateBill.java**: Bill generation and printing

## 🧰 **Software Stack Used**

### 🖥️ **Frontend**
- **Java Swing** for GUI  
- Custom styling using **UIStyle**

### 🧩 **Backend**
- **Java SE** for core logic  
- Event handling, validation, and CRUD operations implemented in pure Java

### 🗄️ **Database**
- **MySQL** for relational database management

### 🔗 **Database Connectivity**
- **JDBC (Java Database Connectivity)** using MySQL Connector  

---

## 🖼️ **GUI Design**

| Screenshot | Description |
|-------------|-------------|
| <img width="876" height="613" alt="image" src="https://github.com/user-attachments/assets/12ed998a-0c21-4e4c-94d5-02462503ab3a" /> | Main Dashboard |
| <img width="593" height="953" alt="image" src="https://github.com/user-attachments/assets/b37af8f2-8ea6-49b4-b19f-22c7e5a47938" /> | Automatic Bill Generation |
| <img width="761" height="1014" alt="image" src="https://github.com/user-attachments/assets/d68f1ee8-8daf-489f-935b-c70ee81ba8e7" /> | Appointment & Customer Management |
| <img width="729" height="465" alt="image" src="https://github.com/user-attachments/assets/8641f60c-5fd7-4a59-81be-0a1791af3708" /> | Repair Status|

---

## 🗃️ **Database Design**

| Screenshot | Description |
|-------------|-------------|
| <img width="1360" height="227" alt="image" src="https://github.com/user-attachments/assets/705c5422-3532-404c-be57-d48b9081bff5" /> | Customers SQL table |
| <img width="1066" height="218" alt="image" src="https://github.com/user-attachments/assets/c53df5ee-fcfa-46b8-a79d-109c0f453378" /> | Vehicle SQL Tables |
| <img width="1378" height="278" alt="image" src="https://github.com/user-attachments/assets/45688323-080c-4c32-b875-0e1a022d6391" /> | Appointments SQL Tables |

---


## 📋 Prerequisites

Before running this project, ensure you have the following installed:

1. **Java Development Kit (JDK)** - Version 8 or higher
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Verify installation: `java -version`

2. **MySQL Database Server** - Version 5.7 or higher
   - Download from: https://dev.mysql.com/downloads/mysql/
   - During installation, remember your MySQL root password (default is often empty or "root")

3. **MySQL Connector/J** - Already included in `lib/mysql-connector-j-9.0.0.jar`

## 📥 Installation

### Step 1: Clone the Repository

Open your terminal or command prompt and run:

```bash
git clone https://github.com/yourusername/repair-shop-management.git
cd repair-shop-management/mainproj/mainproj
```

**Note**: Replace `yourusername` with your actual GitHub username.

### Step 2: Set Up the Database

1. **Start MySQL Server**
   - On Windows: MySQL should start automatically after installation
   - You can also start it manually from Services

2. **Open MySQL Command Line or MySQL Workbench**

3. **Create the Database and Tables**

   Run the SQL script located at the root of the project:
   
   ```bash
   # Navigate to the project root directory
   cd D:\Aooproject
   
   # Run the SQL script (replace with your MySQL credentials)
   mysql -u root -p < schema.sql
   ```
   
   Or manually:
   ```sql
   -- In MySQL Workbench or Command Line, execute:
   mysql -u root -p
   -- Enter your password when prompted
   -- Then paste the contents of schema.sql
   ```

4. **Create a Test User**

   You need to create at least one user account to log in. Run the following SQL:
   
   ```sql
   USE repairshop;
   
   INSERT INTO users (username, password, role) 
   VALUES ('admin', 'admin123', 'ADMIN');
   
   INSERT INTO users (username, password, role) 
   VALUES ('staff', 'staff123', 'STAFF');
   ```

### Step 3: Verify Database Configuration

The application connects to MySQL with these credentials:
- **Database URL**: `jdbc:mysql://127.0.0.1:3306/repairshop`
- **Username**: `root`
- **Password**: `root`

**Important**: If your MySQL root password is different, you need to update the credentials in:
- `DatabaseConnection.java` (line 10-11)
- `database/EnhancedDatabaseConnection.java` (line 16-17)

Change these lines:
```java
private static final String USERNAME = "root";  // Your MySQL username
private static final String PASSWORD = "your_password";  // Your MySQL password
```

## ▶️ Running the Application

### Method 1: Using IDE (Recommended)

1. **Open the project in your IDE** (Eclipse, IntelliJ IDEA, or NetBeans)

2. **Add MySQL Connector JAR to classpath**:
   - Right-click on the project
   - Go to Build Path → Configure Build Path
   - Click "Add External JARs"
   - Navigate to `lib/mysql-connector-j-9.0.0.jar`
   - Click OK

3. **Run the application**:
   - Locate `Login.java` in your project
   - Right-click on `Login.java`
   - Select "Run As" → "Java Application"

### Method 2: Using Command Line

```bash
# Navigate to the project directory
cd mainproj/mainproj

# Compile all Java files
javac -cp .;lib/mysql-connector-j-9.0.0.jar *.java entities/*.java exceptions/*.java interfaces/*.java utils/*.java database/*.java

# Run the application
java -cp .;lib/mysql-connector-j-9.0.0.jar Login
```

**For Linux/Mac**, use `:` instead of `;`:
```bash
javac -cp .:lib/mysql-connector-j-9.0.0.jar *.java entities/*.java exceptions/*.java interfaces/*.java utils/*.java database/*.java
java -cp .:lib/mysql-connector-j-9.0.0.jar Login
```

### Method 3: Using JAR File (If Available)

```bash
java -jar RepairShopApp.jar
```

## 🔑 Login Credentials

After setting up the database with the test users, you can log in with:

### Admin Account:
- **Username**: `admin`
- **Password**: `admin123`
- **Role**: ADMIN

### Staff Account:
- **Username**: `staff`
- **Password**: `staff123`
- **Role**: STAFF


## 📚 **Conclusion**

The **Auto Repair Management System** in Java aims to **streamline the operations** of an auto repair shop by providing a centralized and efficient management solution for customers, vehicles, inventory, appointments, and billing.

By fulfilling the **functional requirements**—such as user management, service tracking, invoicing, and reporting—the system enhances both **operational efficiency** and **customer satisfaction**.  
Additionally, its **non-functional strengths** in performance, scalability, security, and usability ensure that the solution remains **reliable, maintainable, and future-proof**.

---

### 🧑‍💻 *Developed by*  
**Tanmay Singh**  
📍 *Computer Science - Big Data Analytics*  
🔗 [GitHub Profile](https://github.com/tannnmayy)
