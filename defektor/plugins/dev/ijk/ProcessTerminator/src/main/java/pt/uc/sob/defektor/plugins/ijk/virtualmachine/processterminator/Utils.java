package pt.uc.sob.defektor.plugins.ijk.virtualmachine.processterminator;

import com.google.gson.Gson;

public class Utils {
    public static Param jsonToObject(String json) {
        return new Gson().fromJson(json, Param.class);
    }
}
