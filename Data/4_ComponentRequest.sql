USE MaintainManagement

INSERT INTO ComponentRequest (WarrantyCardID, [Date], Status, Note)
VALUES
(1, '2024-01-10', 'waiting', 'Request for component replacement.'),
(2, '2024-01-15', 'approved', 'Approved by supervisor.'),
(3, '2024-02-05', 'cancel', 'Customer canceled request.'),
(4, '2024-02-20', 'waiting', 'Pending review.'),
(5, '2024-03-02', 'approved', 'Verified and approved.'),
(6, '2024-03-18', 'cancel', 'Supplier issue.'),
(7, '2024-04-08', 'waiting', 'Waiting for confirmation.'),
(8, '2024-04-25', 'approved', 'Request approved.'),
(9, '2024-05-06', 'cancel', 'Not available in stock.'),
(10, '2024-05-21', 'waiting', 'Under consideration.'),
(11, '2024-06-12', 'approved', 'Checked and approved.'),
(12, '2024-06-27', 'cancel', 'Invalid request.'),
(13, '2024-07-14', 'waiting', 'Further inspection required.'),
(14, '2024-07-30', 'approved', 'Approval granted.'),
(15, '2024-08-09', 'cancel', 'Canceled by system.'),
(16, '2024-08-24', 'waiting', 'Supervisor review needed.'),
(17, '2024-09-05', 'approved', 'Final approval done.'),
(18, '2024-09-18', 'cancel', 'Customer withdrew request.'),
(19, '2024-10-02', 'waiting', 'Checking stock availability.'),
(20, '2024-10-19', 'approved', 'Confirmation received.'),
(21, '2024-11-01', 'cancel', 'Supplier unable to deliver.'),
(22, '2024-11-15', 'waiting', 'Supervisor verification pending.'),
(23, '2024-12-03', 'approved', 'Approved for shipment.'),
(24, '2024-12-20', 'cancel', 'Request not valid.');


INSERT INTO ComponentRequestResponsible (StaffID, ComponentRequestID, [Action], CreateDate)
VALUES
(2, 1, 'request', '2024-01-10'),
(3, 2, 'approved', '2024-01-15'),
(3, 3, 'cancel', '2024-02-05'),
(2, 4, 'request', '2024-02-20'),
(3, 5, 'approved', '2024-03-02'),
(3, 6, 'cancel', '2024-03-18'),
(2, 7, 'request', '2024-04-08'),
(3, 8, 'approved', '2024-04-25'),
(2, 9, 'cancel', '2024-05-06'),
(2,10, 'request', '2024-05-21'),
(3,11, 'approved', '2024-06-12'),
(2,12, 'cancel', '2024-06-27')

INSERT INTO ComponentRequestDetail (ComponentID, ComponentRequestID, Quantity, Status)
VALUES
(1, 1, 2,'waiting'),
(2, 2, 1,'waiting'),
(3, 3, 5,'waiting'),
(4, 4, 3,'waiting'),
(5, 5, 2,'waiting'),
(6, 6, 4,'waiting'),
(7, 7, 1,'waiting'),
(8, 8, 6,'waiting'),
(9, 9, 2,'waiting'),
(10, 10, 3,'waiting'),
(1, 11, 5,'waiting'),
(2, 12, 4,'waiting'),
(3, 13, 2,'waiting'),
(4, 14, 1,'waiting'),
(5, 15, 3,'waiting'),
(6, 16, 2,'waiting'),
(7, 17, 4,'waiting'),
(8, 18, 5,'waiting'),
(9, 19, 1,'waiting'),
(2, 20, 3,'waiting'),
(1, 21, 6,'waiting'),
(12, 22, 2,'waiting'),
(12, 23, 4,'waiting'),
(12, 24, 1,'waiting');
