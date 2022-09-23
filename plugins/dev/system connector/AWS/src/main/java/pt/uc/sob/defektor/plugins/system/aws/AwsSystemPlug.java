package pt.uc.sob.defektor.plugins.system.aws;

import com.amazonaws.services.fis.AWSFIS;
import com.amazonaws.services.fis.AWSFISClientBuilder;
import com.amazonaws.services.fis.model.StartExperimentRequest;
import pt.uc.sob.defektor.common.config.SystemConfig;
import pt.uc.sob.defektor.common.data.target_types.AwsTargetType;
import pt.uc.sob.defektor.common.data.target_types.TargetType;
import pt.uc.sob.defektor.common.plugin.abstraction.SystemConnectorPlug;

import java.util.List;
import java.util.Map;

// This class wasn't tested at all. I was trying to build just the abstraction layer to communicate to AWS.
// Feel free to throw it away and start from scratch.
public class AwsSystemPlug extends SystemConnectorPlug {

    private final AWSFIS client;

    public AwsSystemPlug(SystemConfig configuration) {
        super(configuration);
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
        return List.of(AwsTargetType.values());
    }

    public void startExperiment(String experimentTemplateId, Map<String, String> tags) {

        StartExperimentRequest experimentRequest = new StartExperimentRequest()
                .withExperimentTemplateId(experimentTemplateId)
                .withTags(tags);

        client.startExperiment(experimentRequest);
    }
}



