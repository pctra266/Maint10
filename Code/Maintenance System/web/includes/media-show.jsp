<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <c:if test="${not empty component}">
        <button type="button" id="deleteMediaButton" class="btn btn-danger" style="position: absolute; top: 60px; right: 20px;" onclick="deleteCurrentMedia()">
            <i class="fa fa-trash"></i> Delete
        </button>
    </c:if>
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

    function deleteCurrentMedia() {
        if (confirm('Are you sure you want to delete this media?')) {
            const mediaToDelete = mediaList[currentIndex].src;
            fetch('${pageContext.request.contextPath}/ComponentWarehouse/Edit', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'ID=${component.componentID}&deleteMedia=' + encodeURIComponent(mediaToDelete)
            }).then(response => {
                if (response.ok) {
                    // Xóa media kh?i mediaList và reload trang
                    mediaList.splice(currentIndex, 1);
                    if (mediaList.length === 0) {
                        hideModal();
                    } else {
                        currentIndex = Math.min(currentIndex, mediaList.length - 1);
                        displayMedia(currentIndex);
                    }
                    window.location.reload(); // T?i l?i trang ?? c?p nh?t danh sách ?nh/video
                } else {
                    alert('Failed to delete media.');
                }
            }).catch(error => {
                console.error('Error deleting media:', error);
                alert('An error occurred while deleting the media.');
            });
        }
    }
</script>