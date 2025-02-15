USE master
GO

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'MaintainManagement')
BEGIN
    ALTER DATABASE MaintainManagement SET OFFLINE WITH ROLLBACK IMMEDIATE;
    ALTER DATABASE MaintainManagement SET ONLINE;
    DROP DATABASE MaintainManagement;
END

GO
CREATE DATABASE MaintainManagement
GO
USE MaintainManagement
GO

-- Staff Table
CREATE TABLE Staff (
    StaffID int IDENTITY(1,1) NOT NULL PRIMARY KEY,
    UsernameS NVARCHAR(50) UNIQUE,
    PasswordS NVARCHAR(50),
    [Role] NVARCHAR(30) CHECK ([Role] IN ('Admin', 'Technician', 'Inventory Manager', 'Customer', 'Repair Contractor', 'Customer Service Agent', NULL)),
    [Name] NVARCHAR(100),
    Email NVARCHAR(100),
    Phone NVARCHAR(20),
    [Address] NVARCHAR(255),
    Image NVARCHAR(MAX) 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY];

-- StaffLog Table
CREATE TABLE StaffLog (
    StaffLogID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    StaffID INT REFERENCES Staff(StaffID) ON DELETE SET NULL,
    UsernameS NVARCHAR(50) ,
    PasswordS NVARCHAR(50),
    [Role] NVARCHAR(30) CHECK ([Role] IN ('Admin', 'Technician', 'Inventory Manager', 'Customer', 'Repair Contractor', 'Customer Service Agent', NULL)),
    [Name] NVARCHAR(100),
    Email NVARCHAR(100),
    Phone NVARCHAR(20),
    [Address] NVARCHAR(255),
    Image NVARCHAR(MAX) ,
    [Time] DATETIME,
    [Status] NVARCHAR(100),
);

-- Customer Table
CREATE TABLE Customer (
    CustomerID int IDENTITY(1,1) NOT NULL PRIMARY KEY,
    UsernameC NVARCHAR(50) UNIQUE,
    PasswordC NVARCHAR(50),
    [Name] NVARCHAR(100),
	Gender NVARCHAR(10),
    Email NVARCHAR(100),
    Phone NVARCHAR(20),
    [Address] NVARCHAR(255),
    Image NVARCHAR(MAX) 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY];

