USE MaintainManagement

INSERT INTO FooterSetting ( slogan, address, hotline, email,copyrightYear)
VALUES 
('Fast Repairs, Reliable Maintenance!', '123 ABC Street, XYZ District, Hanoi City', '0123 456 789', 'support@main10.com','2025');

INSERT INTO ContactText (title, subtitle) 
VALUES ('Warranty Consultation Registration', 
        'Need assistance with your device? Our warranty consultation service is here to help. Contact now, and our team will reach out to support you as soon as possible.');

INSERT INTO Media (ObjectID, ObjectType, MediaURL, MediaType,UploadedDate) VALUES (1, 'Cover', '/img/backgrounds/bg1.jpg','image', GETDATE())

INSERT INTO MarketingServiceSection (Title, SubTitle)
VALUES ('Our Services', 'We provide comprehensive repair and maintenance services for mobile phones and laptops.');

INSERT INTO MarketingServiceItem (SectionID, Title, Description, ImageURL, SortOrder)
VALUES (1, 'Software Troubleshooting', 'Professional software troubleshooting and system updates for all mobile and laptop devices.', '/img/serviceItems/service1.jpg', 1),
(1, 'Water Damage Repair', 'Specialized repair for water-damaged smartphones and laptops, restoring full functionality.', '/img/serviceItems/service2.PNG', 2),
(1, 'Hardware Diagnostics', 'Comprehensive hardware diagnostics and repair solutions for your devices.', '/img/serviceItems/service3.jpg', 3),
(1, 'Custom Upgrades', 'Upgrade your laptop or phone with the latest components for improved performance.', '/img/serviceItems/service4.jpg', 4);

INSERT INTO CustomerContact (Name, Email, Phone, Message)
VALUES
    ('John Smith', 'john.smith@example.com', '555-0101', 'I need assistance with the product.'),
    ('Jane Doe', 'jane.doe@example.com', '555-0102', 'Please provide detailed information.'),
    ('Michael Johnson', 'michael.johnson@example.com', '555-0103', 'I want to place a bulk order.'),
    ('Emily Davis', 'emily.davis@example.com', '555-0104', 'How can I track my order?'),
    ('Daniel Brown', 'daniel.brown@example.com', '555-0105', 'I am experiencing issues with payment.'),
    ('Olivia Wilson', 'olivia.wilson@example.com', '555-0106', 'What is the product warranty policy?'),
    ('James Taylor', 'james.taylor@example.com', '555-0107', 'I would like to cancel my order.'),
    ('Sophia Martinez', 'sophia.martinez@example.com', '555-0108', 'Are there any current promotions?'),
    ('William Anderson', 'william.anderson@example.com', '555-0109', 'How can I return or exchange a product?'),
    ('Ava Thomas', 'ava.thomas@example.com', '555-0110', 'I need technical support.'),
    ('Alexander Jackson', 'alexander.jackson@example.com', '555-0111', 'I want to update my account information.'),
    ('Mia White', 'mia.white@example.com', '555-0112', 'When will the product be back in stock?'),
    ('Ethan Harris', 'ethan.harris@example.com', '555-0113', 'I want to subscribe to the newsletter.'),
    ('Isabella Martin', 'isabella.martin@example.com', '555-0114', 'Can I pay with a credit card?'),
    ('Mason Thompson', 'mason.thompson@example.com', '555-0115', 'I forgot my password, how can I recover it?'),
    ('Charlotte Garcia', 'charlotte.garcia@example.com', '555-0116', 'What is the return policy?'),
    ('Logan Martinez', 'logan.martinez@example.com', '555-0117', 'I want to learn more about your services.'),
    ('Amelia Robinson', 'amelia.robinson@example.com', '555-0118', 'How can I contact customer service?'),
    ('Lucas Clark', 'lucas.clark@example.com', '555-0119', 'I want to schedule an appointment.'),
    ('Harper Rodriguez', 'harper.rodriguez@example.com', '555-0120', 'Can you provide a quote?'),
    ('Benjamin Lewis', 'benjamin.lewis@example.com', '555-0121', 'I want to join the membership program.'),
    ('Evelyn Lee', 'evelyn.lee@example.com', '555-0122', 'I need help with account login.'),
    ('Henry Walker', 'henry.walker@example.com', '555-0123', 'Do you offer express delivery?'),
    ('Abigail Hall', 'abigail.hall@example.com', '555-0124', 'How can I unsubscribe from emails?'),
    ('Sebastian Allen', 'sebastian.allen@example.com', '555-0125', 'I want to know about job opportunities at the company.'),
    ('Madison Young', 'madison.young@example.com', '555-0126', 'I am interested in your new product.');

