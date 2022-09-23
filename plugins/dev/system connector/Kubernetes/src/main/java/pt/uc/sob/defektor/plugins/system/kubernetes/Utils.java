package pt.uc.sob.defektor.plugins.system.kubernetes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtil;


public class Utils {

    private static final String FILE_PREFIX = "k8_manifest";
    private static final String FILE_SUFFIX = ".tmp";

    public static File inputStreamToTempFile(InputStream yamlFileStream) {
        File tempFile = createTempFile();
        tempFile.deleteOnExit();

        copyInputStreamToFile(yamlFileStream, tempFile);
        return tempFile;
    }

    private static void copyInputStreamToFile(InputStream yamlFileStream, File tempFile) {
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtil.copy(yamlFileStream, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File createTempFile() {
        File file;
        try {
            file = File.createTempFile(FILE_PREFIX, FILE_SUFFIX);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
