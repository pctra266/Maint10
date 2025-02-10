<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Responsive Admin & Dashboard Template based on Bootstrap 5">
    <meta name="author" content="AdminKit">
    <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">

    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="shortcut icon" href="img/icons/icon-48x48.png" />
    <link href="css/light.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    <title>Update Customer Information</title>
</head>

<body>
    <main class="d-flex w-100">
        <div class="container d-flex flex-column">
            <div class="row vh-100">
                <div class="col-sm-10 col-md-8 col-lg-6 col-xl-5 mx-auto d-table h-100">
                    <div class="d-table-cell align-middle">
                        <div class="text-center mt-4">
                            <h1 class="h2">Update Customer Information</h1>
                            <p class="lead">Edit the customer's details and submit to update.</p>
                        </div>

                        <div class="card">
                            <div class="card-body">
                                <div class="m-sm-3">

                                 
                                    <c:if test="${not empty requestScope.error}">
                                        <div class="alert alert-danger" role="alert">
                                            ${requestScope.error}
                                        </div>
                                    </c:if>

                                   
                                    <c:if test="${not empty requestScope.mess}">
                                        <div class="alert alert-success" role="alert">
                                            ${requestScope.mess}
                                        </div>
                                    </c:if>

                                
                                    <form action="customer?action=update" method="post" enctype="multipart/form-data">
                                        <input type="hidden" name="customerId" value="${customer.customerID}" />
                                        <input type="hidden" name="username" value="${customer.usernameC}" />
                                        <input type="hidden" name="password" value="${customer.passwordC}" />

                                  
                                        <div class="mb-3">
                                            <label class="form-label">Name</label>
                                            <input class="form-control form-control-lg" name="name"
                                                placeholder="Enter customer's name" 
                                                value="${customer.name != null ? customer.name : ''}" />
                                        </div>

                                      
                                        <div class="mb-3">
                                            <label class="form-label">Email</label>
                                            <input class="form-control form-control-lg" type="email" name="email"
                                                placeholder="Enter customer's email" 
                                                value="${customer.email != null ? customer.email : ''}" />
                                        </div>

                                       
                                        <div class="mb-3">
                                            <label class="form-label">Gender</label>
                                            <div>
                                                <input type="radio" id="male" name="gender" value="Male"
                                                    <c:if test="${customer.gender == 'Male'}">checked</c:if> />
                                                <label for="male">Male</label>

                                                <input type="radio" id="female" name="gender" value="Female"
                                                    <c:if test="${customer.gender == 'Female'}">checked</c:if> />
                                                <label for="female">Female</label>
                                            </div>
                                        </div>

                                     
                                        <div class="mb-3">
                                            <label class="form-label">Phone</label>
                                            <input class="form-control form-control-lg" type="text" name="phone"
                                                placeholder="Enter customer's phone number" 
                                                value="${customer.phone != null ? customer.phone : ''}" />
                                        </div>

                                      
                                        <div class="mb-3">
                                            <label class="form-label">Address</label>
                                            <input class="form-control form-control-lg" name="address"
                                                placeholder="Enter customer's address" 
                                                value="${customer.address != null ? customer.address : ''}" />
                                        </div>

                                       
                                        <div class="mb-3">
                                            <label class="form-label">Current Image</label><br>
                                            <c:if test="${not empty customer.image}">
                                                <img src="${customer.image}" alt="Customer Image"
                                                    style="width: 150px; height: auto; border-radius: 8px;">
                                            </c:if>
                                            <c:if test="${empty customer.image}">
                                                <p>No image available</p>
                                            </c:if>
                                        </div>

                                      
                                        <div class="mb-3">
                                            <label class="form-label">Upload New Image</label>
                                            <input class="form-control form-control-lg" type="file" name="image" />
                                        </div>

                                      
                                        <div class="d-grid gap-2 mt-3">
                                            <button type="submit" class="btn btn-lg btn-primary">Update</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                       
                        <div class="text-center mb-3">
                            <a href="customer">Back to Customer List</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <script src="js/app.js"></script>
</body>

</html>
