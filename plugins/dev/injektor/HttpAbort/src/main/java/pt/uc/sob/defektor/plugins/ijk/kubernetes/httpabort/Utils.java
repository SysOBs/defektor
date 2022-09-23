package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpabort;

import com.google.gson.Gson;

import java.io.*;

public class Utils {

    public static class Strings {
        public static final String PREFIX = "http-abort";
        public static final String SUFFIX = ".yaml";
        public static final String MANIFEST_NAME = "virtual-service-http-abort.yaml";
    }

    public static class JSON {
        public static Params jsonToObject(String json) {
            return new Gson().fromJson(json, Params.class);
        }
    }

    public static File stringBuilderToTempFile(StringBuilder stringBuilder, String prefix, String suffix) throws IOException {
        final File tempFile = File.createTempFile(prefix, suffix);
        tempFile.deleteOnExit();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(stringBuilder.toString());
        }
        return tempFile;
    }

    public static StringBuilder changedYAMLManifest(InputStream inputStream, Params param) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            MyReader myReader = new MyReader(new InputStreamReader(inputStream), param);
            String buffer;
            while ((buffer = myReader.readLine()) != null) {
                stringBuilder
                        .append(buffer)
                        .append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }
}
