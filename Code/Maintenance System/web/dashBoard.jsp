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
        <meta charset="UTF-8">
        <title>Update Product</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            /* ----- BỐ CỤC GRID CHO CÁC THẺ (CARD) ----- */
            .dashboard .sidebar-nav {
                /* Dùng grid để chia cột tự co giãn */
                display: grid;
                /* Mỗi cột tối thiểu 250px, tự co giãn cho vừa màn hình */
                grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
                gap: 20px; /* Khoảng cách giữa các card */

                list-style: none;
                margin: 0 auto;
                padding: 0;
            }

            /* Mỗi <li> là một card */
            .dashboard .sidebar-nav li {
                background-color: #326ABC;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                position: relative;
                padding: 16px;
                transition: transform 0.2s, box-shadow 0.2s;
            }

            /* Hiệu ứng hover cho card */
            .dashboard .sidebar-nav li:hover {
                transform: translateY(-3px);
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
            }

            /* ----- PHẦN HIỂN THỊ LINK + ICON ----- */
            .dashboard .sidebar-nav li a {
                /* Cho link hiển thị dạng ngang có icon bên trái */
                display: inline-flex;
                align-items: center;
                gap: 8px;

                font-weight: 600;
                font-size: 1rem;
                color: #333;
                text-decoration: none;
            }

            /* Hover trên link (tiêu đề) */
            .dashboard .sidebar-nav li a:hover {
                text-decoration: underline;
                color: #326ABC;
            }

            /* Dùng pseudo-element để hiển thị icon Font Awesome */
            .dashboard .sidebar-nav li a::before {
                font-family: "Font Awesome 5 Free";
                font-weight: 900;
                margin-right: 4px;
            }

            /* Gán icon cho từng item theo thứ tự (tuỳ chỉnh theo nhu cầu) */
            .dashboard .sidebar-nav li:nth-child(1) a::before {
                content: "\f007"; /* fa-user */
            }
            .dashboard .sidebar-nav li:nth-child(2) a::before {
                content: "\f1ea"; /* fa-newspaper */
            }
            .dashboard .sidebar-nav li:nth-child(3) a::before {
                content: "\f15b"; /* fa-file-alt */
            }
            .dashboard .sidebar-nav li:nth-child(4) a::before {
                content: "\f075"; /* fa-comment */
            }
            .dashboard .sidebar-nav li:nth-child(5) a::before {
                content: "\f49a"; /* fa-box */
            }
            .dashboard .sidebar-nav li:nth-child(6) a::before {
                content: "\f56c"; /* fa-file-contract */
            }
            .dashboard .sidebar-nav li:nth-child(7) a::before {
                content: "\f2c2"; /* fa-id-card */
            }
            .dashboard .sidebar-nav li:nth-child(8) a::before {
                content: "\f07a"; /* fa-shopping-cart */
            }
            /* ... Nếu bạn có nhiều hơn 8 item, tiếp tục thêm nth-child(9), (10)... */

            /* ----- TÙY CHỌN: NHÃN "Connected" Ở GÓC CARD ----- */
            /* Nếu muốn hiển thị một nhãn nhỏ ở góc, bỏ comment đoạn dưới: */
            /*
            .dashboard .sidebar-nav li::after {
                content: "Connected";
                background-color: #e0f5ea;
                color: #2baa6c;
                border-radius: 4px;
                padding: 4px 8px;
                font-size: 0.8rem;
                position: absolute;
                top: 16px;
                right: 16px;
            }
            */

            /* ----- NẾU TRƯỚC ĐÓ CÓ DÙNG .main .sidebar-nav li a::before { content: none !important; } THÌ XOÁ HOẶC BỎ ĐI ----- */
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
                                    <a href="feedback?action=viewFeedbackDashboard">Feedback </a>
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
                                    <a href="feedback?action=viewFeedback">Feedback list</a>
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
                                    <a href="componentRequest?action=viewComponentRequestDashboard">Component Request</a>
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
