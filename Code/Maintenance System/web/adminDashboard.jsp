<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Cài đặt Giới hạn Dung lượng Ảnh & Video</title>
    <script>
        function toggleCustomInput(type) {
            let selectBox = document.getElementById("maxSizeSelect" + type);
            let customInput = document.getElementById("customSize" + type);
            let unitInput = document.getElementById("customUnit" + type);
            
            if (selectBox.value === "custom") {
                customInput.style.display = "inline-block";
                unitInput.style.display = "inline-block";
            } else {
                customInput.style.display = "none";
                unitInput.style.display = "none";
            }
        }
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
    <h2>Thiết lập Giới hạn Dung lượng Ảnh & Video</h2>
    
    <c:set var="maxUploadSizeImageMB" value="${applicationScope.maxUploadSizeImageMB}" />
    <c:if test="${empty maxUploadSizeImageMB}">
        <c:set var="maxUploadSizeImageMB" value="5" />
        <c:set var="applicationScope.maxUploadSizeImageMB" value="5" />
    </c:if>

    <p><strong>Giới hạn ảnh hiện tại:</strong> ${maxUploadSizeImageMB} MB</p>

    <form action="updateMaxSize" method="post">
        <label>Chọn dung lượng tối đa cho ảnh:</label>
        <select id="maxSizeSelectImage" name="maxSizeImage" onchange="toggleCustomInput('Image')">
            <option value="5" <c:if test="${maxUploadSizeImageMB == 5}">selected</c:if>>5 MB</option>
            <option value="10" <c:if test="${maxUploadSizeImageMB == 10}">selected</c:if>>10 MB</option>
            <option value="15" <c:if test="${maxUploadSizeImageMB == 15}">selected</c:if>>15 MB</option>
            <option value="kb" <c:if test="${maxUploadSizeImageMB == 1}">selected</c:if>>500 KB</option>
            <option value="1g" <c:if test="${maxUploadSizeImageMB == 1024}">selected</c:if>>1 GB</option>
            <option value="custom">Tùy chỉnh</option>
        </select>
        
        <input type="number" id="customSizeImage" name="customSizeImage" min="1" placeholder="Nhập số" style="display:none;">
        <select id="customUnitImage" name="customUnitImage" style="display:none;">
            <option value="mb">MB</option>
            <option value="kb">KB</option>
            <option value="gb">GB</option>
        </select>

        <br><br>

        <c:set var="maxUploadSizeVideoMB" value="${applicationScope.maxUploadSizeVideoMB}" />
        <c:if test="${empty maxUploadSizeVideoMB}">
            <c:set var="maxUploadSizeVideoMB" value="50" />
            <c:set var="applicationScope.maxUploadSizeVideoMB" value="50" />
        </c:if>

        <p><strong>Giới hạn video hiện tại:</strong> ${maxUploadSizeVideoMB} MB</p>

        <label>Chọn dung lượng tối đa cho video:</label>
        <select id="maxSizeSelectVideo" name="maxSizeVideo" onchange="toggleCustomInput('Video')">
            <option value="50" <c:if test="${maxUploadSizeVideoMB == 50}">selected</c:if>>50 MB</option>
            <option value="100" <c:if test="${maxUploadSizeVideoMB == 100}">selected</c:if>>100 MB</option>
            <option value="200" <c:if test="${maxUploadSizeVideoMB == 200}">selected</c:if>>200 MB</option>
            <option value="500" <c:if test="${maxUploadSizeVideoMB == 500}">selected</c:if>>500 MB</option>
            <option value="1g" <c:if test="${maxUploadSizeVideoMB == 1024}">selected</c:if>>1 GB</option>
            <option value="custom">Tùy chỉnh</option>
        </select>
        
        <input type="number" id="customSizeVideo" name="customSizeVideo" min="1" placeholder="Nhập số" style="display:none;">
        <select id="customUnitVideo" name="customUnitVideo" style="display:none;">
            <option value="mb">MB</option>
            <option value="kb">KB</option>
            <option value="gb">GB</option>
        </select>

        <br><br>
        <input type="submit" value="Cập nhật">
    </form>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
    </div>
</body>
</html>
