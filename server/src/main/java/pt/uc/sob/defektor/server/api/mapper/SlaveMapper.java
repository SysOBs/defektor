package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.SlaveData;
import pt.uc.sob.defektor.server.model.Slave;

public class SlaveMapper {

    public static SlaveData convertToDAO(Slave slave) {
        SlaveData slaveData = new SlaveData();
        slaveData.setId(slave.getId());
        slaveData.setAddress(slave.getAddress());
        slaveData.setPort(slave.getPort());
        slaveData.setCredentials(CredentialsMapper.convertToDAO(slave.getCredentials()));
        return slaveData;
    }

    public static Slave convertToDTO(SlaveData slaveData) {
        Slave slave = new Slave();
        slave.setId(slaveData.getId());
        slave.setAddress(slaveData.getAddress());
        slave.setPort(slaveData.getPort());
        slave.setCredentials(CredentialsMapper.convertToDTO(slaveData.getCredentials()));
        return slave;
    }
}
