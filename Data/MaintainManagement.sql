--create database MaintainManagement

CREATE TABLE Account (
    UserName NVARCHAR(50) PRIMARY KEY,
    [Password] NVARCHAR(6)
);

INSERT INTO Account (UserName, [Password]) 
VALUES 
('pctra', '123456'),
('user1', 'abcdef'),
('user2', '654321');