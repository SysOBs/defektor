package pt.uc.sob.defektor.server.utils;

import pt.uc.sob.defektor.server.api.data.KeyValueData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class Utils {

    public static <T> boolean isUnique(T t, List<T> tList)  {
        for(T t1 : tList)
            if(t1.hashCode() == t.hashCode())
                return false;
        return true;
    }

    public static void writeStringToFile(String fileName, String content) {
        try {
            File file = new File(fileName);
            file.createNewFile();
            FileOutputStream oFile = new FileOutputStream(file, false);
            oFile.write(content.getBytes(StandardCharsets.UTF_8));
            oFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSystemNameFromClassName(String pluginClassName) {
        return pluginClassName.split(".ijk.")[1].split("\\.")[0];
    }

    public static String envVarsToString(List<KeyValueData> env) {
        StringBuilder returnedString = new StringBuilder();
        for(KeyValueData keyValue : env) {
            returnedString.append(" -e ").append(keyValue.getKey()).append("=").append(keyValue.getValue());
        }
        return returnedString.toString();
    }

    public static UUID generateUUID() {
        return UUID.randomUUID();
    }

    public static class Time {

        public static String getCurrentTimestamp() {
            return new Timestamp(System.currentTimeMillis()).getTime() + "000";
        }
    }
}
