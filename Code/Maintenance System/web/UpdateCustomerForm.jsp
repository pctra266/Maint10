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
                                    <form action="customer?action=update" method="post">
                                        
                                          <!-- Customer ID (hidden for security) -->
                                        <input type="hidden" name="customerId" value="${customer.customerID}" />
                                        <input type="hidden" name="username" value ="${customer.usernameC}"  />
                                        <input type="hidden" name="password" value ="${customer.passwordC}" />
                                        
                                        <div class="mb-3">
                                            <label class="form-label">Name</label>
                                            <input class="form-control form-control-lg" name="name" placeholder="Enter customer's name" value="${customer.name}" />
                                        </div>

                                        
                                        <div class="mb-3">
                                            <label class="form-label">Email</label>
                                            <input class="form-control form-control-lg" type="email" name="email" placeholder="Enter customer's email" value="${customer.email}" />
                                        </div>

                                        
                                        <div class="mb-3">
                                            <label class="form-label">Phone</label>
                                            <input class="form-control form-control-lg" type="text" name="phone" placeholder="Enter customer's phone number" value="${customer.phone}" />
                                        </div>

                                        
                                        <div class="mb-3">
                                            <label class="form-label">Address</label>
                                            <input class="form-control form-control-lg" name="address" placeholder="Enter customer's address" value="${customer.address}" />
                                        </div>

                                      
                                        <div class="mb-3">
                                            <label class="form-label">Image</label>
                                            <input class="form-control form-control-lg" type="text" name="image" />
                                        </div>

                                       
                                  

                                        
                                        <div class="d-grid gap-2 mt-3">
                                            <button type="submit" class="btn btn-lg btn-primary">Update</button>
                                        </div>

                                        
                                        <a style="color: red">${requestScope.error}</a>
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
