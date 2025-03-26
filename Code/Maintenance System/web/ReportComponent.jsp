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
      .report-container {
        display: flex;
        min-height: 100vh;
        padding: 20px;
        gap: 20px;
      }
      .report-layout {
            display: flex;
            gap: 20px;
            margin: 20px 0;
        }
        .left-panel {
            flex: 0 0 35%;
            background-color: #ffffff;
            border: 1px solid #ccc;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
        }
        .report-btn {
            background-color: #29b6f6;
            color: #fff;
            border: 2px solid transparent;
            padding: 12px 16px;
            font-size: 1rem;
            font-weight: 600;
            border-radius: 8px;
            width: 100%;
            margin-bottom: 12px;
            cursor: pointer;
            transition: background-color 0.3s ease, border-color 0.3s ease;
        }
        .report-btn:hover {
            background-color: #0288d1;
            border-color: #0288d1;
        }
        /* Panel bên phải */
        .right-panel {
            flex: 1;
            background-color: #ffffff;
            border: 1px solid #ccc;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
        }
        /* Bảng báo cáo */
        .report-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            overflow: hidden;
        }
        .report-table thead {
            background-color: #0288d1;
            color: #fff;
            text-align: center;
        }
        .report-table th,
        .report-table td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ccc;
        }
        .invoice-table-btn {
            background-color: #29b6f6;
            border: none;
            color: #fff;
            padding: 8px 12px;
            border-radius: 8px;
            transition: background-color 0.3s ease;
        }
        .invoice-table-btn:hover {
            background-color: #0288d1;
        }
        /* Panel bên phải */
.right-panel {
    flex: 1;
    background-color: #ffffff;
    border: 1px solid #ccc;
    border-radius: 12px;
    padding: 20px;
    box-shadow: 0 4px 10px rgba(0,0,0,0.05);
}

/* Container cho mỗi báo cáo */
.report-container {
    background-color: #fff;
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
    padding: 20px;
    margin-bottom: 20px;
}

/* Tiêu đề báo cáo */
.report-title {
    font-size: 1.4rem;
    color: #0277bd; /* Xanh nước đậm */
    margin-bottom: 20px;
    font-weight: 600;
}

/* Bảng báo cáo */
.report-table {
    width: 100%;
    border-collapse: collapse;
    border: 1px solid #ccc;
    border-radius: 8px;
    overflow: hidden;
}

.report-table thead {
    background-color: #0288d1; /* Header màu xanh đậm */
    color: #fff;
    text-align: center;
}

.report-table th,
.report-table td {
    padding: 12px;
    text-align: center;
    border: 1px solid #ccc;
    font-size: 0.95rem;
}

.report-table tbody tr:nth-child(even) {
    background-color: #f2f9fc; 
}

.report-empty {
    color: #d32f2f; 
    font-weight: 600;
    margin-top: 20px;
}
    </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">   
                    <div class="report-layout">
                    <div class="left-panel">
                        <form action="ReportComponentController" method="post">
                            <input type="hidden" name="action" value="Top10">
                            <button type="submit" class="report-btn">Top 10 Components with Highest Inventory Quantity</button>                    
                        </form>
                        <form action="ReportComponentController" method="post">
                            <input type="hidden" name="action" value="Type">
                            <button type="submit" class="report-btn">Components Statistics By Type</button>                    
                        </form>
                        <form action="ReportComponentController" method="post">
                            <input type="hidden" name="action" value="Summary">
                            <button type="submit" class="report-btn">Components Summary Report</button>                    
                        </form>
                        <form action="ReportComponentController" method="post">
                            <input type="hidden" name="action" value="Brand">
                            <button type="submit" class="report-btn">Components Statistics By Brand</button>                    
                        </form>
                        <form action="ReportComponentController" method="post">
                            <input type="hidden" name="action" value="Low">
                            <button type="submit" class="report-btn">Low Inventory Parts Report</button>                    
                        </form>
                        <form action="ReportComponentController" method="post">
                            <input type="hidden" name="action" value="Out">
                            <button type="submit" class="report-btn">Out of Stock Parts Report</button>                    
                        </form>
                        <form action="ReportComponentController" method="post">
                            <input type="hidden" name="action" value="Most">
                            <button type="submit" class="report-btn">Most Used Components In Products</button>                    
                        </form>
                        <form action="ReportComponentController" method="post">
                            <input type="hidden" name="action" value="Price">
                            <button type="submit" class="report-btn">Average Price By Component Type</button>                    
                        </form>
                    </div>

                <div class="right-panel" id="content-display">
                    <c:if test="${action eq 'Top10'}">
                        <div class="report-container">
                            <table class="report-table">
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
                        </div>
                    </c:if>
                    <c:if test="${action eq 'Type'}">
                        <div class="report-container">
                            <table class="report-table">
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
                        </div>
                    </c:if>
                    <c:if test="${action eq 'Summary'}">
                        <div class="report-container">
                            <table class="report-table">
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
                        </div>
                    </c:if>
                    <c:if test="${action eq 'Brand'}">
                        <div class="report-container">
                            <table class="report-table">
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
                        </div>
                    </c:if>
                    <c:if test="${action eq 'Low'}">
                        <c:if test="${empty list or fn:length(list) == 0}">
                            <h3 class="report-empty">Không có giá trị nào phù hợp trong list này.</h3>
                        </c:if>
                        <c:if test="${empty list and fn:length(list) > 0}">
                            <div class="report-container">
                                <table class="report-table">
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
                            </div>
                        </c:if>
                    </c:if>
                    <c:if test="${action eq 'Out'}">
                        <c:if test="${empty list or fn:length(list) == 0}">
                            <h3 class="report-empty">Không có giá trị nào phù hợp trong list này.</h3>
                        </c:if>
                        <c:if test="${empty list and fn:length(list) > 0}">
                            <div class="report-container">
                                <table class="report-table">
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
                            </div>
                        </c:if>
                    </c:if>
                    <c:if test="${action eq 'Most'}">
                        <div class="report-container">
                            <table class="report-table">
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
                        </div>
                    </c:if>
                    <c:if test="${action eq 'Price'}">
                        <div class="report-container">
                            <table class="report-table">
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
                        </div>
                    </c:if>
                </div>
        </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>
</html>
