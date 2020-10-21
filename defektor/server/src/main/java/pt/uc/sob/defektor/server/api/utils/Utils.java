package pt.uc.sob.defektor.server.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pt.uc.sob.defektor.server.model.Plan;
import pt.uc.sob.defektor.server.pojos.loadgen.Env;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

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

    public static List<List<String>> stringListSplitter(List<String> stringList, String splitPattern){
        List<List<String>> finalStringList = new ArrayList<>();
        for(String stringElement : stringList){
            finalStringList.add(Arrays.asList(stringElement.split(splitPattern)));
        }
        return finalStringList;
    }

    public static boolean isPlanUnique(List<Plan> planList, Plan plan) {
        for(Plan auxPlan : planList){
            if(auxPlan.getId().equals(plan.getId())){
                return false;
            }
        }
        return true;
    }

}
