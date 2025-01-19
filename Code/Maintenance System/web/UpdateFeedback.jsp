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
        <title>Feedback Detail</title>
         <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
        <h1>History</h1>
        <h1>Detail Feedback</h1>
        <form action="UpdateFeedback" method="post">
        <table >
            <tbody>
                <tr>
                    <td>Feedback ID: </td>
                    <td><input type="text" readonly="" value="${feedbackUpdate.feedbackID}" name="feedbackId"></td>
                </tr>
                <tr>
                    <td><input type="hidden" value="${feedbackUpdate.customerID}"></td>
                </tr>
                <tr>
                    <td>Customer Name: </td>
                    <td><input type="text" readonly="" value="${feedbackUpdate.customerName}"></td>
                </tr>
                <tr>
                    <td>Create Date: </td>
                    <td><input type="text" readonly="" value="${feedbackUpdate.dateCreated}"></td>
                </tr>
                <tr>
                    <td>Product Name: </td>
                    <td><input type="text" readonly="" value="${feedbackUpdate.productName}"></td>
                </tr>
                <tr>
                    <td>Issue Description: </td>
                    <td><input type="text" readonly="" value="${feedbackUpdate.issueDescription}"></td>
                </tr>
                <tr>
                    <td>Warranty Status: </td>
                    <td><input type="text" readonly="" value="${feedbackUpdate.warrantyStatus}"></td>
                </tr>
                <tr>
                    <td><input type="hidden"  value="${feedbackUpdate.warrantyCardID}"></td>
                </tr>
                <tr>
                    <td>Note : </td>
                    <td><textarea name="note">${feedbackUpdate.note}</textarea></td>
                </tr>
                 <tr>
                    <td>Image : </td>
                    <td><input type="text" readonly="" value="${feedbackUpdate.imageURL}"></td>
                </tr>
                 <tr>
                    <td>Video : </td>
                    <td><input type="text" readonly="" value="${feedbackUpdate.videoURL}"></td>
                </tr>
                <tr>
                    <td> <button type="submit" > Save Change</button> </td>
                </tr>
            </tbody>
            
        </table>
         </form>
                </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>

        <script src="js/app.js"></script>
    </body>
</html>
