package pt.uc.sob.defektor.plugins.system.kubernetes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtil;


public class Utils {

    public static File inputStreamToTempFile(InputStream yamlFileStream) throws IOException {
        final String PREFIX = "stream2file";
        final String SUFFIX = ".tmp";

        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtil.copy(yamlFileStream, out);
        }
        return tempFile;
    }
}
