
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

