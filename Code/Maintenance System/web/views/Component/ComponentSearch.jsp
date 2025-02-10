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
        <base href="${pageContext.request.contextPath}/">

        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

        <link rel="canonical" href="https://demo-basic.adminkit.io/" />

        <title>Component Warehouse</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="css/range-slider.css" rel="stylesheet">
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
            .btn-sort {
                background: none;
                border: none;
                padding: 0;
                cursor: pointer;
            }

        </style>
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="../../includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="../../includes/navbar-top.jsp" />
                <main class="content">
                    <a href="ComponentWarehouse" class="btn btn-primary  d-flex align-items-center justify-content-center" style="transform:translate(-30%,-60%); height: 2.5rem; width: 5.2rem"><i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span> </a>
                    <h2>Advanced Search</h2>
                    <form action="ComponentWarehouse/Search" method="get" class="row align-items-center">
                        <input type="hidden" name="page" value="${currentPage}" />
                        <input type="hidden" name="sort" value="${sort}" />
                        <input type="hidden" name="order" value="${order}" />
                        <div class="col-md-3 input-group d-flex justify-content-end">
                            <input type="search" style="flex: 1 1 auto" class="form-control form-control-md" placeholder="Code" name="searchCode" value="${searchCode}" />
                            <select name="searchType" class="form-select form-select-md" style="flex: 1 1 auto;">
                                <option value="">Type</option>
                                <c:forEach var="type" items="${typeList}">
                                    <option value="${type}" ${searchType eq type ? "selected" : ""}>${type}</option>
                                </c:forEach>
                            </select>

                            <!-- Thêm trường chọn cho Brand -->
                            <select name="searchBrand" class="form-select form-select-md" style="flex: 1 1 auto;">
                                <option value="">Brand</option>
                                <c:forEach var="brand" items="${brandList}">
                                    <option value="${brand}" ${searchBrand eq brand ? "selected" : ""}>${brand}</option>
                                </c:forEach>
                            </select>
                            <input type="search" style="flex: 1 1 auto" class="form-control form-control-md" placeholder="Name" name="searchName" value="${searchName}" />  
                            <button type="submit" class="btn btn-primary">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search align-middle"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
                            </button>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="slider-container" data-type="quantity">
                                    <div class="price-input">
                                        <div class="field">
                                            <span>Quantity</span>
                                            <input type="text" class="input-min format-int" name="searchQuantityMin" value="${searchQuantityMin}" step="1">
                                        </div>
                                        <div class="separator">-</div>
                                        <div class="field">
                                            <input type="text" class="input-max format-int" name="searchQuantityMax" value="${searchQuantityMax}" step="1">
                                        </div>
                                    </div>
                                    <div class="slider">
                                        <div class="progress"></div>
                                    </div>
                                    <div class="range-input">
                                        <input type="range" class="range-min" min="0" max="${quantityMax}" value="${searchQuantityMin}" step="1">
                                        <input type="range" class="range-max" min="0" max="${quantityMax}" value="${searchQuantityMax}" step="1">
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="slider-container" data-type="price">
                                    <div class="price-input">
                                        <div class="field">
                                            <span>Price</span>
                                            <input type="text" class="input-min format-float" name="searchPriceMin" value="${searchPriceMin}" step="0.01">
                                        </div>
                                        <div class="separator">-</div>
                                        <div class="field">
                                            <input type="text" class="input-max format-float" name="searchPriceMax" value="${searchPriceMax==priceMax?searchPriceMax+0.01:searchPriceMax}" step="0.01" >
                                        </div>
                                    </div>
                                    <div class="slider">
                                        <div class="progress"></div>
                                    </div>
                                    <div class="range-input">
                                        <input type="range" class="range-min" min="0" max="${priceMax+0.01}" value="${searchPriceMin}" step="0.01">
                                        <input type="range" class="range-max" min="0" max="${priceMax+0.01}" value="${searchPriceMax==priceMax?searchPriceMax+0.01:searchPriceMax}" step="0.01">
                                    </div>
                                </div>
                            </div>
                        </div>




                        <div class="col-sm-6 col-md-6 mt-2">
                            <label>Show 
                                <select name="page-size" class="form-select form-select-sm d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                    <option value="5" ${size==5?"selected":""}>5</option>
                                    <option value="7" ${size==7?"selected":""}>7</option>
                                    <option value="10" ${size==10?"selected":""}>10</option>
                                    <option value="15" ${size==15?"selected":""}>15</option>
                                </select> 
                                entries
                            </label>
                        </div>
                    </form>
                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th style="width:3%">#</th>
                                <th style="width:13%">
                                    <form action="ComponentWarehouse/Search" method="get">
                                        <input type="hidden" name="page" value="${currentPage}" />
                                        <input type="hidden" name="page-size" value="${size}" />
                                        <input type="hidden" name="search" value="${search}" />
                                        <input type="hidden" name="sort" value="code" />
                                        <input type="hidden" name="order" value="${sort eq 'code' and order eq 'asc' ? 'desc' : 'asc'}" />
                                        <input type="hidden" name="searchName" value="${searchName}" />
                                        <input type="hidden" name="searchCode" value="${searchCode}" />
                                        <input type="hidden" name="searchType" value="${searchType}" />
                                        <input type="hidden" name="searchBrand" value="${searchBrand}" />
                                        <input type="hidden" name="searchQuantityMin" value="${searchQuantityMin}" />
                                        <input type="hidden" name="searchQuantityMax" value="${searchQuantityMax}" />
                                        <input type="hidden" name="searchPriceMin" value="${searchPriceMin}" />
                                        <input type="hidden" name="searchPriceMax" value="${searchPriceMax}" />
                                        <button type="submit" class="btn-sort">
                                            <i class="align-middle fas fa-fw
                                               ${sort eq 'code' ? (order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>
                                        Code
                                    </form>
                                </th>
                                <th>Type</th>
                                <th>Brand</th>
                                <th style="width:30%">
                                    <form action="ComponentWarehouse/Search" method="get">
                                        <input type="hidden" name="page" value="${currentPage}" />
                                        <input type="hidden" name="page-size" value="${size}" />
                                        <input type="hidden" name="sort" value="name" />
                                        <input type="hidden" name="order" value="${sort eq 'name' and order eq 'asc' ? 'desc' : 'asc'}" />
                                        <input type="hidden" name="searchName" value="${searchName}" />
                                        <input type="hidden" name="searchCode" value="${searchCode}" />
                                        <input type="hidden" name="searchType" value="${searchType}" />
                                        <input type="hidden" name="searchBrand" value="${searchBrand}" />
                                        <input type="hidden" name="searchQuantityMin" value="${searchQuantityMin}" />
                                        <input type="hidden" name="searchQuantityMax" value="${searchQuantityMax}" />
                                        <input type="hidden" name="searchPriceMin" value="${searchPriceMin}" />
                                        <input type="hidden" name="searchPriceMax" value="${searchPriceMax}" />
                                        <button type="submit" class="btn-sort">
                                            <i class="align-middle fas fa-fw
                                               ${sort eq 'name' ? (order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>
                                        Name
                                    </form>
                                </th>

                                <th style="width:15%">
                                    <form action="ComponentWarehouse/Search" method="get">
                                        <input type="hidden" name="page" value="${currentPage}" />
                                        <input type="hidden" name="page-size" value="${size}" />
                                        <input type="hidden" name="sort" value="quantity" />
                                        <input type="hidden" name="order" value="${sort eq 'quantity' and order eq 'asc' ? 'desc' : 'asc'}" />
                                        <input type="hidden" name="searchName" value="${searchName}" />
                                        <input type="hidden" name="searchCode" value="${searchCode}" />
                                        <input type="hidden" name="searchType" value="${searchType}" />
                                        <input type="hidden" name="searchBrand" value="${searchBrand}" />
                                        <input type="hidden" name="searchQuantityMin" value="${searchQuantityMin}" />
                                        <input type="hidden" name="searchQuantityMax" value="${searchQuantityMax}" />
                                        <input type="hidden" name="searchPriceMin" value="${searchPriceMin}" />
                                        <input type="hidden" name="searchPriceMax" value="${searchPriceMax}" />
                                        <button type="submit" class="btn-sort">
                                            <i class="align-middle fas fa-fw
                                               ${sort eq 'quantity' ? (order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>
                                        Quantity
                                    </form>
                                </th>

                                <th style="width:15%">
                                    <form action="ComponentWarehouse/Search" method="get">
                                        <input type="hidden" name="page" value="${currentPage}" />
                                        <input type="hidden" name="page-size" value="${size}" />
                                        <input type="hidden" name="sort" value="price" />
                                        <input type="hidden" name="order" value="${sort eq 'price' and order eq 'asc' ? 'desc' : 'asc'}" />
                                        <input type="hidden" name="searchName" value="${searchName}" />
                                        <input type="hidden" name="searchCode" value="${searchCode}" />
                                        <input type="hidden" name="searchType" value="${searchType}" />
                                        <input type="hidden" name="searchBrand" value="${searchBrand}" />
                                        <input type="hidden" name="searchQuantityMin" value="${searchQuantityMin}" />
                                        <input type="hidden" name="searchQuantityMax" value="${searchQuantityMax}" />
                                        <input type="hidden" name="searchPriceMin" value="${searchPriceMin}" />
                                        <input type="hidden" name="searchPriceMax" value="${searchPriceMax}" />
                                        <button type="submit" class="btn-sort">
                                            <i class="align-middle fas fa-fw
                                               ${sort eq 'price' ? (order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>
                                        Unit Price
                                    </form>
                                </th>


                                <th style="width:8%">Action<a href="ComponentWarehouse/Search?page=${currentPage}&page-size=${size}"><i class="fa fa-refresh ms-2"></i></a></th>
                            </tr>
                        </thead>
                        <!--                        varStatus để lấy trạng thái của vòng lặp-->
                        <c:forEach var="component" items="${components}" varStatus="status">
                            <tr class="${status.index % 2 == 0 ? 'table-primary' : ''}">
                                <td>${status.index+1+(currentPage-1)*size}</td>
                                <td>${component.componentCode}</td>
                                <td>${component.type}</td>
                                <td>${component.brand}</td>
                                <td>${component.componentName}</td>
                                <td>${component.quantity}</td>
                                <td>${component.price}</td>
                                <td class="table-action">
                                    <a href="ComponentWarehouse/Detail?ID=${component.componentID}"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-2 align-middle"><path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path></svg></a>
                                    <a data-bs-toggle="modal" data-bs-target="#centeredModalPrimary_${component.componentID}"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash align-middle"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg></a>
                                </td>
                            </tr>
                            <div class="modal fade" id="centeredModalPrimary_${component.componentID}" tabindex="-1" style="display: none;" aria-hidden="true">
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
                                            <a href="ComponentWarehouse/Delete?ID=${component.componentID}&page=${currentPage}&page-size=${size}&searchCode=${searchCode}&searchName=${searchName}&sort=${sort}&order=${order}&searchType=${searchType}&searchBrand=${searchBrand}&searchQuantityMin=${searchQuantityMin}&searchQuantityMax=${searchQuantityMax}&searchPriceMin=${searchPriceMin}&searchPriceMax=${searchPriceMax}" type="button" class="btn btn-primary">Delete</a>
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
                            <c:if test="${page > totalPages}">
                                <c:set var="page" value="totalPages" />
                            </c:if>
                            <!-- Nút "Đầu" -->
                            <a href="ComponentWarehouse/Search?page=1&page-size=${size}&searchCode=${searchCode}&searchName=${searchName}&sort=${sort}&order=${order}&searchType=${searchType}&searchBrand=${searchBrand}&searchQuantityMin=${searchQuantityMin}&searchQuantityMax=${searchQuantityMax}&searchPriceMin=${searchPriceMin}&searchPriceMax=${searchPriceMax}" style="margin-right:5px" class="btn btn-primary ${currentPage <= 1 ? 'disabled' : ''} btn-pagination">&lt;&lt;</a>

                            <!-- Nút "Trước" -->
                            <a href="ComponentWarehouse/Search?page=${currentPage - 1}&page-size=${size}&searchCode=${searchCode}&searchName=${searchName}&sort=${sort}&order=${order}&searchType=${searchType}&searchBrand=${searchBrand}&searchQuantityMin=${searchQuantityMin}&searchQuantityMax=${searchQuantityMax}&searchPriceMin=${searchPriceMin}&searchPriceMax=${searchPriceMax}" class="btn btn-primary ${currentPage <= 1 ? 'disabled' : ''} btn-pagination">&lt;</a>

                            <!-- Các số trang -->
                            <c:set var="startPage" value="${currentPage - (totalPagesToShow / 2)+1}" />
                            <c:set var="endPage" value="${startPage + totalPagesToShow - 1}" />

                            <!-- Điều chỉnh startPage và endPage nếu cần -->
                            <c:if test="${startPage < 1}">
                                <c:set var="startPage" value="1" />
                                <c:set var="endPage" value="${totalPagesToShow>totalPages?totalPages:totalPagesToShow}" />
                            </c:if>
                            <c:if test="${endPage > totalPages}">
                                <c:set var="endPage" value="${totalPages}" />
                                <c:set var="startPage" value="${endPage - totalPagesToShow + 1>0?endPage - totalPagesToShow + 1:1}" />
                            </c:if>

                            <c:forEach var="i" begin="${startPage}" end="${endPage}">
                                <a href="ComponentWarehouse/Search?page=${i}&page-size=${size}&searchCode=${searchCode}&searchName=${searchName}&sort=${sort}&order=${order}&searchType=${searchType}&searchBrand=${searchBrand}&searchQuantityMin=${searchQuantityMin}&searchQuantityMax=${searchQuantityMax}&searchPriceMin=${searchPriceMin}&searchPriceMax=${searchPriceMax}" class="btn btn-primary ${i == currentPage ? 'active' : ''} btn-pagination">${i}</a>
                            </c:forEach>

                            <!-- Nút "Sau" -->
                            <a href="ComponentWarehouse/Search?page=${currentPage + 1}&page-size=${size}&searchCode=${searchCode}&searchName=${searchName}&sort=${sort}&order=${order}&searchType=${searchType}&searchBrand=${searchBrand}&searchQuantityMin=${searchQuantityMin}&searchQuantityMax=${searchQuantityMax}&searchPriceMin=${searchPriceMin}&searchPriceMax=${searchPriceMax}" class="btn btn-primary ${currentPage >= totalPages ? 'disabled' : ''} btn-pagination">&gt;</a>

                            <!-- Nút "Cuối" -->
                            <a href="ComponentWarehouse/Search?page=${totalPages}&page-size=${size}&searchCode=${searchCode}&searchName=${searchName}&sort=${sort}&order=${order}&searchType=${searchType}&searchBrand=${searchBrand}&searchQuantityMin=${searchQuantityMin}&searchQuantityMax=${searchQuantityMax}&searchPriceMin=${searchPriceMin}&searchPriceMax=${searchPriceMax}" style="margin-left:5px" class="btn btn-primary ${currentPage >= totalPages ? 'disabled' : ''} btn-pagination">&gt;&gt;</a>
                        </div>

                        <!-- Ô nhập trang -->
                        <div class="text-center" style="margin-top: 1rem;">
                            <form class="row align-items-center justify-content-center" action="" method="get">
                                <input type="number" style="width:4.5rem; padding:.3rem .5rem" class="form-control mb-2 me-sm-2" id="inlineFormInputName2" name="page" min="1" max="${totalPages}" placeholder="Page">
                                <input type="hidden" name="page-size" value="${size}"> <!-- Giữ lại page-size -->
                                <input type="hidden" name="search" value="${search}" />
                                <input type="hidden" name="sort" value="${sort}" />
                                <input type="hidden" name="searchName" value="${searchName}" />
                                <input type="hidden" name="searchCode" value="${searchCode}" />
                                <input type="hidden" name="searchType" value="${searchType}" />
                                <input type="hidden" name="searchBrand" value="${searchBrand}" />
                                <input type="hidden" name="searchQuantityMin" value="${searchQuantityMin}" />
                                <input type="hidden" name="searchQuantityMax" value="${searchQuantityMax}" />
                                <input type="hidden" name="searchPriceMin" value="${searchPriceMin}" />
                                <input type="hidden" name="searchPriceMax" value="${searchPriceMax}" />
                                <button type="submit" style="width:3rem" class="btn btn-primary mb-2">Go</button>
                            </form>
                        </div>
                        <c:if test="${not empty deleteStatus}">
                            <div class="alert alert-warning alert-dismissible" role="alert">
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                <div class="alert-message">
                                    <strong>${deleteStatus}</strong>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </main>
                <jsp:include page="../../includes/footer.jsp" />
            </div>

        </div>

        <script src="js/app.js"></script>
                <script src="js/range-slider.js"></script>
                        <script src="js/format-input.js"></script>



    </body>

</html>
