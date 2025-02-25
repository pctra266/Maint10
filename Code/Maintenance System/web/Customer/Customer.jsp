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
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center">Customer List</h1>

                    <form action="customer"  method="get" style="text-align: center; margin-bottom: 20px;">

                        <input type="text" name="name" placeholder="Search by name" value="${searchName}" />
                        <select name="gender">
                            <option value="">Search by gender</option>
                            <option value="Male" ${searchGender == 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${searchGender == 'Female' ? 'selected' : ''}>Female</option>
                        </select>
                        <input type="text" name="address" placeholder="Search by address" value="${searchAddress}" />
                        <input type="text" name="phone" placeholder="Search by phone" value="${searchPhone}" />
                        <input type="text" name="email" placeholder="Search by email" value="${searchEmail}" />

                        <input type="number" name="searchDate" placeholder="Day" style="width: 5%;" min="1" max="31" value="${searchDate}" />
                        <input type="number" name="searchMonth" placeholder="Month" style="width: 5%;" min="1" max="12" value="${searchMonth}" />
                        <input type="number" name="searchYear" placeholder="Year" style="width: 5%;" min="1900" max="2100" value="${searchYear}" />

                        <!-- Dropdown để chọn số lượng khách hàng hiển thị mỗi trang -->
                        <div class="col-sm-6 col-md-6">
                            <label>Show 
                                <select name="page-size" class="form-select form-select-sm d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                    <option value="5" ${size == 5 ? 'selected' : ''}>5</option>
                                    <option value="10" ${size == 10 ? 'selected' : ''}>10</option>
                                    <option value="20" ${size == 20 ? 'selected' : ''}>20</option>

                                </select> 
                                entries
                            </label>
                        </div>

                        <button type="submit">Search</button>
                    </form>

                    <!-- Create New Customer Button -->
                    <div style="margin-bottom: 20px;">
                        <a href="customer?action=add" class="btn btn-primary">Create New Customer</a>
                        <a href="customer" class="btn btn-secondary">All Customers</a>
                        <a href="importExcelCustomer.jsp" class="btn btn-secondary">Import Excel</a>
                        <a href="importExcelCustomer.jsp" class="btn btn-secondary">Import Excel</a>
                    </div>

                    <!-- Table of Customers with Sort Buttons -->
                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>
                                    ID
                                    <a href="customer?field=customerID&order=asc&name=${searchName}&gender=${searchGender}&address=${searchAddress}&phone=${searchPhone}&email=${searchEmail}&searchDate=${searchDate}&searchMonth=${searchMonth}&searchYear=${searchYear}&page-size=${size}">↑</a>
                                    <a href="customer?field=customerID&order=desc&name=${searchName}&gender=${searchGender}&address=${searchAddress}&phone=${searchPhone}&email=${searchEmail}&searchDate=${searchDate}&searchMonth=${searchMonth}&searchYear=${searchYear}&page-size=${size}">↓</a>
                                </th>
                                <th>
                                    Name
                                    <a href="customer?field=name&order=asc&name=${searchName}&gender=${searchGender}&address=${searchAddress}&phone=${searchPhone}&email=${searchEmail}&searchDate=${searchDate}&searchMonth=${searchMonth}&searchYear=${searchYear}&page-size=${size}">↑</a>
                                    <a href="customer?field=name&order=desc&name=${searchName}&gender=${searchGender}&address=${searchAddress}&phone=${searchPhone}&email=${searchEmail}&searchDate=${searchDate}&searchMonth=${searchMonth}&searchYear=${searchYear}&page-size=${size}">↓</a>
                                </th>
                                <th>
                                    Gender
                                    <a href="customer?field=gender&order=asc&name=${searchName}&gender=${searchGender}&address=${searchAddress}&phone=${searchPhone}&email=${searchEmail}&searchDate=${searchDate}&searchMonth=${searchMonth}&searchYear=${searchYear}&page-size=${size}">↑</a>
                                    <a href="customer?field=gender&order=desc&name=${searchName}&gender=${searchGender}&address=${searchAddress}&phone=${searchPhone}&email=${searchEmail}&searchDate=${searchDate}&searchMonth=${searchMonth}&searchYear=${searchYear}&page-size=${size}">↓</a>
                                </th>
                                <th>
                                    Date Of Birth

                                </th>
                                <th>Image</th>
                                <th>
                                    Address
                                    <a href="customer?field=address&order=asc&name=${searchName}&gender=${searchGender}&address=${searchAddress}&phone=${searchPhone}&email=${searchEmail}&searchDate=${searchDate}&searchMonth=${searchMonth}&searchYear=${searchYear}&page-size=${size}">↑</a>
                                    <a href="customer?field=address&order=desc&name=${searchName}&gender=${searchGender}&address=${searchAddress}&phone=${searchPhone}&email=${searchEmail}&searchDate=${searchDate}&searchMonth=${searchMonth}&searchYear=${searchYear}&page-size=${size}">↓</a>
                                </th>
                                <th>
                                    Email
                                    <a href="customer?field=email&order=asc&name=${searchName}&gender=${searchGender}&address=${searchAddress}&phone=${searchPhone}&email=${searchEmail}&searchDate=${searchDate}&searchMonth=${searchMonth}&searchYear=${searchYear}&page-size=${size}">↑</a>
                                    <a href="customer?field=email&order=desc&name=${searchName}&gender=${searchGender}&address=${searchAddress}&phone=${searchPhone}&email=${searchEmail}&searchDate=${searchDate}&searchMonth=${searchMonth}&searchYear=${searchYear}&page-size=${size}">↓</a>
                                </th>
                                <th>
                                    Phone
                                    <a href="customer?field=phone&order=asc&name=${searchName}&gender=${searchGender}&address=${searchAddress}&phone=${searchPhone}&email=${searchEmail}&searchDate=${searchDate}&searchMonth=${searchMonth}&searchYear=${searchYear}&page-size=${size}">↑</a>
                                    <a href="customer?field=phone&order=desc&name=${searchName}&gender=${searchGender}&address=${searchAddress}&phone=${searchPhone}&email=${searchEmail}&searchDate=${searchDate}&searchMonth=${searchMonth}&searchYear=${searchYear}&page-size=${size}">↓</a>
                                </th>

                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty searchMessage}">
                                    <tr>
                                        <td colspan="8" class="text-center">${searchMessage}</td>
                                    </tr>
                                </c:when>
                                <c:when test="${not empty listCustomer}">
                                    <c:forEach var="customer" items="${listCustomer}">
                                        <tr>
                                            <td>${customer.customerID}</td>
                                            <td>${customer.name}</td>
                                            <td>${customer.gender}</td>
                                            <td>
                                                <fmt:formatDate value="${customer.dateOfBirth}" pattern="dd-MM-yyyy" />
                                            </td>

                                            <td><img src="${customer.image}" alt="Customer Image" width="50" height="50" /></td>
                                            <td>${customer.address}</td>
                                            <td>${customer.email}</td>
                                            <td>${customer.phone}</td>
                                            <td>
                                                <a href="customer?action=detail&id=${customer.customerID}">Detail</a>
                                                <a href="customer?action=update&id=${customer.customerID}">Update</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="8" class="text-center">No result.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <!-- Pagination -->
                    <div class="text-center" style="margin-top: 20px;">
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <a href="customer?index=${i}&page-size=${size}&name=${searchName}&gender=${searchGender}&address=${searchAddress}&phone=${searchPhone}&email=${searchEmail}&searchDate=${searchDate}&searchMonth=${searchMonth}&searchYear=${searchYear}&field=${sortBy}&order=${sortOrder}" class="pagination-link">${i}</a>

                        </c:forEach>
                    </div>

                </main>

                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
