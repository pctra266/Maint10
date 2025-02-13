/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class OtherUtils {
   public static String saveImage(Part imagePart, HttpServletRequest request, String target) throws IOException {
    if (imagePart == null || imagePart.getSize() == 0) {
        return null; // Không có file nào được tải lên
    }
    
     // 🔥 Lấy giá trị maxSize từ ServletContext
    Integer maxSizeMB = (Integer) request.getServletContext().getAttribute("maxUploadSizeImageMB");

    // Nếu maxSizeMB chưa có, đặt giá trị mặc định 5MB
    if (maxSizeMB == null) {
        maxSizeMB = 5; // Giá trị mặc định
        request.getServletContext().setAttribute("maxUploadSizeImageMB", maxSizeMB);
    }

    System.out.println("maxSizeMB hiện tại là: " + maxSizeMB);

    // Chuyển đổi sang byte để kiểm tra
    long maxSizeBytes = maxSizeMB * 1024L * 1024L;

    if (imagePart.getSize() > maxSizeBytes) {
        return "File is too large. Max size: " + maxSizeMB + "MB";
    }


    // Kiểm tra MIME type (có thể bị giả mạo)
    String mimeType = imagePart.getContentType();
    if (mimeType == null || !mimeType.startsWith("image/")) {
        return "Invalid picture"; // File không phải là hình ảnh
    }

    // Kiểm tra phần mở rộng file
    String originalFileName = imagePart.getSubmittedFileName();
    if (originalFileName == null || originalFileName.isEmpty()) {
        return "Invalid picture"; // Tên file không hợp lệ
    }

    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
    List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp");

    if (!allowedExtensions.contains(fileExtension)) {
        return "Invalid picture"; // Định dạng file không hợp lệ
    }

    // Lưu tạm file để kiểm tra magic number
    File tempFile = File.createTempFile("upload_", "." + fileExtension);
    try (InputStream input = imagePart.getInputStream();
         FileOutputStream output = new FileOutputStream(tempFile)) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    // Kiểm tra magic number
    if (!isValidImage(tempFile)) {
        tempFile.delete(); // Xóa file tạm nếu không hợp lệ
        return "Invalid picture"; // File không phải là ảnh thật
    }

    // Đường dẫn lưu file thật
    String uploadPath = request.getServletContext().getRealPath("/" + target);
    File uploadDir = new File(uploadPath);
    if (!uploadDir.exists()) {
        uploadDir.mkdirs();
    }

    // Tạo tên file duy nhất
    String fileName = System.currentTimeMillis() + "_" + originalFileName;
    String filePath = uploadPath + File.separator + fileName;

    // Lưu file lên server
    imagePart.write(filePath);
    
    // Xóa file tạm sau khi xác minh xong
    tempFile.delete();

    return target + "/" + fileName; // Trả về đường dẫn tương đối
}
   
   public static boolean isValidImage(File file) throws IOException {
    try (FileInputStream fis = new FileInputStream(file)) {
        byte[] header = new byte[4];
        fis.read(header);
        String hex = bytesToHex(header);

        // Kiểm tra xem file có phải là ảnh thực sự không
        return hex.startsWith("FFD8") ||  // JPEG
               hex.startsWith("89504E47") ||  // PNG
               hex.startsWith("47494638") ||  // GIF
               hex.startsWith("424D");  // BMP
    }
}

// Chuyển đổi bytes sang chuỗi hex để kiểm tra magic number
private static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
        sb.append(String.format("%02X", b));
    }
    return sb.toString();
}
//save video ----------------------------------------------------------------------------------------------------------------
public static String saveVideo(Part videoPart, HttpServletRequest request, String target) throws IOException {
    if (videoPart == null || videoPart.getSize() == 0) {
        return null; // Không có file nào được tải lên
    }

    // 🔥 Lấy giá trị maxSize từ ServletContext
    Integer maxSizeMB = (Integer) request.getServletContext().getAttribute("maxUploadSizeVideoMB");
    if (maxSizeMB == null) {
        maxSizeMB = 50; // Giá trị mặc định 50MB
        request.getServletContext().setAttribute("maxUploadSizeVideoMB", maxSizeMB);
    }

    long maxSizeBytes = maxSizeMB * 1024L * 1024L;
    if (videoPart.getSize() > maxSizeBytes) {
        return "File is too large. Max size: " + maxSizeMB + "MB";
    }

    // Kiểm tra MIME type (có thể bị giả mạo)
    String mimeType = videoPart.getContentType();
    if (mimeType == null || !mimeType.startsWith("video/")) {
        System.out.println("khong phai video");
        return "Invalid video";
    }

    // Kiểm tra phần mở rộng file
    String originalFileName = videoPart.getSubmittedFileName();
    if (originalFileName == null || originalFileName.isEmpty()) {
        System.out.println("rat hay haha");
        return "Invalid video";
    }

    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
    List<String> allowedExtensions = Arrays.asList("mp4", "avi", "mov", "wmv", "flv", "mkv", "webm");
    if (!allowedExtensions.contains(fileExtension)) {
        System.out.println("in valid 3");
        return "Invalid video";
    }

    // Lưu tạm file để kiểm tra magic number
    File tempFile = File.createTempFile("upload_video_", "." + fileExtension);
    try (InputStream input = videoPart.getInputStream();
         FileOutputStream output = new FileOutputStream(tempFile)) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    // Kiểm tra magic number
    if (!isValidVideo(tempFile)) {
        tempFile.delete();
        return "Invalid video";
    }

    // Đường dẫn lưu file thật
    String uploadPath = request.getServletContext().getRealPath("/" + target);
    File uploadDir = new File(uploadPath);
    if (!uploadDir.exists()) {
        uploadDir.mkdirs();
    }

    // Tạo tên file duy nhất
    String fileName = System.currentTimeMillis() + "_" + originalFileName;
    String filePath = uploadPath + File.separator + fileName;

    // Lưu file lên server
    videoPart.write(filePath);
    tempFile.delete();

    return target + "/" + fileName; // Trả về đường dẫn tương đối
}

public static boolean isValidVideo(File file) throws IOException {
    try (FileInputStream fis = new FileInputStream(file)) {
        byte[] header = new byte[8];
        fis.read(header);
        String hex = bytesToHex(header);
        System.out.println("File Header: " + bytesToHex(header));
        // Kiểm tra magic number của các định dạng video phổ biến
        return hex.startsWith("000001BA") ||  // MPEG-2
               hex.startsWith("000001B3") ||  // MPEG-1
               hex.startsWith("1A45DFA3") ||  // MKV (Matroska)
               hex.substring(8).startsWith("66747970") ||  // MP4
               hex.startsWith("3026B275") ||  // WMV
               hex.startsWith("52494646");    // AVI
    }
}


}
