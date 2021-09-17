package pt.uc.sob.defektor.plugins.datacollector.jaeger;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Utils {

    public static Params jsonToObject(String json) {
        return new Gson().fromJson(json, Params.class);
    }

    public static String httpGet(String uri) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        return response.body();

    }

    public static byte[] concatenateByteArrays(byte[] byteArray1, byte[] byteArray2) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        try {
            outputStream.write(byteArray1);
            outputStream.write(byteArray2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray( );
    }
}
