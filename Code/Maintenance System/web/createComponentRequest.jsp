<%-- 
    Document   : createComponentRequest
    Created on : Feb 19, 2025, 11:28:53 PM
    Author     : Tra Pham
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Component Request</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1>Create Component Request</h1>
                    <div>
                        ${mess}
                    </div>
                    <form action="componentRequest" method="post">
                        <input type="hidden" name="action" value="createComponentRequest">
                        <input type="text" readonly="" name="warrantyCardID" value="${warrantyCardID}">
                    <c:forEach var="o" items="${listComponentByProductCode}" >
                        <div>
                            Component Name: ${o.componentName}
                            Component Code: ${o.componentCode}
                            <input type="text" readonly="" name="componentIDs" value="${o.componentID}">
                            <input type="number" name="quantities"  min="0" required>
                            <br>
                        </div>
                        
                    </c:forEach>
                            <div><textarea name="note"></textarea></div>
                            
                            <button class="btn btn-primary" type="submit">Submit</button>
                    </form>
                    
                </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>

        <script src="js/app.js"></script>

    </body>
</html>
