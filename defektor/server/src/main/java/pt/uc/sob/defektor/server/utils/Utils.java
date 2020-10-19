package pt.uc.sob.defektor.server.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Utils {
    public static List<List<String>> stringListSplitter(List<String> stringList, String splitPattern){
        List<List<String>> finalStringList = new ArrayList<>();
        for(String stringElement : stringList){
            finalStringList.add(Arrays.asList(stringElement.split(splitPattern)));
        }
        return finalStringList;
    }

    public static UUID generateUUID(){
        return UUID.randomUUID();
    }
}
