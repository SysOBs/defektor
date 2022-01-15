package pt.uc.sob.defektor.plugins.system.aws;

import com.amazonaws.services.fis.AWSFIS;
import com.amazonaws.services.fis.AWSFISClientBuilder;
import com.amazonaws.services.fis.model.ListActionsRequest;
import com.amazonaws.services.fis.model.ListActionsResult;
import org.json.JSONObject;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfigs;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;

import java.util.ArrayList;
import java.util.List;

public class AWSSystemPlug extends SystemConnectorPlug {

    AWSFIS client;

    public AWSSystemPlug(SystemConfigs configs) {
        super(configs);

        client = AWSFISClientBuilder.defaultClient();
        configure();
    }

    @Override
    public void help() {
        ListActionsResult actionsList = client.listActions(new ListActionsRequest());

        System.out.println(actionsList);
        JSONObject json = new JSONObject(actionsList); // Convert text to object
        System.out.println(json.toString(4));
    }

    @Override
    public void configure() {
    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
                add(TargetType.POD);
                add(TargetType.NODE);
            }
        };
    }



    public static void main(String[] args) {
        new AWSSystemPlug(null).help();
    }
}



