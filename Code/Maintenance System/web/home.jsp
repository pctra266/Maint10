<%-- 
    Document   : home
    Created on : Jan 20, 2025, 11:25:46 AM
    Author     : Tra Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
        <h1>Home Page</h1>
         </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>
    </body>
</html>
