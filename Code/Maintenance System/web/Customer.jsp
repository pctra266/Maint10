<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

                    <!-- Search Form -->
                    <form action="customer" method="get" style="text-align: center; margin-bottom: 20px;">
                        <input type="hidden" name="action" value="search" />
                        <input type="text" name="text" placeholder="Search by name" value="${searchQuery}" />
                        <button type="submit">Search</button>
                    </form>

                    <!-- Create Button -->
                    <div style=" margin-bottom: 20px;">
                        <a href="customer?action=add" class="btn btn-primary">Create New Customer</a>
                    </div>
                    <div>
                        <a href="customer">All Cstomer</a>
                    </div>
                    <!-- Table of customers -->
                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Image</th>
                                <th>Address</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty searchMessage}">
                                    <tr>
                                        <td colspan="7" class="text-center">${searchMessage}</td>
                                    </tr>
                                </c:when>
                                <c:when test="${not empty listCustomer}">
                                    <c:forEach var="customer" items="${listCustomer}">
                                        <tr>
                                            <td>${customer.customerID}</td>
                                            <td>${customer.name}</td>
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
                                        <td colspan="7" class="text-center">No result.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <div class="text-center">
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <a href="customer?index=${i}" class="pagination-link">${i}</a>
                        </c:forEach>
                    </div> 

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>

        <script src="js/app.js"></script>
    </body>
</html>
