
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Responsive Admin &amp; Dashboard Template based on Bootstrap 5">
        <meta name="author" content="AdminKit">
        <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">
        <base href="${pageContext.request.contextPath}/">

        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="shortcut icon" href="icons/icon-48x48.png" />

        <link rel="canonical" href="https://demo-basic.adminkit.io/" />

        <title>Warranty Card</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="../../includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="../../includes/navbar-top.jsp" />
                <main class="content">
                    <a href="WarrantyCard" class="btn btn-primary  d-flex align-items-center justify-content-center" style="transform:translate(-30%,-60%); height: 2.5rem; width: 5.2rem"><i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span> </a>
                    <h1 class="text-center text-primary" style="font-size: 4rem">Create Warranty Card</h1>
                    <div class="d-flex flex-column col justify-content-center align-items-center vh-50">
                        <form action="WarrantyCard/Add" method="GET" class="w-50 row">
                            <div class="row d-flex align-items-center ">
                                <!-- Ô input -->
                                <div class="col bg-primary bg-opacity-75 p-4 rounded-pill shadow"> <!-- Tăng padding -->
                                    <input type="text" class="form-control w-100 border-0 text-white" style="font-size: 2rem; background-color: rgba(59,125,221,0)"  name="productCode" id="ProductCode" value="${ProductCode}" required style="font-size: 1.5rem;"> <!-- Tăng kích thước font chữ -->
                                </div>
                                <!-- Nút tìm kiếm -->
                                <div class="col-auto">
                                    <button type="submit" class="btn col-auto bg-primary bg-opacity-75 p-4 rounded-circle shadow h-100" style="font-size: 1.5rem;"> <!-- Tăng padding và kích thước font chữ -->
                                        <i class="align-middle fas fa-fw fa-search text-white text-opacity-75"></i>
                                    </button>
                                </div>
                            </div>
                        </form>

                        <c:if test="${not empty NotFoundProduct}">
                            <div class="col-md-7 alert alert-dark alert-dismissible mt-4" role="alert">
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                <div class="alert-message text-center">
                                    <strong>${NotFoundProduct}</strong>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${not empty pd}">
                            <div class="col-md-7 alert alert-success alert-dismissible mt-4" role="alert">
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                <div class="alert-message text-center">
                                    <strong>Found product</strong>
                                </div>
                            </div>
                        </c:if>

                    </div>
                    <c:if test="${not empty pd}">
                        <hr class=""> 
                        <h1 class="text-center text-primary" style="font-size: 4rem">Warranty Card</h1>
                        <c:if test="${not empty createFail}">
                            <div class="d-flex justify-content-center">
                                <div class="col-md-7 alert alert-danger alert-dismissible mt-4 text-center" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message">
                                        <strong>${createFail}</strong>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <form action="WarrantyCard/Add" method="GET" class="row g-3">
                            <!-- ID ẩn -->
                            <input type="hidden" name="productDetailID" value="${pd.productDetailID}">
                            <!-- Mã sản phẩm -->
                            <div class="col-md-3">
                                <label for="productCode" class="form-label">Product Code</label>
                                <input type="text" class="form-control" name="productCode" id="productCode" 
                                       value="${pd.productCode}" readonly="readonly" required>
                            </div>

                            <!-- Ngày mua (Định dạng yyyy-MM-dd) -->
                            <div class="col-md-3">
                                <label for="purchaseDate" class="form-label">Purchase Date</label>
                                <input type="date" class="form-control" name="purchaseDate" id="purchaseDate"
                                       value="${pd.purchaseDate}" readonly="readonly">
                            </div>

                            <!-- Tên sản phẩm -->
                            <div class="col-md-6">
                                <label for="productName" class="form-label">Product Name</label>
                                <input type="text" class="form-control" name="productName" id="productName" 
                                       value="${pd.productName}" readonly="readonly">
                            </div>

                            <!-- Username -->
                            <div class="col-md-3">
                                <label for="usernameC" class="form-label">Name</label>
                                <input type="text" class="form-control" name="usernameC" id="usernameC" 
                                       value="${pd.name}" readonly="readonly">
                            </div>
                            <!-- Thời gian bảo hành -->
                            <div class="col-md-3">
                                <label for="warrantyPeriod" class="form-label">Warranty Period (Months)</label>
                                <input type="number" class="form-control" name="warrantyPeriod" id="warrantyPeriod" 
                                       value="${pd.warrantyPeriod}" readonly="readonly">
                            </div>


                            <!-- Địa chỉ -->
                            <div class="col-md-6">
                                <label for="address" class="form-label">Address</label>
                                <input type="text" class="form-control" name="address" id="address" 
                                       value="${pd.address}" readonly="readonly">
                            </div>


                            <!-- Số điện thoại -->
                            <div class="col-md-3">
                                <label for="phone" class="form-label">Phone</label>
                                <input type="text" class="form-control" name="phone" id="phone" 
                                       value="${pd.phone}" readonly="readonly">
                            </div>

                            <!-- Tên khách hàng -->
                            <div class="col-md-3">
                                <label for="name" class="form-label">Username</label>
                                <input type="text" class="form-control" name="name" id="name" 
                                       value="${pd.usernameC}" readonly="readonly" required>
                            </div>

                            <!-- Email -->
                            <div class="col-md-6">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" name="email" id="email" 
                                       value="${pd.email}" readonly="readonly">
                            </div>
                            <div class="col-md-12">
                                <textarea class="form-control" placeholder="Descript issue" name= "issue" rows="2" style="height: 10rem;"></textarea>
                            </div>




                            <!-- Nút Submit -->
                            <div class="col-12 text-center">
                                <button type="submit" class="btn btn-primary btn-pill p-3" style="font-size: 1.3rem">Create</button>
                            </div>
                        </form>
                    </c:if>




                </main>

                <jsp:include page="../../includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>
    </body>

</html>
