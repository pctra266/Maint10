<%-- 
    Document   : updateProduct1
    Created on : Feb 6, 2025, 5:07:44 AM
    Author     : sonNH
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            /* Reset cơ bản */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            /* Phông chữ và màu nền chung */
            body {
                font-family: 'Inter', sans-serif;
                background-color: #f5f6fa;
                color: #333;
                line-height: 1.6;
            }

            /* Layout chính với thanh sidebar và nội dung */
            .wrapper {
                display: flex;
                min-height: 100vh;
            }

            /* Sidebar (giữ tông màu ban đầu) */
            .wrapper > .jspIncludeSidebar,
            .wrapper > .navbar-left {
                width: 250px;
                background-color: #2c3e50;
                color: #fff;
                padding: 20px;
            }

            /* Phần main (nội dung chính) */
            .main {
                flex: 1;
                display: flex;
                flex-direction: column;
            }

            /* Thanh navbar top */
            .navbar-top {
                background-color: #fff;
                padding: 15px 20px;
                border-bottom: 1px solid #ddd;
            }

            /* Nội dung chính */
            .content {
                flex: 1;
                padding: 20px;
            }

            /* Tiêu đề form với màu xanh dương chủ đạo */
            .content h2 {
                margin-bottom: 20px;
                font-size: 24px;
                color: #007BFF;
            }

            /* Các thông báo lỗi và thành công */
            .content .error,
            .content .success {
                text-align: center;
                margin-bottom: 15px;
                padding: 10px;
                border-radius: 4px;
            }
            .content .error {
                background-color: #f8d7da;
                color: #a94442;
            }
            .content .success {
                background-color: #d4edda;
                color: #40754c;
            }

            /* Form */
            form {
                background-color: #fff;
                padding: 25px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }

            /* Label và input */
            form label {
                display: block;
                margin-bottom: 5px;
                font-weight: 600;
                color: #34495e;
            }
            form input[type="number"],
            form input[type="date"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 16px;
            }

            /* Button submit: sử dụng màu xanh dương chủ đạo */
            .btn {
                display: inline-block;
                padding: 10px 20px;
                background-color: #007BFF;
                color: #fff;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s ease;
                text-align: center;
            }
            .btn:hover {
                background-color: #0056b3;
            }

            /* Footer */
            footer {
                background-color: #fff;
                text-align: center;
                padding: 15px 20px;
                border-top: 1px solid #ddd;
                margin-top: 20px;
            }

            /* Responsive cho màn hình nhỏ */
            @media (max-width: 768px) {
                .wrapper {
                    flex-direction: column;
                }
                .navbar-left, .jspIncludeSidebar {
                    width: 100%;
                }
                .main {
                    margin-left: 0;
                }
            }
            .btn-export {
                display: inline-block;
                padding: 10px 20px;
                background-color: #007BFF; /* Màu xanh dương chủ đạo */
                color: #fff;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .btn-export i {
                margin-right: 8px;
            }

            .btn-export:hover {
                background-color: #0056b3;
            }

        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <c:if test="${not empty errorMessage}">
                        <div style="color: red; text-align: center; margin-bottom: 10px;">
                            ${errorMessage}
                        </div>
                    </c:if>
                    <c:if test="${not empty successMessage}">
                        <div style="color: green; text-align: center; margin-bottom: 10px;">
                            ${successMessage}
                        </div>
                    </c:if>

                    <h2 style="text-align: center">Create Invoice for Repair Contractor</h2>
                    <form action="repairCreateInvoice" method="get" onsubmit="return validateAmount();">
                        <label for="amount">Amount:</label>
                        <input type="number" id="amount" name="amount" step="0.01" required min="0" /><br/><br/>

                        <label for="dueDate">Due Date:</label>
                        <input type="date" id="dueDate" name="dueDate" required 
                               min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" /><br/><br/>

                        <input type="hidden" name="invoiceType" value="RepairContractorToTechnician" />
                        <input type="hidden" name="status" value="pending" />

                        <input type="hidden" name="staffId" value="${param.staffId}" />
                        <input type="hidden" name="contractorCardID" value="${param.code}" />
                        <input type="hidden" name="warrantyCardID" value="${param.cardId}" />

                        <button type="submit" class="btn btn-invoice">Create Invoice</button>
                    
                    </form>

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script>
                        function validateAmount() {
                            var amountInput = document.getElementById("amount");
                            if (amountInput.value.trim() === "") {
                                alert("Amount không được nhập toàn khoảng trắng.");
                                amountInput.focus();
                                return false;
                            }
                            return true;
                        }
        </script>

    </body>
</html>
