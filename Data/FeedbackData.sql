
INSERT INTO ProductDetail (CustomerID, ProductID, ProductCode, PurchaseDate)
VALUES
(1, 1, 'P103453', '2025-01-01'),
(2, 2, 'P1345345', '2025-01-02'),
(3, 3, 'P1434343', '2025-01-03'),
(4, 1, 'P13435', '2025-01-04'),
(5, 4, 'P134535', '2025-01-05');

INSERT INTO WarrantyCard (WarrantyCardCode, ProductDetailID, IssueDescription, WarrantyStatus, CreatedDate)
VALUES
('WC101', 10, 'Screen malfunction', 'fixing', '2025-01-10'),
('WC102', 2, 'Battery issue', 'completed', '2025-01-11'),
('WC103', 3, 'Overheating problem', 'fixing', '2025-01-12'),
('WC104', 4, 'Keyboard not working', 'cancel', '2025-01-13'),
('WC105', 5, 'Touchpad unresponsive', 'completed', '2025-01-14');


INSERT INTO Feedback (CustomerID, WarrantyCardID, Note, DateCreated, IsDeleted, ImageURL, VideoURL)
VALUES
(2, 2, 'The repair process was quick and efficient.', GETDATE(), 0, 'https://example.com/images/repair1.jpg', 'https://example.com/videos/repair1.mp4'),
(2, 3, 'The staff was very helpful and resolved my issue completely.', GETDATE(), 0, 'https://example.com/images/helpful_staff.jpg', NULL),
(3, 4, 'I was satisfied with the repair, but it took a bit longer than expected.', GETDATE(), 0, NULL, 'https://example.com/videos/long_repair.mp4'),
(4, 5, 'The issue was resolved, but I had to visit the service center twice.', GETDATE(), 0, 'https://example.com/images/service_center.jpg', NULL),
(5, 1, 'The repair was excellent, and the product works like new.', GETDATE(), 0, NULL, NULL),
(1, 2, 'The staff explained everything clearly and provided great service.', GETDATE(), 0, 'https://example.com/images/excellent_service.jpg', 'https://example.com/videos/excellent_service.mp4'),
(2, 3, 'The process was smooth, but I had to wait for parts to arrive.', GETDATE(), 0, NULL, NULL),
(3, 4, 'I appreciate the service, but I feel the warranty coverage could be better.', GETDATE(), 0, 'https://example.com/images/warranty_issue.jpg', NULL),
(4, 5, 'I had a great experience with the service team.', GETDATE(), 0, NULL, 'https://example.com/videos/great_service.mp4'),
(5, null, 'The issue was fixed promptly, and I was kept informed throughout.', GETDATE(), 0, 'https://example.com/images/fixed_issue.jpg', NULL);


