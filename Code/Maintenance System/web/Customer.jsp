<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Customer Management Page">
    <meta name="author" content="AdminKit">
    <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

    <title>Customer Management</title>

    <link href="css/light.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

</head>

<body>
    <div class="wrapper">
        <jsp:include page="/includes/navbar-left.jsp" />

        <div class="main">
            <jsp:include page="/includes/navbar-top.jsp" />
            <main class="content">

                <h2>Customer Management</h2>

                <!-- Search Form -->
                <form action="customer" method="get" style="text-align: center; margin-bottom: 20px;">
                    <input type="hidden" name="action" value="search" />
                    <input type="text" name="text" placeholder="Search by name" value="${textSearch}" />
                    <button type="submit">Search</button>
                </form>

                <c:choose>
                    <c:when test="${not empty listSearchCustomer}">
                        <h3>Search Results for "${textSearch}"</h3>
                        <table class="table table-hover my-0">
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Image</th>
                                <th>Address</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Actions</th>
                            </tr>
                            <c:forEach var="customer" items="${listSearchCustomer}">
                                <tr>
                                    <td>${customer.customerID}</td>
                                    <td>${customer.name}</td>
                                    <td><img src="${customer.image}" alt="Customer Image" width="100" height="100" /></td>
                                    <td>${customer.address}</td>
                                    <td>${customer.email}</td>
                                    <td>${customer.phone}</td>
                                    <td>
                                        <a href="customer?action=detail&id=${customer.customerID}">Detail</a>
                                        <a href="customer?action=update&id=${customer.customerID}">Update</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>

                    <c:otherwise>
                        <h3>All Customers</h3>
                        <table class="table table-hover my-0">
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Image</th>
                                <th>Address</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Actions</th>
                            </tr>
                            <c:forEach var="customer" items="${listCustomer}">
                                <tr>
                                    <td>${customer.customerID}</td>
                                    <td>${customer.name}</td>
                                    <td><img src="${customer.image}" alt="Customer Image" width="100" height="100" /></td>
                                    <td>${customer.address}</td>
                                    <td>${customer.email}</td>
                                    <td>${customer.phone}</td>
                                    <td>
                                        <a href="customer?action=detail&id=${customer.customerID}">Detail</a>
                                        <a href="customer?action=update&id=${customer.customerID}">Update</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </main>
            <jsp:include page="/includes/footer.jsp" />
        </div>
    </div>

    <script src="js/app.js"></script>
</body>

</html>
