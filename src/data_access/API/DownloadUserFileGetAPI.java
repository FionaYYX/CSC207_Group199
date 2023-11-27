package data_access.API;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.logging.Level;

public class DownloadUserFileGetAPI extends DropBoxAPI {
    public ArrayList<String> downloadUserFile(String username) {

        ListContentsUserFolderPostAPI user_files = new ListContentsUserFolderPostAPI();
        ArrayList<String> files = user_files.listContentsUserFolder(username);
        ArrayList<String> fileData = new ArrayList<>();
        HttpResponse<String> response;
        for (String file : files) {
            String body = requestBody + String.format("%s/%s\"}", username, file);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://content.dropboxapi.com/2/files/download"))
                    .header("Authorization", "Bearer sl.BqhAJ_VCdKcbrgUdOiGT3q6YhtQxEfZzq_b4gQgOyka1_KjM28NAKNsJPKaxrecuBJRTqKDeuKQBDhadUOPI1iraLnOng8SRfB8BIu40kzZiTQaGy2WRNFfmBvf9IIslpv6UkgwfkYTpE65noY8VZrA")
                    .header("Dropbox-API-Arg", body)
                    .build();
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                fileData.add(response.body());
                logger.log(Level.INFO, "%s file successfully downloaded.", username);
            } catch (IOException | InterruptedException e) {
                logger.log(Level.WARNING, "Unsuccessful connection");
            }
        }
        return fileData;
    }
}
