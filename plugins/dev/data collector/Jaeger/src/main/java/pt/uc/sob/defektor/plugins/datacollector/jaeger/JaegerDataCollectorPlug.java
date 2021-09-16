package pt.uc.sob.defektor.plugins.datacollector.jaeger;

import com.fasterxml.jackson.databind.ObjectMapper;
import pt.uc.sob.defektor.common.DataCollectorPlug;
import pt.uc.sob.defektor.common.com.collectorparams.DataCollectorParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

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
        String URI = serializedParams.getHost() +
                "?start=" + serializedParams.getStartTimestamp() +
                "&end=" + serializedParams.getEndTimestamp() +
                "&service=" + serializedParams.getService();

        byte[] byteArray = null;
        try {
            byteArray = Utils.httpGet(URI).getBytes(StandardCharsets.UTF_8);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return byteArray;
    }
}
