<%-- 
    Document   : Stafflist
    Created on : Mar 14, 2025, 6:25:39 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                transform: scale(1.05); /* Phóng to nhẹ khi hover */
            }

            /* 🌟 Danh sách nhân viên */
            .container {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 20px;
            }

            /* 🌟 Hồ sơ nhân viên */
            .profile-card {
                display: flex;
                align-items: center;
                background: white;
                border-left: 6px solid #007bff;
                padding: 15px;
                border-radius: 12px;
                box-shadow: 2px 4px 10px rgba(0, 0, 0, 0.12);
                text-align: center;
                width: 300px;
                transition: all 0.3s ease-in-out; /* Hiệu ứng mượt mà */
            }

            /* 🌟 Hiệu ứng khi hover */
            .profile-card:hover {
                transform: translateY(-5px); /* Nâng thẻ lên nhẹ */
                box-shadow: 4px 8px 14px rgba(0, 0, 0, 0.18);
            }

            /* 🌟 Hình ảnh */
            .profile-card img {
                width: 65px;
                height: 65px;
                border-radius: 50%;
                object-fit: cover;
                margin-right: 15px;
                border: 2px solid #ddd;
                transition: transform 0.3s ease-in-out;
            }

            /* 🌟 Hiệu ứng khi hover vào ảnh */
            .profile-card:hover img {
                transform: scale(1.1); /* Phóng to nhẹ ảnh */
            }

            /* 🌟 Thông tin nhân viên */
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
                        <div class="add__signin-next">                        
                            <form action="reportStaffController" method="get">
                                <button class="btn btn-primary" type="submit" style="width: 100px">Back</button>
                            </form>                        
                        </div>
                    <table class="table table-hover my-0">
                        <c:if test="${action eq 'viewAllActive'}">
                            <div class="container">                            
                                <c:forEach var="List" items="${list}" >

                                        <div class="profile-card">
                                            <img src="${List.getImage()}" alt="Ảnh đại diện">
                                            <div class="info">
                                                <div class="name">${List.getName()}</div>
                                                <div class="position">Role: ${List.getStr()}</div>
                                                <div class="position">Product repair: ${List.getCount()}</div>
                                                <form action="reportStaffController" method="post">
                                                    <input type="hidden" name="action" value="Allinfor">
                                                    <input type="hidden" name="staffid" value="${List.getStaffID()}">
                                                    <button class="btn btn-primary" type="submit" style="width: 100px">All info</button>
                                                </form>
                                            </div>
                                        </div>                                                                                                  
                                </c:forEach>

                            </div>
                        </c:if>   
                        
                        <c:if test="${action eq 'viewAllInactive'}">
                            <div class="container">
                                <c:forEach var="List" items="${listRelax}">
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
                                </c:forEach>
                            </div>
                        </c:if>   

                        
                    </table>


                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

            <script src="js/app.js"></script>

    </body>
</html>
