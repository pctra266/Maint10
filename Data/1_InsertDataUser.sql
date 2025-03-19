USE MaintainManagement

INSERT INTO [Role] (RoleName)
VALUES 
('Admin'),
('Technician'),
('Inventory Manager'),
('Repair Contractor'),
('Customer Service Agent'),
('Inactive');

INSERT INTO Staff (UsernameS, PasswordS, RoleID, [Name], Gender, DateOfBirth, Email, Phone, [Address], Image)
VALUES
('admin01', 'T2D6i9+ldWGqevu6W6FAP1iXBbI=', 1, 'Robert Johnson', 'Male', '1985-03-15', 'admin1@example.com', '0123456789', '123 Admin Street', 'img/Staff/avata.jpg'),
('tech01', 'Cw2LaFmhUP2i/jGdPuB5aVCxAQg=', 2, 'Mark Thompson', 'Male', '1990-06-20', 'tech1@example.com', '0123456790', '456 Tech Street', 'img/Staff/avata.jpg'),
('inv_manager1', 'G1VKaeAjqcN762tlQAIFfla2Hnc=', 3, 'Jessica Brown', 'Female', '1988-11-10', 'phamtra001@gmail.com', '0123456791', '789 Inventory Ave', 'img/Staff/avata.jpg'),
('cust_service1', 'mnIoccUReEqm3CnIyVEsEqXI418=', 5, 'Laura Clark', 'Female', '1992-08-25', 'cs1@example.com', '0123456792', '321 Service Blvd', 'img/Staff/avata.jpg'),
('repair_contractor1', 'cDW7EzjR1mP8REo1UF3mWlnuZdo=', 4, 'Daniel Scott', 'Male', '1987-05-18', 'repair1@example.com', '0123456793', '654 Repair St', 'img/Staff/avata.jpg'),
('admin02', 'O5eiLfa5pJNrHk7ck1tNBw6pUYE=', 1, 'Emily Carter', 'Female', '1989-12-02', 'admin2@example.com', '0123456794', '234 Admin Street', 'img/Staff/avata.jpg'),
('tech02', 't6oNiVU8eL8t1C+e11mByn7n7oA=', 2, 'Steven White', 'Male', '1991-07-14', 'tech2@example.com', '0123456795', '567 Tech St', 'img/Staff/avata.jpg'),
('inv_manager2', '2L5q1/vlAepSi4mrWEoz+oJnTEY=', 3, 'Ashley Davis', 'Female', '1993-04-22', 'inv2@example.com', '0123456796', '890 Inventory Ave', 'img/Staff/avata.jpg'),
('cust_service2', 'hNpyQH84BIbgDRM+z0nJgUqCeV8=', 5, 'Alex Morgan', 'Other', '1995-10-30', 'cs2@example.com', '0123456797', '432 Service Blvd', 'img/Staff/avata.jpg'),
('repair_contractor2', 'NiH6AmjlqDCWROFjLhNDiYQ1F5U=', 4, 'Anthony Lewis', 'Male', '1986-09-05', 'repair2@example.com', '0123456798', '765 Repair St', 'img/Staff/avata.jpg'),
('admin03', 'm1HKaKLljc/8wGtZ4IIGTTCCPdE=', 1, 'David Wilson', 'Male', '1984-02-17', 'admin3@example.com', '0123456799', '345 Admin Ave', 'img/Staff/avata.jpg'),
('tech03', 'z7f1n0HLfbf71/EAPMwcnzUhKLg=', 2, 'Brian Harris', 'Male', '1990-08-09', 'tech3@example.com', '0123456800', '678 Tech St', 'img/Staff/avata.jpg'),
('inv_manager3', 'hMOqi32PeNalXipffGflzq/L9EY=', 3, 'Amanda Garcia', 'Female', '1987-03-25', 'inv3@example.com', '0123456801', '901 Inventory St', 'img/Staff/avata.jpg'),
('cust_service3', '6glxG6yT7gkwR4oRHlU7X7akgY4=', 5, 'Megan Allen', 'Female', '1994-05-19', 'cs3@example.com', '0123456802', '543 Service Rd', 'img/Staff/avata.jpg'),
('repair_contractor3', '01g8MZ8t7IJL2iIGtzRMPTzJiTo=', 4, 'Joseph Walker', 'Male', '1985-12-29', 'repair3@example.com', '0123456803', '876 Repair St', 'img/Staff/avata.jpg'),
('admin04', 'VSoUE0cbBMY/79BqIk1v+kCdG1M=', 1, 'Sophia Miller', 'Female', '1986-06-06', 'admin4@example.com', '0123456804', '456 Admin Blvd', 'img/Staff/avata.jpg'),
('tech04', 'DTxKhA7pCN1NwkGj1iPWadYUOz0=', 2, 'Kevin Martin', 'Male', '1992-01-11', 'tech4@example.com', '0123456805', '789 Tech Rd', 'img/Staff/avata.jpg'),
('inv_manager4', 'R+RhrqNOhedR/3foIWXfAA8DHnc=', 3, 'Taylor Lee', 'Other', '1988-04-04', 'inv4@example.com', '0123456806', '234 Inventory Blvd', 'img/Staff/avata.jpg'),
('cust_service4', 'nMGd0IHxlcneVgs1GoQ6SjRSB2A=', 5, 'Rachel Adams', 'Female', '1993-07-07', 'cs4@example.com', '0123456807', '678 Service Ave', 'img/Staff/avata.jpg'),
('repair_contractor4', '8qZHe/AetEyne9jfDSciHoot6Y4=', 4, 'Christopher Young', 'Male', '1989-09-21', 'repair4@example.com', '0123456808', '123 Repair Rd', 'img/Staff/avata.jpg');





