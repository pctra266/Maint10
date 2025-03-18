<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Responsive Admin & Dashboard Template based on Bootstrap 5">
    <meta name="author" content="AdminKit">
    <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">

    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="shortcut icon" href="img/icons/icon-48x48.png" />
    <link rel="canonical" href="https://demo-basic.adminkit.io/" />

    <title>Component Type Management</title>

    <link href="${pageContext.request.contextPath}/css/light.css" rel="stylesheet">
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
        <jsp:include page="/includes/navbar-left.jsp" />
        <div class="main">
            <jsp:include page="/includes/navbar-top.jsp" />
            <main class="content">
                <h2>Component Type List</h2>

                <!-- Thông báo -->
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success alert-dismissible" role="alert">
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        <div class="alert-message">
                            <strong>${successMessage}</strong>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        <div class="alert-message">
                            <strong>${errorMessage}</strong>
                        </div>
                    </div>
                </c:if>

                <!-- Nút chức năng -->
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <form action="ComponentType" method="POST" style="display: inline;">
                        <button type="submit" class="btn btn-success" name="action" value="add">
                            <i class="fas fa-plus"></i> Add Component Type
                        </button>
                    </form>
                    <form action="ComponentType/Search" method="get" style="display: inline;">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-search"></i> Advanced Search
                        </button>
                    </form>
                </div>

                <!-- Form tìm kiếm và phân trang -->
                <form action="ComponentType" method="get" class="row align-items-center">
                    <input type="hidden" name="page" value="${pagination.currentPage}">
                    <input type="hidden" name="sort" value="${pagination.sort}">
                    <input type="hidden" name="order" value="${pagination.order}">
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
                    <div class="col-sm-6 col-md-6 text-end">
                        <div class="col-md-3 input-group d-flex justify-content-end">
                            <input type="search" style="flex: 0.5 1 auto" name="search" class="form-control form-control-md" 
                                   placeholder="Search by Type Name" value="${pagination.searchValues[0]}" aria-controls="datatables-column-search-text-inputs">
                            <button type="submit" class="btn btn-primary">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search align-middle">
                                    <circle cx="11" cy="11" r="8"></circle>
                                    <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <c:if test="${fn:length(pagination.searchFields) > 0}">
                        <c:forEach var="i" begin="0" end="${fn:length(pagination.searchFields) - 1}">
                            <input type="hidden" name="${pagination.searchFields[i]}" value="${pagination.searchValues[i]}">
                        </c:forEach>
                    </c:if>
                </form>

                <!-- Bảng danh sách Component Type -->
                <table class="table table-hover my-0">
                    <thead>
                        <tr>
                            <th style="width:3%">#</th>
                            <th style="width:10%">
                                <form action="ComponentType" method="get">
                                    <input type="hidden" name="page" value="${pagination.currentPage}" />                                  
                                    <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                    <input type="hidden" name="search" value="${pagination.searchValues[0]}" />
                                    <input type="hidden" name="sort" value="TypeID" />
                                    <input type="hidden" name="order" value="${pagination.sort eq 'TypeID' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                    <button type="submit" class="btn-sort">
                                        <i class="align-middle fas fa-fw
                                           ${pagination.sort eq 'TypeID' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                        </i>
                                    </button>
                                    Type ID
                                </form>
                            </th>
                            <th>
                                <form action="ComponentType" method="get">
                                    <input type="hidden" name="page" value="${pagination.currentPage}" />                                  
                                    <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                    <input type="hidden" name="search" value="${pagination.searchValues[0]}" />
                                    <input type="hidden" name="sort" value="TypeName" />
                                    <input type="hidden" name="order" value="${pagination.sort eq 'TypeName' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                    <button type="submit" class="btn-sort">
                                        <i class="align-middle fas fa-fw
                                           ${pagination.sort eq 'TypeName' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                        </i>
                                    </button>
                                    Type Name
                                </form>
                            </th>
                            <th style="width:8%">Action
                                <a href="?page=${pagination.currentPage}&page-size=${pagination.pageSize}&search=${pagination.searchValues[0]}">
                                    <i class="fa fa-refresh ms-2"></i>
                                </a>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="type" items="${typeList}" varStatus="status">
                            <tr class="${status.index % 2 == 0 ? 'table-primary' : ''}">
                                <td>${status.index + 1 + (pagination.currentPage - 1) * pagination.pageSize}</td>
                                <td>${type.typeID}</td>
                                <td>${type.typeName}</td>
                                <td class="table-action">
                                    <a data-bs-toggle="modal" data-bs-target="#deleteTypeModal_${type.typeID}">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash align-middle">
                                            <polyline points="3 6 5 6 21 6"></polyline>
                                            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                                        </svg>
                                    </a>
                                </td>
                            </tr>

                            <!-- Modal Xóa -->
                            <div class="modal fade" id="deleteTypeModal_${type.typeID}" tabindex="-1" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Delete Confirmation</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body m-3">
                                            <p class="mb-0">Confirm your action. Really want to delete type "<strong>${type.typeName}</strong>"?</p>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            <a href="ComponentType?action=delete&typeID=${type.typeID}&page=${pagination.currentPage}&page-size=${pagination.pageSize}&search=${pagination.searchValues[0]}&sort=${pagination.sort}&order=${pagination.order}" 
                                               class="btn btn-primary">Delete</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Thông báo khi không có dữ liệu -->
                <c:if test="${totalTypes == 0}">
                    <div class="alert alert-primary alert-dismissible" role="alert">
                        <div class="alert-message text-center">
                            <strong style="font-size:1.6rem">No component types found in the filter</strong>
                        </div>
                    </div>
                </c:if>

                <!-- Modal Thêm -->
                <div class="modal fade" id="addTypeModal" tabindex="-1" aria-labelledby="addTypeModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <form action="ComponentType" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="addTypeModalLabel">Add New Component Type</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label for="typeName" class="form-label">Type Name</label>
                                        <input type="text" class="form-control" id="typeName" name="typeName" required>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <button type="submit" class="btn btn-primary" name="action" value="add">Add Component Type</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Phân trang -->
                <jsp:include page="/includes/pagination.jsp" />
            </main>
            <jsp:include page="/includes/footer.jsp" />
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js"></script>
    <script>
        // Mở modal thêm khi nhấn nút "Add Component Type"
        document.querySelector('button[name="action"][value="add"]').addEventListener('click', function(e) {
            e.preventDefault();
            new bootstrap.Modal(document.getElementById('addTypeModal')).show();
        });
    </script>
</body>
</html>