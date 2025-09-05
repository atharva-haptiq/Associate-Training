# 🏬 Store Inventory Management Console Application

A **Java console-based application** for managing store inventory, built using **JDBC** and **MySQL**. This system allows you to manage categories, products, and transactions with full CRUD operations, pagination, and proper stock tracking.

---

## 📌 Features

### 🔹 Category Management
- Add, view, update, delete categories
- Paginated category listing
- Fetch category by name or ID

### 🔹 Product Management
- Add, view, update, delete products
- Fetch by ID or name
- Smart update: only modified fields are updated
- Pagination support
- Filter low-stock products (e.g., stock ≤ 10)
- Automatic stock updates during transactions

### 🔹 Transaction Management
- Create IN/OUT transactions
- Automatically updates product stock
- Prevents OUT transactions if stock is insufficient
- View all transactions with pagination
- Filter by product, category, or date
- Update and delete transactions

### 🛡 Security & Safety
- Uses `PreparedStatement` to prevent SQL injection
- Transaction rollback on failure
- Try-with-resources used for all JDBC operations

---

## 🧱 Project Structure

```
src/
│
├── entities/              # Entity classes: Product, Category, Transaction
├── enums/                 # Enum: TransactionType (IN, OUT)
├── repository/            # DBConnection utility
├── service/               # Service interfaces
├── serviceImpls/          # Service implementations
└── Main.java              # Console-based user interface
```

---

## 🛠 Technologies Used

- **Java 8**
- **MySQL 8**
- **JDBC (Java Database Connectivity)**
- **Maven** *(optional for dependency management)*

---

## ⚙️ Setup Instructions

### 1. 🔧 Database Setup

Run the following SQL to create the database and tables:

```sql
CREATE DATABASE inventory_db;

USE inventory_db;

CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL CHECK (price >= 0),
    stock INT DEFAULT 0 CHECK (stock >= 0),
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    type ENUM('IN', 'OUT') NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);
```

### 2. 🔑 Update DB Credentials

In `DBConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/inventory_db";
private static final String USER = "your_mysql_username";
private static final String PASSWORD = "your_mysql_password";
```

---

## ▶️ Running the App

1. Open the project in your favorite IDE (IntelliJ, VS Code, Eclipse, etc.)
2. Run `Main.java`
3. Use the console prompts to manage:
    - Categories
    - Products
    - Transactions

---

## 💡 Example Features to Try

- Create a new category (e.g., "Electronics")
- Add products to a category (e.g., "Laptop", "Mouse")
- Record IN and OUT transactions
- Fetch low-stock products
- Filter transactions by product or date

---

## 📌 Sample SQL Queries for Testing

```sql
-- Get all products in low stock
SELECT * FROM products WHERE stock <= 10;

-- Get all transactions for a product
SELECT * FROM transactions WHERE product_id = 3;

-- Paginate products (page 2, 10 items per page)
SELECT * FROM products LIMIT 10 OFFSET 10;

-- Filter transactions on specific date
SELECT * FROM transactions WHERE DATE(date) = '2025-08-05';
```

---

## 🚀 Future Enhancements (Ideas)

- Export data to CSV (products, transactions)
- Undo last transaction
- Login system for admins
- Generate reports (daily stock movement)

---

## 🧑‍💻 Author

Built as part of a real-world Java + JDBC learning project.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).