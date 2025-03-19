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
        <link rel="stylesheet" href="css/light.css">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <!-- Customize Cover Image -->
                    <div class="card shadow-sm mb-4">
                        <div class="card-header">
                            <h2>Current Cover Image:</h2>
                            <c:if test="${not empty messBackground}">
                                <p style="color: green;">${messBackground}</p>
                            </c:if>
                        </div>
                        <div class="card-body" >
                            <div>
                                <img src="${pageContext.request.contextPath}${backgroundImage}" alt="Cover Image" width="400px">
                            </div>
                            <form action="updateCover" method="post" enctype="multipart/form-data">
                                <label for="coverImage">Select New Cover Image:</label>
                                <input type="file" name="coverImage" id="coverImage" accept="image/*" required>
                                <button class="btn btn-primary" type="submit">Upload</button>
                            </form>
                        </div>
                    </div>
                    <!-- Customize Footer -->
                    <div class="card shadow-sm mb-4">
                        <div class="card-header">
                            <h2 class="mb-0">Customize Footer</h2>
                            <c:if test="${not empty messFooter}">
                                <div class="alert alert-success" role="alert">
                                    ${messFooter}
                                </div>
                            </c:if>
                        </div>
                        <div class="card-body">
                            <form action="FooterController" method="post">
                                <div class="mb-3">
                                    <label for="slogan" class="form-label">Slogan:</label>
                                    <input type="text" class="form-control" id="slogan" name="slogan" value="${footer.slogan}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="address" class="form-label">Address:</label>
                                    <input type="text" class="form-control" id="address" name="address" value="${footer.address}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="hotline" class="form-label">Hotline:</label>
                                    <input type="text" class="form-control" id="hotline" name="hotline" value="${footer.hotline}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email:</label>
                                    <input type="email" class="form-control" id="email" name="email" value="${footer.email}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="copyrightYear" class="form-label">Copyright Year:</label>
                                    <input type="text" class="form-control" id="copyrightYear" name="copyrightYear" value="${footer.copyrightYear}" required>
                                </div>
                                <button style="width: 7%" type="submit" class="btn btn-primary">Update</button>
                            </form>
                        </div>
                    </div>

                    <!-- Customize Warranty Consultation Text -->
                    <div class="card shadow-sm mb-4">
                        <div class="card-header">
                            <h2 class="mb-0">Customize Warranty Consultation Text</h2>
                        </div>
                        <div class="card-body">
                            <c:if test="${not empty messContactText}">
                                <div class="alert alert-success" role="alert">
                                    ${messContactText}
                                </div>
                            </c:if>

                            <form action="ContactController" method="post">
                                <div class="mb-3">
                                    <label for="title" class="form-label">Title:</label>
                                    <input type="text" id="title" name="title" class="form-control" value="${contactText.title}" required>
                                </div>

                                <div class="mb-3">
                                    <label for="subtitle" class="form-label">Subtitle:</label>
                                    <textarea id="subtitle" name="subtitle" class="form-control" rows="3" required>${contactText.subtitle}</textarea>
                                </div>

                                <button style="width: 7%" class="btn btn-primary" type="submit">Update</button>
                            </form>
                        </div>
                    </div>


                    <div class="card shadow-sm mb-4">
                        <div class="card-header">
                            <h2 class="mb-0">Manage Marketing Service Section</h2>
                            <c:if test="${not empty message}">
                                <div class="alert alert-success mt-2" role="alert">
                                    ${message}
                                </div>
                            </c:if>
                        </div>
                        <div class="card-body">
                            <form action="MarketingServiceSectionController" method="post">
                                <input type="hidden" name="sectionID" value="${section.sectionID}">

                                <div class="mb-3">
                                    <label for="title" class="form-label">Title:</label>
                                    <input type="text" class="form-control" id="title" name="title" value="${section.title}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="subTitle" class="form-label">SubTitle:</label>
                                    <input type="text" class="form-control" id="subTitle" name="subTitle" value="${section.subTitle}" required>
                                </div>
                                <button style="width: 10%" type="submit" class="btn btn-primary ">Update Section</button>
                            </form>
                        </div>
                    </div>


                    <div class="card shadow-sm mb-4">
                        <div class="card-header">
                            <h2>Manage Marketing Service Items</h2>
                            <c:if test="${not empty message}">
                                <p style="color:green;">${message}</p>
                            </c:if>
                        </div>
                        <div class="card-body" >
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Service ID</th>
                                        <th>Title</th>
                                        <th>Description</th>
                                        <!--<th>Image URL</th>-->
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
                                            <!--<td>${item.imageURL}</td>-->
                                            <td>${item.sortOrder}</td>
                                            <td>
                                                <a class="btn btn-primary" href="MarketingServiceItemController?action=edit&amp;serviceID=${item.serviceID}">Edit</a> |
                                                <a class="btn btn-primary" href="MarketingServiceItemController?action=delete&amp;serviceID=${item.serviceID}"
                                                   onclick="return confirm('Are you sure to delete this item?');">Delete</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <br>
                            <a class="btn btn-primary" href="addServiceItem.jsp">Add New Service Item</a>
                        </div><!-- end -->
                    </div>

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
            <script src="js/app.js"></script>
        </div>
    </body>
</html>


