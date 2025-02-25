USE MaintainManagement

INSERT INTO Brand (BrandName) VALUES
('ASUS'),
('MSI'),
('Gigabyte'),
('Lenovo'),
('Dell'),
('Intel'),
('Corsair'),
('NVIDIA'),
('Samsung'),
('Apple'),
('Sony'),
('Microsoft'),
('Bose'),
('LG'),
('Huawei'),
('OnePlus');

INSERT INTO ComponentType (TypeName) VALUES
('Mainboard'),
('Processor'),
('RAM'),
('Graphics Card'),
('SSD'),
('Battery'),
('Display'),
('Camera');
INSERT INTO Component (ComponentCode, ComponentName, Quantity, Price, Image, BrandID, TypeID)
VALUES
-- Các mainboard cho máy tính
('MB-ASUS-B760M', 'Mainboard ASUS B760M-K Prime D4', 50, 120.00, NULL, 1, 1),
('MB-MSI-Z490', 'Mainboard MSI Z490-A PRO', 40, 150.00, NULL, 2, 1),
('MB-GIG-B450M', 'Mainboard Gigabyte B450M DS3H', 30, 90.00, NULL, 3, 1),
('MB-ASUS-Z590', 'Mainboard ASUS ROG Strix Z590-E', 25, 250.00, NULL, 1, 1),
-- Các mainboard cho laptop
('MB-LEN-X1', 'Mainboard Lenovo ThinkPad X1', 60, 180.00, NULL, 4, 1),
('MB-DELL-XPS13', 'Mainboard Dell XPS 13', 70, 200.00, NULL, 5, 1),
-- Các linh kiện khác cho máy tính
('CPU-I7-10700K', 'Processor Intel Core i7-10700K', 100, 350.00, NULL, 6, 2),
('RAM-COR-16GB', 'RAM Corsair Vengeance 16GB DDR4', 200, 80.00, NULL, 7, 3),
('GPU-RTX-3060', 'Graphics Card NVIDIA RTX 3060', 50, 450.00, NULL, 8, 4),
('SSD-SAM-970', 'SSD Samsung 970 Evo 1TB', 75, 120.00, NULL, 9, 5),
-- Các linh kiện cho điện thoại
('BAT-IPH-12', 'Battery iPhone 12', 150, 50.00, NULL, 10, 6),
('BAT-SAM-S20', 'Battery Samsung Galaxy S20', 100, 45.00, NULL, 10, 6),
('DISP-IPH-12', 'Display iPhone 12', 120, 120.00, NULL, 10, 7),
('CAM-IPH-13P', 'Camera iPhone 13 Pro', 80, 200.00, NULL, 10, 8),
-- Các linh kiện cho các thiết bị khác
('BAT-MAC-PRO', 'Battery MacBook Pro', 50, 130.00, NULL, 10, 6),
('DISP-MAC-PRO', 'Display MacBook Pro', 40, 180.00, NULL, 10, 7),
-- Các linh kiện mới cho máy tính
('MB-ASUS-ROG', 'Mainboard ASUS ROG Strix B550-F', 40, 200.00, NULL, 1, 1),
('CPU-AMD-RYZEN', 'Processor AMD Ryzen 5 5600X', 80, 300.00, NULL, 6, 2),
('RAM-GSKILL-32GB', 'RAM G.Skill Ripjaws V 32GB DDR4', 150, 150.00, NULL, 7, 3),
('GPU-AMD-RX6800', 'Graphics Card AMD Radeon RX 6800', 30, 600.00, NULL, 8, 4),
('SSD-WD-1TB', 'SSD WD Blue 1TB', 100, 100.00, NULL, 9, 5),
-- Các linh kiện cho điện thoại
('BAT-SAM-S21', 'Battery Samsung Galaxy S21', 120, 55.00, NULL, 10, 6),
('DISP-SAM-S21', 'Display Samsung Galaxy S21', 100, 130.00, NULL, 10, 7),
('CAM-SAM-S21', 'Camera Samsung Galaxy S21', 90, 220.00, NULL, 10, 8),
-- Các linh kiện cho các thiết bị khác
('BAT-MAC-AIR', 'Battery MacBook Air M1', 60, 140.00, NULL, 10, 6),
('DISP-MAC-AIR', 'Display MacBook Air M1', 50, 200.00, NULL, 10, 7),
('BAT-DURACELL-AA', 'Pin Duracell AA', 200, 10.00, NULL, 1, 6),
('BAT-ENERGIZER-AA', 'Pin Energizer AA', 150, 12.00, NULL, 2, 6),
('BAT-PANASONIC-AA', 'Pin Panasonic AA', 180, 11.00, NULL, 3, 6),
('BAT-SONY-CR2032', 'Pin Sony CR2032', 250, 5.00, NULL, 4, 6),
('BAT-SAMSUNG-18650', 'Pin Samsung 18650', 100, 15.00, NULL, 5, 6),
('BAT-LG-18650', 'Pin LG 18650', 120, 14.00, NULL, 6, 6),
('BAT-VARTA-AA', 'Pin Varta AA', 160, 9.00, NULL, 7, 6),
('BAT-MAXELL-AAA', 'Pin Maxell AAA', 140, 8.00, NULL, 8, 6),
('BAT-TOSHIBA-CR123A', 'Pin Toshiba CR123A', 90, 7.00, NULL, 9, 6),
('BAT-PHILIPS-9V', 'Pin Philips 9V', 80, 13.00, NULL, 10, 6);



