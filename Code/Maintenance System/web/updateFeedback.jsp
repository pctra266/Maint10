<%-- 
    Document   : UpdateFeedback
    Created on : Jan 17, 2025, 9:14:09 PM
    Author     : Tra Pham
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback Detail</title>
        <link href="css/light.css" rel="stylesheet">
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
                            <h1 class="text-center">Feedback Detail</h1>
                        </div>

                        <div class="card-body">
                            <form action="feedback" method="post">

                                <input type="hidden" name="action" value="updateFeedback">
                                <!-- hidden field -->
                                    
                                <div>
                                    <input type="hidden" value="${feedbackUpdate.customerID}">
                                </div>
                                 <div>
                                    <input type="hidden"  value="${feedbackUpdate.warrantyCardID}">
                                </div>
                                <!-- end hidden field -->
                                <div class="row">
                                <div class="mb-3 col-md-6">
                                    <label class="form-label">Feedback ID: </label>
                                    <input class="form-control" type="text" readonly="" value="${feedbackUpdate.feedbackID}" name="feedbackId">
                                </div>
                            

                                <div  class="mb-3 col-md-6">
                                    <label class="form-label">Customer Name: </label>
                                    <input class="form-control" type="text" readonly="" value="${feedbackUpdate.customerName}">
                                </div>
                                </div>
                                <div class="row">
                                <div class="mb-3 col-md-6">
                                    <label class="form-label">Phone Number: </label>
                                    <input class="form-control" type="text" readonly="" value="${feedbackUpdate.customerPhoneNumber}" >
                                </div>
                            

                                <div  class="mb-3 col-md-6">
                                    <label class="form-label">Customer Email: </label>
                                    <input class="form-control" type="text" readonly="" value="${feedbackUpdate.customerEmail}">
                                </div>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Create Date: </label>
                                    <input class="form-control" type="text" readonly="" value="${feedbackUpdate.dateCreated}">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Product Name: </label>
                                    <input class="form-control" type="text" readonly="" value="${feedbackUpdate.productName}">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Issue Description: </label>
                                    <input class="form-control" type="text" readonly="" value="${feedbackUpdate.issueDescription}">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Warranty Status: </label>
                                    <input class="form-control" type="text" readonly="" value="${feedbackUpdate.warrantyStatus}">
                                </div>
                               
                                <div class="row">
                                <div class="mb-3 col-md-6">
                                    <label class="form-label">Image : </label>
                                    <img src="${feedbackUpdate.imageURL}" alt="" style="max-width: 100%; height: auto;">
                                </div>

                                <div class="mb-3 col-md-6">
                                    <label class="form-label">Video : </label>
                                    <video src="${feedbackUpdate.videoURL}" style="max-width: 100%; height: auto;" controls="" ></video>
                                </div>
                                </div>
                                 <div class="mb-3">
                                    <label class="form-label">Note : </label>
                                    <textarea class="form-control" name="note">${feedbackUpdate.note}</textarea>
                                </div>
                                <a class="btn btn-primary" href="feedback">Back</a>
                                <button class="btn btn-primary" type="submit" > Save Change</button> 
                            </form>
                                
                        </div><!-- end card body -->               
                    </div><!-- end class card -->

                </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>

        <script src="js/app.js"></script>
    </body>
</html>
