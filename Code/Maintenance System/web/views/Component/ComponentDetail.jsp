
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

                    <h2>Component Detail</h2>
  <!--                                Alert khi du lieu truyen sang sever sai-->
                                <c:if test="${not empty codeAlert}">
                                    <div class="alert alert-danger alert-dismissible" role="alert">
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                        <div class="alert-message">
                                            <strong>${codeAlert}</strong>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${not empty nameAlert}">
                                    <div class="alert alert-danger alert-dismissible" role="alert">
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                        <div class="alert-message">
                                            <strong>${nameAlert}</strong>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${not empty quantityAlert}">
                                    <div class="alert alert-danger alert-dismissible" role="alert">
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                        <div class="alert-message">
                                            <strong>${quantityAlert}</strong>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${not empty priceAlert}">
                                    <div class="alert alert-danger alert-dismissible" role="alert">
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                        <div class="alert-message">
                                            <strong>${priceAlert}</strong>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${not empty addAlert1}">
                                    <div class="alert alert-success alert-dismissible" role="alert">
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                        <div class="alert-message">
                                            <strong>${addAlert1}</strong>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${not empty updateAlert1}">
                                    <div class="alert alert-success alert-dismissible" role="alert">
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                        <div class="alert-message">
                                            <strong>${updateAlert1}</strong>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${not empty updateAlert0}">
                                    <div class="alert alert-danger alert-dismissible" role="alert">
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                        <div class="alert-message">
                                            <strong>${updateAlert0}</strong>
                                        </div>
                                    </div>
                                </c:if>
                    <form class="row"action="ComponentWarehouse/Edit"  method="POST" enctype="multipart/form-data">
                        <div class="col-md-8">
                            <div class="col-md-12 row g-3">
                                <input type="hidden" class="form-control" name="ID" id="validationDefault01" value="${component.componentID}" required>

                                <div class="col-md-10">
                                    <label for="Name" class="form-label">Name</label>
                                    <input type="text" class="form-control" name="Name"id="Name" value="${component.componentName}" required>
                                </div>
                                <div class="col-md-10">
                                    <label for="Code" class="form-label">Code</label>
                                    <input type="text" class="form-control" name="Code"id="Code" value="${component.componentCode}" required>
                                </div>
                                 <div class="col-md-3">
                                    <label for="Type" class="form-label">Type</label>
                                    <select name="Type" class="form-select form-select-md" id="Type" required style="flex: 1 1 auto;">
                                        <option value disabled selected>Choose</option>
                                        <c:forEach var="t" items="${typeList}">
                                            <option value="${t}" ${t eq component.type ? "selected" : ""}>${t}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label for="Brand" class="form-label">Brand</label>
                                    <select name="Brand" class="form-select form-select-md" id="Brand" required style="flex: 1 1 auto;">
                                        <option value disabled selected>Choose</option>
                                        <c:forEach var="b" items="${brandList}">
                                            <option value="${b}" ${b eq component.brand ? "selected" : ""}>${b}</option>
                                        </c:forEach>
                                    </select>                              
                                </div>
                                <div class="col-md-6"></div>
                                <div class="col-md-3">
                                    <label for="Quantity" class="form-label">Quantity</label>
                                    <input type="number" class="form-control" name="Quantity"id="Quantity" value="${component.quantity}" min="0" required>
                                </div>
                                <div class="col-md-3">
                                    <label for="Price" class="form-label">Price</label>
                                    <input type="number" class="form-control" name="Price" id="Price" value="${component.price}" step="0.01" min="0" required>
                                </div> 
                                <div class="col-md-10">
                                    <label for="validationDefault06" class="form-label">Products</label>
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th style="width:45%">Name</th>
                                                <th style="width:40%">Code</th>
                                                    <th class="d-none d-md-table-cell" style="width:25%">Action</th>
                                                </tr>
                                            </thead>
                                        <c:forEach items="${list}" var="p">
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        ${p.productName}
                                                    </td>
                                                    <td>
                                                        ProductCode
                                                    </td>
                                                    <td>
                                                        Action
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </c:forEach>
                                    </table>
                                </div> 
                                <div class="col-md-12">
                                    <button class="btn btn-primary" type="submit">Save</button>
                                </div>
                              
                            </div>
                        </div>

                        <div class="col-md-4 row">
                            <img src="${component.image}" id="currentImage"alt="${component.componentName}" style="max-width: 100%; height: auto;">
                            <input type="file" name="newImage" id="newImage" accept="image/*" onchange="previewImage(event)">
                        </div>

                    </form>
                </main>

                <jsp:include page="../../includes/footer.jsp" />
            </div>

        </div>

        <script src="js/app.js"></script>
        <script>
                                // Hàm để xem trước ảnh khi người dùng chọn tệp mới
                                function previewImage(event) {
                                    const file = event.target.files[0];
                                    if (file) {
                                        const reader = new FileReader();
                                        reader.onload = function (e) {
                                            const imgElement = document.getElementById('currentImage');
                                            imgElement.src = e.target.result;
                                        };
                                        reader.readAsDataURL(file);
                                    }
                                }
        </script>
    </body>

</html>
