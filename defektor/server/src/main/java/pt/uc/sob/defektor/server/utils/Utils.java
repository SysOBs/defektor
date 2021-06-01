package pt.uc.sob.defektor.server.utils;

import pt.uc.sob.defektor.server.Orchestrator;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {

    public static <T> boolean isUnique(T t, List<T> tList)  {
        for(T t1 : tList)
            if(t1.hashCode() == t.hashCode())
                return false;
        return true;
    }

//    public static List<EnvVar> stringEnvToObject(List<String> stringList){
//        List<EnvVar> envVars = new ArrayList<>();
//
//        for(String stringElement : stringList){
//            String[] splicedString = stringElement.split("=");
//            envVars.add(new EnvVar(splicedString[0], splicedString[1], null));
//        }
//        return envVars;
//    }

    public static String getStringedCurrentDate() {
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();

        return dateFormat.format(today);
    }

    public static String getResourceFileAbsolutePath(String resourceRelativePath) {
        try {
            return Paths.get(Orchestrator.class.getResource(resourceRelativePath).toURI()).toFile().getAbsolutePath();
        } catch (URISyntaxException e) {
            /*
            ADD LOGGING
             */
            return null;
        }
    }

    public static String getSystemNameFromClassName(String pluginClassName) {
        return pluginClassName.split(".ijk.")[1].split("\\.")[0];
    }
}
