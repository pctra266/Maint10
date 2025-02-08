/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author ADMIN
 */
public class OtherUtils {
    public static String saveImage(Part imagePart, HttpServletRequest request, String target) throws IOException {
        if (imagePart == null || imagePart.getSize() == 0) {
            return null;
        }

        // Đường dẫn tuyệt đối đến thư mục img/???
        String uploadPath = request.getServletContext().getRealPath("/"+target);
        System.out.println("Upload Path: " + uploadPath); // Kiểm tra đường dẫn
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }

        // Tạo tên file duy nhất
        String originalFileName = imagePart.getSubmittedFileName();
        if (originalFileName == null || originalFileName.isEmpty()) {
            return null; // Trả về null nếu không có tên file
        }
        String fileName = originalFileName;
//    String fileName = System.currentTimeMillis() + "_" + originalFileName;
        String filePath = uploadPath + File.separator + fileName;

        try {
            imagePart.write(filePath); // Ghi file lên server
        } catch (IOException e) {
            e.printStackTrace(); // In ra lỗi nếu có
            return null; // Trả về null nếu có lỗi
        }

        // Trả về đường dẫn tương đối để lưu vào database
        return target + "/" + fileName; // Chỉ cần đường dẫn tương đối
    }
}
