<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Customer Information</title>
    <link rel="stylesheet" href="css/light.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6f9;
            font-family: 'Inter', sans-serif;
        }
        .card {
            border-radius: 10px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }
        .btn-primary {
            background-color: #007bff;
            border-radius: 5px;
        }
        .form-control {
            border-radius: 5px;
            padding: 10px;
        }
        .form-label {
            font-weight: 600;
        }
        .customer-image {
            width: 150px;
            height: auto;
            border-radius: 8px;
            margin-bottom: 15px;
        }
    </style>
</head>

<body>
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-6">
                <div class="card p-4">
                    <h2 class="text-center mb-4">Update Customer Information</h2>
                    
                    <c:if test="${not empty requestScope.error}">
                        <div class="alert alert-danger">${requestScope.error}</div>
                    </c:if>
                    
                    <c:if test="${not empty requestScope.mess}">
                        <div class="alert alert-success">${requestScope.mess}</div>
                    </c:if>
                    
                    <form action="customer?action=update" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="customerId" value="${customer.customerID}" />
                        <input type="hidden" name="username" value="${customer.usernameC}" />
                        <input type="hidden" name="password" value="${customer.passwordC}" />

                        <div class="mb-3">
                            <label class="form-label">Name</label>
                            <input class="form-control" name="name" placeholder="Enter customer's name" value="${customer.name}" required />
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input class="form-control" type="email" name="email" placeholder="Enter customer's email" value="${customer.email}" required />
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Gender</label>
                            <div>
                                <input type="radio" id="male" name="gender" value="Male" <c:if test="${customer.gender == 'Male'}">checked</c:if> />
                                <label for="male">Male</label>
                                <input type="radio" id="female" name="gender" value="Female" <c:if test="${customer.gender == 'Female'}">checked</c:if> />
                                <label for="female">Female</label>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Date Of Birth</label>
                            <input class="form-control" type="date" name="dateOfBirth" id="dateOfBirth" value="${customer.dateOfBirth}" required />
                            <script>
                                document.getElementById("dateOfBirth").setAttribute("max", new Date().toISOString().split("T")[0]);
                            </script>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Phone</label>
                            <input class="form-control" type="text" name="phone" placeholder="Enter phone number" value="${customer.phone}" required />
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Address</label>
                            <input class="form-control" name="address" placeholder="Enter address" value="${customer.address}" required />
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Current Image</label><br>
                            <c:if test="${not empty customer.image}">
                                <img src="${customer.image}" alt="Customer Image" class="customer-image">
                            </c:if>
                            <c:if test="${empty customer.image}">
                                <p>No image available</p>
                            </c:if>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Upload New Image</label>
                            <input class="form-control" type="file" name="image" />
                        </div>

                        <div class="d-grid gap-2 mt-3">
                            <button type="submit" class="btn btn-primary btn-lg">Update</button>
                        </div>
                    </form>
                </div>
                <div class="text-center mt-3">
                    <a href="customer" class="btn btn-outline-secondary">Back to Customer List</a>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>