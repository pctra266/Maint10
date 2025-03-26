<%-- 
    Document   : ReportStaff
    Created on : Mar 13, 2025, 2:12:01 AM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link href="css/light.css" rel="stylesheet">

        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

        <style>
            /* 🌟 Reset chung */
            body {
                font-family: 'Poppins', sans-serif;
                background-color: #f4f6f9;
                margin: 0;
                padding: 0;
                color: #333;
            }

            /* 🌟 Layout chính */
            .wrapper {
                display: flex;
            }
            .main {
                flex-grow: 1;
                padding: 20px;
            }
            .content {
                background: white;
                padding: 20px;
                border-radius: 12px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.12);
            }

            /* 🌟 Bảng dữ liệu */
            .table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            .table th, .table td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            .table tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            /* 🌟 Nút bấm chính */
            .btn-primary {
                background: linear-gradient(135deg, #007bff, #0056b3);
                color: white;
                border: none;
                padding: 10px 18px;
                border-radius: 6px;
                cursor: pointer;
                transition: all 0.3s ease-in-out;
                font-weight: bold;
            }
            .btn-primary:hover {
                background: linear-gradient(135deg, #0056b3, #004085);
                transform: scale(1.08);
            }

            /* 🌟 Thanh phân trang */
            .pagination a {
                margin: 0 5px;
                padding: 8px 12px;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 4px;
                transition: background 0.3s;
            }
            .pagination a:hover {
                background-color: #0056b3;
            }
            .pagination a.active {
                background-color: #0056b3;
                font-weight: bold;
            }


            /* 🌟 Form tìm kiếm */
            .form-container {
                background: #fff;
                padding: 20px;
                border-radius: 12px;
                box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.12);
                margin: 15px 0;
            }
            .input-group, .form-select {
                transition: all 0.3s ease-in-out;
                border-radius: 8px;
            }
            .form-control:focus, .form-select:focus {
                box-shadow: 0 0 10px rgba(0, 123, 255, 0.5);
                border-color: #007bff;
            }

            /* 🌟 Hộp tải lên file */
            .upload-box { 
                border: 2px dashed #007bff;
                padding: 12px;
                text-align: center;
                background: white;
                width: 120px;
                border-radius: 8px;
                cursor: pointer;
                transition: all 0.3s ease;
            }
            .upload-box:hover { 
                background: #e9f5ff;
                border-color: #0056b3;
            }
            .upload-box p { margin: 0; color: #007bff; font-weight: bold; }
            input[type="file"] { display: none; }

            /* 🌟 Danh sách nhân viên */
            .container {
                display: flex;
                flex-wrap: wrap; /* Giúp các thẻ xuống dòng nếu không đủ chỗ */
                justify-content: center; /* Căn giữa các thẻ */
                gap: 20px; /* Tạo khoảng cách giữa các thẻ */
            }


            /* 🌟 Hiệu ứng xuất hiện từng cái */
            .profile-card {
                display: flex;
                align-items: center;
                background: white;
                border-left: 6px solid #007bff;
                padding: 15px;
                border-radius: 12px;
                box-shadow: 2px 4px 10px rgba(0, 0, 0, 0.12);
                transform: translateY(30px);
                opacity: 0;
                text-align: center; /* Căn chữ vào giữa */
                width: 300px;
                animation: fadeInUp 0.8s forwards ease-in-out;
            }

            /* 🌟 Tạo hiệu ứng xuất hiện */
            @keyframes fadeInUp {
                from {
                    opacity: 0;
                    transform: translateY(30px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            /* 🌟 Tạo độ trễ xuất hiện cho từng profile-card */
            .profile-card:nth-child(1) { animation-delay: 0.2s; }
            .profile-card:nth-child(2) { animation-delay: 0.4s; }
            .profile-card:nth-child(3) { animation-delay: 0.6s; }
            .profile-card:nth-child(4) { animation-delay: 0.8s; }
            .profile-card:nth-child(5) { animation-delay: 1s; }
            .profile-card:nth-child(6) { animation-delay: 1.2s; }
            .profile-card:nth-child(7) { animation-delay: 1.4s; }
            .profile-card:nth-child(8) { animation-delay: 1.6s; }
            .profile-card:nth-child(9) { animation-delay: 1.8s; }
            .profile-card:nth-child(10) { animation-delay: 2s; }

            /* 🌟 Hiệu ứng hover */
            .profile-card:hover {
                transform: scale(1.05);
                box-shadow: 3px 6px 14px rgba(0, 0, 0, 0.18);
            }

            /* 🌟 Hình ảnh */
            .profile-card img {
                width: 65px;
                height: 65px;
                border-radius: 50%;
                object-fit: cover;
                margin-right: 15px;
                border: 2px solid #ddd;
            }
            .profile-card .info {
                display: flex;
                flex-direction: column;
            }
            .profile-card .name {
                font-size: 16px;
                font-weight: bold;
            }
            .profile-card .position {
                font-size: 14px;
                color: #666;
            }
            

            /* 🌟 Thẻ thống kê */
            .container1 {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                gap: 20px;
            }
            .card {
                background: white;
                padding: 20px;
                border-radius: 12px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                text-align: center;
                opacity: 0;
                transform: translateY(20px);
                animation: fadeIn 1s forwards ease-in-out;
            }
            .card:nth-child(1) { animation-delay: 0.2s; }
            .card:nth-child(2) { animation-delay: 0.4s; }
            .card:nth-child(3) { animation-delay: 0.6s; }
            .card:nth-child(4) { animation-delay: 0.8s; }
            .card:nth-child(5) { animation-delay: 1s; }
            .card:nth-child(6) { animation-delay: 1.2s; }
            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 8px 15px rgba(0, 0, 0, 0.15);
            }
            @keyframes fadeIn {
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            /* 🌟 Biểu tượng */
            .icon {
                font-size: 24px;
                background: #e0e0e0;
                padding: 12px;
                border-radius: 50%;
                display: inline-block;
            }

            /* 🌟 Màu sắc cho trạng thái */
            .green { color: green; font-weight: bold; }
            .red { color: red; font-weight: bold; }

        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center">Report Staff List</h1>
                    <div class="container1">
                        <div class="card">
                            <h3>New staff </h3>
                            <h2>${count}</h2>
                            <p class="green">+${average}% by total</p>
                        </div>
                        <div class="card">
                            <h3>Total staff</h3>
                            <h2>${total}</h2>
                            <p class="green"></p>
                        </div>
                        <div class="card">
                            <h3>Staff active</h3>
                            <h2>${active}</h2>
                            <p class="green">${averageactive}% of total staff</p>
                        </div>
                        <div class="card">
                            <h3>Free time</h3>
                            <h2>${relax}</h2>
                            <p class="red">${averagerelax}% of total staff</p>
                        </div>
                    </div>                        

                    <table class="table table-hover my-0">

                        <h2>
                            Active 
                            <c:if test="${fn:length(list) > 6}">
                                <a href="reportStaffController?action=viewAllActive" class="arrow-link">»</a>
                            </c:if>
                        </h2>
                        <div class="container">                            
                            <c:forEach var="List" items="${list}" varStatus="status">
                                <c:if test="${status.index < 6}">
                                    <div class="profile-card">
                                        <img src="${List.getImage()}" alt="Ảnh đại diện">
                                        <div class="info">
                                            <div class="name">${List.getName()}</div>
                                            <div class="position">Role: ${List.getStr()}</div>
                                            <div class="position">Product repair: ${List.getCount()}</div>
                                            <form action="reportStaffController" method="post">
                                                <input type="hidden" name="staffID" value="${List.getStaffID()}">
                                                <input type="hidden" name="action" value="Staffinfo">
                                                <button class="btn btn-primary" type="submit" style="width: 100px">All info</button>
                                            </form>
                                        </div>
                                    </div>                                                                  
                                 </c:if>
                                
                            </c:forEach>
                            
                        </div>

                        <h2>
                            Free time 
                            <c:if test="${fn:length(listRelax) > 6}">
                                <a href="reportStaffController?action=viewAllInactive" class="arrow-link">»</a>
                            </c:if>
                        </h2>
                        <div class="container">
                            <c:forEach var="List" items="${listRelax}" varStatus="status">
                                <c:if test="${status.index < 6}">
                                    <form action="StaffController" method="post">
                                        <div class="profile-card">
                                            <input type="hidden" name="staffid" value="${List.getStaffID()}">
                                            <input type="hidden" name="action" value="Infor">
                                            <img src="${List.getImage()}" alt="Ảnh đại diện">
                                            <div class="info">
                                                <div class="name">${List.getName()}</div>
                                                <div class="position">Role: ${List.getStr()}</div>
                                                <button class="btn btn-primary" type="submit" style="width: 60px; margin-left: 50px ">Info</button>
                                            </div>
                                        </div>
                                    </form>
                                </c:if>
                            </c:forEach>
                        </div>
                        
                    </table>


                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

            <script src="js/app.js"></script>

    </body>
</html>
