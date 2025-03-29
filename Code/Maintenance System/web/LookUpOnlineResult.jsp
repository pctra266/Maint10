<%-- 
    Document   : YourWarrantyCard
    Created on : Feb 3, 2025, 9:08:43 PM
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Warranty Card</title>
        <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>


    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center "> Warranty Card</h1>

                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>Warranty Card ID</th>
                                <th>Warranty Card Code</th>
                                <th>Product</th>
                                <th>Create Date</th>
                                <th>Status</th>
                                
                            </tr>
                        </thead>
                        <tbody>
                           
                               
                                    <tr>
                                        <td>${warrantycard.warrantyCardID}</td>
                                        <td>${warrantycard.warrantyCardCode}</td>
                                        <td>
                                           ${warrantycard.productName}
                                        </td>
                                       <td>${warrantycard.createdDate}</td>

                                        <td>${warrantycard.warrantyStatus}</td>
                                       
                                    </tr>
                               
                            
                           
                        </tbody>
                    </table>


                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>


</html>
