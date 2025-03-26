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
                            <c:if test="${not empty message1}">
                                <div class="alert alert-success mt-2" role="alert">
                                    ${message1}
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
                                <div class="alert alert-success mt-2" role="alert">
                                    ${message}
                                </div>
                            </c:if>
                        </div>
                        <div class="card-body" >
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Service ID</th>
                                        <th>Title</th>
                                        <th>Description</th>
                                        <th>Image </th>
                                        <th>Sort Order</th>
                                        <th>Update</th>
                                        <th>Delete</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${items}" var="item">
                                        <tr>
                                            <td>${item.serviceID}</td>
                                            <td>${item.title}</td>
                                            <td>${item.description}</td>
                                            <td>
                                                <c:if test="${not empty item.imageURL}">
                                                    <img src="${pageContext.request.contextPath}${item.imageURL}" alt="Image" class="img-thumbnail" style="max-width: 80px;">
                                                </c:if>
                                                <c:if test="${empty item.imageURL}">
                                                    <span>No Image</span>
                                                </c:if>
                        </td>
                                            <td>${item.sortOrder}</td>
                                            <td>
                                                <!-- Edit button mở modal -->
                        <button type="button" class="btn btn-primary" 
                                data-serviceid="${item.serviceID}"
                                data-sectionid="${item.sectionID}"
                                data-title="${item.title}"
                                data-description="${item.description}"
                                data-imageurl="${item.imageURL}"
                                data-sortorder="${item.sortOrder}"
                                data-bs-toggle="modal" data-bs-target="#editItemModal">
                            Update
                        </button>
                                            </td>
                                            <td>
                                                <a class="btn btn-primary" href="MarketingServiceItemController?action=delete&amp;serviceID=${item.serviceID}"
                                                   onclick="return confirm('Are you sure to delete this item?');">Delete</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <br>
                                    <!-- Button Add New mở modal -->
         <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addItemModal">
        Add New Service Item
    </button>
    <!-- Modal Add New Service Item -->
<div class="modal fade" id="addItemModal" tabindex="-1" aria-labelledby="addItemModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form id="addItemForm" action="MarketingServiceItemController" method="post" enctype="multipart/form-data">
        <div class="modal-header">
          <h5 class="modal-title" id="addItemModalLabel">Add New Service Item</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <input type="hidden" name="action" value="new">
            <input type="hidden" name="sectionID" value="1">
            <div class="mb-3">
                <label for="newTitle" class="form-label">Title:</label>
                <input type="text" class="form-control" id="newTitle" name="title" required>
            </div>
            <div class="mb-3">
                <label for="newDescription" class="form-label">Description:</label>
                <textarea class="form-control" id="newDescription" name="description" rows="3" required></textarea>
            </div>
            <div class="mb-3">
                <label for="newImageURL" class="form-label">Image:</label>
                <input type="file" class="form-control" id="newImageURL" name="imageURL" accept="image/*" onchange="previewNewImage(event)">
                <img id="newImagePreview" src="" alt="Image Preview" class="img-fluid mt-2" style="max-height: 150px; display:none;">
                <div id="imageError" class="text-danger mt-1" style="display:none;">Please select an image file.</div>
            </div>
            <div class="mb-3">
                <label for="newSortOrder" class="form-label">Sort Order:</label>
                <input type="number" class="form-control" id="newSortOrder" name="sortOrder" required>
            </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button type="submit" class="btn btn-primary">Add Service Item</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Modal Edit Service Item -->
<div class="modal fade" id="editItemModal" tabindex="-1" aria-labelledby="editItemModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form id="editItemForm" action="MarketingServiceItemController" method="post" enctype="multipart/form-data">
          
        <div class="modal-header">
          <h5 class="modal-title" id="editItemModalLabel">Edit Service Item</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <input type="hidden" name="action" value="edit">
            <input type="hidden" id="editServiceID" name="serviceID" value="">
            <input type="hidden" name="sectionID" value="1">
            <div class="mb-3">
                <label for="editTitle" class="form-label">Title:</label>
                <input type="text" class="form-control" id="editTitle" name="title" required>
            </div>
            <div class="mb-3">
                <label for="editDescription" class="form-label">Description:</label>
                <textarea class="form-control" id="editDescription" name="description" rows="3" required></textarea>
            </div>
            <div class="mb-3">
                
                <label for="editImageURL" class="form-label">Image:</label>
                <input type="file" class="form-control" id="editImageURL" name="imageURL" accept="image/*" onchange="previewEditImage(event)">
                <img id="editImagePreview" src="" alt="Image Preview" class="img-fluid mt-2" style="max-height: 150px; display:none;">
                <div id="imageError" class="text-danger mt-1" style="display:none;">Please select an image file.</div>
            </div>
            
            
            <div class="mb-3">
                <label for="editSortOrder" class="form-label">Sort Order:</label>
                <input type="number" class="form-control" id="editSortOrder" name="sortOrder" required>
            </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button type="submit" class="btn btn-primary">Update Service Item</button>
        </div>
      </form>
    </div>
  </div>
  </div>
                        </div><!-- end -->
                    </div>

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
            <script src="js/app.js"></script>
                <script>
    // Preview image for Add New
    function previewNewImage(event) {
        var file = event.target.files[0];
        if (file) {
            var preview = document.getElementById('newImagePreview');
            preview.src = URL.createObjectURL(file);
            preview.style.display = "block";
        }
    }
    
    // Preview image for Edit
    function previewEditImage(event) {
        var file = event.target.files[0];
        if (file) {
            var preview = document.getElementById('editImagePreview');
            preview.src = URL.createObjectURL(file);
            preview.style.display = "block";
        }
    }
    
    // Nạp dữ liệu vào modal Edit khi mở
    $('#editItemModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var serviceID = button.data('serviceid');
        var title = button.data('title');
        var description = button.data('description');
        var imageURL = button.data('imageurl');
        var sortOrder = button.data('sortorder');
        
        // Set dữ liệu vào form
        $('#editServiceID').val(serviceID);
        $('#editTitle').val(title);
        $('#editDescription').val(description);
        $('#editSortOrder').val(sortOrder);
        if(imageURL) {
        $('#currentImageURL').val(imageURL);
        $('#editImagePreview').attr('src', '${pageContext.request.contextPath}' + imageURL).show();

    } else {
        $('#editImagePreview').hide();
    }
    });
    
    // Gửi form AJAX cho modal Add và Edit
    $("#addItemForm, #editItemForm").submit(function(e){
        e.preventDefault(); // Ngăn submit thông thường
        var form = $(this);
        $.ajax({
            url: form.attr("action"),
            type: "POST",
            data: new FormData(this),
            processData: false,
            contentType: false,
            success: function(response){
                location.reload(); // Reload trang để cập nhật danh sách
            },
            error: function(xhr, status, error){
                alert("Operation failed: " + error);
            }
        });
    });
</script>
<!-- Script cho validate và preview ảnh -->


        </div>
    </body>
</html>