CREATE TABLE Brand (
    BrandID INT IDENTITY(1,1) PRIMARY KEY,
    BrandName NVARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE ComponentType (
    TypeID INT IDENTITY(1,1) PRIMARY KEY,
    TypeName NVARCHAR(50) NOT NULL UNIQUE
);
-- Component Table
CREATE TABLE Component (
    ComponentID int IDENTITY(1,1) NOT NULL PRIMARY KEY ,
	ComponentCode Nvarchar(20) null,
    ComponentName NVARCHAR(100),
	 BrandID INT FOREIGN KEY REFERENCES Brand(BrandID),
    TypeID INT FOREIGN KEY REFERENCES ComponentType(TypeID),
    Quantity int,
	[Status] bit default 1,
    Price FLOAT,
    Image NVARCHAR(MAX) 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY];
-- Tạo chỉ mục chỉ áp dụng ràng buộc unique khi ComponentCode không phải NULL
CREATE UNIQUE INDEX UX_ComponentCode ON Component(ComponentCode)
WHERE ComponentCode IS NOT NULL;


-- Product Table (Updated)
CREATE TABLE Product (
    ProductID int IDENTITY(1,1) NOT NULL PRIMARY KEY,
    Code Nvarchar(20) null,
    ProductName NVARCHAR(100) , 
	 BrandID INT FOREIGN KEY REFERENCES Brand(BrandID),
    [Type] nvarchar(100), 
    Quantity int,
    WarrantyPeriod int NOT NULL,
    [Status] nvarchar(100),
    Image NVARCHAR(MAX)
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY];

-- ProductComponents Table
CREATE TABLE ProductComponents (
    ProductComponentsID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    ProductID INT NOT NULL REFERENCES Product(ProductID),
    ComponentID INT NOT NULL REFERENCES Component(ComponentID),
    Quantity INT NOT NULL CHECK (Quantity >= 1) --Component in a product if equal to 0, it should not be in this table
);

-- ProductDetail Table
CREATE TABLE ProductDetail (
    ProductDetailID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    CustomerID INT NOT NULL REFERENCES Customer(CustomerID),
    ProductID INT NOT NULL REFERENCES Product(ProductID),
    ProductCode NVARCHAR(50) NOT NULL UNIQUE,
    PurchaseDate DATETIME NOT NULL
);

-- WarrantyCard Table
CREATE TABLE WarrantyCard (
    WarrantyCardID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    WarrantyCardCode NVARCHAR(10) NOT NULL UNIQUE,
    ProductDetailID INT NOT NULL REFERENCES ProductDetail(ProductDetailID),
    IssueDescription NVARCHAR(MAX),
    WarrantyStatus NVARCHAR(50) NOT NULL CHECK (WarrantyStatus IN ('fixing', 'done', 'completed', 'cancel')),
	[ReturnDate] DATETIME, --Ngay du kien
	DoneDate DATETIME, -- Ngay sua xong
	CompleteDate DATETIME, --Ngay tra may
	CancelDate DATETIME, --Ngay huy card
    CreatedDate DATETIME DEFAULT GETDATE(),
	[Image] NVARCHAR(MAX)
);

-- ComponentRequest Table
CREATE TABLE ComponentRequest (
    ComponentRequestID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    WarrantyCardID INT NOT NULL REFERENCES WarrantyCard(WarrantyCardID),
    [Date] DATETIME DEFAULT GETDATE(),
    Status NVARCHAR(20) NOT NULL CHECK (Status IN ('waiting', 'approved', 'cancel')),
    Note NVARCHAR(MAX)
);

-- ComponentRequestDetail Table
CREATE TABLE ComponentRequestDetail (
    ComponentRequestDetailID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    ComponentID INT NOT NULL REFERENCES Component(ComponentID),
    ComponentRequestID INT NOT NULL REFERENCES ComponentRequest(ComponentRequestID),
    Quantity INT NOT NULL CHECK (Quantity >= 0)
);

-- ComponentRequestResponsible Table
CREATE TABLE ComponentRequestResponsible (
    ComponentRequestResponsibleID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    StaffID INT NOT NULL REFERENCES Staff(StaffID),
    ComponentRequestID INT NOT NULL REFERENCES ComponentRequest(ComponentRequestID),
    [Action] NVARCHAR(10) NOT NULL CHECK ([Action] IN ('request', 'approved', 'cancel'))
);

-- WarrantyCardDetail Table
CREATE TABLE WarrantyCardDetail (
    WarrantyCardDetailID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    WarrantyCardID INT NOT NULL REFERENCES WarrantyCard(WarrantyCardID),
    ProductComponentsID INT NOT NULL REFERENCES ProductComponents(ProductComponentsID),
    Status NVARCHAR(20) NOT NULL CHECK (Status IN ('under_warranty', 'repaired', 'replace')),
    Price FLOAT NOT NULL CHECK (Price >= 0),
    Quantity INT NOT NULL CHECK (Quantity >= 0)
);

-- WarrantyCardProcess Table
CREATE TABLE WarrantyCardProcess (
    WarrantyCardProcessID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    WarrantyCardID INT NOT NULL REFERENCES WarrantyCard(WarrantyCardID),
    HandlerID INT NOT NULL REFERENCES Staff(StaffID),
    [Action] NVARCHAR(20) NOT NULL CHECK ([Action] IN ('reception', 'wait_components', 'received_components', 'outsource', 'completed', 'cancel')),
    ActionDate DATETIME DEFAULT GETDATE(),
    Note NVARCHAR(MAX)
);

-- Payment Table
CREATE TABLE Payment (
    PaymentID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    WarrantyCardID INT NOT NULL REFERENCES WarrantyCard(WarrantyCardID),
    PaymentDate DATE NOT NULL,
    PaymentMethod NVARCHAR(20) NOT NULL CHECK (PaymentMethod IN ('cash', 'bank_transfer')),
    Amount FLOAT NOT NULL CHECK (Amount >= 0),
    Status NVARCHAR(20) NOT NULL CHECK (Status IN ('pending', 'complete', 'fail'))
);

-- Feedback Table
CREATE TABLE Feedback (
    FeedbackID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    CustomerID INT NOT NULL REFERENCES Customer(CustomerID),
    WarrantyCardID INT REFERENCES WarrantyCard(WarrantyCardID),
    Note NVARCHAR(MAX),
	DateCreated DATETIME NOT NULL,
	IsDeleted BIT DEFAULT 0 NOT NULL,
	ImageURL NVARCHAR(500),
	VideoURL NVARCHAR(500)
);
-- FeedbackLog Table
CREATE TABLE FeedbackLog (
    FeedbackLogID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,      
    FeedbackID INT NOT NULL REFERENCES Feedback(FeedbackID),                      
    [Action] NVARCHAR(50) NOT NULL CHECK ([Action] IN ('update', 'delete')),                  
    OldFeedbackText NVARCHAR(1000),       
    NewFeedbackText NVARCHAR(1000),       
    ModifiedBy INT NOT NULL REFERENCES Staff(StaffID),                      
    DateModified DATETIME                
);


-- Tăng tốc truy vấn: Chỉ mục sẽ giúp tăng tốc các truy vấn có điều kiện lọc hoặc tìm kiếm theo các cột
CREATE NONCLUSTERED INDEX IX_Customer_Phone ON Customer(Phone);
CREATE NONCLUSTERED INDEX IX_WarrantyCard_WarrantyCardCode ON WarrantyCard(WarrantyCardCode);
CREATE NONCLUSTERED INDEX IX_WarrantyCard_WarrantyStatus ON WarrantyCard(WarrantyStatus);

--them data vao staff
INSERT INTO Staff (UsernameS, PasswordS, [Role], [Name], Email, Phone, [Address], Image)
VALUES
('admin01', 'T2D6i9+ldWGqevu6W6FAP1iXBbI=', 'Admin', 'Admin User 1', 'admin1@example.com', '0123456789', '123 Admin Street', NULL),
('tech01', 'Cw2LaFmhUP2i/jGdPuB5aVCxAQg=', 'Technician', 'Technician 1', 'tech1@example.com', '0123456790', '456 Tech Street', NULL),
('inv_manager1', 'G1VKaeAjqcN762tlQAIFfla2Hnc=', 'Inventory Manager', 'Inventory Manager 1', 'inv1@example.com', '0123456791', '789 Inventory Ave', NULL),
('cust_service1', 'mnIoccUReEqm3CnIyVEsEqXI418=', 'Customer Service Agent', 'Customer Service 1', 'cs1@example.com', '0123456792', '321 Service Blvd', NULL),
('repair_contractor1', 'cDW7EzjR1mP8REo1UF3mWlnuZdo=', 'Repair Contractor', 'Repair Contractor 1', 'repair1@example.com', '0123456793', '654 Repair St', NULL),
('admin02', 'O5eiLfa5pJNrHk7ck1tNBw6pUYE=', 'Admin', 'Admin User 2', 'admin2@example.com', '0123456794', '234 Admin Street', NULL),
('tech02', 't6oNiVU8eL8t1C+e11mByn7n7oA=', 'Technician', 'Technician 2', 'tech2@example.com', '0123456795', '567 Tech St', NULL),
('inv_manager2', '2L5q1/vlAepSi4mrWEoz+oJnTEY=', 'Inventory Manager', 'Inventory Manager 2', 'inv2@example.com', '0123456796', '890 Inventory Ave', NULL),
('cust_service2', 'hNpyQH84BIbgDRM+z0nJgUqCeV8=', 'Customer Service Agent', 'Customer Service 2', 'cs2@example.com', '0123456797', '432 Service Blvd', NULL),
('repair_contractor2', 'NiH6AmjlqDCWROFjLhNDiYQ1F5U=', 'Repair Contractor', 'Repair Contractor 2', 'repair2@example.com', '0123456798', '765 Repair St', NULL),
('admin03', 'm1HKaKLljc/8wGtZ4IIGTTCCPdE=', 'Admin', 'Admin User 3', 'admin3@example.com', '0123456799', '345 Admin Ave', NULL),
('tech03', 'z7f1n0HLfbf71/EAPMwcnzUhKLg=', 'Technician', 'Technician 3', 'tech3@example.com', '0123456800', '678 Tech St', NULL),
('inv_manager3', 'hMOqi32PeNalXipffGflzq/L9EY=', 'Inventory Manager', 'Inventory Manager 3', 'inv3@example.com', '0123456801', '901 Inventory St', NULL),
('cust_service3', '6glxG6yT7gkwR4oRHlU7X7akgY4=', 'Customer Service Agent', 'Customer Service 3', 'cs3@example.com', '0123456802', '543 Service Rd', NULL),
('repair_contractor3', '01g8MZ8t7IJL2iIGtzRMPTzJiTo=', 'Repair Contractor', 'Repair Contractor 3', 'repair3@example.com', '0123456803', '876 Repair St', NULL),
('admin04', 'VSoUE0cbBMY/79BqIk1v+kCdG1M=', 'Admin', 'Admin User 4', 'admin4@example.com', '0123456804', '456 Admin Blvd', NULL),
('tech04', 'DTxKhA7pCN1NwkGj1iPWadYUOz0=', 'Technician', 'Technician 4', 'tech4@example.com', '0123456805', '789 Tech Rd', NULL),
('inv_manager4', 'R+RhrqNOhedR/3foIWXfAA8DHnc=', 'Inventory Manager', 'Inventory Manager 4', 'inv4@example.com', '0123456806', '234 Inventory Blvd', NULL),
('cust_service4', 'nMGd0IHxlcneVgs1GoQ6SjRSB2A=', 'Customer Service Agent', 'Customer Service 4', 'cs4@example.com', '0123456807', '678 Service Ave', NULL),
('cust_service5', 'nMGd0IHxlcneVgs1GoQ6SjRSB2A=', NULL, 'Customer Service 5', 'cs5@example.com', '0123456847', '679 Service Ave', NULL),
('repair_contractor4', '8qZHe/AetEyne9jfDSciHoot6Y4=', 'Repair Contractor', 'Repair Contractor 4', 'repair4@example.com', '0123456808', '123 Repair Rd', NULL);


-- them data vao customer
INSERT INTO Customer (UsernameC, PasswordC, [Name], Gender, Email, Phone, [Address], Image)
VALUES
('cust01', 'T2D6i9+ldWGqevu6W6FAP1iXBbI=', 'Customer 1', 'Male', 'customer1@example.com', '0123456001', '123 Customer St',' img/avatars/avatar.jpg'),
('cust02', 'Cw2LaFmhUP2i/jGdPuB5aVCxAQg=', 'Customer 2', 'Female', 'customer2@example.com', '0123456002', '234 Customer Ave', ' img/avatars/avatar.jpg'),
('cust03', 'G1VKaeAjqcN762tlQAIFfla2Hnc=', 'Customer 3', 'Male', 'customer3@example.com', '0123456003', '345 Customer Blvd', ' img/avatars/avatar.jpg'),
('cust04', 'mnIoccUReEqm3CnIyVEsEqXI418=', 'Customer 4', 'Female', 'customer4@example.com', '0123456004', '456 Customer Rd', ' img/avatars/avatar.jpg'),
('cust05', 'cDW7EzjR1mP8REo1UF3mWlnuZdo=', 'Customer 5', 'Male', 'customer5@example.com', '0123456005', '567 Customer St', ' img/avatars/avatar.jpg'),
('cust06', 'O5eiLfa5pJNrHk7ck1tNBw6pUYE=', 'Customer 6', 'Female', 'customer6@example.com', '0123456006', '678 Customer Blvd', ' img/avatars/avatar.jpg'),
('cust07', 't6oNiVU8eL8t1C+e11mByn7n7oA=', 'Customer 7', 'Male', 'customer7@example.com', '0123456007', '789 Customer Ave', ' img/avatars/avatar.jpg'),
('cust08', '2L5q1/vlAepSi4mrWEoz+oJnTEY=', 'Customer 8', 'Female', 'customer8@example.com', '0123456008', '890 Customer Rd', ' img/avatars/avatar.jpg'),
('cust09', 'hNpyQH84BIbgDRM+z0nJgUqCeV8=', 'Customer 9', 'Male', 'customer9@example.com', '0123456009', '123 Customer Blvd', ' img/avatars/avatar.jpg'),
('cust10', 'NiH6AmjlqDCWROFjLhNDiYQ1F5U=', 'Customer 10', 'Female', 'customer10@example.com', '0123456010', '234 Customer St', ' img/avatars/avatar.jpg'),
('cust11', 'm1HKaKLljc/8wGtZ4IIGTTCCPdE=', 'Customer 11', 'Male', 'customer11@example.com', '0123456011', '345 Customer Ave', ' img/avatars/avatar.jpg'),
('cust12', 'z7f1n0HLfbf71/EAPMwcnzUhKLg=', 'Customer 12', 'Female', 'customer12@example.com', '0123456012', '456 Customer Rd',' img/avatars/avatar.jpg'),
('cust13', 'hMOqi32PeNalXipffGflzq/L9EY=', 'Customer 13', 'Male', 'customer13@example.com', '0123456013', '567 Customer Blvd', ' img/avatars/avatar.jpg'),
('cust14', '6glxG6yT7gkwR4oRHlU7X7akgY4=', 'Customer 14', 'Female', 'customer14@example.com', '0123456014', '678 Customer Ave', ' img/avatars/avatar.jpg'),
('cust15', '01g8MZ8t7IJL2iIGtzRMPTzJiTo=', 'Customer 15', 'Male', 'customer15@example.com', '0123456015', '789 Customer Rd', ' img/avatars/avatar.jpg'),
('cust16', 'VSoUE0cbBMY/79BqIk1v+kCdG1M=', 'Customer 16', 'Female', 'customer16@example.com', '0123456016', '890 Customer St',' img/avatars/avatar.jpg'),
('cust17', 'DTxKhA7pCN1NwkGj1iPWadYUOz0=', 'Customer 17', 'Male', 'customer17@example.com', '0123456017', '123 Customer Ave', ' img/avatars/avatar.jpg'),
('cust18', 'R+RhrqNOhedR/3foIWXfAA8DHnc=', 'Customer 18', 'Female', 'customer18@example.com', '0123456018', '234 Customer Blvd', 'img/avatars/avatar.jpg'),
('cust19', 'nMGd0IHxlcneVgs1GoQ6SjRSB2A=', 'Customer 19', 'Male', 'customer19@example.com', '0123456019', '345 Customer Rd', 'img/avatars/avatar.jpg'),
('cust20', '8qZHe/AetEyne9jfDSciHoot6Y4=', 'Customer 20', 'Female', 'customer20@example.com', '0123456020', '456 Customer St', ' img/avatars/avatar.jpg');

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





-- Thêm dữ liệu mới cho bảng Product
INSERT INTO Product (Code, ProductName,              BrandID, [Type],           Quantity, WarrantyPeriod, [Status], Image)
VALUES 
    ('P001',  'Samsung Galaxy S23',        9,  'Smartphone',      100,  24, 'Active', NULL),
    ('P002',  'Apple iPhone 14',           10, 'Smartphone',      150,  24, 'Active', NULL),
    ('P003',  'Sony WH-1000XM5',           11, 'Headphones',      200,  12, 'Active', NULL),
    ('P004',  'Dell XPS 13',               5,  'Laptop',          80,   36, 'Active', NULL),
    ('P005',  'Microsoft Surface Pro 9',   12, 'Tablet',          60,   36, 'Active', NULL),
    ('P006',  'Apple MacBook Air M2',      10, 'Laptop',          120,  24, 'Active', NULL),
    ('P007',  'Bose QuietComfort 45',      13, 'Headphones',      140,  12, 'Active', NULL),
    ('P008',  'HP Spectre x360',           5,  'Laptop',          90,   24, 'Active', NULL),
    ('P009',  'LG OLED C2',                14, 'TV',              50,   36, 'Active', NULL),
    ('P010',  'Sony PlayStation 5',        11, 'Gaming Console',  180,  12, 'Active', NULL),
    ('P011',  'Samsung QLED 8K TV',        9,  'TV',              70,   36, 'Active', NULL),
    ('P012',  'Apple iPad Pro 11"',        10, 'Tablet',          160,  24, 'Active', NULL),
    ('P013',  'Fitbit Charge 5',           15, 'Wearable',        200,  12, 'Active', NULL),
    ('P014',  'GoPro Hero 11',             8,  'Camera',          50,   24, 'Active', NULL),
    ('P015',  'Nintendo Switch OLED',      11, 'Gaming Console',  90,   12, 'Active', NULL),
    ('P016',  'Apple AirPods Pro 2',       10, 'Earbuds',         150,  24, 'Active', NULL),
    ('P017',  'OnePlus 10 Pro',            16, 'Smartphone',      100,  18, 'Active', NULL),
    ('P018',  'Microsoft Xbox Series X',   12, 'Gaming Console',  120,  12, 'Active', NULL),
    ('P019',  'Huawei Mate 50 Pro',        15, 'Smartphone',      60,   36, 'Active', NULL),
    ('P020',  'Google Pixel 7',            16, 'Smartphone',      80,   24, 'Active', NULL),
    ('P021',  'Xiaomi Mi 11',              16, 'Smartphone',      90,   24, 'Active', NULL),
    ('P022',  'Oculus Quest 2',            12, 'VR Headset',      70,   12, 'Active', NULL),
    ('P023',  'Razer Blade 15',            1,  'Laptop',          50,   24, 'Active', NULL),
    ('P024',  'Dell Alienware m15',        5,  'Laptop',          40,   36, 'Active', NULL);










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


INSERT INTO ProductDetail (CustomerID, ProductID, ProductCode, PurchaseDate)
VALUES
(1, 1, 'P103453', '2025-01-01'),
(2, 2, 'P1345345', '2025-01-02'),
(3, 3, 'P1434343', '2025-01-03'),
(4, 1, 'P13435', '2025-01-04'),
(5, 4, 'P134535', '2025-01-05')

INSERT INTO WarrantyCard (WarrantyCardCode, ProductDetailID, IssueDescription, WarrantyStatus, CreatedDate)
VALUES
('WC101', 1, 'Screen malfunction', 'fixing', '2025-01-10'),
('WC102', 2, 'Battery issue', 'completed', '2025-01-11'),
('WC103', 3, 'Overheating problem', 'fixing', '2025-01-12'),
('WC104', 4, 'Keyboard not working', 'cancel', '2025-01-13'),
('WC105', 5, 'Touchpad unresponsive', 'completed', '2025-01-14'),
('WC106', 6, 'Audio not working', 'fixing', '2025-01-15'),
('WC107', 7, 'Charging port damaged', 'completed', '2025-01-16'),
('WC108', 8, 'Camera malfunction', 'fixing', '2025-01-17'),
('WC109', 9, 'Bluetooth not connecting', 'completed', '2025-01-18'),
('WC110', 10, 'Wi-Fi connectivity issue', 'cancel', '2025-01-19'),
('WC111', 11, 'Screen flickering', 'fixing', '2025-01-20'),
('WC112', 12, 'Battery draining quickly', 'completed', '2025-01-21'),
('WC113', 13, 'Speaker distortion', 'fixing', '2025-01-22'),
('WC114', 14, 'Power button not working', 'completed', '2025-01-23'),
('WC115', 15, 'Overheating during charging', 'fixing', '2025-01-24'),
('WC116', 16, 'Random shutdowns', 'cancel', '2025-01-25'),
('WC117', 17, 'Hard drive failure', 'completed', '2025-01-26'),
('WC118', 18, 'USB port not functioning', 'fixing', '2025-01-27'),
('WC119', 19, 'Software crashes frequently', 'completed', '2025-01-28'),
('WC120', 20, 'Screen brightness not adjustable', 'fixing', '2025-01-29');

INSERT INTO Feedback (CustomerID, WarrantyCardID, Note, DateCreated, IsDeleted, ImageURL, VideoURL)
VALUES
(1, 2, 'The repair process was quick and efficient.', '2025-02-14', 0, 'https://via.placeholder.com/300', 'https://samplelib.com/lib/preview/mp4/sample-5s.mp4'),
(2, 3, 'The staff was very helpful and resolved my issue completely.', '2025-02-14', 0, 'https://via.placeholder.com/300', NULL),
(3, 4, 'I was satisfied with the repair, but it took a bit longer than expected.', '2025-03-14', 0, NULL, 'https://samplelib.com/lib/preview/mp4/sample-5s.mp4'),
(4, 5, 'The issue was resolved, but I had to visit the service center twice.', '2025-03-24', 0, 'https://via.placeholder.com/300', NULL),
(5, 1, 'The repair was excellent, and the product works like new.', '2025-03-04', 0, NULL, NULL),
(6, 2, 'The staff explained everything clearly and provided great service.', '2025-03-11', 0, 'https://via.placeholder.com/300', 'https://samplelib.com/lib/preview/mp4/sample-5s.mp4'),
(7, 3, 'The process was smooth, but I had to wait for parts to arrive.', '2025-03-02', 0, NULL, NULL),
(8, 4, 'I appreciate the service, but I feel the warranty coverage could be better.','2025-03-31', 0, 'https://via.placeholder.com/300', NULL),
(9, 5, 'I had a great experience with the service team.', '2025-03-25', 0, NULL, 'https://samplelib.com/lib/preview/mp4/sample-5s.mp4'),
(1, null, 'The issue was fixed promptly, and I was kept informed throughout.', '2025-03-01', 0, 'https://via.placeholder.com/300', NULL),
(1, 1, 'The service was excellent, and the staff was very professional.', '2025-03-15', 0, NULL, NULL),
(1, 2, 'I had to wait longer than expected, but the repair quality was good.', '2025-03-20', 0, NULL, NULL),
(1, 3, 'The staff went above and beyond to assist me.', '2025-03-22', 0, NULL, NULL),
(14, 4, 'I received clear communication throughout the process.', '2025-03-10', 0, NULL, NULL),
(5, 5, 'The product repair was satisfactory, but follow-up could improve.', '2025-03-08', 0, NULL, NULL),
(16, null, 'The repair team was friendly and answered all my questions.', '2025-03-12', 0, NULL, NULL),
(7, 1, 'The replacement part was not in stock, causing a delay.', '2025-03-18', 0, NULL, NULL),
(4, 2, 'I am impressed with how quickly the service was completed.', '2025-03-05', 0, NULL, NULL),
(19, null, 'The service center was clean and well-organized.', '2025-03-14', 0, NULL, NULL),
(2, 4, 'The technician was skilled and resolved the issue efficiently.', '2025-03-28', 0, NULL, NULL);

