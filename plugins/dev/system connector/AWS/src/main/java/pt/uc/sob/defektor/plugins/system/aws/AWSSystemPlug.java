package pt.uc.sob.defektor.plugins.system.aws;

import com.amazonaws.services.fis.AWSFIS;
import com.amazonaws.services.fis.AWSFISClientBuilder;
import com.amazonaws.services.fis.model.ExperimentTemplate;
import com.amazonaws.services.fis.model.ListActionsRequest;
import com.amazonaws.services.fis.model.ListActionsResult;
import com.amazonaws.services.fis.model.StartExperimentRequest;
import org.json.JSONObject;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfigs;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AWSSystemPlug extends SystemConnectorPlug {

    AWSFIS client;

    public AWSSystemPlug(SystemConfigs configs) {
        super(configs);
        client = AWSFISClientBuilder.defaultClient();
    }

    @Override
    public void help() {
    }

    @Override
    public void configure() {
    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
            }
        };
    }

    public void startExperiment(String experimentTemplateId, Map<String, String> tags) {

        StartExperimentRequest experimentRequest = new StartExperimentRequest()
                .withExperimentTemplateId(experimentTemplateId)
                .withTags(tags);

        client.startExperiment(experimentRequest);
    }
}



