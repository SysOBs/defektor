package pt.uc.sob.defektor.server.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.data.SlaveData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.mapper.Mapper;
import pt.uc.sob.defektor.server.api.repository.DefektorRepository;
import pt.uc.sob.defektor.server.api.service.SlaveService;
import pt.uc.sob.defektor.server.api.utils.Utils;
import pt.uc.sob.defektor.server.model.Slave;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SlaveServiceImpl implements SlaveService {

    private final DefektorRepository defektorRepository;
    private final String SLAVE_DB_FILE_DIR = Utils.getSlaveFileDB();


    @Override
    public Slave slaveAdd(Slave slave) throws DuplicateEntryException {
        defektorRepository.save(Mapper.convertToDAO(slave), SLAVE_DB_FILE_DIR);
        return slave;
    }

    @Override
    public Slave slaveGet(UUID id) {
        SlaveData slave = (SlaveData) defektorRepository.findById(id, SLAVE_DB_FILE_DIR);

        if(slave == null)
            return null;

        return Mapper.convertToDTO(slave);
    }

    @Override
    public List<Slave> slavesList() {
        return (List<Slave>) defektorRepository.findAll(SLAVE_DB_FILE_DIR).stream()
                .map(Mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void slaveDelete(UUID id) throws NullPointerException {
        SlaveData slave = (SlaveData) defektorRepository.findById(id, SLAVE_DB_FILE_DIR);
        if(slave == null) {
            throw new NullPointerException();
        }
        defektorRepository.delete(slave, SLAVE_DB_FILE_DIR);
    }
}
