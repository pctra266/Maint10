<%-- 
    Document   : customizeHomepage
    Created on : Mar 15, 2025, 9:09:48 AM
    Author     : Tra Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="coverDAO" class="DAO.HomePage_CoverDAO" scope="page" />
<c:set var="backgroundImage" value="${coverDAO.getBackgroundImage()}" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Customize HomePage</title>
    </head>
    <body>
        <h1>Customize HomePage</h1>
        
        <!-- Customize Cover Image -->
        <h2>Customize Cover Image</h2>
        <c:if test="${not empty messBackground}">
            <p style="color: green;">${messBackground}</p>
        </c:if>
        <div>
            <h3>Current Cover Image:</h3>
            <img src="${pageContext.request.contextPath}${backgroundImage}" alt="Cover Image" width="400px">
        </div>
        <form action="updateCover" method="post" enctype="multipart/form-data">
            <label for="coverImage">Select New Cover Image:</label>
            <input type="file" name="coverImage" id="coverImage" accept="image/*" required>
            <button type="submit">Upload</button>
        </form>
        <hr>
        
        <!-- Customize Footer -->
        <h2>Customize Footer</h2>
        <c:if test="${not empty messFooter}">
            <p style="color: green;">${messFooter}</p>
        </c:if>
        <form action="FooterController" method="post">
            <label>Slogan:</label>
            <input type="text" name="slogan" value="${footer.slogan}" required><br>
            <label>Address:</label>
            <input type="text" name="address" value="${footer.address}" required><br>
            <label>Hotline:</label>
            <input type="text" name="hotline" value="${footer.hotline}" required><br>
            <label>Email:</label>
            <input type="email" name="email" value="${footer.email}" required><br>
            <label>Copyright Year:</label>
            <input type="text" name="copyrightYear" value="${footer.copyrightYear}" required><br>
            <button type="submit">Update</button>
        </form>
        <hr>
        
        <!-- Customize Warranty Consultation Text -->
        <h2>Customize Warranty Consultation Text</h2>
        <c:if test="${not empty messContactText}">
            <p style="color: green;">${messContactText}</p>
        </c:if>
        <form action="ContactController" method="post">
            <label for="title">H1 Title:</label>
            <input type="text" name="title" value="${contactText.title}" required><br>
            <label for="subtitle">H6 Subtitle:</label>
            <textarea name="subtitle" required>${contactText.subtitle}</textarea><br>
            <button type="submit">Update</button>
        </form>
        <hr>
        
        <!-- Phần quản lý Marketing Service Section (inlined từ manageSection.jsp) -->
        <h2>Manage Marketing Service Section</h2>
        <c:if test="${not empty message}">
            <p style="color:green;">${message}</p>
        </c:if>
        <form action="MarketingServiceSectionController" method="post">
            <input type="hidden" name="sectionID" value="${section.sectionID}">
            <label>Title:</label>
            <input type="text" name="title" value="${section.title}" required><br>
            <label>SubTitle:</label>
            <input type="text" name="subTitle" value="${section.subTitle}" required><br>
            <button type="submit">Update Section</button>
        </form>
        <br>
        <!-- (Nếu cần) Link chuyển sang trang quản lý item, nhưng ở đây chúng ta tích hợp luôn bên dưới -->
        <hr>
        
        <!-- Phần quản lý Marketing Service Items (inlined từ manageServiceItems.jsp) -->
        <h2>Manage Marketing Service Items</h2>
        <c:if test="${not empty message}">
            <p style="color:green;">${message}</p>
        </c:if>
        <table border="1" cellpadding="5">
            <thead>
                <tr>
                    <th>Service ID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Image URL</th>
                    <th>Sort Order</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${items}" var="item">
                    <tr>
                        <td>${item.serviceID}</td>
                        <td>${item.title}</td>
                        <td>${item.description}</td>
                        <td>${item.imageURL}</td>
                        <td>${item.sortOrder}</td>
                        <td>
                            <a href="MarketingServiceItemController?action=edit&amp;serviceID=${item.serviceID}">Edit</a> |
                            <a href="MarketingServiceItemController?action=delete&amp;serviceID=${item.serviceID}"
                               onclick="return confirm('Are you sure to delete this item?');">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <a href="addServiceItem.jsp">Add New Service Item</a>
        <br><br>
        <a href="MarketingServiceSectionController">Back to Section</a>
    </body>
</html>


