package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpdelay;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class Utils {

    public static File streamToTempFile(InputStream in, String prefix, String suffix) throws IOException {
        final File tempFile = File.createTempFile(prefix, suffix);
        //TODO MUST UNCOMMENT
//        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }

    public static void changeYAMLManifest(File file, Param param) {
        try {
            InputStream inputStream = new FileInputStream(file);
            MyReader myReader = new MyReader(new InputStreamReader(inputStream), param);
            String buffer = null;
            while ((buffer = myReader.readLine()) != null) {

            }

            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
