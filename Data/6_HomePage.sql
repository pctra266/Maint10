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