INSERT INTO ProductType (TypeName)
VALUES 
    ('Smartphone'),
    ('Headphones'),
    ('Laptop'),
    ('Tablet'),
    ('TV'),
    ('Gaming Console'),
    ('Wearable'),
    ('Camera'),
    ('Earbuds'),
    ('VR Headset');



INSERT INTO Product (Code, ProductName, BrandID, ProductTypeID, Quantity, WarrantyPeriod, [Status], Image)
VALUES 
    ('P001',  'Samsung Galaxy S23',        9,  1,  100,  24, 'Active', NULL),
    ('P002',  'Apple iPhone 14',           10, 1,  150,  24, 'Active', NULL),
    ('P003',  'Sony WH-1000XM5',           11, 2,  200,  12, 'Active', NULL),
    ('P004',  'Dell XPS 13',               5,  3,  80,   36, 'Active', NULL),
    ('P005',  'Microsoft Surface Pro 9',   12, 4,  60,   36, 'Active', NULL),
    ('P006',  'Apple MacBook Air M2',      10, 3,  120,  24, 'Active', NULL),
    ('P007',  'Bose QuietComfort 45',      13, 2,  140,  12, 'Active', NULL),
    ('P008',  'HP Spectre x360',           5,  3,  90,   24, 'Active', NULL),
    ('P009',  'LG OLED C2',                14, 5,  50,   36, 'Active', NULL),
    ('P010',  'Sony PlayStation 5',        11, 6,  180,  12, 'Active', NULL),
    ('P011',  'Samsung QLED 8K TV',        9,  5,  70,   36, 'Active', NULL),
    ('P012',  'Apple iPad Pro 11"',        10, 4,  160,  24, 'Active', NULL),
    ('P013',  'Fitbit Charge 5',           15, 7,  200,  12, 'Active', NULL),
    ('P014',  'GoPro Hero 11',             8,  8,  50,   24, 'Active', NULL),
    ('P015',  'Nintendo Switch OLED',      11, 6,  90,   12, 'Active', NULL),
    ('P016',  'Apple AirPods Pro 2',       10, 9,  150,  24, 'Active', NULL),
    ('P017',  'OnePlus 10 Pro',            16, 1,  100,  18, 'Active', NULL),
    ('P018',  'Microsoft Xbox Series X',   12, 6,  120,  12, 'Active', NULL),
    ('P019',  'Huawei Mate 50 Pro',        15, 1,  60,   36, 'Active', NULL),
    ('P020',  'Google Pixel 7',            16, 1,  80,   24, 'Active', NULL),
    ('P021',  'Xiaomi Mi 11',              16, 1,  90,   24, 'Active', NULL),
    ('P022',  'Oculus Quest 2',            12, 10, 70,   12, 'Active', NULL),
    ('P023',  'Razer Blade 15',            1,  3,  50,   24, 'Active', NULL),
    ('P024',  'Dell Alienware m15',        5,  3,  40,   36, 'Active', NULL);



