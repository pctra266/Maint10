<%-- 
    Document   : AddCustomer
    Created on : Feb 3, 2025, 4:23:28 PM
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Responsive Admin &amp; Dashboard Template based on Bootstrap 5">
    <meta name="author" content="AdminKit">
    <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">
    <base href="${pageContext.request.contextPath}/">

    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="shortcut icon" href="icons/icon-48x48.png" />
    <link rel="canonical" href="https://demo-basic.adminkit.io/" />

    <title>Customer</title>

    <link href="css/light.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
</head>

<body>
    <div class="wrapper">
        <jsp:include page="/includes/navbar-left.jsp" />
        <div class="main">
            <jsp:include page="/includes/navbar-top.jsp" />
            <main class="content">
                <h2>Customer Add</h2>
                <form class="row" action="customer?action=add" method="POST" enctype="multipart/form-data" >
                    <div class="col-md-8">
                        <div class="col-md-12 row g-3">
                            <div class="col-md-10">
                                <label for="username" class="form-label">Username</label>
                                <input type="text" class="form-control" name="username" id="username" value="${username}" required>
                            </div>
                            <div class="col-md-10">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" class="form-control" name="password" id="password" required>
                            </div>
                            <div class="col-md-10">
                                <label for="customerName" class="form-label">Customer Name</label>
                                <input type="text" class="form-control" name="name" id="customerName" value="${customerName}" required>
                            </div>
                            <div class="col-md-10">
                                <label for="customerEmail" class="form-label">Email</label>
                                <input type="email" class="form-control" name="email" id="customerEmail" value="${customerEmail}" required>
                            </div>
                            <div class="col-md-10">
                                <label for="customerPhone" class="form-label">Phone</label>
                                <input type="tel" class="form-control" name="phone" id="customerPhone" value="${customerPhone}" required>
                            </div>
                            <div class="col-md-10">
                                <label for="customerAddress" class="form-label">Address</label>
                                <input type="text" class="form-control" name="address" id="customerAddress" value="${customerAddress}" required>
                            </div>
                            <div class="col-md-10">
                                <label for="customerImage" class="form-label">Image</label>
                                <input type="file" class="form-control" name="image" id="customerImage" >
                            </div>
                            <div class="col-md-12" style="margin-bottom: 1rem">
                                <button class="btn btn-success" type="submit">Add</button>
                            </div>
                        </div>

                     
                        <c:if test="${not empty error}">
                            <div class="col-md-10 alert alert-danger alert-dismissible" role="alert">
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                <div class="alert-message">
                                    <strong>${error}</strong>
                                </div>
                            </div>
                        </c:if>
                    
                    </div>

                   
                </form>

            </main>

            <jsp:include page="/includes/footer.jsp" />
        </div>
    </div>

    <script src="js/app.js"></script>
    
    
</body>

</html>
