package pt.uc.sob.defektor.plugins.system.virtualmachine;


import com.google.gson.Gson;

public class Utils {

    public static Configs jsonToObject(String json) {
        return new Gson().fromJson(json, Configs.class);
    }
}
