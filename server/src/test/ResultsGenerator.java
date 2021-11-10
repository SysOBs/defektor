import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.Test;
import pt.uc.sob.defektor.server.model.Plan;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ResultsGenerator {

    @Test
    public void generateDataHttpAbort() throws IOException {

        extracted(5, 1);
        extracted(5, 1);
        extracted(5, 1);
        extracted(5, 1);
        extracted(5, 1);
        extracted(5, 1);
//
//        extracted(10, 1);
//        extracted(10, 1);
//        extracted(10, 1);
//
//        extracted(15, 1);
//        extracted(15, 1);
//        extracted(15, 1);
    }

    private void extracted(int duration, int numClients) throws IOException {
        String filePath = "/home/goncalo/Desktop/defektor/locust/plans/http-abort/manifest_";
        getHttpURLConnection(filePath + "25p", duration);
        getHttpURLConnection(filePath + "50p", duration);
        getHttpURLConnection(filePath + "75p", duration);
        getHttpURLConnection(filePath + "100p", duration);
    }

    private void getHttpURLConnection(String fileName, int duration) throws IOException {
        final Type PLAN_TYPE = new TypeToken<Plan>() {
        }.getType();
        Gson gson = new Gson();
        URL url = new URL("http://localhost:8080/defektor-api/1.0.0/plan");
        JsonReader jsonReader;
        Plan plan;
        HttpURLConnection con;
        jsonReader = new JsonReader(new FileReader(fileName));
        plan = gson.fromJson(jsonReader, PLAN_TYPE);

        duration = duration * 60;
        plan.getInjektions().get(0).getWorkLoad().setDuration(duration);

        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = new Gson().toJson(plan).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response);
        }
    }

}