-- Chèn thêm dữ liệu vào Customer với DateOfBirth
INSERT INTO Customer (UsernameC, PasswordC, [Name], Gender, DateOfBirth, Email, Phone, [Address], Image)
VALUES
('cust01', 'T2D6i9+ldWGqevu6W6FAP1iXBbI=', 'John Smith', 'Male', '1990-01-15', 'customer1@example.com', '0123456001', '123 Customer St', 'img/avatar/avatar.jpg'),
('cust02', 'Cw2LaFmhUP2i/jGdPuB5aVCxAQg=', 'Emily Johnson', 'Female', '1992-03-22', 'customer2@example.com', '0123456002', '234 Customer Ave', 'img/avatar/avatar.jpg'),
('cust03', 'G1VKaeAjqcN762tlQAIFfla2Hnc=', 'Michael Brown', 'Male', '1988-07-10', 'customer3@example.com', '0123456003', '345 Customer Blvd', 'img/avatar/avatar.jpg'),
('cust04', 'mnIoccUReEqm3CnIyVEsEqXI418=', 'Sarah Davis', 'Female', '1995-05-30', 'customer4@example.com', '0123456004', '456 Customer Rd', 'img/avatar/avatar.jpg'),
('cust05', 'cDW7EzjR1mP8REo1UF3mWlnuZdo=', 'William Wilson', 'Male', '1987-09-18', 'customer5@example.com', '0123456005', '567 Customer St', 'img/avatar/avatar.jpg'),
('cust06', 'O5eiLfa5pJNrHk7ck1tNBw6pUYE=', 'Olivia Taylor', 'Female', '1993-12-25', 'customer6@example.com', '0123456006', '678 Customer Blvd', 'img/avatar/avatar.jpg'),
('cust07', 't6oNiVU8eL8t1C+e11mByn7n7oA=', 'James Anderson', 'Male', '1989-06-14', 'customer7@example.com', '0123456007', '789 Customer Ave', 'img/avatar/avatar.jpg'),
('cust08', '2L5q1/vlAepSi4mrWEoz+oJnTEY=', 'Sophia Thomas', 'Female', '1997-04-05', 'customer8@example.com', '0123456008', '890 Customer Rd', 'img/avatar/avatar.jpg'),
('cust09', 'hNpyQH84BIbgDRM+z0nJgUqCeV8=', 'Benjamin Moore', 'Male', '1986-08-27', 'customer9@example.com', '0123456009', '123 Customer Blvd', 'img/avatar/avatar.jpg'),
('cust10', 'NiH6AmjlqDCWROFjLhNDiYQ1F5U=', 'Isabella Martin', 'Female', '1991-02-16', 'customer10@example.com', '0123456010', '234 Customer St', 'img/avatar/avatar.jpg'),
('cust11', 'm1HKaKLljc/8wGtZ4IIGTTCCPdE=', 'Jacob Thompson', 'Male', '1984-11-21', 'customer11@example.com', '0123456011', '345 Customer Ave', 'img/avatar/avatar.jpg'),
('cust12', 'z7f1n0HLfbf71/EAPMwcnzUhKLg=', 'Mia Garcia', 'Female', '1996-07-08', 'customer12@example.com', '0123456012', '456 Customer Rd', 'img/avatar/avatar.jpg'),
('cust13', 'hMOqi32PeNalXipffGflzq/L9EY=', 'Daniel Martinez', 'Male', '1985-10-19', 'customer13@example.com', '0123456013', '567 Customer Blvd', 'img/avatar/avatar.jpg'),
('cust14', '6glxG6yT7gkwR4oRHlU7X7akgY4=', 'Charlotte Robinson', 'Female', '1994-09-12', 'customer14@example.com', '0123456014', '678 Customer Ave', 'img/avatar/avatar.jpg'),
('cust15', '01g8MZ8t7IJL2iIGtzRMPTzJiTo=', 'Matthew Clark', 'Male', '1983-03-05', 'customer15@example.com', '0123456015', '789 Customer Rd', 'img/avatar/avatar.jpg'),
('cust16', 'VSoUE0cbBMY/79BqIk1v+kCdG1M=', 'Amelia Rodriguez', 'Female', '1998-06-30', 'customer16@example.com', '0123456016', '890 Customer St', 'img/avatar/avatar.jpg'),
('cust17', 'DTxKhA7pCN1NwkGj1iPWadYUOz0=', 'David Lewis', 'Male', '1982-12-11', 'customer17@example.com', '0123456017', '123 Customer Ave', 'img/avatar/avatar.jpg'),
('cust18', 'R+RhrqNOhedR/3foIWXfAA8DHnc=', 'Abigail Lee', 'Female', '1990-05-09', 'customer18@example.com', '0123456018', '234 Customer Blvd', 'img/avatar/avatar.jpg'),
('cust19', 'nMGd0IHxlcneVgs1GoQ6SjRSB2A=', 'Christopher Walker', 'Male', '1981-07-22', 'customer19@example.com', '0123456019', '345 Customer Rd', 'img/avatar/avatar.jpg'),
('cust20', '8qZHe/AetEyne9jfDSciHoot6Y4=', 'Madison Hall', 'Female', '1999-08-14', 'customer20@example.com', '0123456020', '456 Customer St', 'img/avatar/avatar.jpg');


