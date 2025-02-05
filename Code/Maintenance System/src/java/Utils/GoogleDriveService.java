//package Utils;
//
//import com.google.api.client.http.FileContent;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.DriveScopes;
//import com.google.api.services.drive.model.File;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.Collections;
//
//public class GoogleDriveService {
//    private static final String SERVICE_ACCOUNT_JSON = "C:/path/to/your/service-account.json";
//    
//    public static Drive getDriveService() throws IOException {
//        HttpTransport httpTransport = new NetHttpTransport();
//        JsonFactory jsonFactory = new JacksonFactory();
//        
//        GoogleCredential credential = GoogleCredential
//                .fromStream(new java.io.FileInputStream(SERVICE_ACCOUNT_JSON))
//                .createScoped(Collections.singleton(DriveScopes.DRIVE));
//
//        return new Drive.Builder(httpTransport, jsonFactory, credential)
//                .setApplicationName("GoogleDriveUpload")
//                .build();
//    }
//
//    public static String uploadFile(String filePath, String mimeType) throws IOException {
//        Drive driveService = getDriveService();
//        
//        File fileMetadata = new File();
//        fileMetadata.setName(Paths.get(filePath).getFileName().toString());
//
//        java.io.File file = new java.io.File(filePath);
//        FileContent mediaContent = new FileContent(mimeType, file);
//
//        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
//                .setFields("id")
//                .execute();
//
//        return uploadedFile.getId();
//    }
//}
