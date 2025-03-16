<%-- 
    Document   : WarrantyCardDetail
    Created on : Feb 19, 2025, 12:05:43 PM
    Author     : ADMIN
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
        <title>Warranty Card</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="css/media-show.css" rel="stylesheet">
        <%--vnpay --%>
        <script src="js/jquery-1.11.3.min.js"></script>
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
                        <input type="hidden" name="target" value="${empty detailWarrantyCardFrom ? '/MaintenanceSystem/WarrantyCard' : detailWarrantyCardFrom}">
                        <button type="submit" class="btn btn-primary d-flex align-items-center justify-content-center" style="transform:translate(-30%,-60%); height: 2.5rem; width: 5.2rem">
                            <i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span>
                        </button>
                    </form>

                    <!-- Alerts -->
                    <c:if test="${not empty addAlert1}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message"><strong>${addAlert1}</strong></div>
                        </div>
                    </c:if>
                    <c:if test="${not empty updateAlert1}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message"><strong>${updateAlert1}</strong></div>
                        </div>
                    </c:if>
                    <c:if test="${not empty updateAlert0}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message"><strong>${updateAlert0}</strong></div>
                        </div>
                    </c:if>             
                    <!-- Process Buttons -->
                    <div class="mb-3">
                        <c:if test="${latestProcess!=null && !(latestProcess.action=='create'||latestProcess.action=='refuse')}">
                            <div class="com-md-12 d-flex justify-content-between">
                                <h2>Now process: ${latestProcess.action} </h2>
                                <!-- Nút Show Invoices chuyển hướng đến trang mới -->
                                <div class="d-flex ">
                                    <div>
                                        <form>
                                            <a href="Invoice/List?ID=${card.warrantyCardID}" class="btn btn-info me-2 h-100">Invoice list</a>

                                        </form>

                                    </div>
                                    <div>
                                        <form action="searchwc" class="h-100" method="post">
                                            <input type="hidden" id="warrantyCode" name="warrantyCode" value="${card.warrantyCardCode}" >
                                            <button type="submit" class="btn btn-primary me-4"><i class="fa fa-file-pdf me-2"></i>Export</button>
                                        </form>
                                    </div>

                                </div>

                            </div>
                            <h3>Process Actions</h3>
                            <c:if test="${!latestProcess.action.endsWith('outsource') || latestProcess.action=='receive_from_outsource' || latestProcess.action=='refuse_outsource' || latestProcess.action=='cancel_outsource'}">
                                <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="process">
                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                    <input type="hidden" name="processAction" value="fixing">
                                    <button type="submit" class="btn btn-primary me-2" ${latestProcess != null && (latestProcess.action == 'receive' || latestProcess.action=='receive_from_outsource' || latestProcess.action=='refuse_outsource' || latestProcess.action=='cancel_outsource')? '' : 'disabled'}>Fixing</button>
                                </form>
                                <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="process">
                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                    <input type="hidden" name="processAction" value="refix">
                                    <button type="submit" class="btn btn-warning me-2" ${latestProcess != null && (latestProcess.action == 'fixed' || latestProcess.action == 'completed' || latestProcess.action == 'cancel') ? '' : 'disabled'}>Refix</button>
                                </form>
                                <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="process">
                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                    <input type="hidden" name="processAction" value="outsource">
                                    <button type="submit" class="btn btn-info me-2" ${latestProcess != null  && (latestProcess.action == 'fixing'||latestProcess.action == 'refix') ? '' : 'disabled'}>Outsource</button>
                                </form>
                                <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="process">
                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                    <input type="hidden" name="processAction" value="fixed">
                                    <button type="submit" class="btn btn-success me-2" ${latestProcess != null && (latestProcess.action == 'fixing'||latestProcess.action == 'refix'||latestProcess.action == 'outsource') ? '' : 'disabled'}>Fixed</button>
                                </form>
                                <form action="Invoice/Create" method="get" class="d-inline">
                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                    <button type="submit" class="btn btn-info me-2">Create Invoice</button>
                                </form>
                                <form action="vnpayajax" id="frmCreateOrder" method="post" class="d-inline">     
                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                    <input type="hidden" data-val="true" data-val-number="The field Amount must be a number." data-val-required="The Amount field is required." id="amount"  name="amount" type="number" value="${price}" />
                                    <input type="hidden" id="bankCode" name="bankCode" value="">
                                    <input type="hidden" id="language" name="language" value="en">
                                    <button type="submit" class="btn btn-success me-2" ${latestProcess != null && latestProcess.action == 'fixed' ? '' : 'disabled'} href>Payment</button>
                                </form>
                                <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="process">
                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                    <input type="hidden" name="processAction" value="cancel">
                                    <button type="submit" class="btn btn-danger me-2" ${latestProcess != null && latestProcess.action != 'completed' && latestProcess.action != 'fixed' && latestProcess.action != 'cancel' ? '' : 'disabled'}>Cancel</button>
                                </form>
                                <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="process">
                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                    <input type="hidden" name="processAction" value="refuse">
                                    <button type="submit" class="btn btn-instagram" ${latestProcess != null && latestProcess.action != 'completed' && latestProcess.action != 'fixed' ? '' : 'disabled'}>Refuse</button>
                                </form>
                            </c:if>
                            <c:if test="${latestProcess.action.endsWith('outsource') && latestProcess.action!='receive_from_outsource' && latestProcess.action!='refuse_outsource' && latestProcess.action!='cancel_outsource' }">
                                <form action="WarrantyCard/OutsourceRequest" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="processOutsource">
                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                    <input type="hidden" name="processAction" value="cancel_outsource">
                                    <button type="submit" class="btn btn-instagram" ${latestProcess != null && (latestProcess.action == 'request_outsource' && latestProcess.action != 'accept_outsource')? '' : 'disabled'}>Cancel request</button>
                                </form>
                                <form action="WarrantyCard/OutsourceRequest" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="processOutsource">
                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                    <input type="hidden" name="processAction" value="send_outsource">
                                    <button type="submit" class="btn btn-primary" ${latestProcess != null && latestProcess.action == 'accept_outsource' ? '' : 'disabled'}>Hand over</button>
                                </form>
                                <form action="WarrantyCard/OutsourceRequest" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="processOutsource">
                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                    <input type="hidden" name="processAction" value="receive_from_outsource">
                                    <button type="submit" class="btn btn-success" ${latestProcess != null && latestProcess.action == 'back_outsource'? '' : 'disabled'}>Receive</button>
                                </form>
                            </c:if>
                        </div>
                    </c:if>

                    <div class="row">
                        <div class="col-md-8 row">  
                            <c:if test="${latestProcess!=null && !(latestProcess.action=='create'||latestProcess.action=='refuse')}">
                                <h3>Repair List</h3>
                                <div class="row">
                                    <div class="mb-2 col-auto">
                                        <form action="WarrantyCard/AddComponent">
                                            <input type="hidden" name="ID" value="${card.warrantyCardID}"/>
                                            <button  class="btn btn-primary" ${latestProcess != null && latestProcess.action != 'fixed' && latestProcess.action != 'completed' && latestProcess.action != 'cancel' ? "":"disabled"}>
                                                <i class="fas fa-plus"></i> Add New Component
                                            </button>
                                        </form>

                                    </div>
                                    <form action="/MaintenanceSystem/componentRequest" class="mb-2 col-auto">
                                        <input type="hidden" name="action" value="createComponentRequest" readonly> 
                                        <input type="hidden" name="warrantyCardID" value="${card.warrantyCardID}" readonly>  
                                        <input type="hidden" name="productCode" value="${card.productCode}" readonly>  
                                        <button type="submit" class="btn btn-success"><i class="fas fa-add"></i> Request Component</button>
                                    </form>
                                </div>
                                <table class="table table-hover my-0 ">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Name</th>
                                            <th>Status</th>
                                            <th>Price</th>
                                            <th>Quantity</th>
                                            <th>Note</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="detail" items="${cardDetails}" varStatus="status">
                                            <tr class="${status.index % 2 == 0 ? 'table-primary' : ''}">
                                                <td>${status.index + 1}</td>
                                                <td>${detail.componentName}</td>
                                                <td>
                                                    <form action="WarrantyCard/Detail"  method="post" id="updateForm-${status.index}">
                                                        <input type="hidden" name="action" value="update">
                                                        <input type="hidden" name="warrantyCardDetailID" value="${detail.warrantyCardDetailID}">
                                                        <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                                        <select name="status" class="form-select status-select ${ updateAlert0!=null && detail.status == 'fixing'? "border-warning border-2":"" }">
                                                            <option value="fixing" ${detail.status == 'fixing' ? 'selected' : ''}>Fixing</option>
                                                            <option value="warranty_repaired" ${detail.status == 'warranty_repaired' ? 'selected' : ''}>Repaired (Warranty)</option>
                                                            <option value="warranty_replaced" ${detail.status == 'warranty_replaced' ? 'selected' : ''}>Replaced (Warranty)</option>
                                                            <option value="repaired" ${detail.status == 'repaired' ? 'selected' : ''}>Repaired</option>
                                                            <option value="replace" ${detail.status == 'replace' ? 'selected' : ''}>Replace</option>
                                                        </select>
                                                </td>
                                                <td>
                                                    <input type="text" name="price" class="form-control form-control-sm price-input format-float" value="${detail.price}" min="0" step="0.01" ${detail.status == 'warranty_repaired' || detail.status == 'warranty_replaced' ? 'readonly' : ''}>
                                                </td>
                                                <td>
                                                    <input type="text" name="quantity" class="form-control form-control-sm format-int" value="${detail.quantity}" min="0">
                                                </td>
                                                <td>
                                                    <textarea type="text" name="note" class="form-control form-control-sm">${detail.note}</textarea>
                                                    </form>
                                                </td>
                                                <td class="table-action">
                                                    <button type="submit" form="updateForm-${status.index}" class="btn btn-sm me-1 save"  title="Save">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-save align-middle"><path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"></path><polyline points="17 21 17 13 7 13 7 21"></polyline><polyline points="7 3 7 8 15 8"></polyline></svg>
                                                    </button>
                                                    <form action="WarrantyCard/Detail" method="post" class="d-inline" id="deleteForm-${status.index}">
                                                        <input type="hidden" name="action" value="delete">
                                                        <input type="hidden" name="warrantyCardDetailID" value="${detail.warrantyCardDetailID}">
                                                        <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                                        <button type="submit" class="btn btn-sm" onclick="return confirm('Are you sure you want to delete this component?');" title="Delete">
                                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash align-middle"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <h2 class="mt-4">Warranty Code: ${card.warrantyCardCode}</h2>
                            <div class="col-md-6 ">
                                <div class="row g-2 ms-2">
                                    <div class="col-md-12"><h3>CUSTOMER INFORMATION</h3></div>
                                    <div class="col-md-12"><div>Name: ${customer.name}</div></div>
                                    <div class="col-md-12"><div>Phone: ${customer.phone}</div></div>
                                    <div class="col-md-12"><div>Email: ${customer.email}</div></div>
                                    <div class="col-md-12"><h3>HANDLER INFORMATION</h3></div>
                                    <div class="col-md-12"><div>Name: ${handler.name}</div></div>
                                    <div class="col-md-12"><div>Phone: ${handler.phone}</div></div>
                                    <div class="col-md-12"><div>Employee ID: ${handler.usernameS}</div></div>
                                    <div class="col-md-12"><h3>PRODUCT</h3></div>
                                    <div class="col-md-12"><div>Product Code: ${card.productDetailCode}</div></div>
                                    <div class="col-md-12"><div>Product Name: ${card.productName}</div></div>
                                    <div class="col-md-12"><div>Purchased Date: ${pd.getFormatPurchaseDate()}</div></div>
                                    <div class="col-md-12">
                                        <div>Warranty Period (Months): ${pd.warrantyPeriod}</div>
                                    </div>
                                    <div class="col-md-12">
                                        <div id="warrantyStatus"></div>
                                    </div>
                                    <!-- Upload file ảnh -->
                                    <form action="WarrantyCard/Detail"  method="POST" enctype="multipart/form-data">
                                        <input type="hidden" name="action" value="uploadImages">
                                        <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                        <div class="col-md-12">
                                            <label for="mediaFiles" class="form-label">Upload Images/Videos:</label>
                                            <input type="file" class="form-control" name="mediaFiles" id="mediaFiles" accept="image/*,video/*" multiple onchange="previewMedia(event)">
                                            <div id="previewContainer" class="media-preview mt-3"></div>   
                                        </div>
                                        <div class="col-md-3">
                                            <button type="submit" class="btn btn-primary">Upload</button>
                                        </div> 
                                    </form>
                                    <div class="col-md-12">
                                        <div>Issue Description:</div>
                                        <input type="hidden" class="form-control" value="${card.warrantyCardID}" readonly>  
                                        <div class="col-md-12">
                                            <textarea class="form-control" readonly>${card.issueDescription}</textarea>
                                        </div>
                                    </div>  
                                </div>

                            </div>
                            <div class="col-md-6 ">
                                <div class="row g-2 mt-4">
                                    <table class="table table-bordered">
                                        <tbody>
                                            <tr>
                                                <th>Canceled Date</th>
                                                <td>${card.getFormatCanceldDate()}</td>
                                            </tr>
                                            <tr>
                                                <th>Created Date</th>
                                                <td>${card.getFormatCreatedDate()}</td>
                                            </tr>
                                            <tr>
                                                <th>Estimated Return Date</th>
                                                <td>${card.getFormatReturnDate()}</td>
                                            </tr>
                                            <tr>
                                                <th>Repaired Date</th>
                                                <td>${card.getFormatDonedDate()}</td>
                                            </tr>
                                            <tr>
                                                <th>Completed Date</th>
                                                <td>${card.getFormatCompletedDate()}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <h3>Component request:</h3>
                            <c:if test="${empty componentRequests}">
                                <h4 class="text-center text-black-50">No component request created.</h4>
                            </c:if>
                            <c:forEach var="request" items="${componentRequests.keySet()}">
                                <table class="table table-bordered ">
                                    <thead>
                                        <tr>
                                            <td width="60%">${request.componentRequestID}</td>
                                            <td width="20%">Quantity</td>
                                            <td width="20%">Status</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="requestDetail" varStatus="status" items="${componentRequests[request]}">
                                            <tr>
                                                <td>${requestDetail.componentName}</td>
                                                <td>${requestDetail.quantity}</td>
                                                <c:if test="${status.index==0}">
                                                    <td rowspan="${componentRequests[request].size()}" class="text-center" style="color:${request.status eq 'approved'?"#12c700":request.status eq 'cancel'?"red":""}">
                                                        ${request.status}
                                                    </td>
                                                </c:if>
                                            </tr>   
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:forEach>
                            <%--For showing images --%>
                            <h3 class="mt-2">Medias:</h3>
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
                                <c:if test="${not empty card}">
                                    <button type="button" id="deleteMediaButton" class="btn btn-danger" style="position: absolute; top: 60px; right: 20px;" onclick="deleteCurrentMedia()">
                                        <i class="fa fa-trash"></i> 
                                    </button>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div class="row ms-0">
                        <div class="col-md-8 row ">
                            <c:if test="${latestProcess!=null && (latestProcess.action=='create' || latestProcess.action == 'refuse')}">
                                <div class = "col-md-12 d-flex justify-content-center mt-2" >
                                    <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                        <input type="hidden" name="action" value="process">
                                        <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                        <input type="hidden" name="processAction" value="receive">
                                        <button type="submit" class="btn-lg btn-primary me-2" ${latestProcess != null && (latestProcess.action == 'create'||latestProcess.action == 'cancel' || latestProcess.action == 'refuse' ) ? '' : 'disabled'}>RECEIVE</button>
                                    </form>                    
                                </div>
                            </c:if>
                        </div>

                </main>
                <jsp:include page="../../includes/footer.jsp" />
            </div>

            <script src="js/app.js"></script>
            <script src="js/format-input.js"></script>
            <script src="js/media-show.js"></script>
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
                                                fetch('${pageContext.request.contextPath}/WarrantyCard/Detail', {
                                                    method: 'POST',
                                                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                                                    body: 'ID=${card.warrantyCardID}&action=deleteMedia&deleteMedia=' + encodeURIComponent(mediaToDelete)
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
                                                    //An the img co src bang voi mediaToDelete
                                                    let allImgs = document.querySelectorAll('img');
                                                    allImgs.forEach(img => {
                                                        if (img.src.endsWith(mediaToDelete)) {
                                                            let parent = img.parentElement; // Lấy thẻ cha của <img>
                                                            if (parent) {
                                                                parent.remove(); // Xóa luôn thẻ bọc ngoài
                                                            }
                                                        }
                                                    });
                                                    //
                                                }).catch(error => {
                                                    console.error('Error deleting media:', error);
                                                    alert('An error occurred while deleting the media.');
                                                });
                                            }
                                        }
                                        // Table: Disable price input for warranty states
                                        document.querySelectorAll('.status-select').forEach(select => {
                                            const priceInput = select.closest('tr').querySelector('.price-input');
                                            select.addEventListener('change', function () {
                                                priceInput.readOnly = (this.value === 'warranty_repaired' || this.value === 'warranty_replaced');
                                                if (this.value === 'warranty_repaired' || this.value === 'warranty_replaced')
                                                    priceInput.value = 0;
                                            });
                                        });

                                        window.onload = function () {
                                            const purchasedDateStr = "${pd.getFormatPurchaseDate()}"; // Giả sử định dạng "dd/MM/yyyy"
                                            const warrantyPeriod = ${pd.warrantyPeriod==null?0:pd.warrantyPeriod}; // Số tháng bảo hành
                                            const statusDiv = document.getElementById('warrantyStatus');
                                            // Kiểm tra purchasedDateStr có hợp lệ không
                                            console.log("purchasedDateStr:", purchasedDateStr); // Debug giá trị
                                            if (purchasedDateStr.length < 1) {
                                                statusDiv.innerText = "Not covered by warranty";
                                                statusDiv.style.color = "orange";
                                                return;
                                            }

                                            if (!purchasedDateStr || purchasedDateStr.trim() === '' || !purchasedDateStr.includes('-')) {
                                                statusDiv.innerText = "Status: Cannot determine warranty status (invalid purchase date)";
                                                statusDiv.style.color = "orange";
                                                return;
                                            }

                                            // Tách chuỗi thủ công thay vì dùng destructuring
                                            const dateParts = purchasedDateStr.split('-');
                                            if (dateParts.length !== 3) {
                                                statusDiv.innerText = "Status: Cannot determine warranty status (invalid date format)";
                                                statusDiv.style.color = "orange";
                                                return;
                                            }

                                            // Gán giá trị truyền thống
                                            const day = parseInt(dateParts[0], 10);
                                            const month = parseInt(dateParts[1], 10);
                                            const year = parseInt(dateParts[2], 10);
                                            // Kiểm tra các giá trị có hợp lệ không
                                            if (isNaN(day) || isNaN(month) || isNaN(year)) {
                                                statusDiv.innerText = "Status: Cannot determine warranty status (invalid date components)";
                                                statusDiv.style.color = "orange";
                                                return;
                                            }

                                            // Tạo Date object (month - 1 vì JS đếm từ 0)
                                            const purchasedDate = new Date(year, month - 1, day);
                                            // Kiểm tra Date có hợp lệ không
                                            if (isNaN(purchasedDate.getTime())) {
                                                statusDiv.innerText = "Status: Cannot determine warranty status (invalid date)";
                                                statusDiv.style.color = "orange";
                                                return;
                                            }

                                            // Tính ngày hết bảo hành
                                            const warrantyEndDate = new Date(purchasedDate);
                                            warrantyEndDate.setMonth(purchasedDate.getMonth() + warrantyPeriod);
                                            // So sánh với ngày hiện tại
                                            const today = new Date();
                                            today.setHours(0, 0, 0, 0);
                                            if (today <= warrantyEndDate) {
                                                statusDiv.innerText = "Status: Still under warranty";
                                                statusDiv.style.color = "green";
                                            } else {
                                                statusDiv.innerText = "Status: Out of warranty";
                                                statusDiv.style.color = "red";
                                            }
                                        };
                                        let selectedFiles = []; // Lưu danh sách file để preview và xóa

                                        // Preview và xóa file
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
                                        // Thiết lập Due Date mặc định khi modal mở
                                        document.getElementById('centeredModalPrimary_Invoice_1').addEventListener('shown.bs.modal', function () {
                                            const dueDateInput = document.getElementById('dueDate');
                                            const today = new Date();
                                            const defaultDueDate = new Date(today);
                                            defaultDueDate.setDate(today.getDate() + 3); // Thêm 3 ngày

                                            const year = defaultDueDate.getFullYear();
                                            const month = String(defaultDueDate.getMonth() + 1).padStart(2, '0');
                                            const day = String(defaultDueDate.getDate()).padStart(2, '0');
                                            const formattedDate = year + "-" + month + "-" + day;

                                            dueDateInput.value = formattedDate;
                                        });

            </script>
            <%-- vnpay --%>
            <script type="text/javascript">
                $("#frmCreateOrder").submit(function () {
                    var postData = $("#frmCreateOrder").serialize();
                    var submitUrl = $("#frmCreateOrder").attr("action");
                    $.ajax({
                        type: "POST",
                        url: submitUrl,
                        data: postData,
                        dataType: 'JSON',
                        success: function (x) {
                            if (x.code === '00') {
                                if (window.vnpay) {
                                    vnpay.open({width: 768, height: 600, url: x.data});
                                } else {
                                    location.href = x.data;
                                }
                                return false;
                            } else {
                                alert(x.Message);
                            }
                        }
                    });
                    return false;
                });
            </script>       
    </body>
</html>