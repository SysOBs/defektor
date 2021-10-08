package pt.uc.sob.defektor.server.campaign.workloadgen;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.server.api.data.*;
import pt.uc.sob.defektor.server.api.service.SlaveService;
import pt.uc.sob.defektor.server.campaign.run.data.RunStatus;
import pt.uc.sob.defektor.server.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkloadGenerator {

    private static final boolean IS_TEST = true;
    private final SlaveService slaveService;

    public void performWorkloadGen(WorkLoadData workload, UUID campaignID, RunData runData) throws CampaignException {
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

            final String DOCKER_RUN_COMMAND = buildDockerRunCommand(workload, campaignID, env, image, runData.getRunNumber(), runData.getStatus());
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

    public void stopWorkloadGen(UUID campaignID, RunData runData, String faultOccurrence, Integer duration) throws CampaignException {
        List<String> commands = new ArrayList<>();
        List<SlaveData> slavesList = slaveService.slavesList();

        String resultsFolderName = campaignID + ".RUN_" + runData.getRunNumber() + "." + statusToString(runData.getStatus());
        String containerName = "workload_gen_" + resultsFolderName;
        for (SlaveData slaveData : slavesList) {

            //TODO TO REMOVE
            if(IS_TEST) {
                commands.add("docker cp " + containerName + ":/load " + resultsFolderName);
                commands.add("scp -r ~/" + resultsFolderName + " goncalo@192.168.1.1:/home/goncalo/Desktop/defektor/locustResults/" + duration + "_MIN/" + faultOccurrence + "_FO");
                commands.add("rm -rf " + resultsFolderName);
            }
            commands.add("docker stop " + containerName);
            commands.add("docker rm " + containerName);

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
    private String buildDockerRunCommand(WorkLoadData workload, UUID campaignID, List<KeyValueData> env, DockerImageData image, Integer runNumber, RunStatus status) {
        final String IMAGE = " " + image.getUser() + "/" + image.getName() + ":" + image.getTag();
        final String ENV_VARS = Utils.WorkloadGen.envVarsToString(env);
        final String COMMAND = " " + workload.getCmd();
        final String DOCKER_NAME = " --name workload_gen_" + campaignID + ".RUN_" + runNumber + "." + statusToString(status);
        final String OPTIONS = " -d -t -i" + DOCKER_NAME + ENV_VARS ;

        return "docker run" + OPTIONS + IMAGE + COMMAND;
    }

    private String statusToString(RunStatus runStatus) {
        return runStatus == RunStatus.RUNNING_GOLDEN_RUN ? "GOLDEN_RUN" : "FAULT_INJECTION_RUN";
    }
}
