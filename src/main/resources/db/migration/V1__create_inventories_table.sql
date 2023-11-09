CREATE TABLE inventories
(
    id              BINARY(16) PRIMARY KEY,
    product_id      BINARY(16),
    stock_quantity INT,
    version INT
);
