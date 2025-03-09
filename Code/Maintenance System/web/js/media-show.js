// media-show.js
var zoomLevel = 1;
var minZoom = 0.5;
var maxZoom = 3;
var isDragging = false;
var startX, startY, translateX = 0, translateY = 0;
var mediaList = [];
var currentIndex = 0;

function initMediaList(images, videos) {
    mediaList = [];
    images.forEach(image => mediaList.push({ src: image, type: "image" }));
    videos.forEach(video => mediaList.push({ src: video, type: "video" }));
}

function showModal(src, type) {
    var modal = document.getElementById('mediaModal');
    var modalContent = document.getElementById('modalContent');
    modalContent.innerHTML = '';
    zoomLevel = 1;
    translateX = 0;
    translateY = 0;

    currentIndex = mediaList.findIndex(item => item.src === src);

    if (currentIndex === -1 || mediaList.length === 0) {
        console.error("Media not found or mediaList is empty");
        return;
    }
    displayMedia(currentIndex);

    modal.style.display = 'flex';
}

function displayMedia(index) {
    if (index < 0 || index >= mediaList.length) {
        console.error("Invalid index:", index);
        return;
    }

    var modalContent = document.getElementById('modalContent');
    modalContent.innerHTML = '';
    var mediaElement;

    if (mediaList[index].type === 'image') {
        mediaElement = document.createElement('img');
        mediaElement.src = mediaList[index].src;
        mediaElement.draggable = false;
    } else if (mediaList[index].type === 'video') {
        mediaElement = document.createElement('video');
        mediaElement.src = mediaList[index].src;
        mediaElement.controls = true;
        mediaElement.draggable = false;
        mediaElement.play();
    } else {
        console.error("Unknown media type at index:", index);
        return;
    }
    modalContent.appendChild(mediaElement);

    modalContent.onwheel = function (e) {
        e.preventDefault();
        var delta = e.deltaY > 0 ? -0.1 : 0.1;
        zoomLevel += delta;
        zoomLevel = Math.min(Math.max(zoomLevel, minZoom), maxZoom);
        applyTransform(mediaElement);
    };

    mediaElement.onmousedown = function (e) {
        e.preventDefault();
        if (zoomLevel > 0) {
            isDragging = true;
            startX = e.clientX - translateX;
            startY = e.clientY - translateY;
            mediaElement.style.cursor = 'grabbing';
        }
    };

    document.onmousemove = function (e) {
        if (isDragging) {
            translateX = e.clientX - startX;
            translateY = e.clientY - startY;
            applyTransform(mediaElement);
        }
    };

    document.onmouseup = function () {
        isDragging = false;
        mediaElement.style.cursor = 'grab';
    };

    mediaElement.ondragstart = function (e) {
        e.preventDefault();
        return false;
    };
}

function applyTransform(element) {
    if (zoomLevel !== 1 || translateX !== 0 || translateY !== 0) {
        element.style.transform = 'translate(-50%, -50%) translate(' + translateX + 'px, ' + translateY + 'px) scale(' + zoomLevel + ')';
    } else {
        element.style.transform = 'translate(-50%, -50%)';
    }
}

function hideModal() {
    var modal = document.getElementById('mediaModal');
    var modalContent = document.getElementById('modalContent');
    var video = modalContent.getElementsByTagName('video')[0];
    if (video) video.pause();
    modal.style.display = 'none';
    zoomLevel = 1;
    translateX = 0;
    translateY = 0;
    var mediaElement = modalContent.querySelector('img') || modalContent.querySelector('video');
    if (mediaElement) {
        mediaElement.style.transform = 'translate(-50%, -50%)';
    }
}

function showNext() {
    if (mediaList.length > 0) {
        currentIndex = (currentIndex + 1) % mediaList.length;
        displayMedia(currentIndex);
    }
}

function showPrevious() {
    if (mediaList.length > 0) {
        currentIndex = (currentIndex - 1 + mediaList.length) % mediaList.length;
        displayMedia(currentIndex);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('mediaModal').onclick = function (e) {
        if (e.target === this) hideModal();
    };
});