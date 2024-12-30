-- Create Users Table
CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15),
    address TEXT,
    role ENUM('Admin', 'Customer') DEFAULT 'Customer',  -- Added role column
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create Pizzas Table
CREATE TABLE IF NOT EXISTS Pizzas (
    pizza_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    base_price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(255)
);

-- Create Toppings Table
CREATE TABLE IF NOT EXISTS Toppings (
    topping_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(5, 2) NOT NULL
);

-- Create Pizza_Toppings Table
CREATE TABLE IF NOT EXISTS Pizza_Toppings (
    pizza_id INT,
    topping_id INT,
    PRIMARY KEY (pizza_id, topping_id),
    FOREIGN KEY (pizza_id) REFERENCES Pizzas(pizza_id) ON DELETE CASCADE,
    FOREIGN KEY (topping_id) REFERENCES Toppings(topping_id) ON DELETE CASCADE
);

-- Create Orders Table
CREATE TABLE IF NOT EXISTS Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_status ENUM('Pending', 'Preparing', 'Baked', 'Delivered', 'Cancelled') DEFAULT 'Pending',
    total_price DECIMAL(10, 2),
    delivery_address TEXT,
    placed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Create Order_Pizzas Table
CREATE TABLE IF NOT EXISTS Order_Pizzas (
    order_id INT,
    pizza_id INT,
    quantity INT DEFAULT 1,
    price DECIMAL(10, 2),
    PRIMARY KEY (order_id, pizza_id),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (pizza_id) REFERENCES Pizzas(pizza_id) ON DELETE CASCADE
);

-- Create Payments Table
CREATE TABLE IF NOT EXISTS Payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    payment_method ENUM('Credit Card', 'Debit Card', 'PayPal', 'Cash') NOT NULL,
    payment_status ENUM('Pending', 'Completed', 'Failed') DEFAULT 'Pending',
    amount DECIMAL(10, 2),
    paid_at TIMESTAMP,
    loyalty_points_earned INT DEFAULT 0,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE
);

-- Create Loyalty_Program Table
CREATE TABLE IF NOT EXISTS Loyalty_Program (
    user_id INT,
    loyalty_points INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- Create Seasonal_Specials Table
CREATE TABLE IF NOT EXISTS Seasonal_Specials (
    special_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    discount_percentage DECIMAL(5, 2),
    start_date DATE,
    end_date DATE
);

-- Create Order_Feedback Table
CREATE TABLE IF NOT EXISTS Order_Feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    user_id INT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    feedback_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
