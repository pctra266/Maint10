
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

        <title>Warranty Card</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="../includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="../includes/navbar-top.jsp" />
                <main class="content">
                    <a href="WarrantyCard" class="btn btn-primary  d-flex align-items-center justify-content-center" style="transform:translate(-30%,-60%); height: 2.5rem; width: 5.2rem"><i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span> </a>
                    <h2>Create Warranty Card</h2>
                    <!-- Hiển thị cảnh báo lỗi -->
                    <c:if test="${not empty nameAlert}">
                        <div class="col-md-10 alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${nameAlert}</strong>
                            </div>
                        </div>
                    </c:if>

                    <div class="d-flex justify-content-center align-items-center vh-50">
                        <form action="WarrantyCard/Add" method="GET" class="w-50">
                            <div class="row d-flex align-items-center ">
                                <!-- Ô input -->
                                <div class="col bg-primary bg-opacity-75 p-3 rounded-pill shadow">
                                    <input type="text" class="form-control w-100" name="ProductCode" id="ProductCode" value="${ProductCode}" required>
                                </div>
                                <!-- Nút tìm kiếm -->
                                <div class="col-auto">
                                    <button type="submit" class="btn col-auto bg-primary bg-opacity-75 p-3 rounded-circle shadow  h-100">
                                       <i class="align-middle fas fa-fw fa-search text-white text-opacity-75"></i>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>




                </main>

                <jsp:include page="../includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>
    </body>

</html>
