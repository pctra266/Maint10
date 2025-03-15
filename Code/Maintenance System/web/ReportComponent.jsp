<%-- 
    Document   : ReportComponent
    Created on : Mar 15, 2025, 3:31:35 AM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link href="css/light.css" rel="stylesheet">

        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

        <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #f8f9fa;
        margin: 0;
        padding: 0;
      }
      /* Container bọc chung */
      .report-container {
        display: flex;
        min-height: 100vh;
        padding: 20px;
        gap: 20px;
      }
      /* Cột bên trái chiếm 30% */
      .left {
        flex: 3; /* tương đương với 30% nếu tổng flex là 10 */
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 15px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      }
      /* Cột bên phải chiếm 70% */
      .content {
        flex: 7; /* tương đương với 70% */
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 15px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      }
      /* Styling cho các nút trong sidebar */
      .left form {
        margin-bottom: 15px;
      }
      .left form button {
        width: 100%;
        padding: 10px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background 0.3s ease;
      }
      .left form button:hover {
        background-color: #0056b3;
      }
      /* Table styling */
      table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
      }
      table thead {
        background-color: #007bff;
        color: #fff;
      }
      table th, table td {
        padding: 10px;
        text-align: left;
        border-bottom: 1px solid #ddd;
      }
      table tbody tr:nth-child(even) {
        background-color: #f2f2f2;
      }
    </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">                    
                    <div class="left">
                    <form action="ReportComponentController" method="post">
                        <input type="hidden" name="action" value="Top10">
                        <button type="submit">Top 10 Components with Highest Inventory Quantity</button>                    
                    </form>
                    <form action="ReportComponentController" method="post">
                        <input type="hidden" name="action" value="Type">
                        <button type="submit">Components Statistics By Type</button>                    
                    </form>
                    <form action="ReportComponentController" method="post">
                        <input type="hidden" name="action" value="Summary">
                        <button type="submit">Components Summary Report</button>                    
                    </form>
                    <form action="ReportComponentController" method="post">
                        <input type="hidden" name="action" value="Brand">
                            <button type="submit">Components Statistics By Brand</button>                    
                    </form>
                    <form action="ReportComponentController" method="post">
                        <input type="hidden" name="action" value="Low">
                        <button type="submit">Low Inventory Parts Report</button>                    
                    </form>
                    <form action="ReportComponentController" method="post">
                        <input type="hidden" name="action" value="Out">
                        <button type="submit">Out of Stock Parts Report</button>                    
                    </form>
                    <form action="ReportComponentController" method="post">
                        <input type="hidden" name="action" value="Most">
                        <button type="submit">Most Used Components In Products</button>                    
                    </form>
                    <form action="ReportComponentController" method="post">
                        <input type="hidden" name="action" value="Price">
                        <button type="submit">Average Price By Component Type</button>                    
                    </form>
                </div>
                <div class="content" id="content-display">        
                    <c:if test="${action eq 'Top10'}">
                        <table>
                            <h3>List for quantity</h3>
                            <thead>
                                <tr>
                                    <th>ComponentID</th>
                                    <th>ComponentName</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>InventoryValue</th>
                                </tr>   
                            </thead>
                            <tbody>
                                <c:forEach var="List" items="${list}">
                                    <tr>
                                        <td># ${List.getComponentid()}</td>
                                        <td>${List.getComponentname()}</td>                                                                        
                                        <td>${List.getQuanity()}</td>
                                        <td>${List.getPrice()}</td>
                                        <td>${List.getInventoryValue()}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    
                    <c:if test="${action eq 'Type'}">
                        <table>
                            <h3>List for quantity</h3>
                            <thead>
                                <tr>
                                    <th>Type Name</th>
                                    <th>Number Components</th>
                                    <th>Total Quantity</th>
                                    <th>Total Value</th>
                                </tr>   
                            </thead>
                            <tbody>
                                <c:forEach var="List" items="${list}">
                                    <tr>
                                        <td># ${List.getComponentid()}</td>
                                        <td>${List.getComponentname()}</td>                                                                        
                                        <td>${List.getQuanity()}</td>
                                        <td>${List.getPrice()}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${action eq 'Summary'}">
                        <table>
                            <h3>List for quantity</h3>
                            <thead>
                                <tr>
                                    <th>TotalComponents</th>
                                    <th>Total Quantity</th>
                                    <th>TotalInventoryValue</th>
                                </tr>   
                            </thead>
                            <tbody>
                                <c:forEach var="List" items="${list}">
                                    <tr>
                                        <td># ${List.getComponentid()}</td>
                                        <td>${List.getQuanity()}</td>
                                        <td>${List.getInventoryValue()}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${action eq 'Brand'}">
                        <table>
                            <h3>List for quantity</h3>
                            <thead>
                                <tr>
                                    <th>Brand Name</th>
                                    <th>Number Components</th>
                                    <th>Total Quantity</th>
                                    <th>Total Value</th>
                                </tr>   
                            </thead>
                            <tbody>
                                <c:forEach var="List" items="${list}">
                                    <tr>
                                        <td># ${List.getComponentid()}</td>
                                        <td>${List.getComponentname()}</td>                                                                        
                                        <td>${List.getQuanity()}</td>
                                        <td>${List.getPrice()}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${action eq 'Low'}">
                        <c:if test="${empty list or fn:length(list) == 0}">
                            <h3>Không có giá trị nào phù hợp trong list này.</h3>
                        </c:if>
                        <c:if test="${empty list and fn:length(list) > 0}">
                            <table>
                                <h3>List for quantity</h3>
                                <thead>
                                    <tr>
                                        <th>ComponentID</th>
                                        <th>ComponentName</th>
                                        <th>Quantity</th>
                                        <th>Price</th>
                                        <th>InventoryValue</th>
                                    </tr>   
                                </thead>
                                <tbody>
                                    <c:forEach var="List" items="${list}">
                                        <tr>
                                            <td># ${List.getComponentid()}</td>
                                            <td>${List.getComponentname()}</td>                                                                        
                                            <td>${List.getQuanity()}</td>
                                            <td>${List.getPrice()}</td>
                                            <td>${List.getInventoryValue()}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                        
                    </c:if>
                    <c:if test="${action eq 'Out'}">
                        <c:if test="${empty list or fn:length(list) == 0}">
                            <h3>Không có giá trị nào phù hợp trong list này.</h3>
                        </c:if>
                        <c:if test="${empty list and fn:length(list) > 0}">
                            <table>
                                <h3>List for quantity</h3>
                                <thead>
                                    <tr>
                                        <th>ComponentID</th>
                                        <th>ComponentName</th>
                                        <th>Quantity</th>
                                        <th>Price</th>
                                        <th>InventoryValue</th>
                                    </tr>   
                                </thead>
                                <tbody>
                                    <c:forEach var="List" items="${list}">
                                        <tr>
                                            <td># ${List.getComponentid()}</td>
                                            <td>${List.getComponentname()}</td>                                                                        
                                            <td>${List.getQuanity()}</td>
                                            <td>${List.getPrice()}</td>
                                            <td>${List.getInventoryValue()}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                    </c:if>
                    <c:if test="${action eq 'Most'}">
                        <table>
                            <h3>List for quantity</h3>
                            <thead>
                                <tr>
                                    <th>Brand Name</th>
                                    <th>Number Components</th>
                                    <th>Total Used</th>
                                </tr>   
                            </thead>
                            <tbody>
                                <c:forEach var="List" items="${list}">
                                    <tr>
                                        <td># ${List.getComponentid()}</td>
                                        <td>${List.getComponentname()}</td>                                                                        
                                        <td>${List.getQuanity()}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${action eq 'Price'}">
                        <table>
                            <h3>List for quantity</h3>
                            <thead>
                                <tr>
                                    <th>Type Name</th>
                                    <th>AveragePrice</th>
                                </tr>   
                            </thead>
                            <tbody>
                                <c:forEach var="List" items="${list}">
                                    <tr>
                                        <td># ${List.getComponentid()}</td>
                                        <td>${List.getQuanity()}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>                     
                
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>
</html>
