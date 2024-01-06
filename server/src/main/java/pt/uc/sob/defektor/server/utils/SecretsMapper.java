package pt.uc.sob.defektor.server.utils;

import lombok.extern.log4j.Log4j2;
import pt.uc.sob.defektor.common.config.InjektorParams;
import pt.uc.sob.defektor.server.api.data.PlanData;
import pt.uc.sob.defektor.server.api.data.PlanSecretsData;

import java.util.Objects;

@Log4j2
public class SecretsMapper {

    private PlanData plan;
    private PlanSecretsData secrets;


    public SecretsMapper(PlanData plan, PlanSecretsData secrets){
        this.plan = plan;
        this.secrets = secrets;
    }


    //not sure if this is also necessary for injections
    //need to validate if this handles errors well
    public PlanData map(){
       var injektors = plan.getInjektors();

       injektors.forEach(e -> {
           var params = e.getParameters().getJsonConfig();
           var fields = e.getParameters().getJsonConfig().keySet();
           fields.forEach(f -> {
               try {
                   var value = params.getString(f);
                   if(value.startsWith("secret::")){
                       var str = value.split("::", 2)[1]; // limit 2 in case the secret itself contains '::'
                       if(secrets.getSecrets().containsKey(str)){
                           params.remove(f);
                           params.put(f, secrets.getSecrets().get(str));
                       }
                   }
               }catch (Exception ex){
                   // it is not a string...
               }
           });
           e.setParameters(new InjektorParams(params));

       });

       plan.setInjektors(injektors);
       return plan;
    }


}
