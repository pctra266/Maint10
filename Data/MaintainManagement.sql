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

CREATE TABLE Account (
    UserName NVARCHAR(50) PRIMARY KEY,
    [Password] NVARCHAR(50)
);

INSERT INTO Account (UserName, [Password]) 
VALUES 
('pctra266', 'trapham'),
('user1', '123'),
('user2', '456');