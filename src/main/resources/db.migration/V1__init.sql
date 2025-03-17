-- Create the database (if it does not exist)
CREATE DATABASE IF NOT EXISTS orderdb;

-- Switch to the 'orderdb' database
\c orderdb;
CREATE TABLE IF NOT EXISTS orders (
  id UUID PRIMARY KEY,
  customer_id UUID NOT NULL,
  product_id UUID NOT NULL,
  quantity INTEGER NOT NULL,
  price DECIMAL(10,2),
  status VARCHAR(20),
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);

INSERT INTO orders (customer_id, product_id, quantity, price, status, created_at, updated_at)
VALUES
    (1, 101, 2, 19.99, 'Pending', NOW(), NOW()),
    (1, 102, 1, 49.99, 'Shipped', NOW(), NOW()),
    (2, 103, 3, 9.99, 'Delivered', NOW(), NOW()),
    (2, 104, 1, 299.99, 'Pending', NOW(), NOW()),
    (3, 105, 5, 15.49, 'Shipped', NOW(), NOW()),
    (4, 106, 2, 29.99, 'Delivered', NOW(), NOW()),
    (5, 107, 1, 109.99, 'Pending', NOW(), NOW()),
    (6, 108, 4, 39.99, 'Pending', NOW(), NOW()),
    (7, 109, 3, 79.99, 'Shipped', NOW(), NOW()),
    (8, 110, 2, 20.49, 'Delivered', NOW(), NOW());