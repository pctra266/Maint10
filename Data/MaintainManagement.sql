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
--('Admin', 'Technician', 'Inventory Manager', 'Customer', 'Repair Contractor', 'Customer Service Agent', NULL)
CREATE TABLE [Role] (
    RoleID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    RoleName NVARCHAR(50) UNIQUE NOT NULL
);

-- Staff Table
CREATE TABLE Staff (
    StaffID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    UsernameS NVARCHAR(50) UNIQUE,
    PasswordS NVARCHAR(50),
    [Name] NVARCHAR(100),
    RoleID INT REFERENCES [Role](RoleID), 
    Gender NVARCHAR(10) CHECK (Gender IN ('Male', 'Female', 'Other')),
    DateOfBirth DATE,
    Email NVARCHAR(100),
    Phone NVARCHAR(20),
    [Address] NVARCHAR(255),
    Image NVARCHAR(MAX) 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY];

-- StaffLog Table
CREATE TABLE StaffLog (
    StaffLogID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    StaffID INT REFERENCES Staff(StaffID) ON DELETE SET NULL,
    UsernameS NVARCHAR(50),
    PasswordS NVARCHAR(50),
    [Name] NVARCHAR(100),
    Gender NVARCHAR(10) CHECK (Gender IN ('Male', 'Female', 'Other')),
    DateOfBirth DATE,
    Email NVARCHAR(100),
    Phone NVARCHAR(20),
    [Address] NVARCHAR(255),
    Image NVARCHAR(MAX),
    [Time] DATETIME,
    [Status] NVARCHAR(100)
);

-- Customer Table
CREATE TABLE Customer (
    CustomerID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    UsernameC NVARCHAR(50) UNIQUE,
    PasswordC NVARCHAR(50),
    [Name] NVARCHAR(100),
    Gender NVARCHAR(10) CHECK (Gender IN ('Male', 'Female', 'Other')),
    DateOfBirth DATE,
    Email NVARCHAR(100),
    Phone NVARCHAR(20),
    [Address] NVARCHAR(255),
    Image NVARCHAR(MAX) 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY];

-- Unique index for UsernameC (ignoring NULL values)
CREATE UNIQUE INDEX UX_UsernameC ON Customer(UsernameC)
WHERE UsernameC IS NOT NULL;


CREATE TABLE [Permissions] (
    PermissionID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    PermissionName NVARCHAR(100) UNIQUE NOT NULL,
    Description NVARCHAR(255)
);

CREATE TABLE Role_Permissions (
    RoleID INT NOT NULL REFERENCES Role(RoleID) ON DELETE CASCADE,
    PermissionID INT NOT NULL REFERENCES Permissions(PermissionID) ON DELETE CASCADE,
    PRIMARY KEY (RoleID, PermissionID)
);



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


CREATE TABLE ProductType (
    ProductTypeID INT IDENTITY(1,1) PRIMARY KEY,
    TypeName NVARCHAR(100) NOT NULL UNIQUE
);

-- Product Table (Updated)
CREATE TABLE Product (
    ProductID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    Code NVARCHAR(20) NULL,
    ProductName NVARCHAR(100), 
    BrandID INT FOREIGN KEY REFERENCES Brand(BrandID),
    ProductTypeID INT NOT NULL REFERENCES ProductType(ProductTypeID),
    Quantity INT,
    WarrantyPeriod INT NOT NULL,
    [Status] NVARCHAR(100),
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

-- Bảng UnknowProduct: Sản phẩm không rõ nguồn gốc
CREATE TABLE UnknownProduct (
    UnknownProductID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    CustomerID INT NOT NULL REFERENCES Customer(CustomerID), -- Liên kết với khách hàng
    ProductName NVARCHAR(50),
	ProductCode NVARCHAR(20), --Auto generate
    Description NVARCHAR(MAX),
    ReceivedDate DATETIME
);

-- Bảng WarrantyProduct: Liên kết sản phẩm từ ProductDetail hoặc UnknowProduct
CREATE TABLE WarrantyProduct (
    WarrantyProductID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    ProductDetailID INT NULL REFERENCES ProductDetail(ProductDetailID),
    UnknownProductID INT NULL REFERENCES UnknownProduct(UnknownProductID),
    CHECK (
        (ProductDetailID IS NOT NULL AND UnknownProductID IS NULL) OR 
        (ProductDetailID IS NULL AND UnknownProductID IS NOT NULL)
    )
);

-- WarrantyCard Table
CREATE TABLE WarrantyCard (
    WarrantyCardID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	HandlerID int references Staff(StaffID),
    WarrantyCardCode NVARCHAR(10) NOT NULL UNIQUE,
    WarrantyProductID INT NOT NULL REFERENCES WarrantyProduct(WarrantyProductID),
    IssueDescription NVARCHAR(MAX),
    WarrantyStatus NVARCHAR(50) NOT NULL CHECK (WarrantyStatus IN ('waiting','fixing','refix', 'done', 'completed', 'cancel')),
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
    Quantity INT NOT NULL CHECK (Quantity >= 0),
	[Status] NVARCHAR(20) NOT NULL CHECK ([Status] IN ('waiting', 'approved', 'cancel'))
);

-- ComponentRequestResponsible Table
CREATE TABLE ComponentRequestResponsible (
    ComponentRequestResponsibleID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    StaffID INT NOT NULL REFERENCES Staff(StaffID),
    ComponentRequestID INT NOT NULL REFERENCES ComponentRequest(ComponentRequestID),
    [Action] NVARCHAR(10) NOT NULL CHECK ([Action] IN ('request', 'approved', 'cancel')),
	CreateDate DATETIME DEFAULT GETDATE(),
);

-- WarrantyCardDetail Table
CREATE TABLE WarrantyCardDetail (
    WarrantyCardDetailID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    WarrantyCardID INT NOT NULL REFERENCES WarrantyCard(WarrantyCardID),
    ComponentID INT NOT NULL REFERENCES Component(ComponentID),
    Status NVARCHAR(20) NOT NULL CHECK (Status IN ('warranty_repaired','warranty_replaced', 'repaired', 'replace','fixing')),
    Price FLOAT NOT NULL CHECK (Price >= 0),
    Quantity INT NOT NULL CHECK (Quantity >= 0)
);

-- WarrantyCardProcess Table
CREATE TABLE WarrantyCardProcess (
    WarrantyCardProcessID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    WarrantyCardID INT NOT NULL REFERENCES WarrantyCard(WarrantyCardID),
    HandlerID INT NOT NULL REFERENCES Staff(StaffID),
    [Action] NVARCHAR(20) NOT NULL CHECK ([Action] IN ('create','receive', 'fixing','refix','wait_components', 'received_components', 'outsource', 'fixed', 'completed','refuse', 'cancel')),
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

GO
CREATE TRIGGER trg_AfterInsert_UnknownProduct
ON UnknownProduct
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    INSERT INTO WarrantyProduct (UnknownProductID)
    SELECT UnknownProductID
    FROM inserted;
END
GO

