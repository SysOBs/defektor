package pt.uc.sob.defektor.server.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.fabric8.kubernetes.api.model.EnvVar;
import pt.uc.sob.defektor.server.Orchestrator;
import pt.uc.sob.defektor.server.model.Plan;
import pt.uc.sob.defektor.server.model.Slave;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class Utils {

    public static class PlanUtils {
        public static boolean isDuplicate(List<Plan> planList, Plan plan) {
            boolean found = false;
            for(Plan itPlan : planList) {
                if(itPlan.getId().equals(plan.getId())) {
                    found = true;
                    break;
                }
            }
            return found;
        }
    }

    public static class SlaveUtils {
        public static boolean isDuplicate(List<Slave> slaveList, Slave slave) {
            boolean found = false;
            for(Slave itSlave : slaveList) {
                if(itSlave.getId().equals(slave.getId())) {
                    found = true;
                    break;
                }
            }
            return found;
        }
    }

    public static class Json {
        public static <T> List<T> readJsonFromFile(String fileDir, Class<T[]> tClass) throws IOException {
            File f = new File(fileDir);
            if(f.exists() && !f.isDirectory() && f.length() != 0) {
                String jsonString = new String(Files.readAllBytes(Paths.get(fileDir)));
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                return new ArrayList<>(Arrays.asList(gson.fromJson(jsonString, tClass)));
            }
            else
                return new ArrayList<>();
        }

        public static <T> void writeJsonToFile(List<T> objectList, String fileDir) throws IOException {
            try (Writer writer = new FileWriter(fileDir)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(objectList, writer);
            }
        }
    }


    public static List<EnvVar> stringEnvToObject(List<String> stringList){
        List<EnvVar> envVars = new ArrayList<>();

        for(String stringElement : stringList){
            String[] splicedString = stringElement.split("=");
            envVars.add(new EnvVar(splicedString[0], splicedString[1], null));
        }
        return envVars;
    }



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



}
