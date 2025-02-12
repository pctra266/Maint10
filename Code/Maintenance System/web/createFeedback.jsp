<%-- 
    Document   : CreateFeedback
    Created on : Jan 17, 2025, 10:26:47 PM
    Author     : Tra Pham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Feedback</title>
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
                            <h1 class="text-center">Create Feedback</h1>
                        </div>
                        <div class="alert-danger">
                            ${mess}
                        </div>
                        <div class="alert-danger">
                            ${pictureAlert}
                        </div>
                        <div class="alert-danger">
                            ${videoAlert}
                        </div>
                         <div class="card-body">
                             <form action="feedback" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="createFeedback">
                            <div>
                                <input style="display: none" name="customerId" type="text" value="1" >
                            </div>
                            <div>
                                <label class="form-label">Product: </label>
                                <select class="form-select" name="WarrantyCardID">
                                    <option value=""></option>
                                    <c:forEach items="${listProductByCustomerId}" var="o">
                                        <option ${(warrantyCardID== o.warrantyCardID)?"selected":""} value="${o.warrantyCardID}">${o.productName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label class="form-label">Image: </label>
                                    <img src="" id="currentImage" alt="" style="max-width: 100%; height: auto;">
                                    <input class="form-control" accept="image/*" name="imageURL" type="file" onchange="previewImage(event)">
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Video: </label>
                                    <video src="" id="currentVideo" style="max-width: 100%; height: auto;" controls="" ></video>
                                    <input class="form-control"  accept="video/*" name="videoURL" type="file" onchange="previewVideo(event)">
                                </div>
                            </div>
                            <div>
                                <label class="form-label">Note: </label>
                                <textarea class="form-control" name="note" required="" ></textarea>
                            </div>
                            <button class="btn btn-primary" type="submit"> Submit </button>
                        </form>
                         </div>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
         <script>
            function previewImage(event) {
                    const file = event.target.files[0];
                    if (!file) return; 

                    else{
                        const img = document.getElementById("currentImage");
                        img.src = URL.createObjectURL(file); 
                    }

                    
            }
            function previewVideo(event) {
                const file = event.target.files[0];
                if (!file) return; 

                

                const video = document.getElementById("currentVideo");
                video.src = URL.createObjectURL(file); 
//                video.load(); // Nạp lại video để hiển thị
//                video.play(); // (Tùy chọn) Tự động phát video
            }
        </script>
    </body>
</html>
