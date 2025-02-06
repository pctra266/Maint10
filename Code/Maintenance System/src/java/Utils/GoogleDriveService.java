package Utils;

import com.google.api.services.drive.Drive;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

public class GoogleDriveService {

    // Đường dẫn đến file JSON của Service Account
    private static final String SERVICE_ACCOUNT_KEY_PATH = "D:\\learning_university\\Term5_SP25\\1_SWP391\\newLife\\newYearNewMe\\ProductsMaintainManagement\\JSonKey\\service-account.json";

    public static Drive getDriveService() throws IOException {
        // Đọc thông tin từ Service Account JSON thông qua FileInputStream
        FileInputStream serviceAccountStream = new FileInputStream(SERVICE_ACCOUNT_KEY_PATH);

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(serviceAccountStream)
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        // Đảm bảo đóng luồng sau khi sử dụng
        serviceAccountStream.close();

        // Tạo Drive service và trả về
        return new Drive.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName("MyDriveApp").build();
    }
}
