package pt.uc.sob.defektor.server.campaign.workloadgen;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.data.DockerImageData;
import pt.uc.sob.defektor.server.api.data.KeyValueData;
import pt.uc.sob.defektor.server.api.data.SlaveData;
import pt.uc.sob.defektor.server.api.data.WorkLoadData;
import pt.uc.sob.defektor.server.campaign.exception.CampaignException;
import pt.uc.sob.defektor.server.api.service.SlaveService;
import pt.uc.sob.defektor.server.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkloadGenerator {

    private final SlaveService slaveService;

    public void performWorkloadGen(WorkLoadData workload, UUID campaignID) throws CampaignException {
        List<SlaveData> slavesList = slaveService.slavesList();
        Integer desiredNumberOfSlaves = workload.getSlaves();

        List<SlaveData> slaveMachineReplicas = slavesList
                .stream()
                .limit(desiredNumberOfSlaves)
                .collect(Collectors.toList());

        if(slaveMachineReplicas.size() != desiredNumberOfSlaves)
            throw new CampaignException("not enough slave machines available");

        for (SlaveData slaveData : slaveMachineReplicas) {
            List<String> commands = new ArrayList<>();
            List<KeyValueData> env = workload.getEnv();
            DockerImageData image = workload.getImage();

            final String DOCKER_RUN_COMMAND = buildDockerRunCommand(workload, campaignID, env, image);
            commands.add(DOCKER_RUN_COMMAND);

            SSHConnectionManager sshConnectionManager = new SSHConnectionManager(
                    slaveData.getAddress(),
                    slaveData.getCredentials().getUsername(),
                    slaveData.getCredentials().getKey(),
                    slaveData.getPort());
            sshConnectionManager.executeCommands(commands);
            sshConnectionManager.close();
        }
    }

    public void stopWorkloadGen(UUID campaignID) throws CampaignException {
        List<String> commands = new ArrayList<>();
        List<SlaveData> slavesList = slaveService.slavesList();

        for (SlaveData slaveData : slavesList) {
            commands.add("docker rm --force workload_gen_" + campaignID);

            SSHConnectionManager sshConnectionManager = new SSHConnectionManager(
                    slaveData.getAddress(),
                    slaveData.getCredentials().getUsername(),
                    slaveData.getCredentials().getKey(),
                    slaveData.getPort());
            sshConnectionManager.executeCommands(commands);
            sshConnectionManager.close();
        }
    }

    @NotNull
    private String buildDockerRunCommand(WorkLoadData workload, UUID campaignID, List<KeyValueData> env, DockerImageData image) {
        final String IMAGE = " " + image.getUser() + "/" + image.getName() + ":" + image.getTag();
        final String ENV_VARS = Utils.envVarsToString(env);
        final String COMMAND = " " + workload.getCmd();
        final String DOCKER_NAME = " --name workload_gen_" + campaignID;
        final String OPTIONS = " -d -t -i" + DOCKER_NAME + ENV_VARS ;

        return "docker run" + OPTIONS + IMAGE + COMMAND;
    }
}
