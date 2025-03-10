<%-- 
    Document   : AcessDenied
    Created on : Mar 10, 2025, 11:22:19 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Access Denied</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <p>${requestScope.user.email}</p>
    </body>
</html>
