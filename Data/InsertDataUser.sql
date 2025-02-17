
USE MaintainManagement
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
('cust01', 'T2D6i9+ldWGqevu6W6FAP1iXBbI=', 'Customer 1', 'Male', 'customer1@example.com', '0123456001', '123 Customer St',' img/avatar/avatar'),
('cust02', 'Cw2LaFmhUP2i/jGdPuB5aVCxAQg=', 'Customer 2', 'Female', 'customer2@example.com', '0123456002', '234 Customer Ave', ' img/avatar/avatar'),
('cust03', 'G1VKaeAjqcN762tlQAIFfla2Hnc=', 'Customer 3', 'Male', 'customer3@example.com', '0123456003', '345 Customer Blvd', ' img/avatar/avatar'),
('cust04', 'mnIoccUReEqm3CnIyVEsEqXI418=', 'Customer 4', 'Female', 'customer4@example.com', '0123456004', '456 Customer Rd', ' img/avatar/avatar'),
('cust05', 'cDW7EzjR1mP8REo1UF3mWlnuZdo=', 'Customer 5', 'Male', 'customer5@example.com', '0123456005', '567 Customer St', ' img/avatar/avatar'),
('cust06', 'O5eiLfa5pJNrHk7ck1tNBw6pUYE=', 'Customer 6', 'Female', 'customer6@example.com', '0123456006', '678 Customer Blvd', ' img/avatar/avatar'),
('cust07', 't6oNiVU8eL8t1C+e11mByn7n7oA=', 'Customer 7', 'Male', 'customer7@example.com', '0123456007', '789 Customer Ave', ' img/avatar/avatar'),
('cust08', '2L5q1/vlAepSi4mrWEoz+oJnTEY=', 'Customer 8', 'Female', 'customer8@example.com', '0123456008', '890 Customer Rd', ' img/avatar/avatar'),
('cust09', 'hNpyQH84BIbgDRM+z0nJgUqCeV8=', 'Customer 9', 'Male', 'customer9@example.com', '0123456009', '123 Customer Blvd', ' img/avatar/avatar'),
('cust10', 'NiH6AmjlqDCWROFjLhNDiYQ1F5U=', 'Customer 10', 'Female', 'customer10@example.com', '0123456010', '234 Customer St', ' img/avatar/avatar'),
('cust11', 'm1HKaKLljc/8wGtZ4IIGTTCCPdE=', 'Customer 11', 'Male', 'customer11@example.com', '0123456011', '345 Customer Ave', ' img/avatar/avatar'),
('cust12', 'z7f1n0HLfbf71/EAPMwcnzUhKLg=', 'Customer 12', 'Female', 'customer12@example.com', '0123456012', '456 Customer Rd',' img/avatar/avatar'),
('cust13', 'hMOqi32PeNalXipffGflzq/L9EY=', 'Customer 13', 'Male', 'customer13@example.com', '0123456013', '567 Customer Blvd', ' img/avatar/avatar'),
('cust14', '6glxG6yT7gkwR4oRHlU7X7akgY4=', 'Customer 14', 'Female', 'customer14@example.com', '0123456014', '678 Customer Ave', ' img/avatar/avatar'),
('cust15', '01g8MZ8t7IJL2iIGtzRMPTzJiTo=', 'Customer 15', 'Male', 'customer15@example.com', '0123456015', '789 Customer Rd', ' img/avatar/avatar'),
('cust16', 'VSoUE0cbBMY/79BqIk1v+kCdG1M=', 'Customer 16', 'Female', 'customer16@example.com', '0123456016', '890 Customer St',' img/avatar/avatar'),
('cust17', 'DTxKhA7pCN1NwkGj1iPWadYUOz0=', 'Customer 17', 'Male', 'customer17@example.com', '0123456017', '123 Customer Ave', ' img/avatar/avatar'),
('cust18', 'R+RhrqNOhedR/3foIWXfAA8DHnc=', 'Customer 18', 'Female', 'customer18@example.com', '0123456018', '234 Customer Blvd', ' img/avatar/avatar'),
('cust19', 'nMGd0IHxlcneVgs1GoQ6SjRSB2A=', 'Customer 19', 'Male', 'customer19@example.com', '0123456019', '345 Customer Rd', ' img/avatar/avatar'),
('cust20', '8qZHe/AetEyne9jfDSciHoot6Y4=', 'Customer 20', 'Female', 'customer20@example.com', '0123456020', '456 Customer St', ' img/avatar/avatar');
