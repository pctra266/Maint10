<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Customer</title>
    <link href="css/light.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        .search-box { margin-bottom: 15px; padding: 8px; width: 100%; max-width: 300px; border: 1px solid #ccc; border-radius: 5px; font-size: 14px; }
        button { background-color: #007bff; color: white; border: none; padding: 10px 15px; font-size: 14px; border-radius: 5px; cursor: pointer; transition: background-color 0.3s, transform 0.2s; display: inline-flex; align-items: center; gap: 5px; }
        button:hover { background-color: #0056b3; transform: scale(1.05); }
        button:active { background-color: #004494; transform: scale(0.98); }
        button i { font-size: 16px; }
    </style>
</head>
<body>
    <div class="wrapper">
        <jsp:include page="/includes/navbar-left.jsp" />
        <div class="main">
            <jsp:include page="/includes/navbar-top.jsp" />
            <main class="content">
                <h1 class="text-center">Customer List</h1>

                <!-- Form tìm kiếm -->
                <form action="customer" method="get" style="text-align: center; margin-bottom: 20px;">
                    <input type="text" name="name" placeholder="Search by name" value="${searchName}" class="search-box" />
                    <select name="gender" class="search-box">
                        <option value="">Search by gender</option>
                        <option value="Male" ${searchGender == 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${searchGender == 'Female' ? 'selected' : ''}>Female</option>
                    </select>
                    <input type="text" name="address" placeholder="Search by address" value="${searchAddress}" class="search-box" />
                    <input type="text" name="phone" placeholder="Search by phone" value="${searchPhone}" class="search-box" />
                    <input type="text" name="email" placeholder="Search by email" value="${searchEmail}" class="search-box" />
                    <input type="date" name="dateOfBirth" value="${dateOfBirth}" class="search-box"/>
                    
                    <button type="submit">Search</button>
                </form>

                <!-- Kiểm tra quyền -->
                <c:set var="canCreate" value="false"/>
                <c:set var="canImport" value="false"/>
                <c:set var="canUpdate" value="false"/>
                <c:set var="canDetail" value="false"/>
                <c:forEach var="perm" items="${sessionScope.permissionIds}">
                    <c:if test="${perm == 27}"><c:set var="canCreate" value="true"/></c:if>
                    <c:if test="${perm == 28}"><c:set var="canImport" value="true"/></c:if>
                    <c:if test="${perm == 29}"><c:set var="canUpdate" value="true"/></c:if>
                    <c:if test="${perm == 31}"><c:set var="canDetail" value="true"/></c:if>
                </c:forEach>

                <div style="margin-bottom: 20px;">
                    <c:if test="${canCreate}">
                        <a href="customer?action=add" class="btn btn-primary">Create New Customer</a>
                    </c:if>
                    <a href="customer" class="btn btn-primary">All Customer</a>
                    <c:if test="${canImport}">
                        <a href="importExcelCustomer.jsp" class="btn btn-secondary">Import Excel</a>
                    </c:if>
                </div>

                <!-- Bảng dữ liệu -->
                <table class="table table-hover my-0">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Gender</th>
                            <th>Date Of Birth</th>
                            <th>Image</th>
                            <th>Address</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <c:if test="${canUpdate or canDetail}">
                                <th>Actions</th>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty searchMessage}">
                                <tr>
                                    <td colspan="9" class="text-center">${searchMessage}</td>
                                </tr>
                            </c:when>
                            <c:when test="${not empty listCustomer}">
                                <c:forEach var="customer" items="${listCustomer}">
                                    <tr>
                                        <td>${customer.customerID}</td>
                                        <td>${customer.name}</td>
                                        <td>${customer.gender}</td>
                                        <td><fmt:formatDate value="${customer.dateOfBirth}" pattern="dd-MM-yyyy" /></td>
                                        <td><img src="${customer.image}" alt="Customer Image" width="50" height="50" /></td>
                                        <td>${customer.address}</td>
                                        <td>${customer.email}</td>
                                        <td>${customer.phone}</td>
                                        <c:if test="${canUpdate or canDetail}">
                                            <td>
                                                <c:if test="${canUpdate}">
                                                    <a href="customer?action=update&id=${customer.customerID}">Update</a>
                                                </c:if>
                                                <c:if test="${canDetail}">
                                                    <a href="customer?action=detail&id=${customer.customerID}">Detail</a>
                                                </c:if>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="9" class="text-center">No result.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
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
