package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpabort;

import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;

import java.io.*;

public class Utils {

    public static File stringBuilderToTempFile(StringBuilder stringBuilder, String prefix, String suffix) throws IOException {
        final File tempFile = File.createTempFile(prefix, suffix);
        tempFile.deleteOnExit();
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(tempFile));
            writer.write(stringBuilder.toString());
        } finally {
            if (writer != null) writer.close();
        }
        return tempFile;
    }

    public static StringBuilder changedYAMLManifest(InputStream inputStream, Param param) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            MyReader myReader = new MyReader(new InputStreamReader(inputStream), param);
            String buffer;
            while ((buffer = myReader.readLine()) != null) {
                stringBuilder.append(buffer + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    public static CustomResourceDefinitionContext buildCustomResourceDefinitionContext() {
        return new CustomResourceDefinitionContext
                .Builder()
                .withGroup("networking.istio.io")
                .withKind("VirtualService")
                .withVersion("v1beta1")
                .withScope("Namespaced")
                .withPlural("virtualservices")
                .build();
    }
}
