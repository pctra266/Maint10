<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Set Image & Video File Size Limit</title>
    <script>
    function toggleCustomInput(type) {
        const selectBox = document.getElementById("maxSizeSelect" + type);
        const customInput = document.getElementById("customSize" + type);
        
        if (selectBox.value === "custom") {
            customInput.style.display = "inline-block";
            if (customSize !== selectBox.value) {
                customInput.value = customSize;
            }
        } else {
            customInput.style.display = "none";
            customInput.value = selectBox.value; 
        }
    }
            window.onload = function() {
            let imageSizeSelect = document.getElementById("maxSizeSelectImage");
            let videoSizeSelect = document.getElementById("maxSizeSelectVideo");
            let customImageSizeInput = document.getElementById("customSizeImage");
            let customVideoSizeInput = document.getElementById("customSizeVideo");

            if (imageSizeSelect.value !== "5" && imageSizeSelect.value !== "10") {
                imageSizeSelect.value = "custom";
                customImageSizeInput.style.display = "inline-block";
                customImageSizeInput.value = "${maxUploadSizeImageMB}"; // Thiết lập giá trị
            }

            if (videoSizeSelect.value !== "50" && videoSizeSelect.value !== "100") {
                videoSizeSelect.value = "custom";
                customVideoSizeInput.style.display = "inline-block";
                customVideoSizeInput.value = "${maxUploadSizeVideoMB}"; // Thiết lập giá trị
            }
        };
</script>
    <script src="js/app.js"></script>
    <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
</head>
<body>
    <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
    <h2>Set Image & Video File Size Limit</h2>
    
    <c:set var="maxUploadSizeImageMB" value="${applicationScope.maxUploadSizeImageMB}" />
    <c:if test="${empty maxUploadSizeImageMB}">
        <c:set var="maxUploadSizeImageMB" value="5" />
        <c:set var="applicationScope.maxUploadSizeImageMB" value="5" />
    </c:if>

    <p><strong>Current image limit:</strong> ${maxUploadSizeImageMB} MB</p>

    <form action="updateMaxSize" method="post">
        <label>Select maximum file size for images:</label>
        <select id="maxSizeSelectImage" name="maxSizeImage" onchange="toggleCustomInput('Image')">
            <option value="5" ${maxUploadSizeImageMB == 5 ? "selected" : ""}>5 MB</option>
            <option value="10" ${maxUploadSizeImageMB == 10 ? "selected" : ""}>10 MB</option>
            <option value="custom" ${!(maxUploadSizeImageMB == 5 || maxUploadSizeImageMB == 10) ? "selected" : ""} >Customize</option>
        </select>
        
        <input type="number" id="customSizeImage" name="customSizeImage" min="1" max="10" placeholder="Enter size(MB)" value="${maxUploadSizeImageMB}" style="display:none;">
        <br><br>

        <c:set var="maxUploadSizeVideoMB" value="${applicationScope.maxUploadSizeVideoMB}" />
        <c:if test="${empty maxUploadSizeVideoMB}">
            <c:set var="maxUploadSizeVideoMB" value="50" />
            <c:set var="applicationScope.maxUploadSizeVideoMB" value="50" />
        </c:if>

        <p><strong>Current video limit:</strong> ${maxUploadSizeVideoMB} MB</p>

        <label>Select maximum file size for videos:</label>
        <select id="maxSizeSelectVideo" name="maxSizeVideo" onchange="toggleCustomInput('Video')">
            <option value="50" ${maxUploadSizeVideoMB == 50 ? "selected" : ""}>50 MB</option>
            <option value="100" ${maxUploadSizeVideoMB == 100 ? "selected" : ""}>100 MB</option>
            <option value="custom" ${!(maxUploadSizeVideoMB == 50 || maxUploadSizeVideoMB == 100) ? "selected" : ""} >Customize</option>
        </select>
        
            <input type="number" id="customSizeVideo" name="customSizeVideo" min="1" max="100" placeholder="Enter size()" value="${maxUploadSizeVideoMB}" style="display:none;">
        <br><br>
        <div>
            ${errorMessage}
        </div>
        
        <input type="submit" value="Update">
    </form>
        <div>
            ${successMessage}
        </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
    </div>
</body>
</html>
