<%-- 
    Document   : ComponentWarehouse
    Created on : Jan 17, 2025, 8:24:34 PM
    Author     : ADMIN
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

        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

        <link rel="canonical" href="https://demo-basic.adminkit.io/" />

        <title>Component Warehouse</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            .btn-pagination {
                width: 40px;
                height: 40px;
                display: flex;
                align-items: center;
                justify-content: center;
                text-align: center;
            }

        </style>
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="../includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="../includes/navbar-top.jsp" />
                <main class="content">
                    <h2>Component Warehouse</h2>
                    <form action="ComponentWarehouse/Add" method="POST" enctype="multipart/form-data" style="display: inline;">
                        <button type="submit" class="btn btn-success"><i class="fas fa-add"></i> Add Component</button>
                    </form>                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Name</th>
                                <th>Quantity</th>
                                <th>Unit Price</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <!--                        varStatus để lấy trạng thái của vòng lặp-->
                        <c:forEach var="component" items="${components}" varStatus="status">
                            <tr class="${status.index % 2 == 0 ? 'table-primary' : ''}">
                                <td>${status.index+1+(currentPage-1)*size}</td>
                                <td>${component.componentName}</td>
                                <td>${component.quantity}</td>
                                <td>${component.price}</td>
                                <td class="table-action">
                                    <a href="ComponentWarehouse/Detail?ID=${component.componentID}"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-2 align-middle"><path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path></svg></a>
                                    <a data-bs-toggle="modal" data-bs-target="#centeredModalPrimary"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash align-middle"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg></a>
                                </td>
                            </tr>
                            <div class="modal fade" id="centeredModalPrimary" tabindex="-1" style="display: none;" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title"></h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body m-3">
                                            <p class="mb-0">If delete this component, this component will disappear in the product has it. Still want to delete?</p>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            <a href="ComponentWarehouse/Delete?ID=${component.componentID}" type="button" class="btn btn-primary">Delete</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <tbody>

                        </tbody>
                    </table

                    <!-- Phân trang -->
                    <div class="text-center">
                        <div class="btn-group me-2" role="group" style="margin-top:1rem" aria-label="First group">
                            <!-- Nút "Đầu" -->
                            <a href="?page=1" style="margin-right:5px" class="btn btn-primary ${currentPage == 1 ? 'disabled' : ''} btn-pagination"><<</a>

                            <!-- Nút "Trước" -->
                            <a href="?page=${currentPage - 1}" class="btn btn-primary ${currentPage == 1 ? 'disabled' : ''} btn-pagination">&lt;</a>

                            <!-- Các số trang -->
                            <c:set var="totalPagesToShow" value="6" />
                            <c:set var="startPage" value="${currentPage - (totalPagesToShow / 2)}" />
                            <c:set var="endPage" value="${startPage + totalPagesToShow - 1}" />

                            <!-- Điều chỉnh startPage và endPage nếu cần -->
                            <c:if test="${startPage < 1}">
                                <c:set var="startPage" value="1" />
                                <c:set var="endPage" value="${totalPagesToShow}" />
                            </c:if>
                            <c:if test="${endPage > totalPages}">
                                <c:set var="endPage" value="${totalPages}" />
                                <c:set var="startPage" value="${endPage - totalPagesToShow + 1}" />
                            </c:if>

                            <c:forEach var="i" begin="${startPage}" end="${endPage}">
                                <a href="?page=${i}" class="btn btn-primary ${i == currentPage ? 'active' : ''} btn-pagination">${i}</a>
                            </c:forEach>

                            <!-- Nút "Sau" -->
                            <a href="?page=${currentPage + 1}" class="btn btn-primary ${currentPage == totalPages ? 'disabled' : ''} btn-pagination">&gt;</a>

                            <!-- Nút "Cuối" -->
                            <a href="?page=${totalPages}" style="margin-left:5px" class="btn btn-primary ${currentPage == totalPages ? 'disabled' : ''} btn-pagination">>></a>
                        </div>

                        <!-- Ô nhập trang -->

                        <div class="text-center"style="margin-top: 1rem;">
                            <form class="row align-items-center justify-content-center" action="" method="get">
                                <input type="number" style="width:4.5rem; padding:.3rem .5rem" class="form-control mb-2 me-sm-2" id="inlineFormInputName2" name="page" min="1" max="${totalPages}" placeholder="Page">
                                <button type="submit" style="width:3rem" class="btn btn-primary mb-2">Go</button>
                            </form>
                        </div>
                        
                    </div>

                </main>
                <jsp:include page="../includes/footer.jsp" />

            </div>

        </div>

        <script src="js/app.js"></script>

    </body>

</html>
