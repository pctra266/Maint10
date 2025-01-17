<%-- 
    Document   : Payment
    Created on : Jan 17, 2025, 2:45:35 PM
    Author     : Tra Pham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
    <table >
                <thead>
                    <tr>
                        <th>Payment</th>
                        <th>Payment</th>
                        <th>Payment</th>
                        <th>Payment</th>
                        <th>Payment</th>
                        <th>Payment</th>
                        <th>Payment</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:foreach items="${listP}" var="o">
                        <tr>
                            <td>${o.getPaymentID}</td>
                            <td>${o.getWarrantyCardID()}</td>
                            <td>${o.getPaymentDate()}</td>
                            <td>${o.getPaymentMethod()}</td>
                            <td>${o.getAmount()}</td>
                            <td>${o.getStatus()}</td>
                            <td><a href="#">Update</a></td>
                            <td><a href="#" >Delete</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

</html>
