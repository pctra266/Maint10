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
    Integer maxSizeMB = (Integer) request.getServletContext().getAttribute("maxUploadSizeMB");

    // Nếu maxSizeMB chưa có, đặt giá trị mặc định 5MB
    if (maxSizeMB == null) {
        maxSizeMB = 5; // Giá trị mặc định
        request.getServletContext().setAttribute("maxUploadSizeMB", maxSizeMB);
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


}
