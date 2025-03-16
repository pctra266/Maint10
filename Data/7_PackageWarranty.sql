USE MaintainManagement
INSERT INTO ExtendedWarranty (ExtendedWarrantyName, ExtendedPeriodInMonths, Price, ExtendedWarrantyDescription)
VALUES
('Basic Warranty', 12, 150.00, 'Standard extended warranty covering 1 year of service and repair for manufacturing defects.'),
('Premium Warranty', 18, 210.00, 'Enhanced extended warranty providing 18 months of comprehensive service and additional support.'),
('Ultimate Warranty', 24, 280.00, 'Top-tier extended warranty offering 2 years of full coverage, including periodic maintenance and priority support.'),
('Deluxe Warranty', 30, 350.00, 'Deluxe extended warranty ensuring 2.5 years of service with extra benefits and on-site support.'),
('Elite Warranty', 36, 400.00, 'Elite extended warranty delivers 3 years of complete coverage with premium customer service and extended repair services.');

INSERT INTO PackageWarranty (WarrantyStartDate, WarrantyEndDate, Note, IsActive)
VALUES
('2025-01-01', '2026-01-01', 'Package warranty 1', 1),
('2025-01-02', '2026-01-02', 'Package warranty 2', 1),
('2025-01-03', '2026-01-03', 'Package warranty 3', 1),
('2025-01-04', '2026-01-04', 'Package warranty 4', 1),
('2025-01-05', '2026-01-05', 'Package warranty 5', 1),
('2025-01-06', '2026-01-06', 'Package warranty 6', 1),
('2025-01-07', '2026-01-07', 'Package warranty 7', 1),
('2025-01-08', '2026-01-08', 'Package warranty 8', 1),
('2025-01-09', '2026-01-09', 'Package warranty 9', 1),
('2025-01-10', '2026-01-10', 'Package warranty 10', 1),
('2025-01-11', '2026-01-11', 'Package warranty 11', 1),
('2025-01-12', '2026-01-12', 'Package warranty 12', 1),
('2025-01-13', '2026-01-13', 'Package warranty 13', 1),
('2025-01-14', '2026-01-14', 'Package warranty 14', 1),
('2025-01-15', '2026-01-15', 'Package warranty 15', 1),
('2025-01-16', '2026-01-16', 'Package warranty 16', 1),
('2025-01-17', '2026-01-17', 'Package warranty 17', 1),
('2025-01-18', '2026-01-18', 'Package warranty 18', 1),
('2025-01-19', '2026-01-19', 'Package warranty 19', 1),
('2025-01-20', '2026-01-20', 'Package warranty 20', 1);

-- Row 1:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (1, 1, '2025-02-01', DATEADD(MONTH, 12, '2025-02-01')); -- 2026-02-01

-- Row 2:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (2, 2, '2025-02-02', DATEADD(MONTH, 18, '2025-02-02')); -- 2026-08-02

-- Row 3:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (3, 3, '2025-02-03', DATEADD(MONTH, 24, '2025-02-03')); -- 2027-02-03

-- Row 4:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (4, 4, '2025-02-04', DATEADD(MONTH, 30, '2025-02-04')); -- 2027-08-04

-- Row 5:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (5, 5, '2025-02-05', DATEADD(MONTH, 36, '2025-02-05')); -- 2028-02-05

-- Row 6:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (1, 6, '2025-02-06', DATEADD(MONTH, 12, '2025-02-06')); -- 2026-02-06

-- Row 7:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (2, 7, '2025-02-07', DATEADD(MONTH, 18, '2025-02-07')); -- 2026-08-07

-- Row 8:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (3, 8, '2025-02-08', DATEADD(MONTH, 24, '2025-02-08')); -- 2027-02-08

-- Row 9:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (4, 9, '2025-02-09', DATEADD(MONTH, 30, '2025-02-09')); -- 2027-08-09

-- Row 10:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (5, 10, '2025-02-10', DATEADD(MONTH, 36, '2025-02-10')); -- 2028-02-10

-- Row 11:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (1, 11, '2025-02-11', DATEADD(MONTH, 12, '2025-02-11')); -- 2026-02-11

-- Row 12:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (2, 12, '2025-02-12', DATEADD(MONTH, 18, '2025-02-12')); -- 2026-08-12

-- Row 13:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (3, 13, '2025-02-13', DATEADD(MONTH, 24, '2025-02-13')); -- 2027-02-13

-- Row 14:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (4, 14, '2025-02-14', DATEADD(MONTH, 30, '2025-02-14')); -- 2027-08-14

-- Row 15:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (5, 15, '2025-02-15', DATEADD(MONTH, 36, '2025-02-15')); -- 2028-02-15

-- Row 16:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (1, 16, '2025-02-16', DATEADD(MONTH, 12, '2025-02-16')); -- 2026-02-16

-- Row 17:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (2, 17, '2025-02-17', DATEADD(MONTH, 18, '2025-02-17')); -- 2026-08-17

-- Row 18:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (3, 18, '2025-02-18', DATEADD(MONTH, 24, '2025-02-18')); -- 2027-02-18

-- Row 19:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (4, 19, '2025-02-19', DATEADD(MONTH, 30, '2025-02-19')); -- 2027-08-19

-- Row 20:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (5, 20, '2025-02-20', DATEADD(MONTH, 36, '2025-02-20')); -- 2028-02-20

-- Row 21:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (1, 1, '2025-02-21', DATEADD(MONTH, 12, '2025-02-21')); -- 2026-02-21

-- Row 22:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (2, 2, '2025-02-22', DATEADD(MONTH, 18, '2025-02-22')); -- 2026-08-22

-- Row 23:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (3, 3, '2025-02-23', DATEADD(MONTH, 24, '2025-02-23')); -- 2027-02-23

-- Row 24:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (4, 4, '2025-02-24', DATEADD(MONTH, 30, '2025-02-24')); -- 2027-08-24

-- Row 25:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (5, 5, '2025-02-25', DATEADD(MONTH, 36, '2025-02-25')); -- 2028-02-25

-- Row 26:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (1, 6, '2025-02-26', DATEADD(MONTH, 12, '2025-02-26')); -- 2026-02-26

-- Row 27:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (2, 7, '2025-02-27', DATEADD(MONTH, 18, '2025-02-27')); -- 2026-08-27

-- Row 28:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (3, 8, '2025-02-28', DATEADD(MONTH, 24, '2025-02-28')); -- 2027-02-28

-- Row 29:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (4, 9, '2025-03-01', DATEADD(MONTH, 30, '2025-03-01')); -- 2027-08-01

-- Row 30:
INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty)
VALUES (5, 10, '2025-03-02', DATEADD(MONTH, 36, '2025-03-02')); -- 2028-03-02
