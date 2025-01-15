USE [master]
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
Create database MaintainManagement
GO
USE MaintainManagement
GO

CREATE TABLE Staff (
    StaffID int IDENTITY(1,1) not null primary key,
	UsernameS NVARCHAR(50),
	PasswordS NVARCHAR(50),
    [Role] NVARCHAR(20),
    [Name] NVARCHAR(100),
    Email NVARCHAR(100),
    Phone NVARCHAR(20),
    [Address] NVARCHAR(255)

);

CREATE TABLE StaffLog (
    StaffLogID int IDENTITY(1,1) not null primary key,
	StaffID int references Staff(StaffID),
    StartDate datetime,
	EndDate datetime,
	[Role] NVARCHAR(20)
);

CREATE TABLE Customer (
    CustomerID int IDENTITY(1,1) not null primary key,
	UsernameC NVARCHAR(50),
	PasswordC NVARCHAR(50),
    [Name] NVARCHAR(100),
    Email NVARCHAR(100),
    Phone NVARCHAR(20),
    [Address] NVARCHAR(255)
);

CREATE TABLE ComponentRequestResponsible (
    ComponentRequestResponsibleID int IDENTITY(1,1) not null primary key,
	StaffID int references Staff(StaffID),
	[Action] NVARCHAR(10)
);

CREATE TABLE ComponentRequest (
    ComponentRequestID int IDENTITY(1,1) not null primary key,
	[Date] DateTime DEFAULT GETDATE(),
	Status NVARCHAR(20),
	Note nvarchar(max)
);

CREATE TABLE Component (
    ComponentID int IDENTITY(1,1) not null primary key,
	ComponentName nvarchar(20),
	Quantity int,
	Price float
);

CREATE TABLE ComponentRequestDetail (
    ComponentRequestDetailID int IDENTITY(1,1) not null primary key,
	ComponentID int references Component(ComponentID),
	ComponentRequestID int references ComponentRequest(ComponentRequestID),
	Quantity int
);
CREATE TABLE Product (
    ProductID int IDENTITY(1,1) not null primary key,
	ProductName NVARCHAR(100) NOT NULL, 
	Quantity int,
	WarrantyDate DATETIME NOT NULL
);

CREATE TABLE ProductComponents (
    ProductComponentsID int IDENTITY(1,1) not null primary key,
	ProductID int references Product(ProductID),
	ComponentID int references Component(ComponentID),
	Quantity int
);

CREATE TABLE ProductDetail (
    CODE int IDENTITY(1,1) not null primary key,
	CustomerID int references Customer(CustomerID),
	ProductID int references Product(ProductID),
	ProductCode  NVARCHAR(50) NOT NULL UNIQUE,
	PurchaseDate DATETIME NOT NULL, 
);

CREATE TABLE WarrantyCard (
    WarrantyCardCode NVARCHAR(10) NOT NULL primary key,
	CODE int references ProductDetail(CODE),
	IssueDescription NVARCHAR(MAX), 
	WarrantyStatus NVARCHAR(50) NOT NULL,
	CreatedDate DATETIME DEFAULT GETDATE(), 
);

CREATE TABLE WarrantyCardDetail (
    WarrantyCardDetailID int IDENTITY(1,1) not null primary key,
	WarrantyCardCode NVARCHAR(10) references WarrantyCard(WarrantyCardCode),
	ProductComponentsID int references ProductComponents(ProductComponentsID),
	Status NVARCHAR(20),
	Quantity int
);

CREATE TABLE WarrantyCardProcess (
    WarrantyCardProcessID int IDENTITY(1,1) not null primary key,
	WarrantyCardCode NVARCHAR(10) references WarrantyCard(WarrantyCardCode),
	HandlerID int references Staff(StaffID),
	[Action] NVARCHAR(20),
	ActionDate DATETIME DEFAULT GETDATE(),
	Note nvarchar(max)
);

CREATE TABLE Payment (
    PaymentID int IDENTITY(1,1) not null primary key,
	WarrantyCardCode NVARCHAR(10) references WarrantyCard(WarrantyCardCode),
	PaymentDate DATE,
	PaymentMethod NVARCHAR(20),
	Amount FLOAT,
	Status NVARCHAR(20)
);

