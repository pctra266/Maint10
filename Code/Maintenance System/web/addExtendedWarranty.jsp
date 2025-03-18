<%-- 
    Document   : addExtendedWarranty
    Created on : Mar 16, 2025, 8:57:17 PM
    Author     : Tra Pham
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Extended Warranty</title>

        <link rel="stylesheet" href="css/light.css">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <div class="card"> 
                        <div class="card-header">
                    <h1>Add Extended Warranty</h1>
                    </div>
                        <div class="card-body">
                    <form action="ExtendedWarrantyController" method="post">
                        <input type="hidden" name="action" value="new">
                        <div class="mb-3">
                            <label for="extendedWarrantyName" class="form-label">Extended Warranty Name</label>
                            <input type="text" class="form-control" id="extendedWarrantyName" name="extendedWarrantyName" required>
                        </div>
                        <div class="mb-3">
                            <label for="extendedPeriodInMonths" class="form-label">Extended Period (Months)</label>
                            <input type="number" class="form-control" id="extendedPeriodInMonths" name="extendedPeriodInMonths" required>
                        </div>
                        <div class="mb-3">
                            <label for="price" class="form-label">Price</label>
                            <input type="text" id="price"  class="form-control format-float price-input" step="0.01" min="0" id="price" name="price" required>
                        </div>
                        <div class="mb-3">
                            <label for="extendedWarrantyDescription" class="form-label">Description</label>
                            <textarea class="form-control" id="extendedWarrantyDescription" name="extendedWarrantyDescription" rows="3"></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">Add</button>
                        <a href="ExtendedWarrantyController" class="btn btn-secondary">Cancel</a>
                    </form>
                            </div>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
            <script src="js/app.js"></script>
            <script>
                
            </script>
        </div>
    </body>
</html>

