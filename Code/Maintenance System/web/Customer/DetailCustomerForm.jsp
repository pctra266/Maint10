<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Customer Detail Page">
    <title>Customer Detail</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
    <div class="container mt-4">
        <!-- Back Button -->
        <a href="customer" class="btn btn-primary mb-3">
            <i class="bi bi-arrow-left"></i> Back
        </a>

        <div class="card shadow-lg p-4">
            <h2 class="text-center text-primary mb-4">Customer Detail</h2>

            <div class="row">
                <!-- Customer Info -->
                <div class="col-md-8">
                    <form>
                        <div class="mb-3">
                            <label class="form-label">Customer ID</label>
                            <input type="text" class="form-control" value="${customer.customerID}" disabled>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Name</label>
                            <input type="text" class="form-control" value="${customer.name}" disabled>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" class="form-control" value="${customer.email}" disabled>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Phone</label>
                            <input type="text" class="form-control" value="${customer.phone}" disabled>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Address</label>
                            <textarea class="form-control" rows="2" disabled>${customer.address}</textarea>
                        </div>
                    </form>
                </div>

                <!-- Customer Image -->
                <div class="col-md-4 text-center">
                    <img src="${customer.image}" alt="Customer Image" class="img-fluid border rounded shadow-sm mb-3" width="200">
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
