<%-- 
    Document   : ReportStaffInfor
    Created on : Mar 13, 2025, 10:32:08 AM
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
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 0;
            }
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
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            .table th, .table td {
                padding: 10px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            .table tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            .alert {
                padding: 10px;
                background-color: #009926; 
                color: white;
                margin-bottom: 15px;
                border-radius: 5px;
                display: block; 
            }
            .alert1 {
                padding: 10px;
                background-color: red; 
                color: white;
                margin-bottom: 15px;
                border-radius: 5px;
                display: block; 
            }
            .btn-primary {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 8px 16px;
                border-radius: 4px;
                cursor: pointer;
                transition: background 0.3s;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }
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
            /* Tổng thể form */
            .form-container {
                background: #fff;
                padding: 20px;
                border-radius: 12px;
                box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
                margin: 15px 0;
            }

            /* Input và Select */
            .input-group, .form-select {
                transition: all 0.3s ease-in-out;
                border-radius: 8px;
            }

            .form-select:focus, 
            .form-control:focus {
                box-shadow: 0 0 8px rgba(0, 123, 255, 0.5);
                border-color: #007bff;
            }

            /* Nút Search */
            button.btn-primary {
                background-color: #007bff;
                border-color: #007bff;
                transition: all 0.3s ease;
                border-radius: 8px;
            }

            button.btn-primary:hover {
                background-color: #0056b3;
                border-color: #004085;
                transform: scale(1.05);
            }

            /* Chỉnh khoảng cách giữa các phần tử */
            .me-2 {
                margin-right: 10px;
            }

            .ms-2 {
                margin-left: 10px;
            }

            .shadow-sm {
                box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
            }
            .upload-box { 
                border: 1px dashed #007bff;
                padding: 10px 5px;
                margin: 15px;
                display: inline-block; 
                background: white; width: 100px;
                border-radius: 5px;
                cursor: pointer;
            }
            .upload-box:hover 
            { 
                color: #007bff;
                background: #e9f5ff;
            }
            .upload-box p { margin: 0; color: #007bff; font-weight: bold; }
            input[type="file"] { display: none; }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center ">Staff Repair List</h1>   
                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Role</th>
                                <th>Gender</th>
                                <th>Action</th>
                                <th>ActionDate</th>                               
                                <th>WarrantyCardCode</th>
                                <c:set var="displayDetail" value="false" />
                                <c:forEach var="item" items="${info}">
                                    <c:if test="${staffProfile.staffID eq item.staffID}">
                                        <c:set var="displayDetail" value="true" />
                                    </c:if>
                                </c:forEach>

                                <c:if test="${displayDetail}">
                                    <th>Detail</th>
                                </c:if>
                            </tr>   
                        </thead>       
                        <tbody>
                            <c:forEach var="List" items="${info}">
                                <tr>
                                    <td>${List.getName()}</td>                                                                      
                                    <td>${List.getRoleName()}</td>
                                    <td>${List.getGender()}</td>
                                    <td>${List.getAction()}</td>
                                    <td>${List.getActionDate()}</td>
                                    <td>${List.getWarrantyCardCode()}</td>
                                    <c:if test="${staffProfile.staffID eq List.staffID}">
                                        <td>
                                            <a href="WarrantyCard" 
                                               class="btn btn-success btn-sm text-white shadow">
                                                <i class="fas fa-edit"></i> More
                                            </a>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <jsp:include page="/includes/pagination.jsp" />           
                     
                
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>
</html>
