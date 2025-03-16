<%-- 
    Document   : viewProduct
    Created on : Feb 21, 2025, 11:49:16 PM
    Author     : sonNH (Modified to English)
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%!
    // Arrays for number to English words conversion
    private static final String[] belowTwenty = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static final String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private static final String[] englishUnits = {"", "thousand", "million", "billion"};

    // Converts a positive number into English words.
    public String convertNumberToEnglishWords(long number) {
        if (number < 0) {
            return "Not valid";
        }
        if (number == 0) {
            return "zero";
        }
        String words = "";
        int unitIndex = 0;
        while (number > 0) {
            int n = (int)(number % 1000); // extract last three digits
            if (n != 0) {
                String s = convertLessThanOneThousandEnglish(n);
                if (!s.isEmpty()) {
                    words = s + " " + englishUnits[unitIndex] + " " + words;
                }
            }
            number /= 1000;
            unitIndex++;
        }
        return words.trim();
    }

    // Converts a number less than 1000 into English words.
    public String convertLessThanOneThousandEnglish(int number) {
        String result = "";
        if (number >= 100) {
            result += belowTwenty[number / 100] + " hundred";
            number %= 100;
            if (number > 0) {
                result += " ";
            }
        }
        if (number >= 20) {
            result += tens[number / 10];
            if (number % 10 > 0) {
                result += " " + belowTwenty[number % 10];
            }
        } else if (number > 0) {
            result += belowTwenty[number];
        }
        return result;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            /* --- Global Styles --- */
            body {
                font-family: 'Inter', sans-serif;
                background-color: #f9fafb;
                color: #333;
                margin: 0;
                padding: 0;
            }
            .wrapper {
                display: flex;
                min-height: 100vh;
            }
            .main {
                flex: 1;
                display: flex;
                flex-direction: column;
                background-color: #f9fafb;
            }
            main.content {
                flex: 1;
                padding: 20px;
                margin-top: 20px;
            }
            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px;
            }
            form {
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            }
            form h2 {
                margin-bottom: 15px;
                color: #2c3e50;
            }
            .form-group {
                margin-bottom: 15px;
            }
            .form-group label {
                font-weight: 600;
                display: block;
                margin-bottom: 5px;
                color: #2c3e50;
            }
            .form-group input {
                width: 100%;
                padding: 10px;
                font-size: 14px;
                border: 1px solid #dcdcdc;
                border-radius: 4px;
            }
            .btn {
                display: inline-block;
                padding: 10px 20px;
                font-size: 14px;
                font-weight: 600;
                color: #fff;
                background-color: #007bff;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
            .btn:hover {
                background-color: #0056b3;
            }
            .pdf-preview {
                background-color: #fff;
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 20px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                margin-bottom: 20px;
            }
            .pdf-header {
                font-size: 20px;
                font-weight: 700;
                text-align: center;
                margin-bottom: 15px;
                color: #2c3e50;
            }
            .pdf-content p {
                margin: 8px 0;
                line-height: 1.6;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
            }
            table th,
            table td {
                padding: 12px;
                text-align: left;
                border: 1px solid #ddd;
            }
            table th {
                background-color: #f5f5f5;
                font-weight: 600;
            }
            .signature-section {
                display: flex;
                align-items: center;
                gap: 20px;
                margin-top: 20px;
            }
            .signature-box {
                border: 2px dashed #ccc;
                width: 200px;
                height: 50px;
                border-radius: 4px;
            }
            @media (max-width: 768px) {
                .container {
                    padding: 10px;
                }
                .signature-section {
                    flex-direction: column;
                    align-items: flex-start;
                }
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <div class="container mt-4">
                        <div class="row">
                            <!-- Left Column: Search Form -->
                            <div class="col-md-4">
                                <h2>Search Warranty Card</h2>
                                <form action="searchwc" method="post">
                                    <div class="form-group">
                                        <label for="warrantyCode">Enter Warranty Card Code:</label>
                                        <input type="text" class="form-control" id="warrantyCode" name="warrantyCode" placeholder="e.g., WC12345" required pattern="[A-Za-z0-9]+" title="Please enter only letters and numbers without spaces">
                                    </div>
                                    <button type="submit" class="btn btn-primary">Search</button>
                                </form>
                            </div>
                            <div class="col-md-8">
                                <div class="pdf-preview">
                                    <div class="pdf-header">Warranty Card Preview</div>
                                    <div class="pdf-content">
                                        <!-- Section 1: Store/Repair Information -->
                                        <div style="display: flex; justify-content: space-around" class="store-info">
                                            <div>
                                                <p><strong>Store:</strong> ABC Electronics</p>
                                                <p><strong>Address:</strong> 123 XYZ Street, Ho Chi Minh City</p>
                                                <p><strong>Contact:</strong> 0901 234 567</p>
                                                <p><strong>Email:</strong> support@abc-electronics.com</p>
                                            </div>
                                            <div>
                                                <p><strong>Branch:</strong> ABC Electronics</p>
                                                <p><strong>Serial:</strong> 123 XYZ Street, Ho Chi Minh City</p>
                                                <p><strong>No:</strong> 0901 234 567</p>
                                            </div>
                                        </div>
                                        <hr>
                                        <!-- Check for errors -->
                                        <c:if test="${not empty error}">
                                            <p style="color: red;">${error}</p>
                                        </c:if>
                                        <!-- Warranty Card Information -->
                                        <c:if test="${not empty warrantyCard}">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <p><strong>Warranty Card Code:</strong> ${warrantyCard.warrantyCardCode}</p>
                                                    <p><strong>Date Created:</strong> ${warrantyCard.createdDate}</p>
                                                    <p><strong>Return Date:</strong> ${warrantyCard.completedDate}</p>
                                                </div>
                                                <div class="col-md-6">
                                                    <p><strong>Staff In Charge:</strong> ${staff.name}</p>
                                                    <p><strong>Staff Phone:</strong> ${staff.phone}</p>
                                                    <p><strong>Staff Email:</strong> ${staff.email}</p>
                                                </div>
                                                <hr>
                                                <!-- Section 2: Customer Information -->
                                                <div class="customer-info" style="display: flex; justify-content: space-between">
                                                    <div>
                                                        <p><strong>Customer Name:</strong> ${customer.name}</p>
                                                        <p><strong>Date of Birth:</strong> 
                                                            <fmt:formatDate value="${customer.dateOfBirth}" pattern="dd-MM-yyyy" />
                                                        </p>
                                                        <p><strong>Customer Phone:</strong> ${customer.phone}</p>
                                                        <p><strong>Email:</strong> ${customer.email}</p>
                                                        <p><strong>Address:</strong> ${customer.address}</p>
                                                    </div>
                                                    <div>
                                                        <c:choose>
                                                            <c:when test="${isUnknownProduct}">
                                                                <p><strong>Product Name:</strong> ${product.productName}</p>
                                                                <p><strong>Product Code:</strong> ${product.productCode}</p>
                                                                <p><strong>Description:</strong> ${product.description}</p>
                                                                <p><strong>Received Date:</strong> ${product.receivedDate}</p>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <p><strong>Product Name:</strong> ${product.productName}</p>
                                                                <p><strong>Product Code:</strong> ${product.code}</p>
                                                                <p><strong>Brand:</strong> ${product.brandName}</p>
                                                                <p><strong>Type:</strong> ${product.productTypeName}</p>
                                                                <p><strong>Purchased Date:</strong> ${product.purchaseDate}</p>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                                <hr>
                                                <!-- Section 4: Component List -->
                                                <table border="1">
                                                    <tr>
                                                        <th>Component Code</th>
                                                        <th>Component Name</th>
                                                        <th>Brand Name</th>
                                                        <th>Type Name</th>
                                                        <th>Price Per Piece</th>
                                                        <th>Number of Uses</th>
                                                        <th>Note</th>
                                                        <th>Total Price</th>
                                                    </tr>
                                                    <c:choose>
                                                        <c:when test="${not empty component}">
                                                            <!-- Initialize grandTotal as a number -->
                                                            <c:set var="grandTotal" value="${0}" scope="page" />
                                                            <c:forEach var="detail" items="${component}">
                                                                <tr>
                                                                    <td>${detail.ComponentCode}</td>
                                                                    <td>${detail.ComponentName}</td>
                                                                    <td>${detail.BrandName}</td>
                                                                    <td>${detail.TypeName}</td>
                                                                    <td>${detail.pricePerPiece}</td>
                                                                    <td>${detail.numberOfUses}</td>
                                                                    <td>${detail.Note}</td>
                                                                    <td>${detail.totalPrice}</td>
                                                                </tr>
                                                                <!-- Sum up total; works with numeric values -->
                                                                <c:set var="grandTotal" value="${grandTotal + detail.totalPrice}" scope="page" />
                                                            </c:forEach>
                                                            <!-- Row showing numeric total -->
                                                            <tr>
                                                                <td colspan="7" style="text-align:right"><strong>Total:</strong></td>
                                                                <td>${grandTotal}</td>
                                                            </tr>

                                                            <tr>
                                                                <td colspan="8" style="text-align:right; white-space: nowrap;">
                                                                    <strong>Total (in words): </strong>
                                                                    <%
                                                                        Object gtObj = pageContext.getAttribute("grandTotal");
                                                                        long total = 0;
                                                                        if (gtObj != null) {
                                                                            try {
                                                                                double doubleVal = Double.parseDouble(gtObj.toString());
                                                                                total = (long) doubleVal;
                                                                            } catch (Exception e) {
                                                                                total = 0;
                                                                            }
                                                                        }
                                                                        out.print(convertNumberToEnglishWords(total));
                                                                    %>
                                                                    dollars
                                                                </td>
                                                            </tr>

                                                        </c:when>

                                                        <c:otherwise>
                                                            <tr>
                                                                <td colspan="9">No data available.</td>
                                                            </tr>
                                                        </c:otherwise>

                                                    </c:choose>
                                                </table>
                                                <hr>
                                                <div style="display: flex; justify-content: space-between">
                                                    <h4>Signature Confirmation</h4>
                                                    <h4>Hanoi, ${day}/${month}/${year}</h4>
                                                </div>
                                                <!-- Section 8: Signatures/Confirmation -->
                                                <div style="display: flex; gap: 20px; align-items: center" class="signature-section">
                                                    <p><strong>Customer:</strong></p>
                                                    <div class="signature-box"></div>
                                                    <p><strong>Technical Staff:</strong></p>
                                                    <div class="signature-box"></div>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div style="display: flex; gap: 10px; align-items: center;">
                            <form action="exportpdf" method="get">
                                <input type="hidden" name="warrantyCardCode" value="${warrantyCard.warrantyCardCode}" />
                                <button type="submit" class="btn btn-success">Export PDF</button>
                            </form>

                            <form action="sendEmail" method="post">
                                <input type="hidden" name="warrantyCardCode" value="${warrantyCard.warrantyCardCode}" />
                                <input type="hidden" name="customerEmail" value="${customer.email}" />
                                <button type="submit" class="btn btn-info">Gửi Email Hợp Đồng</button>
                            </form>
                        </div>


                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
