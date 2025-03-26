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
--Blog
INSERT INTO StaffBlogPosts (StaffID, Title, Content, CreatedDate)
VALUES 
(1, 'The Importance of Teamwork in the Workplace', 
 'Teamwork is a crucial aspect of any successful organization. When employees collaborate effectively, they can achieve greater efficiency and innovation. 
 A strong team fosters a supportive environment, where members learn from each other and solve problems together. 

 Good teamwork also improves communication, reduces stress, and enhances overall job satisfaction. Companies that encourage teamwork often experience higher productivity 
 and employee engagement. 

 Organizations should focus on building a culture that promotes teamwork through effective leadership, clear communication, and mutual respect among employees.', 
 GETDATE()),

(2, 'How to Improve Customer Service Skills', 
 'Providing excellent customer service is essential for any business. To enhance your customer service skills, always listen actively to customers, 
 be patient, and communicate clearly. 

 Showing empathy and understanding their concerns can make a significant difference. Additionally, continuous learning and training can help employees handle 
 difficult situations professionally. 

 Happy customers are more likely to return and recommend your services to others. Companies that prioritize customer service gain a competitive edge in the market 
 and build long-term customer relationships.', 
 GETDATE()),

(3, 'The Benefits of a Positive Work Environment', 
 'A positive work environment plays a key role in employee satisfaction and productivity. When employees feel valued and respected, they are more motivated to perform well. 

 Encouraging open communication, recognizing achievements, and providing growth opportunities contribute to a healthy workplace culture. 

 Companies that focus on employee well-being often see lower turnover rates and improved team morale. A supportive and engaging work environment leads to greater 
 innovation and efficiency in the organization.', 
 GETDATE()),

(4, 'Time Management Tips for Busy Professionals', 
 'Effective time management is essential for professionals who want to stay productive. Start by setting clear priorities and creating a daily to-do list. 

 Avoid multitasking, as it can reduce efficiency. Using productivity tools and scheduling breaks can also help maintain focus. 

 Learning to delegate tasks and saying no to unnecessary commitments will allow you to manage your time more effectively. 

 Developing good time management habits can reduce stress and improve work-life balance, leading to a more fulfilling professional life.', 
 GETDATE()),

(5, 'The Impact of Technology on Modern Businesses', 
 'Technology has transformed the way businesses operate. From automation to artificial intelligence, companies now have tools to streamline processes and improve efficiency. 

 Digital communication allows for faster collaboration, while cloud computing enables remote work and data storage. 

 Businesses that embrace technology can gain a competitive edge and adapt more quickly to market changes. 

 However, organizations must also address cybersecurity challenges and ensure that employees are properly trained to use new technologies effectively.', 
 GETDATE());
