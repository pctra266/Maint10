
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

        <title>Component Warehouse</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="../../includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="../../includes/navbar-top.jsp" />
                <main class="content">

                    <a href="ComponentWarehouse" class="btn btn-primary  d-flex align-items-center justify-content-center" style="transform:translate(-30%,-60%); height: 2.5rem; width: 5.2rem"><i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span> </a>
                    <h2>Component Add</h2>
                    <!-- Hiển thị cảnh báo lỗi -->
                    <c:if test="${not empty nameAlert}">
                        <div class="col-md-10 alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${nameAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty pictureAlert}">
                        <div class="col-md-10 alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${pictureAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty addAlert0}">
                        <div class="col-md-10 alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${addAlert0}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty codeAlert}">
                        <div class="col-md-10 alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${codeAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty quantityAlert}">
                        <div class="col-md-10 alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${quantityAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty priceAlert}">
                        <div class="col-md-10 alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${priceAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <form class="row" action="ComponentWarehouse/Add" method="POST" enctype="multipart/form-data">
                        <div class="col-md-8">
                            <div class="col-md-12 row g-3">
                                <div class="col-md-10">
                                    <label for="Name" class="form-label">Name</label>
                                    <input type="text" class="form-control" name="Name" id="Name" value="${name}" required>
                                </div>
                                <div class="col-md-10">
                                    <label for="Code" class="form-label">Code</label>
                                    <input type="text" class="form-control" name="Code"id="Code" value="${code}" required>
                                </div>
                                <div class="col-md-3">
                                    <label for="Type" class="form-label">Type</label>
                                    <select name="Type" class="form-select form-select-md" id="Type" required style="flex: 1 1 auto;">
                                        <option value disabled selected>Choose</option>
                                        <c:forEach var="t" items="${typeList}">
                                            <option value="${t}" ${t eq type ? "selected" : ""}>${t}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label for="Brand" class="form-label">Brand</label>
                                    <select name="Brand" class="form-select form-select-md" id="Brand" required style="flex: 1 1 auto;">
                                        <option value disabled selected>Choose</option>
                                        <c:forEach var="b" items="${brandList}">
                                            <option value="${b}" ${b eq brand ? "selected" : ""}>${b}</option>
                                        </c:forEach>
                                    </select>                              
                                </div>

                                <div class="col-md-6">
                                </div>
                                <div class="col-md-3">
                                    <label for="Quantity" class="form-label">Quantity</label>
                                    <input type="text" class="form-control format-int" name="Quantity" id="Quantity" value="${quantity}" min="0" required>
                                </div>
                                <div class="col-md-3">
                                    <label for="Price" class="form-label">Price</label>
                                    <input type="text" class="form-control format-float" name="Price" id="Price" value="${price}" step="0.01" min="0" required>
                                </div>
                                <div class="col-md-12" style="margin-bottom: 1rem">
                                    <button class="btn btn-success" type="submit">Add</button>
                                </div>
                            </div>


                        </div>

                        <!-- Upload file ảnh -->
                        <div class="col-md-4 row">
                            <img src="${component.image}" id="currentImage" alt="${component.componentName}" style="max-width: 100%; height: auto;">
                            <input type="file" name="newImage" id="newImage" accept="image/*" onchange="previewImage(event)">
                        </div>
                    </form>

                </main>

                <jsp:include page="../../includes/footer.jsp" />
            </div>

        </div>

        <script src="js/app.js"></script>
        <script src="js/format-input.js"></script>


        <!--        // Hàm để xem trước ảnh khi người dùng chọn tệp mới-->
        <script>
                                function previewImage(event) {
                                    const reader = new FileReader();
                                    reader.onload = function () {
                                        const output = document.getElementById('currentImage');
                                        output.src = reader.result;
                                    };
                                    reader.readAsDataURL(event.target.files[0]);
                                }
        </script>
    </script>
</body>

</html>
