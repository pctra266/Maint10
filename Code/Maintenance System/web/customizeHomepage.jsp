<%-- 
    Document   : customizeHomepage
    Created on : Mar 15, 2025, 9:09:48 AM
    Author     : Tra Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customize HomePage</title>
    </head>
    <body>
        <h1>Customize Footer</h1>
        ${messFooter}
        <form action="FooterController" method="post">
            <label>Slogan:</label>
        <input type="text" name="slogan" value="${footer.slogan}" required><br>

        <label>Address:</label>
        <input type="text" name="address" value="${footer.address}" required><br>

        <label>Hotline:</label>
        <input type="text" name="hotline" value="${footer.hotline}" required><br>

        <label>Email:</label>
        <input type="email" name="email" value="${footer.email}" required><br>

        <label>Copyright Year:</label>
        <input type="text" name="copyrightYear" value="${footer.copyrightYear}" required><br>

        <button type="submit">Update</button>
        </form>
    </body>
</html>
