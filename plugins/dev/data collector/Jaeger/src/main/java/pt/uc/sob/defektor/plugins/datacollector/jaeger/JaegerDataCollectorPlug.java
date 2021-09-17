package pt.uc.sob.defektor.plugins.datacollector.jaeger;

import org.json.JSONObject;
import pt.uc.sob.defektor.common.DataCollectorPlug;
import pt.uc.sob.defektor.common.com.collectorparams.DataCollectorParams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JaegerDataCollectorPlug extends DataCollectorPlug {

    private Params serializedParams;

    public JaegerDataCollectorPlug(DataCollectorParams params) {
        super(params);
    }

    @Override
    public void help() {

    }

    @Override
    public void configure() {
        serializedParams = Utils.jsonToObject(this.params.getJsonDataCollectorParams().toString());
    }

    @Override
    public byte[] getData() {
        configure();
        String URI;
        StringBuilder content = new StringBuilder();
        String returnedString;

        List<String> servicesList = getServiceList();

        for (String service : servicesList) {
            URI = serializedParams.getHost() +
                    "?start=" + serializedParams.getStartTimestamp() +
                    "&end=" + serializedParams.getEndTimestamp() +
                    "&service=" + service;

            System.out.println(URI);

            String serviceJsonString = fetchServiceJson(servicesList, service, URI);
            System.out.println(service);
            System.out.println(serviceJsonString);
            if(serviceJsonString != null) {
                content.append(serviceJsonString);
            }
        }
        returnedString = "[" + content + "]";
        return returnedString.getBytes(StandardCharsets.UTF_8);
    }

    private List<String> getServiceList() {
        List<String> serviceList = new ArrayList<>();
        String URI;
        URI = serializedParams.getHost().split("traces")[0] + "services";
        try {
            String fetchedJSON = Utils.httpGet(URI);
            JSONObject jsonObject = new JSONObject(fetchedJSON);
            var jsonArray = jsonObject.getJSONArray("data");
            for(int i=0; i< jsonArray.length(); i++){
                serviceList.add(jsonArray.getString(i));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return serviceList;
    }

    private String fetchServiceJson(List<String> servicesList, String service, String URI) {
        String fetchedJSON = null;
        try {
            fetchedJSON = Utils.httpGet(URI);
            if (servicesList.indexOf(service) != (servicesList.size() - 1)) {
                fetchedJSON += ",";
            }
            JSONObject jsonObject = new JSONObject(fetchedJSON);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return fetchedJSON;
    }
}
