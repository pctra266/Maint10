<%-- 
    Document   : UpdateFeedback
    Created on : Jan 17, 2025, 9:14:09 PM
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
        <h1>Detail Feedback</h1>
        <form action="UpdateFeedback" method="post">
        <table >
            <tbody>
                <tr>
                    <td>Feedback ID: </td>
                    <td><input type="text" readonly="" value="${feedbackUpdate.feedbackID}" name="feedbackId"></td>
                </tr>
                <tr>
                    <td>Customer ID: </td>
                    <td><input type="text" readonly="" value="${feedbackUpdate.customerID}"></td>
                </tr>
                <tr>
                    <td>Warranty Card ID: </td>
                    <td><input type="text" readonly="" value="${feedbackUpdate.warrantyCardID}"></td>
                </tr>
                <tr>
                    <td>Note : </td>
                    <td><input type="text"  value="${feedbackUpdate.note}" name="note"></td>
                </tr>
                <tr>
                    <td> <button type="submit" > Save Change</button> </td>
                </tr>
            </tbody>
            
        </table>
         </form>       
    </body>
</html>