-- Cập nhật bảng ProductComponents với các linh kiện chi tiết
-- Laptop Dell XPS 13 
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity) 
VALUES (2, 2, 1), -- Mainboard Dell XPS 13 
(2, 3, 1), -- Processor Intel Core i7-10700K 
(2, 3, 2), -- RAM Corsair Vengeance 16GB DDR4 
(2, 4, 1); -- Graphics Card NVIDIA RTX 3060

-- iPhone 12 
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity) 
VALUES (3, 14, 1), -- Battery iPhone 12 
(3, 15, 1), -- Display iPhone 12
(3, 16, 1); -- Camera iPhone 13 Pro

-- Samsung Galaxy S20 
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity) VALUES (4, 14, 1), -- Battery Samsung Galaxy S20
(4, 15, 1), -- Display iPhone 12 
(4, 16, 1); -- Camera iPhone 13 Pro
-- Samsung Galaxy S23
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(1, 14, 1),  -- Battery iPhone 12
(1, 15, 1),  -- Display iPhone 12
(1, 16, 1);  -- Camera iPhone 13 Pro

-- Apple iPhone 14
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(2, 14, 1),  -- Battery iPhone 12
(2, 15, 1),  -- Display iPhone 12
(2, 16, 1);  -- Camera iPhone 13 Pro

-- Sony WH-1000XM5
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(3, 10, 1);  -- Assuming a hypothetical component for headphones

-- Dell XPS 13
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(4, 2, 1),  -- Mainboard Dell XPS 13
(4, 6, 1),  -- Processor Intel Core i7-10700K
(4, 7, 2),  -- RAM Corsair Vengeance 16GB DDR4
(4, 8, 1);  -- Graphics Card NVIDIA RTX 3060

-- Microsoft Surface Pro 9
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(5, 4, 1),  -- Mainboard for Surface Pro
(5, 6, 1),  -- Processor Intel Core i7-10700K
(5, 7, 2);   -- RAM Corsair Vengeance 16GB DDR4

-- Apple MacBook Air M2
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(6, 10, 1),  -- Battery MacBook Air
(6, 11, 1);  -- Display MacBook Air

-- Bose QuietComfort 45
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(7, 10, 1);  -- Assuming a hypothetical component for headphones

-- HP Spectre x360
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(8, 2, 1),  -- Mainboard for HP Spectre
(8, 6, 1),  -- Processor Intel Core i7-10700K
(8, 7, 2);   -- RAM Corsair Vengeance 16GB DDR4

-- LG OLED C2
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(9, 10, 1);  -- Assuming a hypothetical component for TV

-- Sony PlayStation 5
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(10, 8, 1);  -- Graphics Card for PlayStation

-- Samsung QLED 8K TV
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(11, 10, 1);  -- Assuming a hypothetical component for TV

-- Apple iPad Pro 11"
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(12, 10, 1);  -- Assuming a hypothetical component for iPad

-- Fitbit Charge 5
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(13, 10, 1);  -- Assuming a hypothetical component for Fitbit

-- GoPro Hero 11
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(14, 10, 1);  -- Assuming a hypothetical component for GoPro

-- Nintendo Switch OLED
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(15, 10, 1);  -- Assuming a hypothetical component for Nintendo Switch

-- Apple AirPods Pro 2
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(16, 10, 1);  -- Assuming a hypothetical component for AirPods

-- OnePlus 10 Pro
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(17, 14, 1);  -- Battery for OnePlus

-- Microsoft Xbox Series X
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(18, 8, 1);  -- Graphics Card for Xbox

-- Huawei Mate 50 Pro
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(19, 10, 1);  -- Assuming a hypothetical component for Huawei

-- Google Pixel 7
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(20, 14, 1);  -- Battery for Google Pixel

-- Xiaomi Mi 11
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(21, 14, 1);  -- Battery for Xiaomi

-- Oculus Quest 2
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(22, 10, 1);  -- Assuming a hypothetical component for Oculus

-- Razer Blade 15
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(23, 2, 1),  -- Mainboard for Razer Blade
(23, 6, 1),  -- Processor Intel Core i7-10700K
(23, 7, 2);   -- RAM Corsair Vengeance 16GB DDR4

