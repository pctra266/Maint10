<%-- 
    Document   : ComponentWarehouse
    Created on : Jan 17, 2025, 8:24:34 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                        <input type="hidden" name="page" value="${pagination.currentPage}" />
                        <input type="hidden" name="sort" value="${pagination.sort}" />
                        <input type="hidden" name="order" value="${pagination.order}" />
                        <div class="col-md-3 input-group d-flex justify-content-end">
                            <input type="search" style="flex: 1 1 auto" class="form-control form-control-md" placeholder="Code" name="searchCode" value="${pagination.searchValues[1]}" />
                            <select name="searchType" class="form-select form-select-md" style="flex: 1 1 auto;">
                                <option value="">Type</option>
                                <c:forEach var="type" items="${typeList}">
                                    <option value="${type}" ${pagination.searchValues[2] eq type ? "selected" : ""}>${type}</option>
                                </c:forEach>
                            </select>

                            <!-- Thêm trường chọn cho Brand -->
                            <select name="searchBrand" class="form-select form-select-md" style="flex: 1 1 auto;">
                                <option value="">Brand</option>
                                <c:forEach var="brand" items="${brandList}">
                                    <option value="${brand}" ${pagination.searchValues[3]  eq brand ? "selected" : ""}>${brand}</option>
                                </c:forEach>
                            </select>
                            <input type="search" style="flex: 1 1 auto" class="form-control form-control-md" placeholder="Name" name="searchName" value="${pagination.searchValues[0]}" />  
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
                                            <input type="text" class="input-min format-int" name="searchQuantityMin" value="${pagination.rangeValues[2]}" step="1">
                                        </div>
                                        <div class="separator">-</div>
                                        <div class="field">
                                            <input type="text" class="input-max format-int" name="searchQuantityMax" value="${pagination.rangeValues[3]}" step="1">
                                        </div>
                                    </div>
                                    <div class="slider">
                                        <div class="progress"></div>
                                    </div>
                                    <div class="range-input">
                                        <input type="range" class="range-min" min="0" max="${quantityMax}" value="${pagination.rangeValues[2]}" step="1">
                                        <input type="range" class="range-max" min="0" max="${quantityMax}" value="${pagination.rangeValues[3]}" step="1">
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="slider-container" data-type="price">
                                    <div class="price-input">
                                        <div class="field">
                                            <span>Price</span>
                                            <input type="text" class="input-min format-float" name="searchPriceMin" value="${pagination.rangeValues[0]}" step="0.01">
                                        </div>
                                        <div class="separator">-</div>
                                        <div class="field">
                                            <input type="text" class="input-max format-float" name="searchPriceMax" value="${pagination.rangeValues[1]==priceMax?pagination.rangeValues[1]+0.01:pagination.rangeValues[1]}" step="0.01" >
                                        </div>
                                    </div>
                                    <div class="slider">
                                        <div class="progress"></div>
                                    </div>
                                    <div class="range-input">
                                        <input type="range" class="range-min" min="0" max="${priceMax+0.01}" value="${pagination.rangeValues[0]}" step="0.01">
                                        <input type="range" class="range-max" min="0" max="${priceMax+0.01}" value="${pagination.rangeValues[1]==priceMax?pagination.rangeValues[1]+0.01:pagination.rangeValues[1]}" step="0.01">
                                    </div>
                                </div>
                            </div>
                        </div>




                        <div class="col-sm-6 col-md-6">
                            <label>Show 
                                <select name="page-size" class="form-select form-select-sm d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                    <c:forEach items="${pagination.listPageSize}" var="s">
                                        <option value="${s}" ${pagination.pageSize==s?"selected":""}>${s}</option>
                                    </c:forEach>
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
                                        <input type="hidden" name="page" value="${pagination.currentPage}" />
                                        <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                        <input type="hidden" name="sort" value="code" />
                                        <input type="hidden" name="order" value="${pagination.sort eq 'code' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                        <c:if test="${fn:length(pagination.searchFields) > 0}">
                                            <c:forEach var="i" begin="0" end="${fn:length(pagination.searchFields) - 1}">
                                                <input type="hidden" name="${pagination.searchFields[i]}" value="${pagination.searchValues[i]}">
                                            </c:forEach>
                                        </c:if>

                                        <c:if test="${fn:length(pagination.rangeFields) > 0}">
                                            <c:forEach var="i" begin="0" end="${fn:length(pagination.rangeFields) - 1}">
                                                <input type="hidden" name="${pagination.rangeFields[i]}" value="${pagination.rangeValues[i]}">
                                            </c:forEach>
                                        </c:if>
                                        <button type="submit" class="btn-sort">
                                            <i class="align-middle fas fa-fw
                                               ${pagination.sort eq 'code' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>
                                        Code
                                    </form>
                                </th>
                                <th>Type</th>
                                <th>Brand</th>
                                <th style="width:30%">
                                    <form action="ComponentWarehouse/Search" method="get">
                                        <input type="hidden" name="page" value="${pagination.currentPage}" />
                                        <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                        <input type="hidden" name="sort" value="name" />
                                        <input type="hidden" name="order" value="${pagination.sort eq 'name' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                        <c:if test="${fn:length(pagination.searchFields) > 0}">
                                            <c:forEach var="i" begin="0" end="${fn:length(pagination.searchFields) - 1}">
                                                <input type="hidden" name="${pagination.searchFields[i]}" value="${pagination.searchValues[i]}">
                                            </c:forEach>
                                        </c:if>

                                        <c:if test="${fn:length(pagination.rangeFields) > 0}">
                                            <c:forEach var="i" begin="0" end="${fn:length(pagination.rangeFields) - 1}">
                                                <input type="hidden" name="${pagination.rangeFields[i]}" value="${pagination.searchValues[i]}">
                                            </c:forEach>
                                        </c:if>
                                        <button type="submit" class="btn-sort">
                                            <i class="align-middle fas fa-fw
                                               ${pagination.sort eq 'name' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>
                                        Name
                                    </form>
                                </th>

                                <th style="width:15%">
                                    <form action="ComponentWarehouse/Search" method="get">
                                        <input type="hidden" name="page" value="${pagination.currentPage}" />
                                        <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                        <input type="hidden" name="sort" value="quantity" />
                                        <input type="hidden" name="order" value="${pagination.sort eq 'quantity' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                        <c:if test="${fn:length(pagination.searchFields) > 0}">
                                            <c:forEach var="i" begin="0" end="${fn:length(pagination.searchFields) - 1}">
                                                <input type="hidden" name="${pagination.searchFields[i]}" value="${pagination.searchValues[i]}">
                                            </c:forEach>
                                        </c:if>

                                        <c:if test="${fn:length(pagination.rangeFields) > 0}">
                                            <c:forEach var="i" begin="0" end="${fn:length(pagination.rangeFields) - 1}">
                                                <input type="hidden" name="${pagination.rangeFields[i]}" value="${pagination.searchValues[i]}">
                                            </c:forEach>
                                        </c:if>
                                        <button type="submit" class="btn-sort">
                                            <i class="align-middle fas fa-fw
                                               ${pagination.sort eq 'quantity' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>
                                        Quantity
                                    </form>
                                </th>

                                <th style="width:15%">
                                    <form action="ComponentWarehouse/Search" method="get">
                                        <input type="hidden" name="page" value="${pagination.currentPage}" />
                                        <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                        <input type="hidden" name="sort" value="price" />
                                        <input type="hidden" name="order" value="${pagination.sort eq 'price' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                   <c:if test="${fn:length(pagination.searchFields) > 0}">
                                    <c:forEach var="i" begin="0" end="${fn:length(pagination.searchFields) - 1}">
                                        <input type="hidden" name="${pagination.searchFields[i]}" value="${pagination.searchValues[i]}">
                                    </c:forEach>
                                </c:if>

                                <c:if test="${fn:length(pagination.rangeFields) > 0}">
                                    <c:forEach var="i" begin="0" end="${fn:length(pagination.rangeFields) - 1}">
                                        <input type="hidden" name="${pagination.rangeFields[i]}" value="${pagination.searchValues[i]}">
                                    </c:forEach>
                                </c:if>
                                        <button type="submit" class="btn-sort">
                                            <i class="align-middle fas fa-fw
                                               ${pagination.sort eq 'price' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>
                                        Unit Price
                                    </form>
                                </th>


                                <th style="width:8%">Action<a href="ComponentWarehouse/Search?page=${pagination.currentPage}&page-size=${pagination.pageSize}"><i class="fa fa-refresh ms-2"></i></a></th>
                            </tr>
                        </thead>
                        <!--                        varStatus để lấy trạng thái của vòng lặp-->
                        <c:forEach var="component" items="${components}" varStatus="status">
                            <tr class="${status.index % 2 == 0 ? 'table-primary' : ''}">
                                <td>${status.index+1+(pagination.currentPage-1)*pagination.pageSize}</td>
                                <td>${component.componentCode}</td>
                                <td>${component.type}</td>
                                <td>${component.brand}</td>
                                <td>${component.componentName}</td>
                                <td>${component.quantity}</td>
                                <td>${component.price}</td>
                                <td class="table-action">
                                    <a href="ComponentWarehouse/Detail?ID=${component.componentID}&from=search"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-2 align-middle"><path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path></svg></a>
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
                                            <a href="ComponentWarehouse/Delete?ID=${component.componentID}&page=${pagination.currentPage}&page-size=${pagination.pageSize}&searchCode=${pagination.searchValues[1]}&searchName=${pagination.searchValues[0]}&sort=${sort}&order=${order}&searchType=${pagination.searchValues[2]}&searchBrand=${pagination.searchValues[3]}&searchQuantityMin=${pagination.rangeValues[2]}&searchQuantityMax=${pagination.rangeValues[3]}&searchPriceMin=${pagination.rangeValues[0]}&searchPriceMax=${pagination.rangeValues[1]}" type="button" class="btn btn-primary">Delete</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <tbody>

                        </tbody>
                    </table>
                                        <c:if test="${totalComponents==0}">
                        <div class="alert alert-primary alert-dismissible" role="alert">
                            <div class="alert-message text-center">
                                <strong style="font-size:1.6rem">No suitable card in the filter</strong>
                            </div>
                        </div>

                    </c:if>
                    <jsp:include page="../../includes/pagination.jsp"/>
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
