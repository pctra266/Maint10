INSERT INTO [dbo].[Permissions] ([PermissionName], [Description]) VALUES
('VIEW_COMPONENTS_WAREHOUSE', 'Access to Warehouse/Component to view list component only'),
('DELETE_COMPONENT', 'Set status of a component to off in warehouse'),
('VIEW_WARRANTY_CARD_LIST', 'Access to Warehouse/WarrantyCard to view list card only')


INSERT INTO [dbo].[Role_Permissions] ([RoleID], [PermissionID] ) VALUES
(1,1),(2,1),(3,1),
(1,2),(3,2),
(1,3),(2,3)