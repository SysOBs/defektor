package pt.uc.sob.defektor.plugins.system.aws;

import com.amazonaws.services.fis.AWSFIS;
import com.amazonaws.services.fis.AWSFISClientBuilder;
import com.amazonaws.services.fis.model.StartExperimentRequest;
import pt.uc.sob.defektor.common.com.data.target_types.AwsTargetType;
import pt.uc.sob.defektor.common.com.data.target_types.TargetType;
import pt.uc.sob.defektor.common.plugin_interface.SystemConnectorPlug;

import java.util.List;
import java.util.Map;


public class AWSSystemPlug extends SystemConnectorPlug {

    AWSFIS client;

    public AWSSystemPlug(Map<String, String> configuration) {
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

//    public static void main(String[] args) {
//        System.out.println(new AWSSystemPlug(null).getTargetTypes());
//    }
}



