USE master
GO

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'MaintainManagement')
BEGIN
ALTER DATABASE MaintainManagement SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
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
	RoleID INT REFERENCES [Role](RoleID)
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

CREATE TABLE ChatMessages (
    MessageID INT IDENTITY(1,1) PRIMARY KEY,
    SenderID INT NOT NULL,  -- ID của người gửi (CustomerID hoặc StaffID)
    SenderType NVARCHAR(10) , -- Loại người gửi
    ReceiverID INT NOT NULL,  -- ID của người nhận (CustomerID hoặc StaffID)
    ReceiverType NVARCHAR(10), -- Loại người nhận
    MessageText NVARCHAR(MAX) NOT NULL,
    Timestamp DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_ChatMessages_Sender_Customer FOREIGN KEY (SenderID) REFERENCES Customer(CustomerID),
    CONSTRAINT FK_ChatMessages_Sender_Staff FOREIGN KEY (SenderID) REFERENCES Staff(StaffID),
    CONSTRAINT FK_ChatMessages_Receiver_Customer FOREIGN KEY (ReceiverID) REFERENCES Customer(CustomerID),
    CONSTRAINT FK_ChatMessages_Receiver_Staff FOREIGN KEY (ReceiverID) REFERENCES Staff(StaffID)
);

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
) 
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
) 


-- ProductComponents Table
CREATE TABLE ProductComponents (
    ProductComponentsID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    ProductID INT NOT NULL REFERENCES Product(ProductID),
    ComponentID INT NOT NULL REFERENCES Component(ComponentID),
    Quantity INT NOT NULL CHECK (Quantity >= 1) --Component in a product if equal to 0, it should not be in this table
);

CREATE TABLE ExtendedWarranty (
    ExtendedWarrantyID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	ExtendedWarrantyName NVARCHAR(100) NOT NULL, 
    ExtendedPeriodInMonths INT NOT NULL,       
    Price DECIMAL(18,2) NOT NULL,              
    ExtendedWarrantyDescription NVARCHAR(500) NULL,
	IsDelete BIT DEFAULT 0 NOT NULL,
);
--  PackageWarranty table
CREATE TABLE PackageWarranty (
    PackageWarrantyID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    WarrantyStartDate DATETIME NOT NULL,          
    WarrantyEndDate DATETIME NOT NULL,
	DurationMonths INT NOT NULL DEFAULT 12,
	Price DECIMAL(18,2) NOT NULL,
    Note NVARCHAR(500) NULL, 
	IsActive BIT DEFAULT 1 NOT NULL,
);

CREATE TABLE ExtendedWarrantyDetail(
	ExtendedWarrantyDetailID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	ExtendedWarrantyID INT NOT NULL REFERENCES ExtendedWarranty(ExtendedWarrantyID),
	PackageWarrantyID INT NOT NULL REFERENCES PackageWarranty(PackageWarrantyID),
	StartExtendedWarranty DATETIME NOT NULL,
	EndExtendedWarranty DATETIME NOT NULL,
)


-- ProductDetail Table
CREATE TABLE ProductDetail (
    ProductDetailID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    CustomerID INT NOT NULL REFERENCES Customer(CustomerID),
    ProductID INT NOT NULL REFERENCES Product(ProductID),
    ProductCode NVARCHAR(50) NOT NULL UNIQUE,
    PurchaseDate DATETIME NOT NULL,
	PackageWarrantyID INT NOT NULL UNIQUE REFERENCES PackageWarranty(PackageWarrantyID)
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
);

