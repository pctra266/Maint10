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
                                    <select  class="form-select" name="warrantyCardID">
                                        <option value=""></option>
                                        <c:forEach items="${listProductByCustomerId}" var="o">
                                            <option ${(warrantyCardID== o.warrantyCardID)?"selected":""} value="${o.warrantyCardID}">${o.productName!=null?o.productName:o.unknownProductName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div  style="margin-top: 5px" class="row">
                                    <div class="col-md-6">

                                        <c:set var="maxUploadSizeImageMB" value="${applicationScope.maxUploadSizeImageMB}" />
                                        <c:if test="${empty maxUploadSizeImageMB}">
                                            <c:set var="maxUploadSizeImageMB" value="5" />
                                            <c:set var="applicationScope.maxUploadSizeImageMB" value="5" />
                                        </c:if>
                                        <label class="form-label">Image (File Size ${maxUploadSizeImageMB}MB Limit): </label>
                                        <img src="" id="currentImage" alt="" style="max-width: 100%; height: auto;">
                                        <input style="margin-top: 10px" id="fileInput" class="form-control" accept="image/*" name="imageURL" type="file" onchange="previewImage(event)">
                                    </div>
                                    <div class="col-md-6">
                                        <c:set var="maxUploadSizeVideoMB" value="${applicationScope.maxUploadSizeVideoMB}" />
                                        <c:if test="${empty maxUploadSizeVideoMB}">
                                            <c:set var="maxUploadSizeVideoMB" value="10" />
                                            <c:set var="applicationScope.maxUploadSizeVideoMB" value="10" />
                                        </c:if>
                                        <label class="form-label">Video (File Size ${maxUploadSizeVideoMB}MB Limit): </label>
                                        <video src="" id="currentVideo" style="max-width: 100%; height: auto; display: none" controls="" ></video>
                                        <input style="margin-top: 10px" id="fileInput" class="form-control"  accept="video/*" name="videoURL" type="file" onchange="previewVideo(event)">
                                    </div>
                                </div>
                                <div>
                                    <label class="form-label">Note: </label>
                                    <textarea class="form-control" name="note" required="" >${note}</textarea>
                                </div>
                                <button style="margin-top: 15px" class="btn btn-primary" type="submit"> Submit </button>
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
    const img = document.getElementById("currentImage");

    if (file) {
        img.src = URL.createObjectURL(file);
    } else {
        img.src = ""; // Xóa ảnh khi nhấn Cancel
    }
}

function previewVideo(event) {
    const file = event.target.files[0];
    const video = document.getElementById("currentVideo");

    if (file) {
        video.src = URL.createObjectURL(file);
        video.style.display = "block";
    } else {
        video.src = ""; // Xóa video khi nhấn Cancel
         video.style.display = "none";
        video.load();  // Reset video để tránh lỗi hiển thị khung hình cũ
    }
}


                                            document.getElementById("fileInput").addEventListener("change", function () {
                                                let file = this.files[0]; // Lấy file được chọn
                                                if (file) {
                                                    let maxSize = 10 * 1024 * 1024; // 5MB
                                                    if (file.size > maxSize) {
                                                        alert("File không được vượt quá 10MB!");
                                                        this.value = ""; // Reset input file nếu file quá lớn
                                                    }
                                                }
                                            });
        </script>
    </body>
</html>
