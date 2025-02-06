package Utils;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.client.http.FileContent;
import java.io.IOException;

public class FileUploadToDrive {

    public static void uploadFileToDrive(String filePath, String mimeType, String fileName) throws IOException {
        // Tạo đối tượng Drive service
        Drive driveService = GoogleDriveService.getDriveService();

        // Tạo metadata cho file mới trên Google Drive
        File fileMetadata = new File();
        fileMetadata.setName(fileName); // Đặt tên file trên Google Drive

        // Tạo đối tượng File từ đường dẫn file trên máy chủ
        java.io.File filePathToUpload = new java.io.File(filePath);

        // Tạo đối tượng FileContent với mimeType và file
        FileContent mediaContent = new FileContent(mimeType, filePathToUpload);

        // Upload file lên Google Drive
        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id") // Chỉ lấy ID của file
                .execute();

        // In ra ID của file vừa upload
        System.out.println("File ID: " + uploadedFile.getId());
    }
}
