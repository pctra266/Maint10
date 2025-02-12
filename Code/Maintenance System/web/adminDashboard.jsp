<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Cài đặt Giới hạn Dung lượng Ảnh</title>
    <script>
        function toggleCustomInput() {
            let selectBox = document.getElementById("maxSizeSelect");
            let customInput = document.getElementById("customSize");
            let unitInput = document.getElementById("customUnit");
            
            if (selectBox.value === "custom") {
                customInput.style.display = "inline-block";
                unitInput.style.display = "inline-block";
            } else {
                customInput.style.display = "none";
                unitInput.style.display = "none";
            }
        }
    </script>
</head>
<body>
    <h2>Thiết lập Giới hạn Dung lượng Ảnh</h2>
    
    <c:set var="maxUploadSizeMB" value="${applicationScope.maxUploadSizeMB}" />
    <c:if test="${empty maxUploadSizeMB}">
        <c:set var="maxUploadSizeMB" value="5" />
        <c:set var="applicationScope.maxUploadSizeMB" value="5" />
    </c:if>

    <p><strong>Giới hạn hiện tại:</strong> ${maxUploadSizeMB} MB</p>

    <form action="updateMaxSize" method="post">
        <label>Chọn dung lượng tối đa:</label>
        <select id="maxSizeSelect" name="maxSize" onchange="toggleCustomInput()">
            <option value="5" <c:if test="${maxUploadSizeMB == 5}">selected</c:if>>5 MB</option>
            <option value="10" <c:if test="${maxUploadSizeMB == 10}">selected</c:if>>10 MB</option>
            <option value="15" <c:if test="${maxUploadSizeMB == 15}">selected</c:if>>15 MB</option>
            <option value="kb" <c:if test="${maxUploadSizeMB == 1}">selected</c:if>>500 KB</option>
            <option value="1g" <c:if test="${maxUploadSizeMB == 1024}">selected</c:if>>1 GB</option>
            <option value="custom">Tùy chỉnh</option>
        </select>
        
        <input type="number" id="customSize" name="customSize" min="1" placeholder="Nhập số" style="display:none;">
        <select id="customUnit" name="customUnit" style="display:none;">
            <option value="mb">MB</option>
            <option value="kb">KB</option>
            <option value="gb">GB</option>
        </select>

        <br><br>
        <input type="submit" value="Cập nhật">
    </form>
</body>
</html>

