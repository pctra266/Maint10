<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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



                    <form action="customer" method="get" style="text-align: center; margin-bottom: 20px;">
                        <input type="hidden" name="action" value="advancedSearch" />
                        <input type="text" name="name" placeholder="Search by name" value="${searchName}" />
                        <select name="gender">
                            <option value="">Search by gender</option>
                            <option value="Male" ${searchGender == 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${searchGender == 'Female' ? 'selected' : ''}>Female</option>
                        </select>
                        <input type="text" name="address" placeholder="Search by address" value="${searchAddress}" />
                        <input type="text" name="phone" placeholder="Search by phone" value="${searchPhone}" />
                        <input type="text" name="email" placeholder="Search by email" value="${searchEmail}" />
                        <button type="submit">Search</button>
                    </form>

                    <!-- Create Button -->
                    <div style="margin-bottom: 20px;">
                        <a href="customer?action=add" class="btn btn-primary">Create New Customer</a>
                        <a href="customer" class="btn btn-secondary">All Customers</a>
                    </div>

                    <!-- Table of customers with Sort buttons -->
                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>
                                    ID
                                    <a href="customer?action=sort&field=customerID&order=asc">↑</a>
                                    <a href="customer?action=sort&field=customerID&order=desc">↓</a>
                                </th>
                                <th>
                                    Name
                                    <a href="customer?action=sort&field=name&order=asc">↑</a>
                                    <a href="customer?action=sort&field=name&order=desc">↓</a>
                                </th>
                                <th>
                                    Gender
                                    <a href="customer?action=sort&field=gender&order=asc">↑</a>
                                    <a href="customer?action=sort&field=gender&order=desc">↓</a>
                                </th>
                                <th>Image</th>
                                <th>
                                    Address
                                    <a href="customer?action=sort&field=address&order=asc">↑</a>
                                    <a href="customer?action=sort&field=address&order=desc">↓</a>
                                </th>
                                <th>
                                    Email
                                    <a href="customer?action=sort&field=email&order=asc">↑</a>
                                    <a href="customer?action=sort&field=email&order=desc">↓</a>
                                </th>
                                <th>
                                    Phone
                                    <a href="customer?action=sort&field=phone&order=asc">↑</a>
                                    <a href="customer?action=sort&field=phone&order=desc">↓</a>
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

                            </c:choose>
                        </tbody>
                    </table>

                    <!-- Pagination -->
                    <div class="text-center" style="margin-top: 20px;">
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <a href="customer?action=sort&field=${sortField}&order=${sortOrder}&index=${i}" class="pagination-link">${i}</a>
                        </c:forEach>

                    </div>
                </main>

                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>

        <script src="js/app.js"></script>
    </body>
</html>
