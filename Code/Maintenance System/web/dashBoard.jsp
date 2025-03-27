<%-- 
    Document   : updateProduct1
    Created on : Feb 6, 2025, 5:07:44 AM
    Author     : sonNH
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            /* CSS cho phần main */
            .main .content {
                padding: 30px;
                background-color: #f7f7f7;
                font-family: 'Inter', sans-serif;
            }
            
            /* Khung chứa dashboard */
            .dashboard {
                background: #ffffff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }
            
            /* Danh sách sidebar-nav */
            .sidebar-nav {
                list-style: none;
                margin: 0;
                padding: 0;
            }
            
            /* Mỗi item */
            .sidebar-nav li {
                margin-bottom: 15px;
            }
            
            /* Các nút liên kết */
            .sidebar-nav li a {
                display: block;
                position: relative;
                text-decoration: none;
                padding: 12px 20px 12px 50px; /* để dành chỗ cho icon bên trái */
                background: #007bff;
                color: #ffffff;
                border-radius: 5px;
                font-weight: 600;
                transition: background 0.3s;
            }
            
            /* Hover */
            .sidebar-nav li a:hover {
                background: #0056b3;
            }
            
            /* Icon sử dụng Font Awesome – sử dụng pseudo-element */
            .sidebar-nav li a::before {
                font-family: "Font Awesome 5 Free";
                font-weight: 900;
                position: absolute;
                left: 15px;
                top: 50%;
                transform: translateY(-50%);
            }
            
            /* Gán icon cho từng nút theo thứ tự trong danh sách */
            .sidebar-nav li:nth-child(1) a::before { content: "\f007"; }    /* fa-user */
            .sidebar-nav li:nth-child(2) a::before { content: "\f1ea"; }    /* fa-newspaper */
            .sidebar-nav li:nth-child(3) a::before { content: "\f15b"; }    /* fa-file-alt */
            .sidebar-nav li:nth-child(4) a::before { content: "\f075"; }    /* fa-comment */
            .sidebar-nav li:nth-child(5) a::before { content: "\f49a"; }    /* fa-box */
            .sidebar-nav li:nth-child(6) a::before { content: "\f56c"; }    /* fa-file-contract */
            .sidebar-nav li:nth-child(7) a::before { content: "\f2c2"; }    /* fa-id-card */
            .sidebar-nav li:nth-child(8) a::before { content: "\f07a"; }    /* fa-shopping-cart */
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <div class="dashboard">
                        <c:if test="${not empty sessionScope.customer}">
                            <ul class="sidebar-nav">
                                <li>
                                    <a href="profile">Profile</a>
                                </li>
                                <li>
                                    <a href="BlogController">Blog</a>
                                </li>
                                <li>
                                    <a href="InvoiceController">Invoice</a>
                                </li>
                                <li>
                                    <a href="feedback?action=viewFeedbackDashboard">Feedback (Customer)</a>
                                </li>
                                <li>
                                    <a href="packageWarranty">List Package Warranty</a>
                                </li>
                                <li>
                                    <a href="extendedWarranty">Extended Warranty List</a>
                                </li>
                                <li>
                                    <a href="yourwarrantycard">Your Warranty Card</a>
                                </li>
                                <li>
                                    <a href="purchaseproduct">Purchase Product</a>
                                </li>
                            </ul>
                        </c:if>
                        
                        <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '2'}">
                            <ul class="sidebar-nav">
                                <li>
                                    <a href="profile">Profile</a>
                                </li>
                                <li>
                                    <a href="ComponentWarehouse">Component</a>
                                </li>
                                <li>
                                    <a href="viewProduct">Product</a>
                                </li>
                                <li>
                                    <a href="BlogController">Blog</a>
                                </li>
                                <li>
                                    <a href="InvoiceController">Invoice</a>
                                </li>
                                <li>
                                    <a href="WarrantyCard">Warranty Card</a>
                                </li>
                                <li>
                                    <a href="WarrantyCardProcessController">Warranty Card Process</a>
                                </li>
                                <li>
                                    <a href="customer">Customer</a>
                                </li>
                                <li>
                                    <a href="Brand">Brand</a>
                                </li>
                                <li>
                                    <a href="ComponentType">Component Type</a>
                                </li>
                            </ul>
                        </c:if>
                        
                        <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '1'}">
                            <ul class="sidebar-nav">
                                <li>
                                    <a href="profile">Profile</a>
                                </li>
                                <li>
                                    <a href="ComponentWarehouse">Component</a>
                                </li>
                                <li>
                                    <a href="viewProduct">Product</a>
                                </li>
                                <li>
                                    <a href="BlogController">Blog</a>
                                </li>
                                <li>
                                    <a href="StaffController">Staff</a>
                                </li>
                                <li>
                                    <a href="InvoiceController">Invoice</a>
                                </li>
                                <li>
                                    <a href="feedback">Feedback list</a>
                                </li>
                                <li>
                                    <a href="adminDashboard.jsp">Setting Max Size</a>
                                </li>
                                <li>
                                    <a href="packageWarranty">List Package Warranty</a>
                                </li>
                                <li>
                                    <a href="extendedWarranty">Extended Warranty List</a>
                                </li>
                                <li>
                                    <a href="customizeHomepage">Customize Homepage</a>
                                </li>
                                <li>
                                    <a href="customerContact">List Customer Contact</a>
                                </li>
                                <li>
                                    <a href="ReportComponent.jsp">Report Component</a>
                                </li>
                                <li>
                                    <a href="customer">Customer</a>
                                </li>
                                <li>
                                    <a href="reportWarrantyCard.jsp">Report Warranty</a>
                                </li>
                                <li>
                                    <a href="Brand">Brand</a>
                                </li>
                                <li>
                                    <a href="ComponentType">Component Type</a>
                                </li>
                            </ul>
                        </c:if>
                        
                        <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '5'}">
                            <ul class="sidebar-nav">
                                <li>
                                    <a href="profile">Profile</a>
                                </li>
                                <li>
                                    <a href="viewProduct">Product</a>
                                </li>
                                <li>
                                    <a href="BlogController">Blog</a>
                                </li>
                                <li>
                                    <a href="packageWarranty">List Package Warranty</a>
                                </li>
                                <li>
                                    <a href="extendedWarranty">Extended Warranty List</a>
                                </li>
                                <li>
                                    <a href="customerContact">List Customer Contact</a>
                                </li>
                                <li>
                                    <a href="customer">Customer</a>
                                </li>
                            </ul>
                        </c:if>
                        
                        <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '3'}">
                            <ul class="sidebar-nav">
                                <li>
                                    <a href="profile">Profile</a>
                                </li>
                                <li>
                                    <a href="ComponentWarehouse">Component</a>
                                </li>
                                <li>
                                    <a href="viewProduct">Product</a>
                                </li>
                                <li>
                                    <a href="BlogController">Blog</a>
                                </li>
                                <li>
                                    <a href="componentRequest">Component Request</a>
                                </li>
                                <li>
                                    <a href="componentRequest?action=viewListComponentRequest">List Component Request</a>
                                </li>
                                <li>
                                    <a href="componentRequestResponsible">Component Request Log</a>
                                </li>
                                <li>
                                    <a href="Brand">Brand</a>
                                </li>
                                <li>
                                    <a href="ComponentType">Component Type</a>
                                </li>
                            </ul>
                        </c:if>
                        
                        <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '4'}">
                            <ul class="sidebar-nav">
                                <li>
                                    <a href="profile">Profile</a>
                                </li>
                                <li>
                                    <a href="BlogController">Blog</a>
                                </li>
                                <li>
                                    <a href="warrantyCardRepairContractor">Warranty Card Repair Contractor</a>
                                </li>
                            </ul>
                        </c:if>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
