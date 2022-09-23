package pt.uc.sob.defektor.plugins.ijk.aws.stopec2instance;

import com.google.gson.Gson;

public class Utils {
    public static Parameters jsonToObject(String json) {
        return new Gson().fromJson(json, Parameters.class);
    }
}
