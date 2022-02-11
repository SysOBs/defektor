package pt.uc.sob.defektor.plugins.ijk.aws.stopec2instance;

import com.google.gson.Gson;

import java.util.Map;

public class Utils {
    public static Parameters mapToObject(Map<String, String> parameters) {
        return Parameters.builder()
                .experimentTemplateId(parameters.get("experimentTemplateId"))
                .tags(parameters.get("tags"))

    }
}
