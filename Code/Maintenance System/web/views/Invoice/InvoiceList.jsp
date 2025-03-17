<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Invoice List</title>
        <base href="${pageContext.request.contextPath}/">
        <link href="css/light.css" rel="stylesheet">
        <%--vnpay --%>
        <script src="js/jquery-1.11.3.min.js"></script>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="../../includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="../../includes/navbar-top.jsp" />
                <main class="content">
                    <div class="col-md-12 row d-flex justify-content-center">
                        <div class="col-md-10">
                            <h1 class="text-center">Invoices for Warranty Card: ${warrantyCard.warrantyCardCode}</h1>

                            <!-- Nút Back -->
                            <a href="WarrantyCard/Detail?ID=${warrantyCard.warrantyCardID}" class="btn btn-primary mb-3">
                                <i class="fas fa-arrow-left"></i> Back to Warranty Card
                            </a>
                            <c:if test="${not empty paymentMessage}">
                                <div class="alert ${paymentSuccess ? 'alert-success' : 'alert-danger'} alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message"><strong>${paymentMessage}</strong></div>
                                </div>
                            </c:if>
                            <!-- Danh sách invoice -->
                            <c:choose>
                                <c:when test="${not empty invoices}">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Invoice Number</th>
                                                <th>Amount</th>
                                                <th>Issued Date</th>
                                                <th>Due Date</th>
                                                <th>Status</th>
                                                <th>Created By</th>
                                                <th>Customer</th>
                                                <th>Action</>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="invoice" items="${invoices}" varStatus="loop">
                                                <tr>
                                                    <td>${loop.index + 1}</td>
                                                    <td>${invoice.invoiceNumber}</td>
                                                    <td>${invoice.amount}</td>
                                                    <td>${invoice.issuedDate}</td>
                                                    <td>${invoice.dueDate}</td>
                                                    <td>${invoice.status}</td>
                                                    <td>${invoice.createdBy}</td>
                                                    <td>${warrantyCard.customerName}</td>
                                                    <td class="d-flex flex-column">
                                                        <form action="vnpayajax" id="frmCreateOrder-${invoice.invoiceID}" method="post" >     
                                                            <input type="hidden" name="ID" value="${invoice.invoiceID}">
                                                            <input type="hidden" data-val="true" data-val-number="The field Amount must be a number." 
                                                                   data-val-required="The Amount field is required." id="amount-${invoice.invoiceID}"  
                                                                   name="amount" type="number" value="${price}" />
                                                            <input type="hidden" id="bankCode-${invoice.invoiceID}" name="bankCode" value="">
                                                            <input type="hidden" id="language-${invoice.invoiceID}" name="language" value="en">
                                                            <button type="submit" class="btn btn-success me-2 payment-btn" data-id="${invoice.invoiceID}" ${invoice.status == 'paid' ? 'disabled' : ''}>Bank</button>
                                                        </form>
                                                        <form action="Invoice/PayCash" method="post"  onsubmit="return confirm('Confirm cash payment for Invoice #${invoice.invoiceNumber}?');">
                                                            <input type="hidden" name="invoiceID" value="${invoice.invoiceID}">
                                                            <input type="hidden" name="warrantyCardID" value="${warrantyCard.warrantyCardID}">
                                                            <button type="submit" class="btn btn-primary mt-2" ${invoice.status == 'paid' ? 'disabled' : ''}>Cash</button>
                                                        </form>
                                                    </td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <p class="text-center">No invoices found for this warranty card.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </main>
                <jsp:include page="../../includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <%-- vnpay --%>
        <script type="text/javascript">
                                                            $(document).ready(function () {
                                                                $(".payment-btn").click(function (e) {
                                                                    e.preventDefault(); // Ngăn chặn hành vi submit mặc định

                                                                    var invoiceID = $(this).data("id"); // Lấy ID từ nút
                                                                    var form = $("#frmCreateOrder-" + invoiceID); // Lấy form tương ứng
                                                                    var postData = form.serialize();
                                                                    var submitUrl = form.attr("action");

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
                                                                            } else {
                                                                                alert(x.Message);
                                                                            }
                                                                        }
                                                                    });
                                                                });
                                                            });
        </script>       
    </body>
</html>