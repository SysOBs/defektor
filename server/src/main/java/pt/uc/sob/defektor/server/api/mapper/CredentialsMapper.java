package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.SSHCredentialsData;
import pt.uc.sob.defektor.server.model.SSHCredentials;

public class CredentialsMapper {

    public static SSHCredentialsData convertToDAO(SSHCredentials sshCredentials) {
        SSHCredentialsData sshCredentialsData = new SSHCredentialsData();
        sshCredentialsData.setKey(sshCredentials.getKey());
        sshCredentialsData.setUsername(sshCredentials.getUsername());
        return sshCredentialsData;
    }


    public static SSHCredentials convertToDTO(SSHCredentialsData sshCredentialsData) {
        SSHCredentials sshCredentials = new SSHCredentials();
        sshCredentials.setKey(sshCredentialsData.getKey());
        sshCredentials.setUsername(sshCredentialsData.getUsername());
        return sshCredentials;
    }
}
