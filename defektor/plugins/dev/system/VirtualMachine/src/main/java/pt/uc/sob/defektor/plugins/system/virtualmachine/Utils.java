package pt.uc.sob.defektor.plugins.system.virtualmachine;


import com.google.gson.Gson;

public class Utils {

    public static Config jsonToObject(String json) {
        return new Gson().fromJson(json, Config.class);
    }
}