CREATE TABLE ContractorCard (
    ContractorCardID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	WarrantyCardID int references WarrantyCard(WarrantyCardID),
	StaffID int references Staff(StaffID),
	ContractorID int references Staff(StaffID),
	[Date] DATETIME DEFAULT GETDATE(),
	--waiting tuong ung voi request-outsource, receive ~ accept_outsource, 
	--cancel ~ reject_outsource/refuse_outsource/unfixable_outsouce( khong sua duoc)
	[Status] NVARCHAR(20) CHECK (Status in ('waiting', 'receive', 'cancel', 'done')),
	Note NVARCHAR(MAX)
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
	CreateDate DATETIME DEFAULT GETDATE()
);
-- MissingComponentRequest Table
CREATE TABLE MissingComponentRequest (
    MissingComponentRequestID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    ComponentName NVARCHAR(100) NOT NULL,
	ComponentType NVARCHAR(20) NOT NULL CHECK (ComponentType IN ('product', 'unknown product')), -- Phân loại sản phẩm
    TypeID INT NULL REFERENCES ComponentType(TypeID), -- Loại linh kiện (nếu biết)
    BrandID INT NULL REFERENCES Brand(BrandID), -- Thương hiệu (nếu biết)
    RequestedBy INT NOT NULL REFERENCES Staff(StaffID), -- Người yêu cầu
    RequestDate DATETIME DEFAULT GETDATE(), -- Ngày yêu cầu
    Status NVARCHAR(20) NOT NULL CHECK (Status IN ('waiting', 'approved', 'cancel')),
    Note NVARCHAR(MAX)
);


-- WarrantyCardDetail Table
CREATE TABLE WarrantyCardDetail (
    WarrantyCardDetailID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    WarrantyCardID INT NOT NULL REFERENCES WarrantyCard(WarrantyCardID),
    ComponentID INT NULL REFERENCES Component(ComponentID),
	ComponentName NVARCHAR(100),
    Status NVARCHAR(20) NOT NULL CHECK (Status IN ('warranty_repaired','warranty_replaced', 'repaired', 'replace','fixing')),
    Price FLOAT NOT NULL CHECK (Price >= 0),
    Quantity INT NOT NULL CHECK (Quantity >= 0),
    Note NVARCHAR(MAX)
);

-- WarrantyCardProcess Table
CREATE TABLE WarrantyCardProcess (	
    WarrantyCardProcessID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    WarrantyCardID INT NOT NULL REFERENCES WarrantyCard(WarrantyCardID),
    HandlerID INT NOT NULL REFERENCES Staff(StaffID),
    [Action] NVARCHAR(20) NOT NULL CHECK ([Action] IN ('create','receive', 'refuse', 'fixing','refix','wait_components', 'received_components',
	'request_outsource', 'cancel_outsource', 'accept_outsource', 'refuse_outsource' , 'send_outsource','lost', 'receive_outsource', 'fixed_outsource', 
	'unfixable_outsource', 'back_outsource', 'receive_from_outsource' ,'fixed', 'completed', 'cancel')),
    ActionDate DATETIME DEFAULT GETDATE(),
    Note NVARCHAR(MAX)
);

