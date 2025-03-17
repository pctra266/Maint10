<%-- 
    Document   : viewProduct
    Created on : Feb 21, 2025, 11:49:16 PM
    Author     : sonNH
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%! 
    private static final String[] CHU = {"không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};

    // Hàm chuyển đổi số sang chữ (dành cho số dương)
    public String convertNumberToVietnameseWords(long number) {
        if (number < 0) {
            return "Không hợp lệ";
        }
        if (number == 0) {
            return "không";
        }
        String[] units = {"", " triệu", " tỷ"}; // mảng chứa đơn vị sau mỗi nhóm 3  số
        int unitIndex = 0; // biến chỉ số đơn vị

        String result = "";

        while (number > 0) {
            int n = (int)(number % 1000); // lấy ra 3 chữ số cuối cùng
            if(n != 0) {
                result = convertLessThanOneThousand(n) + units[unitIndex] + " " + result; // chuyển 3 chữ số này thành chữ và ghép dơn vị
            }
            number /= 1000; // bỏ đi 3 chữ số vừa xử lí, tăng đơn vị
            unitIndex++;
        }
        return result.trim();// cắt khoảng trống 2 đầu của chuỗi
    }

    // Hàm chuyển đổi số nhỏ hơn 1000 sang chữ
    public String convertLessThanOneThousand(int number) {
        String result = "";
        int hundred = number / 100; // lấy hàng trăm
        int tenUnit = number % 100; // lấy hàng chục và đơn vị 

        if (hundred > 0) {
            result += CHU[hundred] + " trăm";

            if(tenUnit < 10 && tenUnit > 0) {
                result += " lẻ";
            }

        }
        if (tenUnit >= 10) {
            int ten = tenUnit / 10; // lấy số hàng chục
            int unit = tenUnit % 10; // lấy số hàng đơn vị
            if(ten == 1) { 
                result += " mười";
            } else {
                result += " " + CHU[ten] + " mươi";
            }
            if(unit > 0) {
                if(unit == 1 && ten > 1) {
                    result += " mốt";
                } else if(unit == 5) {
                    result += " lăm";
                } else {
                    result += " " + CHU[unit];
                }
            }
        } else if(tenUnit > 0) {
            result += " " + CHU[tenUnit];
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
                            <!-- Cột trái: Form tìm kiếm -->
                            <div class="col-md-4">
                                <h2>Search Warranty Card</h2>
                                <form action="searchwc" method="post">
                                    <div class="form-group">
                                        <label for="warrantyCode">Enter Warranty Card Code:</label>
                                        <input type="text" class="form-control" id="warrantyCode" name="warrantyCode" placeholder="Ví dụ: WC12345" required>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Search</button>
                                </form>
                            </div>
                            <div class="col-md-8">
                                <div class="pdf-preview">
                                    <div class="pdf-header">Warranty Card Preview</div>
                                    <div class="pdf-content">
                                        <!-- Mục 1: Thông tin cửa hàng/sửa chữa -->
                                        <div style="display: flex; justify-content: space-around" class="store-info">
                                            <div>
                                                <p><strong>Store:</strong> ABC Electronics</p>
                                                <p><strong>Address:</strong> 123 Đường XYZ, TP. HCM</p>
                                                <p><strong>Contact:</strong> 0901 234 567</p>
                                                <p><strong>Email:</strong> support@abc-electronics.com</p>
                                            </div>
                                            <div>
                                                <p><strong>Form:</strong> ABC Electronics</p>
                                                <p><strong>Serial:</strong> 123 Đường XYZ, TP. HCM</p>
                                                <p><strong>No:</strong> 0901 234 567</p>
                                            </div>
                                        </div>
                                        <hr>
                                        <!-- Kiểm tra nếu có lỗi -->
                                        <c:if test="${not empty error}">
                                            <p style="color: red;">${error}</p>
                                        </c:if>
                                        <!-- Thông tin hóa đơn bảo hành -->
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
                                                <!-- Mục 2: Thông tin khách hàng -->
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
                                                <!-- Mục 4: Danh sách linh kiện bảo hành -->
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
                                                            <!-- Khởi tạo grandTotal dưới dạng số -->
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
                                                                <!-- Cộng dồn giá; nếu detail.totalPrice là kiểu double/float thì vẫn được -->
                                                                <c:set var="grandTotal" value="${grandTotal + detail.totalPrice}" scope="page" />
                                                            </c:forEach>
                                                            <!-- Dòng hiển thị tổng số dạng số -->
                                                            <tr>
                                                                <td colspan="7" style="text-align:right"><strong>Total:</strong></td>
                                                                <td>${grandTotal}</td>
                                                            </tr>
                                                            <!-- Dòng hiển thị tổng số dạng chữ -->
                                                            <tr>
                                                                <td colspan="7" style="text-align:right"><strong>Total (Bằng chữ): </strong></td>
                                                                <td>
                                                                    <%
                                                                        Object gtObj = pageContext.getAttribute("grandTotal");
                                                                        long total = 0;
                                                                        if (gtObj != null) {
                                                                            try {
                                                                                // 1) Parse thành double
                                                                                double doubleVal = Double.parseDouble(gtObj.toString());
                                                                                // 2) Ép về long nếu chỉ cần lấy phần nguyên
                                                                                total = (long) doubleVal;
                                                                            } catch (Exception e) {
                                                                                total = 0;
                                                                            }
                                                                        }
                                                                        out.print(convertNumberToVietnameseWords(total));
                                                                    %>
                                                                    nghìn đồng
                                                                </td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <tr>
                                                                <td colspan="9">Không có dữ liệu.</td>
                                                            </tr>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </table>
                                                <hr>
                                                <div style="display: flex; justify-content: space-between">
                                                    <h4>Signature Confirmation</h4>
                                                    <h4>Ha Noi, ${day}/${month}/${year}</h4>
                                                </div>
                                                <!-- Mục 8: Chữ ký/Xác nhận -->
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
                        <form action="exportpdf" method="get">
                            <input type="hidden" name="warrantyCardCode" value="${warrantyCard.warrantyCardCode}" />
                            <button type="submit" class="btn btn-success">Export PDF</button>
                        </form>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
