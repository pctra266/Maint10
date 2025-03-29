<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Add Product to Component</title>
    <base href="${pageContext.request.contextPath}/">
    <link href="css/light.css" rel="stylesheet">
            <link rel="shortcut icon" href="icons/icon-48x48.png" />

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
</head>
<body>
    <div class="wrapper">
        <jsp:include page="../../includes/navbar-left.jsp" />
        <div class="main">
            <jsp:include page="../../includes/navbar-top.jsp" />
            <main class="content">
                <h2>Add Product to Component</h2>
                
                <!-- Nút Back -->
                <form action="Redirect" method="POST" class="mb-3">
                    <input type="hidden" name="target" value="ComponentWarehouse/Detail?ID=${componentID}">
                    <button type="submit" class="btn btn-primary d-flex align-items-center justify-content-center" style="height: 2.5rem; width: 5.2rem">
                        <i class="fas fa-arrow-left"></i> <span class="ms-2">Back</span>
                    </button>
                </form>

                <!-- Thông báo lỗi hoặc thành công -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        <div class="alert-message">
                            <strong>${error}</strong>
                        </div>
                    </div>
                </c:if>

                <!-- Form thêm sản phẩm -->
                <form action="ComponentWarehouse/AddProductToComponent" method="POST" class="row g-3">
                    <input type="hidden" name="componentID" value="${componentID}">
                    <div class="col-md-6">
                        <label for="productID" class="form-label">Select Product</label>
                        <select name="productID" id="productID" class="form-select" required>
                            <option value="" disabled selected>Choose a product</option>
                            <c:forEach var="product" items="${productList}">
                                <option value="${product.productId}">${product.productName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6"></div>
                    <div class="col-md-6">
                        <label for="quantity" class="form-label">Quantity</label>
                        <input type="number" name="quantity" id="quantity" class="form-control" min="1" value="1" required>
                    </div>
                    <div class="col-md-12">
                        <button type="submit" class="btn btn-primary">Add Product</button>
                    </div>
                </form>
            </main>
            <jsp:include page="../../includes/footer.jsp" />
        </div>
    </div>
    <script src="js/app.js"></script>
</body>
</html>