-- Feedback Table
CREATE TABLE Feedback (
    FeedbackID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    CustomerID INT NOT NULL REFERENCES Customer(CustomerID),
    WarrantyCardID INT REFERENCES WarrantyCard(WarrantyCardID),
    Note NVARCHAR(MAX),
	DateCreated DATETIME NOT NULL,
	IsDeleted BIT DEFAULT 0 NOT NULL,
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

CREATE TABLE Media (
    MediaID INT IDENTITY(1,1) PRIMARY KEY,
    ObjectID INT NOT NULL, -- Liên kết trực tiếp đến ID của bảng liên quan
    ObjectType NVARCHAR(50) NOT NULL CHECK (ObjectType IN ('Component', 'Product', 'WarrantyCard', 'Feedback','Cover','OurService')),
    MediaURL NVARCHAR(MAX) NOT NULL, -- URL ảnh hoặc video
    MediaType NVARCHAR(10) NOT NULL CHECK (MediaType IN ('image', 'video')), -- Phân loại
    UploadedDate DATETIME DEFAULT GETDATE()
);

CREATE TABLE Invoice (
    InvoiceID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    InvoiceNumber NVARCHAR(20) NOT NULL UNIQUE,
    InvoiceType NVARCHAR(50) NOT NULL 
        CHECK (InvoiceType IN ('RepairContractorToTechnician', 'TechnicianToCustomer')),
    WarrantyCardID INT NOT NULL REFERENCES WarrantyCard(WarrantyCardID),
    Amount FLOAT NOT NULL CHECK (Amount >= 0),
    IssuedDate DATETIME DEFAULT GETDATE(),
    DueDate DATETIME,  -- Ngày đến hạn thanh toán
    Status NVARCHAR(20) NOT NULL 
        CHECK (Status IN ('pending', 'paid', 'overdue')),  -- pending: chờ thanh toán; paid: đã thanh toán; overdue: quá hạn
    CreatedBy INT NOT NULL REFERENCES Staff(StaffID),  -- Người tạo hóa đơn (Repair Contractor hoặc Technician)
    ReceivedBy INT NULL REFERENCES Staff(StaffID),     -- Người nhận hóa đơn (đối với hóa đơn RepairContractorToTechnician là Technician)
    CustomerID INT NULL REFERENCES Customer(CustomerID)  -- Áp dụng cho hóa đơn TechnicianToCustomer
);


-- Payment Table
CREATE TABLE Payment (
    PaymentID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    PaymentDate DATE NOT NULL,
    PaymentMethod NVARCHAR(20) NOT NULL CHECK (PaymentMethod IN ('cash', 'bank_transfer')),
    Amount FLOAT NOT NULL CHECK (Amount >= 0),
    Status NVARCHAR(20) NOT NULL CHECK (Status IN ('pending', 'complete', 'fail')),
	InvoiceID INT NULL REFERENCES Invoice(InvoiceID)
);



CREATE TABLE FooterSetting (
    id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    slogan VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    hotline VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    copyrightYear VARCHAR(255) NOT NULL,
    lastUpdated  DATETIME DEFAULT GETDATE()
);

CREATE TABLE ContactText (
    id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    subtitle TEXT NOT NULL,
    lastUpdated DATETIME DEFAULT GETDATE()
);

CREATE TABLE CustomerContact (
    ContactID INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(100)  NULL,
    Email NVARCHAR(100) NULL,
    Phone NVARCHAR(20) NOT NULL,
    Message NVARCHAR(MAX) NULL,
    CreatedAt DATETIME DEFAULT GETDATE()
);


CREATE TABLE MarketingServiceSection (
    SectionID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    Title NVARCHAR(255),
    SubTitle NVARCHAR(255),
    CreatedDate DATETIME DEFAULT GETDATE(),
    UpdatedDate DATETIME DEFAULT GETDATE()
);

CREATE TABLE MarketingServiceItem (
    ServiceID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    SectionID INT NOT NULL,
    Title NVARCHAR(255) NOT NULL,
    Description NVARCHAR(MAX),
    ImageURL NVARCHAR(MAX),
    SortOrder INT DEFAULT 1,
    CreatedDate DATETIME DEFAULT GETDATE(),
    UpdatedDate DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_MarketingServiceItem_SectionID
        FOREIGN KEY (SectionID) REFERENCES MarketingServiceSection(SectionID)
);
CREATE TABLE StaffBlogPosts (
    BlogPostID INT IDENTITY(1,1) PRIMARY KEY,
    StaffID INT NOT NULL,
    Title NVARCHAR(255) NOT NULL,
    Content NVARCHAR(MAX) NOT NULL,
    CreatedDate DATETIME DEFAULT GETDATE(),
    UpdatedDate DATETIME NULL,
    FOREIGN KEY (StaffID) REFERENCES Staff(StaffID)
);


CREATE TABLE Notifications (
    NotificationID INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    RecipientType NVARCHAR(20) NOT NULL 
        CHECK (RecipientType IN ('Customer', 'Staff')), -- Loại người nhận
    RecipientID INT NOT NULL, -- ID của người nhận (CustomerID hoặc StaffID của Inventory Manager)
    Message NVARCHAR(255) NOT NULL,
    CreatedDate DATETIME DEFAULT GETDATE(),
    IsRead BIT DEFAULT 0, -- 0: chưa đọc, 1: đã đọc
    Target Nvarchar(255) --- Luu noi ma thong bao muon chuyen den
);

-- Index để tối ưu truy vấn
CREATE INDEX IDX_Notifications_Recipient ON Notifications (RecipientType, RecipientID, IsRead);

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
    SELECT UnknownProductID FROM inserted WHERE UnknownProductID IS NOT NULL;
END
GO




