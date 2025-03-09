<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="count" value="${fn:length(images)+fn:length(videos)}"/>
<div class="row">
    <c:forEach var="image" items="${images}">
        <div class="media-item-show col-md-${count<3?12/count:4}">
            <img src="${pageContext.request.contextPath}/${image}" alt="Warranty Image" onclick="showModal('${image}', 'image')">        </div>
        </c:forEach>
        <c:forEach var="video" items="${videos}">
        <div class="media-item-show col-md-${count<3?12/count:4}">
            <video src="${pageContext.request.contextPath}/${video}" controls onclick="showModal('${video}', 'video')"></video>        </div>
            </c:forEach>
</div>
<!-- Modal for Zoom -->
<div id="mediaModal" class="modal">
    <span class="modal-close" onclick="hideModal()">×</span>
    <div id="modalContent" class="modal-content"></div>
    <button type="button"  id="prevButton" class="modal-nav prev" onclick="showPrevious()"><</button>
    <button type="button" id="nextButton" class="modal-nav next" onclick="showNext()">></button>
</div>
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
</script> 