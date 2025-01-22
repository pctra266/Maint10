
INSERT INTO Component (ComponentCode, ComponentName, Quantity, Price, Image)
VALUES
-- Các mainboard cho máy tính
('MB-ASUS-B760M', 'Mainboard ASUS B760M-K Prime D4', 50, 120.00, NULL),
('MB-MSI-Z490', 'Mainboard MSI Z490-A PRO', 40, 150.00, NULL),
('MB-GIG-B450M', 'Mainboard Gigabyte B450M DS3H', 30, 90.00, NULL),
('MB-ASUS-Z590', 'Mainboard ASUS ROG Strix Z590-E', 25, 250.00, NULL),
-- Các mainboard cho laptop
('MB-LEN-X1', 'Mainboard Lenovo ThinkPad X1', 60, 180.00, NULL),
('MB-DELL-XPS13', 'Mainboard Dell XPS 13', 70, 200.00, NULL),
-- Các linh kiện khác cho máy tính
('CPU-I7-10700K', 'Processor Intel Core i7-10700K', 100, 350.00, NULL),
('RAM-COR-16GB', 'RAM Corsair Vengeance 16GB DDR4', 200, 80.00, NULL),
('GPU-RTX-3060', 'Graphics Card NVIDIA RTX 3060', 50, 450.00, NULL),
('SSD-SAM-970', 'SSD Samsung 970 Evo 1TB', 75, 120.00, NULL),
-- Các linh kiện cho điện thoại
('BAT-IPH-12', 'Battery iPhone 12', 150, 50.00, NULL),
('BAT-SAM-S20', 'Battery Samsung Galaxy S20', 100, 45.00, NULL),
('DISP-IPH-12', 'Display iPhone 12', 120, 120.00, NULL),
('CAM-IPH-13P', 'Camera iPhone 13 Pro', 80, 200.00, NULL),
-- Các linh kiện cho các thiết bị khác
('BAT-MAC-PRO', 'Battery MacBook Pro', 50, 130.00, NULL),
('DISP-MAC-PRO', 'Display MacBook Pro', 40, 180.00, NULL);
INSERT INTO Product (ProductName, Quantity, WarrantyPeriod, Image)
VALUES
('Laptop Lenovo ThinkPad X1', 30, 12, NULL),
('Laptop Dell XPS 13', 40, 24, NULL),
('iPhone 12', 50, 6, NULL),
('Samsung Galaxy S20', 60, 3, NULL);


INSERT INTO Product (ProductName, Quantity, WarrantyPeriod, Image) VALUES
('Samsung Galaxy S23', 100, 24, NULL),
('Apple iPhone 14', 150, 24, NULL),
('Sony WH-1000XM5', 200, 12, NULL),
('Dell XPS 13', 80, 36, NULL),
('Microsoft Surface Pro 9', 60, 36, NULL),
('Apple MacBook Air M2', 120, 24, NULL),
('Bose QuietComfort 45', 140, 12, NULL),
('HP Spectre x360', 90, 24, NULL),
('LG OLED C2', 50, 36, NULL),
('Sony PlayStation 5', 180, 12, NULL),
('Samsung QLED 8K TV', 70, 36, NULL),
('Apple iPad Pro 11"', 160, 24, NULL),
('Fitbit Charge 5', 200, 12, NULL),
('GoPro Hero 11', 50, 24, NULL),
('Nintendo Switch OLED', 90, 12, NULL),
('Apple AirPods Pro 2', 150, 24, NULL),
('OnePlus 10 Pro', 100, 18, NULL),
('Microsoft Xbox Series X', 120, 12, NULL),
('Huawei Mate 50 Pro', 60, 36, NULL);
-- Cập nhật bảng ProductComponents với các linh kiện chi tiết
-- Laptop Lenovo ThinkPad X1
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(1, 1, 1),  -- Mainboard Lenovo ThinkPad X1
(1, 2, 1),  -- Processor Intel Core i7-10700K
(1, 3, 2),  -- RAM Corsair Vengeance 16GB DDR4
(1, 4, 1);  -- SSD Samsung 970 Evo 1TB

-- Laptop Dell XPS 13
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(2, 2, 1),  -- Mainboard Dell XPS 13
(2, 3, 1),  -- Processor Intel Core i7-10700K
(2, 3, 2),  -- RAM Corsair Vengeance 16GB DDR4
(2, 4, 1);  -- Graphics Card NVIDIA RTX 3060

-- iPhone 12
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(3, 14, 1),  -- Battery iPhone 12
(3, 15, 1),  -- Display iPhone 12
(3, 16, 1);  -- Camera iPhone 13 Pro

-- Samsung Galaxy S20
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(4, 14, 1),  -- Battery Samsung Galaxy S20
(4, 15, 1),  -- Display iPhone 12
(4, 16, 1);  -- Camera iPhone 13 Pro

-- Thêm dữ liệu vào bảng ProductDetail
INSERT INTO ProductDetail (CustomerID, ProductID, ProductCode, PurchaseDate)
VALUES
(9, 1, 'PDT009', '2024-02-12'),
(10, 2, 'PDT010', '2024-02-15'),
(11, 3, 'PDT011', '2024-02-20'),
(12, 4, 'PDT012', '2024-02-25'),
(13, 1, 'PDT013', '2024-03-01'),
(14, 1, 'PDT014', '2024-03-05'),
(15, 1, 'PDT015', '2024-03-10'),
(16, 2, 'PDT016', '2024-03-15'),
(17, 3, 'PDT017', '2024-03-20'),
(18, 4, 'PDT018', '2024-03-25'),
(19, 2, 'PDT019', '2024-04-01'),
(20, 3, 'PDT020', '2024-04-05'),
(1, 4, 'PDT021', '2024-04-10'),
(2, 1, 'PDT022', '2024-04-15'),
(3, 2, 'PDT023', '2024-04-20'),
(4, 1, 'PDT024', '2024-04-25'),
(5, 4, 'PDT025', '2024-05-01'),
(6, 3, 'PDT026', '2024-05-05'),
(7, 3, 'PDT027', '2024-05-10'),
(8, 4, 'PDT028', '2024-05-15'),
(9, 2, 'PDT029', '2024-05-20'),
(8, 1, 'PDT030', '2024-05-25'),
(1, 3, 'PDT031', '2024-06-01'),
(2, 1, 'PDT032', '2024-06-05'),
(3, 2, 'PDT033', '2024-06-10'),
(4, 1, 'PDT034', '2024-06-15'),
(5, 4, 'PDT035', '2024-06-20'),
(6, 2, 'PDT036', '2024-06-25'),
(7, 1, 'PDT037', '2024-07-01'),
(8, 4, 'PDT038', '2024-07-05'),
(9, 2, 'PDT039', '2024-07-10'),
(14, 3, 'PDT040', '2024-07-15'),
(1, 4, 'PDT041', '2024-07-20'),
(2, 3, 'PDT042', '2024-07-25'),
(3, 2, 'PDT043', '2024-08-01'),
(4, 1, 'PDT044', '2024-08-05'),
(5, 1, 'PDT045', '2024-08-10'),
(6, 2, 'PDT046', '2024-08-15'),
(7, 3, 'PDT047', '2024-08-20'),
(8, 4, 'PDT048', '2024-08-25'),
(9, 4, 'PDT049', '2024-09-01'),
(5, 2, 'PDT050', '2024-09-05');
