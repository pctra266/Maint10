
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
                    <c:set var="viewProductFrom" value="ComponentWarehouse/Detail?ID=${component.componentID}" scope="session" />

                    <c:if test="${sessionScope.detailComponentFrom eq 'search'}">
                        <a href="ComponentWarehouse/Search" class="btn btn-primary  d-flex align-items-center justify-content-center" style="transform:translate(-30%,-60%); height: 2.5rem; width: 5.2rem"><i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span> </a>                        
                    </c:if>
                    <c:if test="${sessionScope.detailComponentFrom ne 'search'}">
                        <a href="ComponentWarehouse" class="btn btn-primary  d-flex align-items-center justify-content-center" style="transform:translate(-30%,-60%); height: 2.5rem; width: 5.2rem"><i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span> </a>                        
                    </c:if>

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
                    <c:if test="${not empty pictureAlert}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${pictureAlert}</strong>
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
                                    <input type="text" class="form-control format-int" name="Quantity"id="Quantity" value="${component.quantity}" min="0" required>
                                </div>
                                <div class="col-md-3">
                                    <label for="Price" class="form-label">Price</label>
                                    <input type="text" class="form-control format-float" name="Price" id="Price" value="${component.price}" step="0.01" min="0" required>
                                </div> 
                                <div class="col-md-10">
                                    <label for="validationDefault06" class="form-label">Products</label>
                                    <c:if test="${not empty remove}">
                                        <div class="alert alert-success alert-dismissible" role="alert">
                                            <div class="alert-message text-center">
                                                <strong>${remove}</strong>
                                            </div>
                                        </div>

                                    </c:if>
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
                                                        ${p.code}
                                                    </td>
                                                    <td>
                                                        <a href="updateproduct?id=${p.productId}">
                                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-eye align-middle"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle></svg>
                                                        </a>
                                                        <a data-bs-toggle="modal" data-bs-target="#centeredModalPrimary_${p.productId}">
                                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash align-middle">
                                                            <polyline points="3 6 5 6 21 6"></polyline>
                                                            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                                                            </svg>
                                                        </a>
                                                        <div class="modal fade" id="centeredModalPrimary_${p.productId}" tabindex="-1" aria-hidden="true">
                                                            <div class="modal-dialog modal-dialog-centered" role="document">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h5 class="modal-title">Delete Confirmation</h5>
                                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                    </div>
                                                                    <div class="modal-body m-3">
                                                                        <p class="mb-0">Confirm your action. Really want to delete?</p>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                                        <a href="ComponentWarehouse/Detail?ID=${component.componentID}&product=${p.productId}" class="btn btn-primary">Delete</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
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
        <script src="js/format-input.js"></script>
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
    </body>

</html>
