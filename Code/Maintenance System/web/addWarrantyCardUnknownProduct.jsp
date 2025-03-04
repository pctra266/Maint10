<%-- 
    Document   : viewProduct
    Created on : Feb 21, 2025, 11:49:16 PM
    Author     : sonNH
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">


    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">


                    <h2>Create Warranty Card</h2>

                    <c:if test="${not empty errorMessage}">
                        <div style="color: red; font-weight: bold;">${errorMessage}</div>
                    </c:if>

                    <!-- Product Information -->
                    <h3>Product Information</h3>
                    <table border="1">
                        <tr><th>Product Name</th><td>${unknownProduct.productName}</td></tr>
                        <tr><th>Product Code</th><td>${unknownProduct.productCode}</td></tr>
                        <tr><th>Description</th><td>${unknownProduct.description}</td></tr>
                        <tr><th>Received Date</th><td>${unknownProduct.receivedDate}</td></tr>
                    </table>

                    <!-- Customer Information -->
                    <h3>Customer Information</h3>
                    <table border="1">
                        <tr><th>Name</th><td>${customer.name}</td></tr>
                        <tr><th>Gender</th><td>${customer.gender}</td></tr
                        <tr><th>Email</th><td>${customer.email}</td></tr>
                        <tr><th>Phone</th><td>${customer.phone}</td></tr>
                        <tr><th>Address</th><td>${customer.address}</td></tr>
                    </table>

                    <!-- Warranty Card Form -->
                    <h3>Enter Warranty Card Details</h3>
                    <form action="addWUP" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="customerId" value="${customer.customerID}" />
                        <input type="hidden" name="productId" value="${unknownProduct.unknownProductId}" />
                        <input type="hidden" name="type" value="create" />

                        <input type="hidden" name="warrantyProductId" value="${warrantyProductId}" />


                        <!-- Nhập mã bảo hành -->
                        <label>Warranty Card Code:</label>
                        <input type="text" name="warrantyCardCode" required/><br/>

                        <!-- Chọn nhân viên sửa -->
                        <label>Assigned Staff:</label>
                        <select name="staffId" required>
                            <c:forEach var="staff" items="${staffList}">
                                <option value="${staff.staffID}">${staff.name}</option>
                            </c:forEach>
                        </select><br/>

                        <!-- Mô tả vấn đề -->
                        <label>Issue Description:</label>
                        <textarea name="issueDescription" required></textarea><br/>

                        <label>Create Date:</label>
                        <input type="datetime-local" name="createDate" id="createDate"/><br/>

                        <!-- Ngày dự kiến trả -->
                        <label>Expected Return Date:</label>
                        <select name="returnDateOption" onchange="toggleDateInput('returnDate')">
                            <option value="">Chưa xác định</option>
                            <option value="select">Chọn ngày</option>
                        </select>
                        <input type="datetime-local" name="returnDate" id="returnDate" style="display: none;" /><br/>

                        <!-- Ngày sửa xong -->
                        <label>Done Date:</label>
                        <select name="doneDateOption" onchange="toggleDateInput('doneDate')">
                            <option value="">Chưa xác định</option>
                            <option value="select">Chọn ngày</option>
                        </select>
                        <input type="datetime-local" name="doneDate" id="doneDate" style="display: none;" /><br/>

                        <!-- Ngày hoàn thành -->
                        <label>Complete Date:</label>
                        <select name="completeDateOption" onchange="toggleDateInput('completeDate')">
                            <option value="">Chưa xác định</option>
                            <option value="select">Chọn ngày</option>
                        </select>
                        <input type="datetime-local" name="completeDate" id="completeDate" style="display: none;" /><br/>

                        <!-- Ngày hủy -->
                        <label>Cancel Date:</label>
                        <select name="cancelDateOption" onchange="toggleDateInput('cancelDate')">
                            <option value="">Chưa xác định</option>
                            <option value="select">Chọn ngày</option>
                        </select>
                        <input type="datetime-local" name="cancelDate" id="cancelDate" style="display: none;" /><br/>

                        <!-- Trạng thái bảo hành -->
                        <label>Warranty Status:</label>
                        <select name="warrantyStatus">
                            <option value="waiting">Waiting</option>
                            <option value="fixing">Fixing</option>
                            <option value="refix">Refix</option>
                            <option value="done">Done</option>
                            <option value="completed">Completed</option>
                            <option value="cancel">Cancel</option>
                        </select><br/>

                        <!-- Upload hình ảnh bảo hành -->
                        <label>Upload Warranty Image:</label>
                        <input type="file" name="warrantyImage" accept="image/*" /><br/>

                        <input type="submit" value="Create Warranty Card" />
                    </form>

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>

        <script>
                            function toggleDateInput(dateField) {
                                var select = document.getElementsByName(dateField + "Option")[0];
                                var input = document.getElementsByName(dateField)[0];

                                if (select.value === "select") {
                                    input.style.display = "inline-block";
                                    input.required = true;
                                } else {
                                    input.style.display = "none";
                                    input.required = false;
                                    input.value = "";
                                }
                            }


        </script>

    </body>
</html>
