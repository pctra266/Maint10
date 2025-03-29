<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Responsive Admin & Dashboard Template based on Bootstrap 5">
        <meta name="author" content="AdminKit">
        <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">
        <base href="${pageContext.request.contextPath}/">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="shortcut icon" href="icons/icon-48x48.png" />
        <link rel="canonical" href="https://demo-basic.adminkit.io/" />
        <title>Component Warehouse</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="css/media-show.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            .media-preview {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
            }
            .media-item {
                position: relative;
                display: inline-block;
            }
            .media-item img, .media-item video {
                max-width: 200px;
                margin: 5px;
            }
            .remove-btn {
                position: absolute;
                top: 0;
                right: 0;
                background: red;
                color: white;
                border: none;
                border-radius: 50%;
                width: 20px;
                height: 20px;
                cursor: pointer;
            }
            .is-invalid {
                border-color: red;
            }
            .invalid-feedback {
                color: red;
                font-size: 0.9rem;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="../../includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="../../includes/navbar-top.jsp" />
                <main class="content">
                    <form action="Redirect" enctype="multipart/form-data">
                        <input type="hidden" name="target" value="${empty detailComponentFrom ? '/MaintenanceSystem/ComponentWarehouse' : detailComponentFrom}">
                        <button type="submit" class="btn btn-primary d-flex align-items-center justify-content-center" style="transform:translate(-30%,-60%); height: 2.5rem; width: 5.2rem">
                            <i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span>
                        </button>
                    </form>
                    <h2>Component Detail</h2>
                    <!-- Alerts -->
                    <c:if test="${not empty codeAlert}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message"><strong>${codeAlert}</strong></div>
                        </div>
                    </c:if>
                    <!--                                Alert khi du lieu truyen sang sever sai-->

                    <c:if test="${not empty nameAlert}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${nameAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty quantityAlert}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${quantityAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty pictureAlert}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${pictureAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty priceAlert}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${priceAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty addAlert1}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${addAlert1}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty updateAlert1}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${updateAlert1}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty updateAlert0}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${updateAlert0}</strong>
                            </div>
                        </div>
                    </c:if>
                    <form class="row" action="ComponentWarehouse/Edit" method="POST" enctype="multipart/form-data">
                        <div class="col-md-8">
                            <div class="col-md-12 row g-3">
                                <input type="hidden" name="ID" value="${component.componentID}" required>
                                <div class="col-md-10">
                                    <label for="Name" class="form-label">Name</label>
                                    <input type="text" class="form-control" name="Name" id="Name" value="${component.componentName}" required>
                                </div>
                                <div class="col-md-10">
                                    <label for="Code" class="form-label">Code</label>
                                    <input type="text" class="form-control" name="Code" id="Code" value="${component.componentCode}" required>
                                </div>
                                <div class="col-md-3">
                                    <label for="Type" class="form-label">Type</label>
                                    <select name="Type" class="form-select form-select-md" id="Type" required style="flex: 1 1 auto;">
                                        <option value disabled selected>Choose</option>
                                        <c:forEach var="t" items="${typeList}">
                                            <option value="${t}" ${t eq component.type ? "selected" : ""}>${t}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label for="Brand" class="form-label">Brand</label>
                                    <select name="Brand" class="form-select form-select-md" id="Brand" required style="flex: 1 1 auto;" >
                                        <option value disabled selected>Choose</option>
                                        <c:forEach var="b" items="${brandList}">
                                            <option value="${b}" ${b eq component.brand ? "selected" : ""}>${b}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-6"></div>
                                <div class="col-md-3">
                                    <label for="Quantity" class="form-label">Quantity</label>
                                    <input type="text" class="form-control format-int" name="Quantity" id="Quantity" value="${component.quantity}" min="0" required>
                                </div>
                                <div class="col-md-3">
                                    <label for="Price" class="form-label">Price</label>
                                    <input type="text" class="form-control format-float" name="Price" id="Price" value="${component.price}" step="0.01" min="0" required>
                                </div>
                                <c:if test="${staff.hasPermissions('EDIT_COMPONENT')}">
                                    <div class="col-md-10">
                                        <label for="mediaFiles" class="form-label">Upload Images/Videos</label>
                                        <input type="file" class="form-control" name="mediaFiles" id="mediaFiles" accept="image/*,video/*" multiple onchange="previewMedia(event)">
                                        <div id="previewContainer" class="media-preview mt-3"></div>
                                    </div>       
                                </c:if>


                                <div class="col-md-10">
                                    <div class="d-flex flex-column">
                                        <label for="validationDefault06" class="form-label">Products</label>
                                        <c:if test="${not empty remove}">
                                            <div class="alert alert-success alert-dismissible" role="alert">
                                                <div class="alert-message text-center"><strong>${remove}</strong></div>
                                            </div>
                                        </c:if>
                                        <c:if test="${not empty addSuccess}">
                                            <div class="alert alert-success alert-dismissible" role="alert">
                                                <div class="alert-message text-center"><strong>${addSuccess}</strong></div>
                                            </div>
                                        </c:if>
                                        <c:if test="${staff.hasPermissions('ADD_COMPONENT_INTO_PRODUCT')}">

                                            <a href="ComponentWarehouse/AddProductToComponent?ID=${component.componentID}" class="btn btn-success mb-3 w-25">
                                                <i class="fas fa-plus"></i> Add Product
                                            </a>

                                        </c:if>

                                    </div>

                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th style="width:45%">Name</th>
                                                <th style="width:40%">Code</th>
                                                <th class="d-none d-md-table-cell" style="width:25%">Action</th>
                                            </tr>
                                        </thead>
                                        <c:forEach items="${list}" var="p">
                                            <tbody>
                                                <tr>
                                                    <td>${p.productName}</td>
                                                    <td>${p.code}</td>
                                                    <td>
                                                        <a href="viewProduct?action=update&id=${p.productId}">
                                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-eye align-middle"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle></svg>
                                                        </a>

                                                        <c:if test="${staff.hasPermissions('DELETE_COMPONENT_IN_PRODUCT')}">

                                                            <a onclick="confirmDelete('${component.componentID}', '${p.productId}')">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash align-middle">
                                                                <polyline points="3 6 5 6 21 6"></polyline>
                                                                <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                                                                </svg>
                                                            </a>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </c:forEach>
                                    </table>
                                </div>
                                <c:if test="${staff.hasPermissions('EDIT_COMPONENT')}">

                                    <div class="col-md-12">
                                        <button class="btn btn-primary" type="submit">Save</button>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <c:set var="count" value="${fn:length(images)+fn:length(videos)}"/>
                            <div class="row" id="mediaListContainer">
                                <c:forEach var="image" items="${images}">
                                    <div class="media-item-show col-md-${count<3?12/count:4}">
                                        <img src="${pageContext.request.contextPath}/${image}" alt="Warranty Image" onclick="showModal('${image}', 'image')">
                                    </div>
                                </c:forEach>
                                <c:forEach var="video" items="${videos}">
                                    <div class="media-item-show col-md-${count<3?12/count:4}">
                                        <video src="${pageContext.request.contextPath}/${video}" controls onclick="showModal('${video}', 'video')"></video>
                                    </div>
                                </c:forEach>
                            </div>
                            <!-- Modal for Zoom -->
                            <div id="mediaModal" class="modal">
                                <span class="modal-close" onclick="hideModal()">×</span>
                                <div id="modalContent" class="modal-content" style="background-color: #333333"></div>
                                <button type="button" id="prevButton" class="modal-nav prev" onclick="showPrevious()"><</button>
                                <button type="button" id="nextButton" class="modal-nav next" onclick="showNext()">></button>
                                <c:if test="${staff.hasPermissions('EDIT_COMPONENT')}">
                                    

                                <c:if test="${not empty component}">
                                    <button type="button" id="deleteMediaButton" class="btn btn-danger" style="position: absolute; top: 60px; right: 20px;" onclick="deleteCurrentMedia()">
                                        <i class="fa fa-trash"></i>
                                    </button>
                                </c:if>
                                </c:if>
                            </div>
                        </div>
                    </form>
                </main>
                <jsp:include page="../../includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script src="js/format-input.js"></script>
        <script src="js/media-show.js"></script>
        <c:if test="${!staff.hasPermissions('EDIT_COMPONENT')}">

            <script>
                                           const inputs = document.querySelectorAll("input");
                                           inputs.forEach(input => input.readOnly = true);
                                           const selects = document.querySelectorAll("select");
                                           selects.forEach(input => input.disabled = true);
            </script>
        </c:if>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var images = [
            <c:forEach var="image" items="${images}" varStatus="loop">
                "${image}"${loop.last ? '' : ','}
            </c:forEach>
                ];
                var videos = [
            <c:forEach var="video" items="${videos}" varStatus="loop">
                "${video}"${loop.last ? '' : ','}
            </c:forEach>
                ];
                initMediaList(images, videos);
            });

            function deleteCurrentMedia() {
                if (confirm('Are you sure you want to delete this media?')) {
                    const mediaToDelete = mediaList[currentIndex].src;
                    fetch('${pageContext.request.contextPath}/ComponentWarehouse/Edit', {
                        method: 'POST',
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                        body: 'ID=${component.componentID}&deleteMedia=' + encodeURIComponent(mediaToDelete)
                    }).then(response => {
                        if (response.ok) {
                            mediaList.splice(currentIndex, 1);
                            if (mediaList.length === 0) {
                                hideModal();
                            } else {
                                currentIndex = Math.min(currentIndex, mediaList.length - 1);
                                displayMedia(currentIndex);
                            }
                        } else {
                            alert('Failed to delete media.');
                        }
                        let allImgs = document.querySelectorAll('img');
                        allImgs.forEach(img => {
                            console.log(img.src + " " + mediaToDelete);
                            if (img.src.endsWith(mediaToDelete)) {
                                let parent = img.parentElement;
                                if (parent) {
                                    parent.remove();
                                }
                            }
                        });
                    }).catch(error => {
                        console.error('Error deleting media:', error);
                        alert('An error occurred while deleting the media.');
                    });
                }
            }

            let selectedFiles = [];
            function previewMedia(event) {
                const files = Array.from(event.target.files);
                selectedFiles = files;
                const previewContainer = document.getElementById('previewContainer');
                previewContainer.innerHTML = '';
                selectedFiles.forEach((file, index) => {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        const div = document.createElement('div');
                        div.className = 'media-item';
                        if (file.type.startsWith('image/')) {
                            const img = document.createElement('img');
                            img.src = e.target.result;
                            div.appendChild(img);
                        } else if (file.type.startsWith('video/')) {
                            const video = document.createElement('video');
                            video.src = e.target.result;
                            video.controls = true;
                            div.appendChild(video);
                        }
                        const removeBtn = document.createElement('button');
                        removeBtn.className = 'remove-btn';
                        removeBtn.innerText = 'X';
                        removeBtn.onclick = () => removeMedia(index);
                        div.appendChild(removeBtn);
                        previewContainer.appendChild(div);
                    };
                    reader.readAsDataURL(file);
                });
            }

            function removeMedia(index) {
                selectedFiles.splice(index, 1);
                const input = document.getElementById('mediaFiles');
                const dataTransfer = new DataTransfer();
                selectedFiles.forEach(file => dataTransfer.items.add(file));
                input.files = dataTransfer.files;
                previewMedia({target: input});
            }

            function confirmDelete(componentID, productID) {
                if (confirm('Are you sure you want to delete this product from the component?')) {
                    window.location.href = 'ComponentWarehouse/Detail?ID=' + componentID + '&product=' + productID + '&action=remove';
                }
            }
        </script>
    </body>
</html>