INSERT INTO MissingComponentRequest (ComponentName, ComponentType, TypeID, BrandID, RequestedBy, Status, Note)
VALUES
    ('Laptop Battery', 'product', 1, 3, 1, 'waiting', 'Urgent request for replacement battery.'),
    ('Screen Panel', 'product', 2, 5, 1, 'approved', 'Approved for immediate replacement.'),
    ('Motherboard', 'product', 3, 7, 1, 'waiting', 'Need verification for compatibility.'),
    ('Power Adapter', 'product', 4, 9, 1, 'cancel', 'Request canceled by user.'),
    ('Keyboard', 'product', 5, 11, 1, 'waiting', 'Broken keys, needs replacement.'),
    ('Cooling Fan', 'product', 6, 13, 2, 'approved', 'Approved for purchase.'),
    ('RAM Module', 'product', 7, 15, 2, 'waiting', 'Upgrade request for 16GB RAM.'),
    ('Hard Drive', 'product', 8, 1, 2, 'cancel', 'Request duplicated, canceling this.'),
    ('Solid State Drive', 'product', 1, 2, 2, 'waiting', 'Require 1TB SSD for upgrade.'),
    ('Graphics Card', 'product', 2, 4, 2, 'approved', 'Urgent replacement for workstation.'),
    ('Touchpad', 'product', 3, 6, 2, 'waiting', 'Touchpad malfunctioning, needs fix.'),
    ('WiFi Module', 'product', 4, 8, 2, 'approved', 'Replace with a compatible model.'),
    ('USB Port Module', 'product', 5, 10, 2, 'cancel', 'Incorrect component requested.'),
    ('DVD Drive', 'product', 6, 12, 2, 'waiting', 'Require replacement for old model.'),
    ('Battery Connector', 'product', 7, 14, 2, 'approved', 'Part is broken, needs fixing.'),
    ('Laptop Casing', 'product', 8, 16, 2, 'waiting', 'Case damaged, needs replacement.'),
    ('BIOS Chip', 'product', 1, 3, 2, 'approved', 'Need new BIOS chip for update.'),
    ('Charging Port', 'product', 2, 5, 2, 'waiting', 'Loose connection, requires fix.'),
    ('Heat Sink', 'product', 3, 7, 2, 'cancel', 'Not needed anymore.'),
    ('Bluetooth Module', 'product', 4, 9, 2, 'waiting', 'Needs replacement due to malfunction.'),
    ('Fingerprint Sensor', 'product', 5, 11, 2, 'approved', 'Request approved for security reasons.'),
    ('Speakers', 'product', 6, 13, 2, 'waiting', 'Sound issues, requires new pair.'),
    ('Webcam', 'product', 7, 15, 2, 'approved', 'Webcam faulty, needs immediate fix.'),
    ('Cooling Pad', 'unknown product', NULL, NULL, 2, 'waiting', 'Requested additional cooling support.'),
    ('Hinge Mechanism', 'unknown product', NULL, NULL, 2, 'approved', 'Hinge broken, needs repair.'),
    ('Screw Set', 'unknown product', NULL, NULL, 2, 'waiting', 'Missing screws, needs replacement.');