-- Dell Alienware m15
INSERT INTO ProductComponents (ProductID, ComponentID, Quantity)
VALUES
(24, 2, 1),  -- Mainboard for Alienware
(24, 6, 1),  -- Processor Intel Core i7-10700K
(24,  7, 2);   -- RAM Corsair Vengeance 16GB DDR4



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
(5, 2, 'PDT050', '2024-09-05'),
(1, 5, 'PDT051', '2024-09-10'),  -- Purchase for Samsung Galaxy S23
(2, 6, 'PDT052', '2024-09-15'),  -- Purchase for Apple iPhone 14
(3, 7, 'PDT053', '2024-09-20'),  -- Purchase for Sony WH-1000XM5
(4, 8, 'PDT054', '2024-09-25'),  -- Purchase for Dell XPS 13
(5, 9, 'PDT055', '2024-09-30'),  -- Purchase for Microsoft Surface Pro 9
(6, 10, 'PDT056', '2024-10-05'),  -- Purchase for Apple MacBook Air M2
(7, 11, 'PDT057', '2024-10-10'),  -- Purchase for Bose QuietComfort 45
(8, 12, 'PDT058', '2024-10-15'),  -- Purchase for HP Spectre x360
(9, 13, 'PDT059', '2024-10-20'),  -- Purchase for LG OLED C2
(11, 14, 'PDT060', '2024-10-25'),  -- Purchase for Sony PlayStation 5
(1, 15, 'PDT061', '2024-10-30'),  -- Purchase for Samsung QLED 8K TV
(2, 16, 'PDT062', '2024-11-05'),  -- Purchase for Apple iPad Pro 11"
(3, 17, 'PDT063', '2024-11-10'),  -- Purchase for Fitbit Charge 5
(4, 18, 'PDT064', '2024-11-15'),  -- Purchase for GoPro Hero 11
(5, 19, 'PDT065', '2024-11-20'),  -- Purchase for Nintendo Switch OLED
(6, 20, 'PDT066', '2024-11-25'),  -- Purchase for Apple AirPods Pro 2
(7, 21, 'PDT067', '2024-12-01'),  -- Purchase for OnePlus 10 Pro
(8, 22, 'PDT068', '2024-12-05'),  -- Purchase for Microsoft Xbox Series X
(9, 23, 'PDT069', '2024-12-10'),  -- Purchase for Huawei Mate 50 Pro
(6, 24, 'PDT070', '2024-12-15');  -- Purchase for Google Pixel 7

WITH RandomData AS (
    SELECT TOP 24
        CustomerID = ABS(CHECKSUM(NEWID())) % 10 + 1, -- Chọn ngẫu nhiên CustomerID từ 1 đến 10
        ProductName = 'Product ' + CHAR(65 + ABS(CHECKSUM(NEWID())) % 26) + CAST(ABS(CHECKSUM(NEWID())) % 100 AS NVARCHAR),
        ProductCode = 'P' + CAST(ABS(CHECKSUM(NEWID())) % 10000 AS NVARCHAR), -- Mã sản phẩm ngẫu nhiên từ P0 đến P9999
        Description = 'This is a description for product ' + CHAR(65 + ABS(CHECKSUM(NEWID())) % 26) + CAST(ABS(CHECKSUM(NEWID())) % 100 AS NVARCHAR),
        ReceivedDate = DATEADD(DAY, -ABS(CHECKSUM(NEWID())) % 730, GETDATE()) -- Ngày ngẫu nhiên trong 2 năm gần đây
    FROM master.dbo.spt_values
)
INSERT INTO UnknownProduct (CustomerID, ProductName, ProductCode, Description, ReceivedDate)
SELECT CustomerID, ProductName, ProductCode, Description, ReceivedDate
FROM RandomData;
-- insert more to customerID = 1 for testing
INSERT INTO UnknownProduct (CustomerID, ProductName, ProductCode, Description, ReceivedDate)
SELECT TOP 5
    1, -- CustomerID cố định là 1
    'Product ' + CHAR(65 + ABS(CHECKSUM(NEWID())) % 26) + CAST(ABS(CHECKSUM(NEWID())) % 100 AS NVARCHAR),
    'P' + CAST(ABS(CHECKSUM(NEWID())) % 10000 AS NVARCHAR),
    'This is a description for product ' + CHAR(65 + ABS(CHECKSUM(NEWID())) % 26) + CAST(ABS(CHECKSUM(NEWID())) % 100 AS NVARCHAR),
    DATEADD(DAY, -ABS(CHECKSUM(NEWID())) % 730, GETDATE()) 
FROM master.dbo.spt_values;
