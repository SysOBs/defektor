package pt.uc.sob.defektor.server.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fabric8.kubernetes.api.model.EnvVar;
import io.kubernetes.client.proto.V1;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pt.uc.sob.defektor.server.model.Plan;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class Utils {

    public static List<Plan> serializePlansFromFile(String fileName) {
        List<Plan> planList = new ArrayList<>();
        File planFile = new File(fileName);

        if (planFile.exists() && !planFile.isDirectory()) {
            try {
                planList = new ArrayList<>(
                        Arrays.asList(
                                new ObjectMapper().readValue(planFile, Plan[].class)
                        )
                );
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return planList;
    }

    public static List<Plan> writePlanListInFile(List<Plan> planList, String fileName){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(fileName) , planList);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return planList;
    }

    public static List<EnvVar> stringEnvToObject(List<String> stringList){
        List<EnvVar> envVars = new ArrayList<>();

        for(String stringElement : stringList){
            String[] splicedString = stringElement.split("=");
            envVars.add(new EnvVar(splicedString[0], splicedString[1], null));
        }
        return envVars;
    }

    public static boolean isPlanUnique(List<Plan> planList, Plan plan) {
        for(Plan auxPlan : planList){
            if(auxPlan.getId().equals(plan.getId())){
                return false;
            }
        }
        return true;
    }

    public static String getStringedCurrentDate() {
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();

        return dateFormat.format(today);
    }

}
