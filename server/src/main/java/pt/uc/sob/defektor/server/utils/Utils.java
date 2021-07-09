package pt.uc.sob.defektor.server.utils;

import pt.uc.sob.defektor.server.api.data.KeyValueData;

import java.util.List;
import java.util.UUID;

public class Utils {

    public static <T> boolean isUnique(T t, List<T> tList)  {
        for(T t1 : tList)
            if(t1.hashCode() == t.hashCode())
                return false;
        return true;
    }


    public static String getSystemNameFromClassName(String pluginClassName) {
        return pluginClassName.split(".ijk.")[1].split("\\.")[0];
    }

    public static String envVarsToString(List<KeyValueData> env) {
        String returnedString = "";
        for(KeyValueData keyValue : env) {
            returnedString += " -e " + keyValue.getKey() + "=" + keyValue.getValue();
        }
        return returnedString;
    }

    public static UUID generateUUID() {
        return UUID.randomUUID();
    }
}
