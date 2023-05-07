package pt.uc.sob.defektor.server.pluginization;

import io.swagger.models.auth.In;
import pt.uc.sob.defektor.common.data.FaultType;
import pt.uc.sob.defektor.common.data.target_types.TargetType;
import pt.uc.sob.defektor.common.plugin.abstraction.InjektorPlug;

import java.util.*;

public class PluginRegistry {
    private final Map<String, Map<String, InjektorPlug>> injektors;

    public PluginRegistry(){
        injektors = new HashMap<>();
    }

    public void RegisterInjektor(InjektorPlug ijk){
        List<FaultType> types = ijk.getFaultTypes();
        for (FaultType type : types) {
            var name = type.getName();
            if (!injektors.containsKey(name)) {
                injektors.put(name, new HashMap<>());
            }

            putInjektor(ijk, name);
        }
    }

    public InjektorPlug getInjektorByName(String faultType, String name){
        if (!injektors.containsKey(faultType)) return null;

        var ijks = injektors.get(faultType);

        if(ijks.containsKey(name)) return ijks.get(name);
        return null;
    }

    private boolean filterBy(InjektorPlug ijk, String target){
        {
            return ijk  .getTargetTypes().stream().anyMatch(e -> Objects.equals(e.getName(), target));
        }
    }

    public InjektorPlug getInjektorByFaultTypeAndTarget(String faultType, String target){
        if (!injektors.containsKey(faultType)) return null;
        var ijks = getFaultTypeIjks(faultType);
        try{
            // For each of the Injektors with this fault type, verify if any of them supports this target type
            // If so return it
            var filteredIjks = ijks.stream().filter(o -> filterBy(o, target) ).toArray();
            var asIjks = (InjektorPlug[])filteredIjks;
            if(filteredIjks.length >= 1){
                return asIjks[0];
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }

    private ArrayList<InjektorPlug> getFaultTypeIjks(String type){
        var maps = injektors.get(type);
        var ijks = new ArrayList<InjektorPlug>();

        maps.forEach((n, ijk) ->{
            ijks.add(ijk);
        });

        return ijks;
    }

    private boolean putInjektor(InjektorPlug i, String type){
        if(!injektors.containsKey(type)) return false;
        injektors.get(type).put(i.getName(), i);
        return true;
    }

    public int count(){
        var sum = 0;
        for (String key : injektors.keySet()) {
            sum += (long) injektors.get(key).keySet().size();
        }
        return sum;
    }

}
