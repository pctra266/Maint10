<%-- 
    Document   : CreateFeedback
    Created on : Jan 17, 2025, 10:26:47 PM
    Author     : Tra Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Create Feedback</h1>
        <form action="CreateFeedback" method="post">
            <table >
                <tbody>
                    <tr>
                        <td>Customer ID: </td>
                        <td><input name="customerId" type="text" value="" readonly=""></td>
                    </tr>
                    <tr>
                        <td>Warranty Card ID: </td>
                        <td><input name="warrantyCardId" type="text" value="" readonly=""></td>
                    </tr>
                    <tr>
                        <td>Note: </td>
                        <td><input name="note" type="text"></td>
                    </tr>
                    <tr>
                        <td><button type="submit"> Submit </button></td>
                    </tr>
                </tbody>
            </table>
            
        </form>
    </body>
</html>
